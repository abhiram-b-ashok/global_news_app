package com.example.newsapplication.ui.settings.bottom_sheet_dialog

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newsapplication.data.models.languages.LanguageModel
import com.example.newsapplication.databinding.LanguageBottomSheetBinding
import com.example.newsapplication.ui.settings.bottom_sheet_dialog.adapters.LanguageAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class LanguageBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: LanguageBottomSheetBinding
    private lateinit var adapter: LanguageAdapter
    var selectedLanguage :String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LanguageBottomSheetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val languageList = arrayListOf(
            LanguageModel("English", "en", true),
            LanguageModel("Arabic", "ar"),
            LanguageModel("French", "fr"),
            LanguageModel("German", "de"),
            LanguageModel("Spanish", "es"),
            LanguageModel("Italian", "it"),
            LanguageModel("Russian", "ru"),
            LanguageModel("Portuguese", "pt"),
            LanguageModel("Chinese", "zh"),
            LanguageModel("Dutch", "nl"),
            LanguageModel("Hebrew", "he"),
            LanguageModel("Swedish", "sv"),
            LanguageModel("Norwegian", "no"),
            LanguageModel("Demur", "ud")
        )

        adapter = LanguageAdapter(languageList)
        binding.languagesRecycler.adapter = adapter
        adapter.onItemClicked = { selected ->
            languageList.forEach {
                it.isSelected = it == selected
                selectedLanguage = it.languageCode
            }


            adapter.notifyDataSetChanged()
            dismiss()

        }

    }


}