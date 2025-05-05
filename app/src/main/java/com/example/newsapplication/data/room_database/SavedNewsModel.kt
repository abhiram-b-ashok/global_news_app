package com.example.newsapplication.data.room_database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_news")
data class SavedNewsModel(

    val title: String,
    val urlToImage: String,
    val publishedAt: String,
    @PrimaryKey val url: String,
    var isSaved: Boolean = false
)
