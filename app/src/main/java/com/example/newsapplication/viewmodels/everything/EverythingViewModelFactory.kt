package com.example.newsapplication.viewmodels.everything

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapplication.repository.everything.EverythingRepository

class EverythingViewModelFactory(private val repository: EverythingRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EverythingViewModel::class.java)) {
            return EverythingViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}