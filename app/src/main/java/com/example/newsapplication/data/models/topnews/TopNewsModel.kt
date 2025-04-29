package com.example.newsapplication.data.models.topnews

import com.example.newsapplication.data.models.everything.EverythingModel
import com.example.newsapplication.data.models.everything.EverythingRootModel
import com.example.newsapplication.data.models.everything.toEverythingModel
import org.json.JSONArray
import org.json.JSONObject

const val NEWS_ITEM_VIEW_TYPE = 1
const val NEWS_VIEW_ALL_VIEW_TYPE = 2


data class TopNewsApiRootModel(
    val status:String,
    val totalResults:Int=4,
    val articles:List<NewsModel>

)

fun JSONObject?.toTopNewsModel(): TopNewsApiRootModel {
    return TopNewsApiRootModel(
        status = this?.optString("status") ?: "",
        totalResults = this?.optInt("totalResults") ?: 0,
        articles = this?.optJSONArray("articles")?.toNewsModel() ?: emptyList()
    )
}

fun JSONArray?.toNewsModel(): List<NewsModel>{
    val list = mutableListOf<NewsModel>()
    for (i in 0 until 4){
        val item = this?.getJSONObject(i)
        list.add(
            NewsModel(
                id = item?.optInt("id") ?: 0,
                title = item?.optString("title") ?: "",
                description = item?.optString("description") ?: "",
                image = item?.optString("urlToImage") ?: "",

            )
        )
    }
    return list
}


interface NewsContract{
    fun provideViewType():Int
}

data class NewsModel(
    val id:Int,
    val title:String,
    val description:String,
    val image:String? = null,

):NewsContract {
    override fun provideViewType(): Int  = NEWS_ITEM_VIEW_TYPE
}

data class NewsViewAll(
    val navigationIdentifier:String
):NewsContract {
    override fun provideViewType(): Int = NEWS_VIEW_ALL_VIEW_TYPE
}



