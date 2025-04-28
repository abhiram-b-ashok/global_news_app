package com.example.newsapplication.apiCall.topnewsapi

import org.json.JSONArray
import org.json.JSONObject

data class TopNewsApiResponse(
    val code: Int?,
    val exception: Throwable?,
    val message: String?,
    val data : JSONObject? = null
)
