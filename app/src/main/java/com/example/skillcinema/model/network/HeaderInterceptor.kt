package com.example.skillcinema.model.network

import com.example.skillcinema.domain.SharedPreferencesManager
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(private val sharedPreferencesManager: SharedPreferencesManager) :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        sharedPreferencesManager.getAuthToken()?.let {
            requestBuilder.addHeader("X-API-KEY", it)
        }
        return chain.proceed(requestBuilder.build())
    }
}