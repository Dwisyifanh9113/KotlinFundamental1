package com.dicoding.kotlinfundamental1.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.kotlinfundamental1.data.Result
import com.dicoding.kotlinfundamental1.data.UserRepository
import com.dicoding.kotlinfundamental1.data.local.FavoriteEntity
import com.dicoding.kotlinfundamental1.data.response.DetailUserResponse
import com.dicoding.kotlinfundamental1.data.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel (private val userRepository: UserRepository) : ViewModel() {
    private val _userEntity = MutableLiveData<FavoriteEntity>()
    val favoriteEntity: LiveData<FavoriteEntity> = _userEntity

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _detailUser = MutableLiveData<DetailUserResponse?>()
    val detailUser: LiveData<DetailUserResponse?> = _detailUser

    fun addFavorite(user: FavoriteEntity) {
        viewModelScope.launch {
            userRepository.addFavorite(user)
        }
    }

    fun deleteFavorite(username: String) {
        viewModelScope.launch {
            userRepository.deleteFavorite(username)
        }
    }

    fun isFavorite(username: String) = userRepository.isFavorite(username)

    fun getDetailUser(username: String) {
        userRepository.getDetailUser(username).observeForever { result ->
            when (result) {
                is Result.Loading -> {
                    _isLoading.value = true
                }

                is Result.Success -> {
                    _isLoading.value = false
                    _detailUser.value = result.data
                    _userEntity.value = FavoriteEntity(0, result.data.login, result.data.avatarUrl)
                }

                is Result.Error -> {
                    _isLoading.value = false
                }

                is Result.Empty -> {
                    _isLoading.value = false
                }
            }
        }
    }
}