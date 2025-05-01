package com.example.newsapplication.ui.home.adapters.topheadlines

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapplication.data.models.topheadlines.TopHeadLinesModel
import com.example.newsapplication.databinding.CellTopHeadlinesItemsBinding
import com.example.newsapplication.utils.loadCellImage
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class TopHeadlinesAdapter(private val list: List<Any?>) : RecyclerView.Adapter<TopHeadlinesAdapter.TopHeadlinesViewHolder>()

{
      var itemOnclickListener: ((TopHeadLinesModel) -> Unit)? = null
    var onSavedClickListener: ((Int,TopHeadLinesModel) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopHeadlinesViewHolder {

        val binding = CellTopHeadlinesItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TopHeadlinesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopHeadlinesViewHolder, position: Int) {
        holder.bind(list[position] as TopHeadLinesModel,itemOnclickListener,onSavedClickListener)


    }

    override fun getItemCount(): Int {
        return list.size
    }
    class TopHeadlinesViewHolder(val binding: CellTopHeadlinesItemsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TopHeadLinesModel, itemOnclickListener: ((TopHeadLinesModel) -> Unit)? , onSavedClickListener: ((Int,TopHeadLinesModel) -> Unit)?) {
            binding.apply {
                title.text = item.title
                val rawDate = item.publishedAt
                val parsedDate = OffsetDateTime.parse(rawDate);
                val digitalDate = DateTimeFormatter.ofPattern("MMM dd, uuuu", Locale.ENGLISH);
                val formattedDate = digitalDate.format(parsedDate)
                newsDate.text =formattedDate
                newsImg.loadCellImage(item.urlToImage)

                if (item.isSaved) {
                    saveButton.setImageResource(com.example.newsapplication.R.drawable.icons8_save_50_filled)
                } else {
                    saveButton.setImageResource(com.example.newsapplication.R.drawable.icons8_save_50_unfilled)
                }
                saveButton.setOnClickListener{
                    onSavedClickListener?.invoke(adapterPosition,item)
                }
            }.root.setOnClickListener {
                itemOnclickListener?.invoke(item)
            }

        }

    }
    fun updateList(newList: List<TopHeadLinesModel>) {
        (list as? MutableList)?.apply {
            clear()
            addAll(newList)
            notifyDataSetChanged()
        }
    }

}