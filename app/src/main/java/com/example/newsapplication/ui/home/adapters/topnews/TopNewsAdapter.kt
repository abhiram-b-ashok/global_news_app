package com.example.newsapplication.ui.home.adapters.topnews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.newsapplication.data.models.topnews.NewsContract
import com.example.newsapplication.data.models.topnews.NewsModel
import com.example.newsapplication.data.models.topnews.NewsViewAll
import com.example.newsapplication.databinding.CellTopNewsItemBinding
import com.example.newsapplication.databinding.CellViewAllBinding
import com.example.newsapplication.utils.loadAsyncImage

class TopNewsAdapter(
    private val list: List<NewsContract>
): Adapter<ViewHolder>() {
    companion object{
        const val NEWS_ITEM_VIEW_TYPE = 1
        const val NEWS_VIEW_ALL_VIEW_TYPE = 2
    }
    var  onItemClickListener:((NewsViewAll)->Unit)? = null

    inner class NewsItemViewHolder(
        private val binding: CellTopNewsItemBinding):ViewHolder(binding.root){
        fun onBinding(item:NewsModel) = binding.apply {
            textTitle.text = item.source
            textDescription.text = item.title
            textAuthor.text = item.author
            imgNews.loadAsyncImage(item.image)
        }
    }

    inner class NewsViewAllViewHolder(
       private val binding: CellViewAllBinding
    ):ViewHolder(binding.root){
        fun onBind(item:NewsViewAll,onItemClickListener:((NewsViewAll)->Unit)? = null) = binding.apply {
            textTitle.text = item.navigationIdentifier
            root.setOnClickListener {
                onItemClickListener?.invoke(item)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      return when(viewType){
           NEWS_ITEM_VIEW_TYPE -> NewsItemViewHolder(CellTopNewsItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
           NEWS_VIEW_ALL_VIEW_TYPE -> NewsViewAllViewHolder(CellViewAllBinding.inflate(LayoutInflater.from(parent.context),parent,false))
           else -> throw IllegalArgumentException("illegal type")
       }
    }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(getItemViewType(position)){
            NEWS_ITEM_VIEW_TYPE -> (holder as NewsItemViewHolder).onBinding(list[position] as NewsModel)
            NEWS_VIEW_ALL_VIEW_TYPE -> (holder as NewsViewAllViewHolder).onBind(list[position] as NewsViewAll,onItemClickListener)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].provideViewType()
    }
}