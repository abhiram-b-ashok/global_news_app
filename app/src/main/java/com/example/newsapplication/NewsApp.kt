package com.example.newsapplication

import android.app.Application
import com.example.newsapplication.data.network.NetworkHandler
import com.example.newsapplication.data.preference.SharedPreferencesManager

class NewsApp: Application() {

    override fun onCreate() {
        super.onCreate()
        initializeSharedPreference()
        initializeNetworkHandler()
    }

    private fun initializeSharedPreference(){
        SharedPreferencesManager.init(this)
    }

    private fun initializeNetworkHandler(){
        NetworkHandler.init()
    }
}