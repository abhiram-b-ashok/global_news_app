package com.example.newsapplication.ui.home.adapters.everything

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapplication.data.models.everything.EverythingModel
import com.example.newsapplication.databinding.CellEverythingItemsBinding
import com.example.newsapplication.databinding.CellTopHeadlinesItemsBinding

class EverythingAdapter(private val list: List<EverythingModel>) : RecyclerView.Adapter<EverythingAdapter.EverythingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EverythingViewHolder {
       val binding = CellEverythingItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return EverythingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EverythingViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return list.size
    }
    class EverythingViewHolder(private val binding: CellEverythingItemsBinding) : RecyclerView.ViewHolder(binding.root) {

    }



}