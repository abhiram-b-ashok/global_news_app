package com.example.newsapplication.ui.settings.bottom_sheet_dialog.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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
            binding.languageName.text = languageModel.languageName
            binding.root.setOnClickListener {
                onItemClicked?.invoke(languageModel)
            }

        }

    }
}