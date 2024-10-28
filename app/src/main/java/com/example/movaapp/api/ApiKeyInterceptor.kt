package com.example.movaapp.api


import com.example.movaapp.utils.api_key
import com.example.movaapp.utils.language
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url.newBuilder().addQueryParameter("api_key", api_key)
            .addQueryParameter("language",  language)
            .build()
        val newRequest = request.newBuilder().url(url).build()
        return chain.proceed(newRequest)
    }
}