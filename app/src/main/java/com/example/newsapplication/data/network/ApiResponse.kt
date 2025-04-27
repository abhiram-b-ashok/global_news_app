package com.example.newsapplication.data.network

import org.json.JSONObject

data class ApiResponse(
    val code: Int?,
    val exception: Throwable?,
    val message: String?,
    val data : JSONObject? = null
)