package com.dicoding.kotlinfundamental1.data.retrofit

import com.dicoding.kotlinfundamental1.data.response.DetailUserResponse
import com.dicoding.kotlinfundamental1.data.response.GithubResponse
import com.dicoding.kotlinfundamental1.data.response.Users
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getUsers(
        @Query("q") username: String,
    ): Call<GithubResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String,
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String,
    ): Call<List<Users>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String,
    ): Call<List<Users>>
}