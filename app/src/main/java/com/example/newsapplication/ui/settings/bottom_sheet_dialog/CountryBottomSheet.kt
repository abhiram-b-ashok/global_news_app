package com.example.newsapplication.ui.settings.bottom_sheet_dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newsapplication.databinding.CountryBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CountryBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: CountryBottomSheetBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CountryBottomSheetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}