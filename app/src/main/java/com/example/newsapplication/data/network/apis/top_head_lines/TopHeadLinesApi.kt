package com.example.newsapplication.data.network.apis.top_head_lines

import android.util.Log
import com.example.newsapplication.data.network.ApiResponse
import com.example.newsapplication.data.network.NetworkHandler
import com.example.newsapplication.data.network.buildUrl
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray

suspend fun getApiRequest(
    httpClient: OkHttpClient = NetworkHandler.okHttpClient
): ApiResponse = withContext(IO) {

    var code = 0
    var exception: Throwable? = null
    val message: String? = null
    var data : JSONArray? = null

    try {

        val url = buildUrl("top-headlines")
            .build()

        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        val response = httpClient.newCall(request).execute()
        code = response.code
        data = response.body?.string()?.let {
            JSONArray(it)
        }

    }catch (e: Exception){
        exception = e
    }

    return@withContext ApiResponse(code, exception, message,data)
}
