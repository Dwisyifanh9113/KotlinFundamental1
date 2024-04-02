package com.dicoding.kotlinfundamental1.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.kotlinfundamental1.data.Result
import com.dicoding.kotlinfundamental1.data.SettingPreferences
import com.dicoding.kotlinfundamental1.data.UserRepository
import com.dicoding.kotlinfundamental1.data.response.Users

class MainViewModel (
    private val pref: SettingPreferences,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _user = MutableLiveData<List<Users>>()
    val user: LiveData<List<Users>> = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = _isEmpty

    init {
        findUser("dwi syifa novia herdiyanti")
    }

    fun findUser(username: String) {
        userRepository.findUser(username).observeForever { result ->
            when (result) {
                is Result.Loading -> {
                    _isLoading.value = true
                    _isEmpty.value = false
                }

                is Result.Success -> {
                    _isLoading.value = false
                    _isEmpty.value = false
                    _user.value = result.data
                }

                is Result.Error -> {
                    _isLoading.value = false
                    _isEmpty.value = true
                }

                is Result.Empty -> {
                    _isLoading.value = false
                    _isEmpty.value = true
                }
            }
        }
    }
    fun getThemeSetting(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }
}