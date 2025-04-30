package com.example.newsapplication.ui.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newsapplication.databinding.FragmentSettingsBinding
import com.example.newsapplication.ui.settings.bottom_sheet_dialog.CountryBottomSheet
import com.example.newsapplication.ui.settings.bottom_sheet_dialog.LanguageBottomSheet

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private val languageBottomSheet = LanguageBottomSheet()
    private val countryBottomSheet = CountryBottomSheet()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etDropdownLanguage.setOnClickListener {
            languageBottomSheet.show(parentFragmentManager, "languageBottomSheet")
            languageBottomSheet.onLanguageSelected = { selected ->
                binding.etDropdownLanguage.setText(selected.languageName)
            }
        }
        binding.etDropdownCountry.setOnClickListener {
            countryBottomSheet.show(parentFragmentManager, "countryBottomSheet")
            countryBottomSheet.onCountrySelected = { selected ->
                binding.etDropdownCountry.setText(selected.countryName)
            }
        }
    }
}