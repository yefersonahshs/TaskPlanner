package com.example.taskplanner.storage

import okhttp3.Interceptor
import okhttp3.Response

class JWTInterceptor(private val tokenStorage: Storage) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        val token = tokenStorage.getToken()
        if (token != null) {
            request.addHeader("Authorization", "Bearer $token")
        }
        return chain.proceed(request.build())
    }


}