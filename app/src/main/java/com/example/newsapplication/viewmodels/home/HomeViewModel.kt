package com.example.newsapplication.viewmodels.home

import androidx.lifecycle.ViewModel
import com.example.newsapplication.repository.home.HomeRepository

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
}