package com.dicoding.kotlinfundamental1.ui.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.kotlinfundamental1.data.SettingPreferences
import com.dicoding.kotlinfundamental1.data.UserRepository
import com.dicoding.kotlinfundamental1.data.dataStore
import com.dicoding.kotlinfundamental1.data.di.Injection
import com.dicoding.kotlinfundamental1.ui.detail.DetailViewModel
import com.dicoding.kotlinfundamental1.ui.detail.FollowViewModel
import com.dicoding.kotlinfundamental1.ui.favorite.FavoriteViewModel
import com.dicoding.kotlinfundamental1.ui.setting.SettingViewModel

class ViewModelFactory private constructor(
    private val pref: SettingPreferences,
    private val userRepository: UserRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when (modelClass) {
        MainViewModel::class.java -> MainViewModel(pref, userRepository)
        DetailViewModel::class.java -> DetailViewModel(userRepository)
        FavoriteViewModel::class.java -> FavoriteViewModel(userRepository)
        FollowViewModel::class.java -> FollowViewModel(userRepository)
        SettingViewModel::class.java -> SettingViewModel(pref)
        else -> throw IllegalArgumentException("Unknown ViewModel class")
    } as T

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(application: Application): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    SettingPreferences.getInstance(application.dataStore),
                    Injection.provideInjection(application.applicationContext)
                )
            }.also { instance = it }
    }
}