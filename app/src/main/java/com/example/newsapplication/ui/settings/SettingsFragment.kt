package com.example.newsapplication.ui.settings

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
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

        val languagePreferences = this@SettingsFragment.requireActivity().getSharedPreferences("language", Context.MODE_PRIVATE)
        val countryPreferences = this@SettingsFragment.requireActivity().getSharedPreferences("country", Context.MODE_PRIVATE)

        binding.etDropdownLanguage.setOnClickListener {
            languageBottomSheet.show(parentFragmentManager, "languageBottomSheet")
            languageBottomSheet.onLanguageSelected = { selected ->
                binding.etDropdownLanguage.setText(selected.languageName)
                languagePreferences.edit().putString("languageName", selected.languageName).apply()
                languagePreferences.edit().putString("languageCode", selected.languageCode).apply()
            }
        }
        binding.etDropdownCountry.setOnClickListener {
            countryBottomSheet.show(parentFragmentManager, "countryBottomSheet")
            countryBottomSheet.onCountrySelected = { selected ->
                binding.etDropdownCountry.setText(selected.countryName)
                countryPreferences.edit().putString("country", selected.countryName).apply()
                languagePreferences.edit().putString("countryCode", selected.countryCode).apply()
            }
        }
        binding.imgSearch.setOnClickListener{
            findNavController().navigate(SettingsFragmentDirections.actionSettingsToDiscover())
        }
    }
    override fun onResume() {
        super.onResume()
        val languagePreferences = this@SettingsFragment.requireActivity().getSharedPreferences("language", Context.MODE_PRIVATE)
        val countryPreferences = this@SettingsFragment.requireActivity().getSharedPreferences("country", Context.MODE_PRIVATE)
        val languageName = languagePreferences.getString("languageName", "English")
        val countryName = countryPreferences.getString("countryName", "USA")
        val languageCode = languagePreferences.getString("languageCode", "en")
        val countryCode = countryPreferences.getString("countryCode", "us")
        binding.etDropdownLanguage.setText(languageName)
        binding.etDropdownCountry.setText(countryName)

    }


}