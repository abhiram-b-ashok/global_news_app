package com.example.newsapplication.ui.saved.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapplication.data.models.topheadlines.TopHeadLinesModel
import com.example.newsapplication.data.room_database.SavedNewsModel
import com.example.newsapplication.databinding.CellSavedNewsItemBinding
import com.example.newsapplication.utils.loadCellImage
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class SavedNewsAdapter(private val list: MutableList<SavedNewsModel>) :
    RecyclerView.Adapter<SavedNewsAdapter.SavedNewsViewHolder>() {

    var onUnSaveClickListener: ((item: SavedNewsModel) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedNewsViewHolder {
        val binding =
            CellSavedNewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SavedNewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SavedNewsViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class SavedNewsViewHolder(val binding: CellSavedNewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SavedNewsModel, position: Int) {
            binding.apply {
                val rawDate = item.publishedAt
                val parsedDate = OffsetDateTime.parse(rawDate);
                val digitalDate = DateTimeFormatter.ofPattern("MMM dd, uuuu", Locale.ENGLISH);
                val formattedDate = digitalDate.format(parsedDate)
                newsDate.text = formattedDate
                title.text = item.title

                newsImg.loadCellImage(item.urlToImage)
                unSaveButton.setOnClickListener {
                    onUnSaveClickListener?.invoke(item)
                }
            }

        }

    }

    fun updateList(newList: List<SavedNewsModel>) {
        (list as? MutableList)?.apply {
            clear()
            addAll(newList)
            notifyDataSetChanged()
        }
    }
}