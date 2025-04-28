package com.example.newsapplication.models.news

import com.example.newsapplication.data.models.news.NewsModel

data class TopNewsApiRootModel(
    val status:String,
    val totalResults:Int,
    val articles:List<NewsModel>

)