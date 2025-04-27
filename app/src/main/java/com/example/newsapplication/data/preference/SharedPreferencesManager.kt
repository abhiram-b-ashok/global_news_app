package com.example.newsapplication.data.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object SharedPreferencesManager {

    private const val PREFERENCE_NAME: String = "MySharedPrefs"
    private lateinit var preferences: SharedPreferences

    //method to initialize the sharedPreference
    fun init(context: Context) {
        preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    // -------- String --------//
    fun saveString(key: String, value: String) {
        preferences.edit() { putString(key, value) }
    }

    fun getString(key: String, defaultValue: String): String {
        return preferences.getString(key, defaultValue) ?: defaultValue
    }

    //------------ Integer ---------//
    fun saveInt(key: String,value: Int){
        preferences.edit() { putInt(key, value) }
    }

    fun getInt(key: String, defaultValue: Int = 0): Int {
        return preferences.getInt(key, defaultValue)
    }

    // ------------ Float ---------//
    fun saveFloat(key: String,value: Float){
        preferences.edit() { putFloat(key, value) }
    }

    fun getFloat(key: String, defaultValue: Float = 0f): Float {
        return preferences.getFloat(key, defaultValue)
    }

    //------------ Long ---------//
    fun saveLong(key: String,value: Long){
        preferences.edit() { putLong(key, value) }
    }

    fun getLong(key: String, defaultValue: Long = 0L): Long {
        return preferences.getLong(key, defaultValue)
    }

    //------------- Boolean -------//
    fun saveBoolean(key: String,value: Boolean){
        preferences.edit() { putBoolean(key, value) }
    }

    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return preferences.getBoolean(key, defaultValue)
    }

}
