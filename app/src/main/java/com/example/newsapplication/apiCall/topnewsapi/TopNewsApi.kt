package com.example.newsapplication.apiCall.topnewsapi

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

suspend fun getTopNewsApi(): TopNewsApiResponse = withContext(IO)
{
    var code = 0
    var exception: Throwable? = null
    val message: String? = null
    var data: JSONObject? = null

    try {
        val url = HttpUrl.Builder()
            .scheme("https")
            .host("newsapi.org")
            .addPathSegment("v2")
            .addPathSegment("top-headlines")
            .addQueryParameter("country", "us")
            .build()

        val request = Request.Builder()
            .url(url)
            .build()
        val httpClient = OkHttpClient()
            .newBuilder()
            .build()

        val response = httpClient.newCall(request).execute()
        code = response.code
        data = response.body?.string()?.let {
            JSONObject(it)
        }

    }
    catch (ex:Exception)
    {
        exception = ex
    }
    return@withContext TopNewsApiResponse(code, exception, message, data)

}