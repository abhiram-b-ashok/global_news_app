package com.example.newsapplication.ui.home.adapters.newstype

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapplication.data.models.newstype.NewsTypeModel
import com.example.newsapplication.databinding.CellNewsTypesBinding

class NewsTypeAdapter(private val newsTypeList: List<NewsTypeModel>) : RecyclerView.Adapter<NewsTypeAdapter.NewsTypeViewHolder>() {

    var onTypeSelected: ((Int,NewsTypeModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsTypeViewHolder {
        val binding =
            CellNewsTypesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsTypeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsTypeViewHolder, position: Int) {
        holder.bind(newsTypeList[position], onTypeSelected)


    }

    override fun getItemCount(): Int {
        return newsTypeList.size
    }

    class NewsTypeViewHolder(val binding: CellNewsTypesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(newsTypeModel: NewsTypeModel, onTypeSelected: ((Int,NewsTypeModel) -> Unit)?) {
            binding.apply {
                typeButton.text = newsTypeModel.type


                if (newsTypeModel.isSelected) {
                    typeButton.setBackgroundResource(com.example.newsapplication.R.drawable.filter_news_shape_selected)
                    typeButton.setTextColor(Color.parseColor("WHITE"))
                } else {
                    typeButton.setBackgroundResource(com.example.newsapplication.R.drawable.filter_news_shape)
                    typeButton.setTextColor(Color.parseColor("GRAY"))
                }
                root.setOnClickListener {
                    onTypeSelected?.invoke(adapterPosition,newsTypeModel)
                }
            }



        }
    }
}