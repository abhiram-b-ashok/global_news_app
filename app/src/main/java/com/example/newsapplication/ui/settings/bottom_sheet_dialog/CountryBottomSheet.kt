package com.example.newsapplication.ui.settings.bottom_sheet_dialog

import android.annotation.SuppressLint
import android.content.Context
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
//    private var countryPreferences = this@CountryBottomSheet.requireActivity()
//        .getSharedPreferences("country", Context.MODE_PRIVATE)

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
            CountryModel("United Arab Emirates", "ae"),
            CountryModel("Argentina", "ar"),
            CountryModel("Austria", "at"),
            CountryModel("Australia", "au"),
            CountryModel("Belgium", "be"),
            CountryModel("Bulgaria", "bg"),
            CountryModel("Brazil", "br"),
            CountryModel("Canada", "ca"),
            CountryModel("Switzerland", "ch"),
            CountryModel("China", "cn"),
            CountryModel("Colombia", "co"),
            CountryModel("Cuba", "cu"),
            CountryModel("Czech Republic", "cz"),
            CountryModel("Germany", "de"),
            CountryModel("Egypt", "eg"),
            CountryModel("France", "fr"),
            CountryModel("United Kingdom", "gb"),
            CountryModel("Greece", "gr"),
            CountryModel("Hong Kong", "hk"),
            CountryModel("Hungary", "hu"),
            CountryModel("Indonesia", "id"),
            CountryModel("Ireland", "ie"),
            CountryModel("Israel", "il"),
            CountryModel("India", "in"),
            CountryModel("Italy", "it"),
            CountryModel("Japan", "jp"),
            CountryModel("South Korea", "kr"),
            CountryModel("Lithuania", "lt"),
            CountryModel("Latvia", "lv"),
            CountryModel("Morocco", "ma"),
            CountryModel("Mexico", "mx"),
            CountryModel("Malaysia", "my"),
            CountryModel("Nigeria", "ng"),
            CountryModel("Netherlands", "nl"),
            CountryModel("Norway", "no"),
            CountryModel("New Zealand", "nz"),
            CountryModel("Philippines", "ph"),
            CountryModel("Poland", "pl"),
            CountryModel("Portugal", "pt"),
            CountryModel("Romania", "ro"),
            CountryModel("Serbia", "rs"),
            CountryModel("Russia", "ru"),
            CountryModel("Saudi Arabia", "sa"),
            CountryModel("Sweden", "se"),
            CountryModel("Singapore", "sg"),
            CountryModel("Slovenia", "si"),
            CountryModel("Slovakia", "sk"),
            CountryModel("South Africa", "za"),
            CountryModel("United States", "us"),
            CountryModel("Venezuela", "ve")
        )

        val previouslySelectedCountry = this@CountryBottomSheet.requireActivity().getSharedPreferences("country", Context.MODE_PRIVATE).getString("countryName", "United States")
        countryAdapter = CountryAdapter(countryList)
        binding.countriesRecycler.adapter = countryAdapter
        countryList.forEach {
            if (it.countryName == previouslySelectedCountry) {
                it.isSelected = true
            }
        }

        countryAdapter.onCountryClicked = { selectedCountry ->
            countryList.forEach {
                it.isSelected = it == selectedCountry
            }
            countryAdapter.notifyDataSetChanged()
            onCountrySelected?.invoke(selectedCountry)
            dismiss()
        }

    }

}
