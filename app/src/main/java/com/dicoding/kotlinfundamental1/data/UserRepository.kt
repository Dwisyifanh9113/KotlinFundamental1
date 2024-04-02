package com.dicoding.kotlinfundamental1.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dicoding.kotlinfundamental1.data.local.FavoriteDao
import com.dicoding.kotlinfundamental1.data.local.FavoriteEntity
import com.dicoding.kotlinfundamental1.data.response.DetailUserResponse
import com.dicoding.kotlinfundamental1.data.response.Users
import com.dicoding.kotlinfundamental1.data.retrofit.ApiService

class UserRepository private constructor(
    private val apiService: ApiService,
    private val favoriteDao: FavoriteDao
) {
    fun findUser(username: String): LiveData<Result<List<Users>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getUsers(username)
            val users = response.items
            if (users == null) emit(Result.Empty)
            else emit(Result.Success(users))
        } catch (e: Exception) {
            Log.d("GithubUserRepository", "getAllFavUser: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getFollowings(username: String): LiveData<Result<List<Users>>> = liveData {
        Log.d("Github User repository", "getFollowings: $username")
        emit(Result.Loading)
        try {
            val response = apiService.getFollowing(username)
            if (response.isEmpty()) emit(Result.Empty)
            else emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d("GithubUserRepository", "getFollowing: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getFollowers(username: String): LiveData<Result<List<Users>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getFollowers(username)
            if (response.isEmpty()) emit(Result.Empty)
            else emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d("GithubUserRepository", "getFollowers: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }

    }

    fun getDetailUser(username: String): LiveData<Result<DetailUserResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getDetailUser(username)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d("GithubUserRepository", "getDetailUser: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getAllFavorite(): LiveData<List<FavoriteEntity>> {
        return favoriteDao.getAllFavoriteUser()
    }

    fun isFavorite(username: String): LiveData<Boolean> {
        return favoriteDao.isFavoriteUserExist(username)
    }

    suspend fun addFavorite(user: FavoriteEntity) {
        favoriteDao.addFavoriteUser(user)
    }

    suspend fun deleteFavorite(username: String) {
        favoriteDao.deleteFavoriteUser(username)
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService,
            favUserDao: FavoriteDao
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, favUserDao)
            }.also { instance = it }
    }
}