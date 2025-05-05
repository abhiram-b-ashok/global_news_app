package com.example.newsapplication.data.room_database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SavedNewsDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveNews(savedNewsModel: SavedNewsModel)

    @Delete
    suspend fun unSaveNews(savedNewsModel: SavedNewsModel)

    @Query("SELECT * FROM saved_news")
    suspend fun getSavedNews(): List<SavedNewsModel>

    @Query("SELECT * FROM saved_news WHERE url = :url")
    suspend fun getNewsByUrl(url: String): SavedNewsModel?



}