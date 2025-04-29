package com.example.newsapplication.data.models.everything

import com.example.newsapplication.data.models.topheadlines.TopHeadLinesModel
import com.example.newsapplication.data.models.topheadlines.TopHeadLinesRootModel
import com.example.newsapplication.data.models.topheadlines.toTopHeadLinesModel
import org.json.JSONArray
import org.json.JSONObject


data class EverythingRootModel(
    val status: String,
    val totalResults: Int,
    val articles: List<EverythingModel>
)

data class EverythingModel(
    val id: Int,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String
)


fun JSONObject?.toEverythingRootModel(): EverythingRootModel {
    return EverythingRootModel(
        status = this?.optString("status") ?: "",
        totalResults = this?.optInt("totalResults") ?: 0,
        articles = this?.optJSONArray("articles")?.toEverythingModel() ?: emptyList()
    )
}

fun JSONArray?.toEverythingModel(): List<EverythingModel>{
    val list = mutableListOf<EverythingModel>()
    for (i in 0 until (this?.length() ?: 0)){
        val item = this?.getJSONObject(i)
        list.add(
            EverythingModel(
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