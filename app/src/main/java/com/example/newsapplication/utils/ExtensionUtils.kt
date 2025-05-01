package com.example.newsapplication.utils

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.newsapplication.R
import org.json.JSONObject

fun Fragment.toast(msg:String){
    Toast.makeText(requireContext(),msg,Toast.LENGTH_SHORT).show()
}

fun ImageView.loadAsyncImage(url:String?){
    Glide
        .with(this.context)
        .load(url)
        .placeholder(R.drawable.errorimgplaceholder)
        .error(R.drawable.errorimgplaceholder)
        .into(this)
}

fun ImageView.loadCellImage(url:String?){
    Glide
        .with(this.context)
        .load(url)
        .placeholder(R.drawable.errorimgplaceholder2)
        .error(R.drawable.errorimgplaceholder2)
        .into(this)
}

fun JSONObject.getStringOrNull(key:String):String?{
    if (this.optString(key) == "null")
        return null
    return this.optString(key)
}

fun JSONObject.getBooleanOrNull(key:String):Boolean?{
    if (this.optBoolean(key).toString() == "null")
        return null
    return this.optBoolean(key)
}

fun JSONObject.getIntOrNull(key:String):Int?{
    if (this.optInt(key).toString() == "null")
        return null
    return this.optInt(key)
}

fun JSONObject.getDoubleOrNull(key:String):Double?{
    if (this.optDouble(key).toString() == "null")
        return null
    return this.optDouble(key)
}

fun View.show(){
    this.visibility = View.VISIBLE
}

fun View.hide(){
    this.visibility = View.GONE
}

fun View.invisible(){
    this.visibility = View.INVISIBLE
}

fun Fragment.errorLog(value:String,customTag:String? = null,) {
    Log.e("${this.javaClass.simpleName}${customTag ?: ""}", value)
}

fun Fragment.infoLog(value:String,customTag:String? = null,){
    Log.i("${this.javaClass.simpleName}${customTag?:""}",value)
}

fun Fragment.warningLog(value:String,customTag:String? = null,){
    Log.w("${this.javaClass.simpleName}${customTag?:""}",value)
}

fun Fragment.verboseLog(value:String,customTag:String? = null){
    Log.v("${this.javaClass.simpleName}${customTag?:""}",value)
}


//2025 May 30 -  YYYY MMM dd
