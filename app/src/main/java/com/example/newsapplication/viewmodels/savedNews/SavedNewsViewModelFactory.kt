package com.example.newsapplication.viewmodels.savedNews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapplication.data.room_database.SavedNewsDao

class SavedNewsViewModelFactory(private val dao: SavedNewsDao ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SavedNewsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SavedNewsViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
