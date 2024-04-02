package com.dicoding.kotlinfundamental1.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Insert
    suspend fun addFavoriteUser(user: FavoriteEntity)

    @Query("SELECT * FROM user ORDER BY id ASC")
    fun getAllFavoriteUser(): LiveData<List<FavoriteEntity>>

    @Query("SELECT EXISTS(SELECT * FROM user WHERE username = :username)")
    fun isFavoriteUserExist(username: String): LiveData<Boolean>

    @Query("DELETE FROM user WHERE username = :username")
    suspend fun deleteFavoriteUser(username: String)
}