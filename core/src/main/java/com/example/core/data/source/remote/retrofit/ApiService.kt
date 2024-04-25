package com.example.core.data.source.remote.retrofit

import com.example.core.data.source.remote.response.DetailUser
import com.example.core.data.source.remote.response.GitHubResponse
import com.example.core.data.source.remote.response.Users
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    suspend fun getSearchUsers(
        @Query("q") query: String
    ): GitHubResponse

    @GET("users/{username}")
    suspend fun getDetailUser(
        @Path("username") username: String
    ): DetailUser

    @GET("users/{username}/following")
    suspend fun getUserFollowing(
        @Path("username") username: String
    ): List<Users>

    @GET("users/{username}/followers")
    suspend fun getUserFollowers(
        @Path("username") username: String
    ): List<Users>

}