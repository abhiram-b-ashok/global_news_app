package com.example.newsapplication.viewmodels.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapplication.data.models.TopHeadLinesRootModel
import com.example.newsapplication.data.models.toTopHeadLinesRootModel
import com.example.newsapplication.data.network.NetworkResult
import com.example.newsapplication.repository.home.HomeRepository
import kotlinx.coroutines.launch

class HomeViewModel(
     val repository: HomeRepository
): ViewModel() {

    val topHeadLinesMutableLiveData = MutableLiveData<NetworkResult<TopHeadLinesRootModel>>(
        NetworkResult.Loading()
    )
    val topHeadLinesLiveData: LiveData<NetworkResult<TopHeadLinesRootModel>> = topHeadLinesMutableLiveData



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
        if (response.exception != null){
            topHeadLinesMutableLiveData.value = NetworkResult.Error(response.message)
        }else{
            topHeadLinesMutableLiveData.value = NetworkResult.Success(response.data.toTopHeadLinesRootModel())
        }
    }
}