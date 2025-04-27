package com.example.newsapplication.viewmodels.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapplication.repository.home.HomeRepository
import kotlinx.coroutines.launch

class HomeViewModel(
     val repository: HomeRepository
): ViewModel() {

    //function to save username in viewmodel take parameter as username
    fun saveUsername(username: String){
        //call repository function to save username using repository object ...
        repository.saveUserName(username)
    }

    fun getUsername(): String {
        return repository.getUserName()
    }

    fun getTopHeadlines() = viewModelScope.launch {
        val response = repository.getTopHeadlines()
        Log.e("response","<<<<< $response")
    }
}