package com.example.newsapplication.ui.settings.bottom_sheet_dialog.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapplication.R
import com.example.newsapplication.data.models.languages.LanguageModel
import com.example.newsapplication.databinding.CellLanguageBinding

class LanguageAdapter(private val list: List<LanguageModel>) :
    RecyclerView.Adapter<LanguageAdapter.LanguageViewHolder>() {
    var onItemClicked: ((LanguageModel) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        val binding =
            CellLanguageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LanguageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        holder.bind(list[position], onItemClicked)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class LanguageViewHolder(val binding: CellLanguageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(languageModel: LanguageModel, onItemClicked: ((LanguageModel) -> Unit)?) {
            binding.apply {
                languageName.text = languageModel.languageName

                if (languageModel.isSelected) {
                    languageName.setBackgroundResource(R.drawable.bottom_sheet_selected_items_border)
                    languageName.setTextColor(Color.parseColor("RED"))
                } else {
                    languageName.setBackgroundResource(R.drawable.bottom_sheet_items_border)
                    languageName.setTextColor(Color.parseColor("GRAY"))
                }
                root.setOnClickListener {
                    onItemClicked?.invoke(languageModel)
                }
            }


        }

    }
}