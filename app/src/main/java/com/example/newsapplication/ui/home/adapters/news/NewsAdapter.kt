package com.example.newsapplication.ui.home.adapters.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.newsapplication.databinding.CellNewsItemBinding
import com.example.newsapplication.databinding.CellViewAllBinding
import com.example.newsapplication.models.news.NEWS_ITEM_VIEW_TYPE
import com.example.newsapplication.models.news.NEWS_VIEW_ALL_VIEW_TYPE
import com.example.newsapplication.models.news.NewsContract
import com.example.newsapplication.models.news.NewsModel
import com.example.newsapplication.models.news.NewsViewAll

class NewsAdapter(
    val list: List<NewsContract>
): Adapter<RecyclerView.ViewHolder>() {

    inner class NewsItemViewHolder(
        private val binding: CellNewsItemBinding):ViewHolder(binding.root){
        fun onBinding(item:NewsModel) = binding.apply {
            textTitle.text = item.title
            textDescription.text = item.description
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
           NEWS_ITEM_VIEW_TYPE -> NewsItemViewHolder(CellNewsItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
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