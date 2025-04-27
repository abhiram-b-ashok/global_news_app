package com.example.newsapplication.data.models

import org.json.JSONArray
import org.json.JSONObject

data class TopHeadLinesRootModel(
    val status: String,
    val totalResults: Int,
    val articles: List<TopHeadLinesModel>
)

data class TopHeadLinesModel(
    val id: Int,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String
)

fun JSONObject?.toTopHeadLinesRootModel(): TopHeadLinesRootModel{
    return TopHeadLinesRootModel(
        status = this?.optString("status") ?: "",
        totalResults = this?.optInt("totalResults") ?: 0,
        articles = this?.optJSONArray("articles")?.toTopHeadLinesModel() ?: emptyList()
    )
}

fun JSONArray?.toTopHeadLinesModel(): List<TopHeadLinesModel>{
    val list = mutableListOf<TopHeadLinesModel>()
    for (i in 0 until (this?.length() ?: 0)){
        val item = this?.getJSONObject(i)
        list.add(
            TopHeadLinesModel(
                id = item?.optInt("id") ?: 0,
                author = item?.optString("author") ?: "",
                title = item?.optString("title") ?: "",
                description = item?.optString("description") ?: "",
                url = item?.optString("url") ?: "",
                urlToImage = item?.optString("urlToImage") ?: "",
                publishedAt = item?.optString("publishedAt") ?: ""
            )
        )
    }
    return list
}