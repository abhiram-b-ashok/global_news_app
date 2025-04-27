package com.example.newsapplication

import android.app.Application
import com.example.newsapplication.data.preference.SharedPreferencesManager

class NewsApp: Application() {

    override fun onCreate() {
        super.onCreate()
        initializeSharedPreference()
    }

    private fun initializeSharedPreference(){
        SharedPreferencesManager.init(this)
    }
}