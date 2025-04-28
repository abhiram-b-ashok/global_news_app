package com.example.newsapplication.ui.home.adapters.topheadlines

import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapplication.data.models.topheadlines.TopHeadLinesModel
import com.example.newsapplication.databinding.CellTopHeadlinesItemsBinding

class TopHeadlinesAdapter(private val list: List<TopHeadLinesModel>) : RecyclerView.Adapter<TopHeadlinesAdapter.TopHeadlinesViewHolder>()

{
      var itemOnclickListener: ((TopHeadLinesModel) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopHeadlinesViewHolder {

        val binding = CellTopHeadlinesItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TopHeadlinesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopHeadlinesViewHolder, position: Int) {
        holder.bind(list[position],itemOnclickListener)


    }

    override fun getItemCount(): Int {
        return list.size
    }
    class TopHeadlinesViewHolder(val binding: CellTopHeadlinesItemsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TopHeadLinesModel, itemOnclickListener: ((TopHeadLinesModel) -> Unit)?) {
            binding.apply {
                title.text = item.title
                newsDate.text = item.publishedAt
                Glide.with(newsImg.context).load(item.urlToImage).into(newsImg)
            }.root.setOnClickListener {
                itemOnclickListener?.invoke(item)
            }

        }

    }

}