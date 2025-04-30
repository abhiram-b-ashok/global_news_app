package com.example.newsapplication.ui.settings.bottom_sheet_dialog

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newsapplication.data.models.countries.CountryModel
import com.example.newsapplication.databinding.CountryBottomSheetBinding
import com.example.newsapplication.ui.settings.bottom_sheet_dialog.adapters.CountryAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CountryBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: CountryBottomSheetBinding
    private lateinit var countryAdapter: CountryAdapter
    var onCountrySelected: ((CountryModel) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CountryBottomSheetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val countryList = arrayListOf(
            CountryModel("USA", "us"),
            CountryModel("Canada", "ca"),
            CountryModel("Mexico", "mx"),
            CountryModel("Germany", "de"),
            CountryModel("France", "fr"),
            CountryModel("Italy", "it"),
            CountryModel("Spain", "es"),
            CountryModel("China", "cn"),
            CountryModel("India", "in"),
            CountryModel("Russia", "ru"),
            CountryModel("Japan", "jp"),
            )


        countryAdapter = CountryAdapter(countryList)
        binding.countriesRecycler.adapter = countryAdapter.apply {
            onCountryClicked = { selectedCountry ->
                countryList.forEach {
                    it.isSelected = it == selectedCountry
                }
                countryAdapter.notifyDataSetChanged()
                onCountrySelected?.invoke(selectedCountry)
                dismiss()
            }

        }

    }
}