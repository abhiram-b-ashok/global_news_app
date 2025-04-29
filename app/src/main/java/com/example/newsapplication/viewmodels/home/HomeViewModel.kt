package com.example.newsapplication.viewmodels.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapplication.data.models.topheadlines.TopHeadLinesRootModel
import com.example.newsapplication.data.models.topheadlines.toTopHeadLinesRootModel
import com.example.newsapplication.data.models.topnews.NewsContract
import com.example.newsapplication.data.models.topnews.NewsViewAll
import com.example.newsapplication.data.models.topnews.TopNewsApiRootModel
import com.example.newsapplication.data.models.topnews.toTopNewsModel
import com.example.newsapplication.data.network.NetworkResult
import com.example.newsapplication.repository.home.HomeRepository
import com.example.newsapplication.ui.home.adapters.topheadlines.TopHeadlinesAdapter
import com.example.newsapplication.ui.home.adapters.topnews.TopNewsAdapter
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: HomeRepository
): ViewModel() {

    private val topHeadLinesMutableLiveData = MutableLiveData<NetworkResult<TopHeadLinesRootModel>>(
        NetworkResult.Loading()
    )
    private val topNewsMutableLiveData = MutableLiveData<NetworkResult<TopNewsApiRootModel>>(
        NetworkResult.Loading()
    )

    val topHeadLinesLiveData: LiveData<NetworkResult<TopHeadLinesRootModel>> = topHeadLinesMutableLiveData
    val topNewsLiveData: LiveData<NetworkResult<TopNewsApiRootModel>> = topNewsMutableLiveData

    private var topHeadlinesRootModel = TopHeadLinesRootModel(
        status = "",
        totalResults = 0,
        articles = emptyList()
    )
    private var topNewsRootModel = TopNewsApiRootModel(
        status = "",
        totalResults = 0,
        articles = emptyList()
    )

    private val topHeadlinesAdapter = TopHeadlinesAdapter(topHeadlinesRootModel.articles)
    val newsList: MutableList<NewsContract> = topNewsRootModel.articles.toMutableList()





    //function to save username in viewmodel take parameter as username
    fun saveUsername(username: String){
        //call repository function to save username using repository object ...
        repository.saveUserName(username)
    }

    fun getUsername(): String {
        return repository.getUserName()
    }

    fun getTopHeadlines(category: String? = null) = viewModelScope.launch {
        topHeadLinesMutableLiveData.value = NetworkResult.Loading()
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
    fun getTopNews() =viewModelScope.launch {
        topNewsMutableLiveData.value = NetworkResult.Loading()
        val response = repository.getTopNews()
        if (response.exception != null){
            topNewsMutableLiveData.value = NetworkResult.Error(response.message)
        }else{
            topNewsRootModel = response.data.toTopNewsModel()
            topNewsMutableLiveData.value = NetworkResult.Success(topNewsRootModel)
            newsList.add(NewsViewAll(navigationIdentifier = "View All"))
            }
    }
}