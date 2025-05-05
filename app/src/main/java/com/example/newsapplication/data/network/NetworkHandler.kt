package com.example.newsapplication.data.network

import okhttp3.HttpUrl
import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

const val SCHEME = "https"
const val HOST = "newsapi.org"
const val API_KEY = "5c6b5f5141ad431c9b142193ecd97e67"

//for build http url ....
fun buildUrl(
    pathSegments: String
) : HttpUrl.Builder{
   return HttpUrl
        .Builder()
        .scheme(SCHEME)
        .host(HOST)
        .addPathSegment("v2")
        .addPathSegment(pathSegments)
        .addQueryParameter("apiKey",API_KEY)
}

object NetworkHandler {

    lateinit var okHttpClient: OkHttpClient

    fun init(){

//        val loggingInterceptor = HttpLoggingInterceptor().apply {
//            level = HttpLoggingInterceptor.Level.BODY
//        }

        okHttpClient = OkHttpClient()
           .newBuilder()
           .connectTimeout(30, TimeUnit.SECONDS)
           .readTimeout(30, TimeUnit.SECONDS)
           .writeTimeout(30, TimeUnit.SECONDS)
//           .addInterceptor(loggingInterceptor)
           .build()
    }
}

sealed class NetworkResult<T>(
    val data: T? = null,
    val message: String? = null
) {

    class Success<T>(data: T) : NetworkResult<T>(data)

    class Error<T>(message: String?, data: T? = null) : NetworkResult<T>(data, message)

    class Loading<T> : NetworkResult<T>()

}