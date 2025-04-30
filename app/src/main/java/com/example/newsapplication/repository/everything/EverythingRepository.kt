package com.example.newsapplication.repository.everything

import com.example.newsapplication.data.network.ApiResponse
import com.example.newsapplication.data.network.apis.everythingapi.getEverythingApi

class EverythingRepository {

    suspend fun getEverything(): ApiResponse {
        return getEverythingApi()
    }
}