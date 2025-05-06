package com.example.newsapplication.ui.settings.bottom_sheet_dialog.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapplication.R
import com.example.newsapplication.data.models.countries.CountryModel
import com.example.newsapplication.databinding.CellCountryBinding

class CountryAdapter(private val list: List<CountryModel>) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    var onCountryClicked: ((CountryModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val binding = CellCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(list[position], onCountryClicked)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class CountryViewHolder(val binding: CellCountryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(countryModel: CountryModel, onCountryClicked: ((CountryModel) -> Unit)?)  {
            binding.apply {
                countryName.text = countryModel.countryName

                if (countryModel.isSelected) {
                    countryName.setBackgroundResource(R.drawable.bottom_sheet_selected_items_border)
                    countryName.setTextColor(Color.parseColor("RED"))
                }
                else{
                    countryName.setBackgroundResource(R.drawable.bottom_sheet_items_border)
                    countryName.setTextColor(Color.parseColor("GRAY"))
                }
                root.setOnClickListener {
                    onCountryClicked?.invoke(countryModel)
                }

            }

        }
    }
}