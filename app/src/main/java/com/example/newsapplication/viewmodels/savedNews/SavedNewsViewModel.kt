package com.example.newsapplication.viewmodels.savedNews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapplication.data.room_database.SavedNewsDao
import com.example.newsapplication.data.room_database.SavedNewsModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SavedNewsViewModel(private val dao: SavedNewsDao) : ViewModel() {

    fun saveNews(news: SavedNewsModel) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.saveNews(news)
        }
    }

    fun unSaveNews(news: SavedNewsModel) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.unSaveNews(news)
        }
    }

    fun getSavedNews(onResult: (List<SavedNewsModel>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = dao.getSavedNews()
            withContext(Dispatchers.Main) {
                onResult(result)
            }
        }
    }
}
