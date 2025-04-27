package com.example.newsapplication.repository.home

import com.example.newsapplication.data.preference.SharedPreferencesManager
import com.example.newsapplication.data.preference.USERNAME_KEY

class HomeRepository {

    //function to store username in share preference
    fun saveUserName(username: String){
        SharedPreferencesManager.saveString(key = USERNAME_KEY,username)
    }

    fun getUserName(): String{
        return SharedPreferencesManager.getString(USERNAME_KEY,"")
    }
}