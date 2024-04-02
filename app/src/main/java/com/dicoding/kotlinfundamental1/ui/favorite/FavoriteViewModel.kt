package com.dicoding.kotlinfundamental1.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.kotlinfundamental1.data.UserRepository
import kotlinx.coroutines.launch

class FavoriteViewModel (private val userRepository: UserRepository) : ViewModel() {
    fun getAllFavorite() = userRepository.getAllFavorite()
    fun deleteAllFavorite(username: String) {
        viewModelScope.launch {
            userRepository.deleteFavorite(username)
        }
    }
}