package com.dicoding.kotlinfundamental1.data.di

import android.content.Context
import com.dicoding.kotlinfundamental1.data.UserRepository
import com.dicoding.kotlinfundamental1.data.local.FavoriteDatabase
import com.dicoding.kotlinfundamental1.data.retrofit.ApiConfig

object Injection {
    fun provideInjection(context: Context): UserRepository {
        val apiService = ApiConfig.getApiService()
        val database = FavoriteDatabase.getInstance(context)
        val dao = database.favoriteDao()
        return UserRepository.getInstance(apiService, dao)
    }
}