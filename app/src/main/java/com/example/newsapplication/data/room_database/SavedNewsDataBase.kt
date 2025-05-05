package com.example.newsapplication.data.room_database

import android.R.attr.version
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SavedNewsModel::class], version = 2)
abstract class SavedNewsDatabase : RoomDatabase() {
    abstract fun savedNewsDao(): SavedNewsDao

    companion object {
        @Volatile
        private var INSTANCE: SavedNewsDatabase? = null

        fun getDatabase(context: Context): SavedNewsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SavedNewsDatabase::class.java,
                    "saved_news_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
