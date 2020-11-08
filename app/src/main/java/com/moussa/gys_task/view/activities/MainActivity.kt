package com.moussa.gys_task.view.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.moussa.gys_task.R
import com.moussa.gys_task.di.DaggerAppComponent
import com.moussa.gys_task.model.TrendingModel
import com.moussa.gys_task.utils.ParentClass
import com.moussa.gys_task.view.adapters.TrendingAdapter
import com.moussa.gys_task.viewmodel.TrendingListViewModel
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.error_occure_dialog.*
import javax.inject.Inject


class MainActivity : ParentClass() {
    private val trendingList: MutableList<TrendingModel> = mutableListOf()

    @Inject
    lateinit var trendingListViewModel: TrendingListViewModel
    private val trendingAdapter: TrendingAdapter = TrendingAdapter(trendingList, this)
    private val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    override fun onStart() {
        super.onStart()
        if (!isConnection()) {
            visitor?.show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUI()
        eventGUI()

    }

    private fun initUI() {
        DaggerAppComponent.builder().build()?.inject(this)
        initSkeleton()
        delcareDialog(this)
        trendingListViewModel = ViewModelProviders.of(this).get(TrendingListViewModel::class.java)
        showItems()
    }

    private fun eventGUI() {
        swipeRefresh.setOnRefreshListener {
            trendingListViewModel.refresh()
            swipeRefresh.isRefreshing = false
            observerViewModel()

        }
        ivList.setOnClickListener {
            val popupMenu: PopupMenu = PopupMenu(this, ivList)
            popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.sortByStar ->
                        sortByStar()
                    R.id.sortByName ->
                        sortByName()
                }
                true
            })
            popupMenu.show()
        }

        visitor?.tvRetry?.setOnClickListener {
            trendingListViewModel.refresh()
            initSkeleton()
            visitor?.dismiss()
        }


    }

    private fun initSkeleton() {
        rvTrending.loadSkeleton(R.layout.loading_state) {
            itemCount(3)
                .shimmer(true).cornerRadius(50f)


        }


    }

    private fun showItems() {
        trendingListViewModel.refresh()
        rvTrending.layoutManager = linearLayoutManager
        rvTrending.adapter = trendingAdapter
        observerViewModel()

    }

    private fun observerViewModel() {
        trendingListViewModel.trendingList.observe(this, Observer {
            if (it !== null) {
                rvTrending?.hideSkeleton()
                rvTrending.visibility = View.VISIBLE
                trendingAdapter.updateTrending(it)
            }
        })

        trendingListViewModel.trendingLoadError.observe(this, Observer { isError ->
            if (isError != false) {
                rvTrending.visibility = View.GONE
                initSkeleton()
            } else {
                rvTrending.visibility = View.VISIBLE
                rvTrending.hideSkeleton()
            }


        }
        )

        trendingListViewModel.loading.observe(this, Observer { loading ->
            Log.e("oooTAG", loading.toString())
        })
    }

    private fun sortByName() {
        var listSortByName: MutableList<TrendingModel> = mutableListOf()
        trendingListViewModel.trendingList.observe(this, Observer {
            if (it !== null) {
                listSortByName = it.sortedBy { it.name }.toMutableList()
                rvTrending?.hideSkeleton()
                rvTrending.visibility = View.VISIBLE

                trendingAdapter.updateTrending(listSortByName)
            }

        })

        trendingListViewModel.trendingLoadError.observe(this, Observer { isError ->
            if (isError != false) {
                rvTrending.visibility = View.GONE
                initSkeleton()
            } else {
                rvTrending.visibility = View.VISIBLE
                rvTrending.hideSkeleton()
            }
        }
        )

        trendingListViewModel.loading.observe(this, Observer { loading ->
            Log.e("oooTAG", loading.toString())
        })
    }

    private fun sortByStar() {
        var listSortByStar: MutableList<TrendingModel> = mutableListOf()
        trendingListViewModel.trendingList.observe(this, Observer {
            if (it !== null) {
                listSortByStar = it.sortedBy { it.stars }.toMutableList()
                rvTrending?.hideSkeleton()
                rvTrending.visibility = View.VISIBLE

                trendingAdapter.updateTrending(listSortByStar)
            }

        })

        trendingListViewModel.trendingLoadError.observe(this, Observer { isError ->
            if (isError != false) {
                rvTrending.visibility = View.GONE
                initSkeleton()
            } else {
                rvTrending.visibility = View.VISIBLE
                rvTrending.hideSkeleton()
            }
        }
        )

        trendingListViewModel.loading.observe(this, Observer { loading ->
            Log.e("oooTAG", loading.toString())

        })
    }

}