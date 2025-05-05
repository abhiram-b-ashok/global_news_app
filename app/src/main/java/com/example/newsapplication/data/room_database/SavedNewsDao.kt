package com.example.newsapplication.data.room_database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SavedNewsDao {

    @Insert
    suspend fun saveNews(savedNewsModel: SavedNewsModel)

    @Delete
    suspend fun unSaveNews(savedNewsModel: SavedNewsModel)

    @Query("SELECT * FROM saved_news")
    suspend fun getSavedNews(): List<SavedNewsModel>



}