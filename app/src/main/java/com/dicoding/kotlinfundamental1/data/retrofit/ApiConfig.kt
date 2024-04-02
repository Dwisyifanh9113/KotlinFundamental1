package com.dicoding.kotlinfundamental1.data.retrofit

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {
        fun getApiService(): ApiService {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val authInterceptor = Interceptor { chain ->
                val request = chain.request()
                val requestHeaders = request.newBuilder()
                    .addHeader("Authorization", "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")
                    .build()
                chain.proceed(requestHeaders)
            }
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}