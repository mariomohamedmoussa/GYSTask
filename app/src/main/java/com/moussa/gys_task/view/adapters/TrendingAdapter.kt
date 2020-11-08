package com.moussa.gys_task.view.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moussa.gys_task.R
import com.moussa.gys_task.model.TrendingModel
import com.moussa.gys_task.utils.ParentClass
import kotlinx.android.synthetic.main.item_trending.view.*

class TrendingAdapter (trendingList: MutableList<TrendingModel>,context: Context) : RecyclerView.Adapter<TrendingAdapter.ViewHolder>(){

    var trendingList: MutableList<TrendingModel> = mutableListOf()
    var context : Context?=null

    init {
        this.trendingList = trendingList
        this.context=context
    }

    fun updateTrending(newTrendingList: MutableList<TrendingModel>) {
        trendingList.clear()

        trendingList.addAll(newTrendingList)
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_trending, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return trendingList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        trendingList[position].let { holder.bindItems(it) }

        holder.itemView.setOnClickListener {

            if (trendingList[position].isOpen){
                holder.itemView.llHideContent.visibility = View.GONE
                holder.itemView.viewHidden.visibility = View.GONE
                trendingList[position].isOpen = false
            }else{
                holder.itemView.llHideContent.visibility = View.VISIBLE
                holder.itemView.viewHidden.visibility = View.VISIBLE
                trendingList[position].isOpen = true


            }
        }

    }



    class ViewHolder(trendingView: View) : RecyclerView.ViewHolder(trendingView){

        fun bindItems(trendingModel: TrendingModel) {

            itemView.tvTrendAuthor.text = trendingModel.author
            itemView.tvTrendName.text = trendingModel.name
            itemView.tvTrendDescription.text = trendingModel.description
            itemView.ivLanguage.setBackgroundColor( Color.parseColor(trendingModel.languageColor))
            itemView.tvLanguageName.text=trendingModel.language
            itemView.tvStarCount.text = trendingModel.stars.toString()
            itemView.tvStarCount.text = trendingModel.stars.toString()
            itemView.tvForkCount.text = trendingModel.forks.toString()
            trendingModel.avatar?.let { ParentClass().loadImage(itemView.civTrendingImage, it) }
        }


    }
}