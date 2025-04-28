package com.example.newsapplication.data.models.news

const val NEWS_ITEM_VIEW_TYPE = 1
const val NEWS_VIEW_ALL_VIEW_TYPE = 2

interface NewsContract{
    fun provideViewType():Int
}

data class NewsModel(
    val id:Int,
    val title:String,
    val description:String,
    val image:String,

):NewsContract {
    override fun provideViewType(): Int  = NEWS_ITEM_VIEW_TYPE
}

data class NewsViewAll(
    val navigationIdentifier:String
):NewsContract {
    override fun provideViewType(): Int = NEWS_VIEW_ALL_VIEW_TYPE
}

val newsList : List<NewsContract> = listOf(
    NewsModel(1,"title1","description1","https://nbcsports.brightspotcdn.com/dims4/default/b756a40/2147483647/strip/true/crop/4546x2557+0+237/resize/1440x810!/quality/90/?url=https%3A%2F%2Fnbc-sports-production-nbc-sports.s3.us-east-1.amazonaws.com%2Fbrightspot%2F40%2F56%2Fc6da28dd411f9cdd137d9f5bd9df%2Fhttps-delivery-gettyimages.com%2Fdownloads%2F2211852585"),
    NewsModel(2,"title2","description2", "https://nbcsports.brightspotcdn.com/dims4/default/b756a40/2147483647/strip/true/crop/4546x2557+0+237/resize/1440x810!/quality/90/?url=https%3A%2F%2Fnbc-sports-production-nbc-sports.s3.us-east-1.amazonaws.com%2Fbrightspot%2F40%2F56%2Fc6da28dd411f9cdd137d9f5bd9df%2Fhttps-delivery-gettyimages.com%2Fdownloads%2F2211852585"),
    NewsModel(3,"title3","description3", "https://nbcsports.brightspotcdn.com/dims4/default/b756a40/2147483647/strip/true/crop/4546x2557+0+237/resize/1440x810!/quality/90/?url=https%3A%2F%2Fnbc-sports-production-nbc-sports.s3.us-east-1.amazonaws.com%2Fbrightspot%2F40%2F56%2Fc6da28dd411f9cdd137d9f5bd9df%2Fhttps-delivery-gettyimages.com%2Fdownloads%2F2211852585"),
    NewsModel(4,"title4","description4", "https://nbcsports.brightspotcdn.com/dims4/default/b756a40/2147483647/strip/true/crop/4546x2557+0+237/resize/1440x810!/quality/90/?url=https%3A%2F%2Fnbc-sports-production-nbc-sports.s3.us-east-1.amazonaws.com%2Fbrightspot%2F40%2F56%2Fc6da28dd411f9cdd137d9f5bd9df%2Fhttps-delivery-gettyimages.com%2Fdownloads%2F2211852585"),
    NewsViewAll("view_all")
)
