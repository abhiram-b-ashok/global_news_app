package com.example.newsapplication.models.news

const val NEWS_ITEM_VIEW_TYPE = 1
const val NEWS_VIEW_ALL_VIEW_TYPE = 2

interface NewsContract{
    fun provideViewType():Int
}

data class NewsModel(
    val id:Int,
    val title:String,
    val description:String
):NewsContract {
    override fun provideViewType(): Int  = NEWS_ITEM_VIEW_TYPE
}

data class NewsViewAll(
    val navigationIdentifier:String
):NewsContract {
    override fun provideViewType(): Int = NEWS_VIEW_ALL_VIEW_TYPE
}

val newsList : List<NewsContract> = listOf(
    NewsModel(1,"title1","description1"),
    NewsModel(2,"title2","description2"),
    NewsModel(3,"title3","description3"),
    NewsModel(4,"title4","description4"),
    NewsViewAll("view_all")
)
