package com.example.newsapplication.data.room_database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_news")
data class SavedNewsModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val urlToImage: String,
    val publishedAt: String,
    val url: String,
    var isSaved: Boolean = true
)
