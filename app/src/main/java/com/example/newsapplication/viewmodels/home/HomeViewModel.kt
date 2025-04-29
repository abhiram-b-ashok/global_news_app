package com.example.newsapplication.viewmodels.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapplication.data.models.topheadlines.TopHeadLinesRootModel
import com.example.newsapplication.data.models.topheadlines.toTopHeadLinesRootModel
import com.example.newsapplication.data.network.NetworkResult
import com.example.newsapplication.repository.home.HomeRepository
import com.example.newsapplication.ui.home.adapters.topheadlines.TopHeadlinesAdapter
import kotlinx.coroutines.launch

class HomeViewModel(
     val repository: HomeRepository
): ViewModel() {

    val topHeadLinesMutableLiveData = MutableLiveData<NetworkResult<TopHeadLinesRootModel>>(
        NetworkResult.Loading()
    )
    val topHeadLinesLiveData: LiveData<NetworkResult<TopHeadLinesRootModel>> = topHeadLinesMutableLiveData
    var topHeadlinesRootModel = TopHeadLinesRootModel(
        status = "",
        totalResults = 0,
        articles = emptyList()
    )
    val topHeadlinesAdapter = TopHeadlinesAdapter(topHeadlinesRootModel.articles)



    //function to save username in viewmodel take parameter as username
    fun saveUsername(username: String){
        //call repository function to save username using repository object ...
        repository.saveUserName(username)
    }

    fun getUsername(): String {
        return repository.getUserName()
    }

    fun getTopHeadlines(category: String? = null) = viewModelScope.launch {
        val response = repository.getTopHeadlines(category= category)
        if (response.exception != null){
            topHeadLinesMutableLiveData.value = NetworkResult.Error(response.message)
        }else{
            val newData = response.data.toTopHeadLinesRootModel()
            topHeadlinesRootModel = newData
            topHeadLinesMutableLiveData.value = NetworkResult.Success(newData)
            topHeadlinesAdapter.updateList(topHeadlinesRootModel.articles)
        }
    }
}