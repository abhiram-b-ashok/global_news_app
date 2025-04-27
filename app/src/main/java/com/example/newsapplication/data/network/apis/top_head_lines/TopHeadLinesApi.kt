package com.example.newsapplication.data.network.apis.top_head_lines

import com.example.newsapplication.data.network.ApiResponse
import com.example.newsapplication.data.network.NetworkHandler
import com.example.newsapplication.data.network.buildUrl
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

suspend fun getApiRequest(
    httpClient: OkHttpClient = NetworkHandler.okHttpClient,
    country: String = "us"
): ApiResponse = withContext(IO) {

    var code = 0
    var exception: Throwable? = null
    var message: String? = null
    var data : JSONObject? = null

    try {

        val url = buildUrl("top-headlines")
            .addQueryParameter("country",country)
            .build()

        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        val response = httpClient.newCall(request).execute()
        code = response.code
        message = (response.message).ifEmpty { "Oops! Something went wrong" }
        if (code == 200){
            data = response.body?.string()?.let {
                JSONObject(it)
            }
        }else{
            exception = Exception(message)
        }

    }catch (e: Exception){
        exception = e
    }
    return@withContext ApiResponse(code, exception, message,data)
}
