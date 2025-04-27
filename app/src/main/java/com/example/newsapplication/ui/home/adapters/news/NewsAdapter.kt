package com.example.newsapplication.ui.home.adapters.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.newsapplication.databinding.CellNewsItemsBinding
import com.example.newsapplication.databinding.CellViewAllBinding
import com.example.newsapplication.data.models.news.NEWS_ITEM_VIEW_TYPE
import com.example.newsapplication.data.models.news.NEWS_VIEW_ALL_VIEW_TYPE
import com.example.newsapplication.data.models.news.NewsContract
import com.example.newsapplication.data.models.news.NewsModel
import com.example.newsapplication.data.models.news.NewsViewAll

class NewsAdapter(
    val list: List<NewsContract>
): Adapter<RecyclerView.ViewHolder>() {

    inner class NewsItemViewHolder(
        private val binding: CellNewsItemsBinding):ViewHolder(binding.root){
        fun onBinding(item:NewsModel) = binding.apply {
            title.text = item.title
            newsDate.text = item.description
            //newsImg.setImageResource(item.)
        }
    }

    inner class NewsViewAllViewHolder(
       private val binding: CellViewAllBinding
    ):ViewHolder(binding.root){
        fun onBind(item:NewsViewAll) = binding.apply {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
      return when(viewType){
           NEWS_ITEM_VIEW_TYPE -> NewsItemViewHolder(CellNewsItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
           NEWS_VIEW_ALL_VIEW_TYPE -> NewsViewAllViewHolder(CellViewAllBinding.inflate(LayoutInflater.from(parent.context),parent,false))
           else -> throw IllegalArgumentException("illegal type")
       }
    }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)){
            NEWS_ITEM_VIEW_TYPE -> (holder as NewsItemViewHolder).onBinding(list[position] as NewsModel)
            NEWS_VIEW_ALL_VIEW_TYPE -> (holder as NewsViewAllViewHolder).onBind(list[position] as NewsViewAll)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].provideViewType()
    }
}