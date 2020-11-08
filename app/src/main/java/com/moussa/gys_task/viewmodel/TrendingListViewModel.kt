package com.moussa.gys_task.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moussa.gys_task.di.DaggerAppComponent
import com.moussa.gys_task.model.TrendingModel
import com.moussa.gys_task.network.TrendingNetwork
import com.moussa.gys_task.network.Urls
import com.moussa.gys_task.utils.ParentClass
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import javax.inject.Inject

class TrendingListViewModel @Inject constructor(
) : ViewModel() {
    init {
        DaggerAppComponent.builder().build()?.inject(this)
    }

    @Inject
    lateinit var retrofit: Retrofit


    val trendingList: MutableLiveData<MutableList<TrendingModel>> =
        MutableLiveData<MutableList<TrendingModel>>()

    val trendingLoadError: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val loading: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun refresh() {
        fetchCountry()
    }

    private fun fetchCountry() {
        loading.value = true
        compositeDisposable.add(

            retrofit.create(TrendingNetwork::class.java).getTrending(Urls.repository)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MutableList<TrendingModel>>() {
                    override fun onSuccess(t: MutableList<TrendingModel>) {
                        trendingList.value = t
                        loading.value = false
                        trendingLoadError.value = false
                    }

                    override fun onError(e: Throwable) {
                        loading.value = true
                        trendingLoadError.value = false
                        ParentClass.showDialog()
                    }
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()

    }
}