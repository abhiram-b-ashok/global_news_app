package com.example.newsapplication.ui.everything.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapplication.R
import com.example.newsapplication.data.models.everything.EverythingModel
import com.example.newsapplication.data.room_database.SavedNewsModel
import com.example.newsapplication.databinding.CellEverythingItemsBinding
import com.example.newsapplication.utils.loadCellImage
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class EverythingAdapter(private val list: List<EverythingModel>):RecyclerView.Adapter<EverythingAdapter.EverythingViewHolder> () {
//    var onItemClickListener: ((EverythingModel) -> Unit)? = null
    var onSaveClickListener: ((EverythingModel, Int) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EverythingViewHolder {
        val binding = CellEverythingItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EverythingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EverythingViewHolder, position: Int) {
        holder.bind(list[position], onSaveClickListener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class EverythingViewHolder(val binding: CellEverythingItemsBinding):RecyclerView.ViewHolder(binding.root) {

        fun bind(item: EverythingModel,  onSaveClickListener: ((EverythingModel,Int) -> Unit)?){
            binding.apply {
                title.text = item.title
                val rawDate = item.publishedAt
                val parsedDate = OffsetDateTime.parse(rawDate);
                val digitalDate = DateTimeFormatter.ofPattern("MMM dd, uuuu", Locale.ENGLISH);
                val formattedDate = digitalDate.format(parsedDate)
                newsDate.text =formattedDate
                newsImg.loadCellImage(item.urlToImage)

                if (item.isSaved) {
                    saveButton.setImageResource(R.drawable.icons8_save_50_filled)
                } else {
                    saveButton.setImageResource(R.drawable.icons8_save_50_unfilled)
                }
                saveButton.setOnClickListener {
                        onSaveClickListener?.invoke(item,adapterPosition)
                }


            }
        }

}
fun checkSaved(saveList: List<SavedNewsModel>)
{
    val savedUrls = saveList.map { it.url }.toSet()
    list.forEach { element ->
        element.isSaved = savedUrls.contains(element.url)
    }
    notifyDataSetChanged()
}
}