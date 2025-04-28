package com.example.newsapplication.data.models.newstype

data class NewsTypeModel (
    val type:String,
    var isSelected:Boolean = false

    )

var newsTypeList = listOf(
    NewsTypeModel("General"),
    NewsTypeModel("Business"),
    NewsTypeModel("Entertainment"),
    NewsTypeModel("Health"),
    NewsTypeModel("Science"),
    NewsTypeModel("Sports"),
    NewsTypeModel("Technology")
)