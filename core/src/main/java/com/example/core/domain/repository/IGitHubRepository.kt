package com.example.core.domain.repository

import com.example.core.data.source.local.entity.FavoriteUserEntity
import com.example.core.data.source.remote.response.DetailUser
import com.example.core.data.source.remote.response.Users
import com.example.core.domain.model.Result
import kotlinx.coroutines.flow.Flow

interface IGitHubRepository {
    suspend fun findSearchUsers(username: String): Flow<Result<List<Users>>>
    suspend fun getDetailUser(username: String): Flow<Result<DetailUser>>
    suspend fun getUserFollowing(username: String): Flow<Result<List<Users>>>
    suspend fun getUserFollowers(username: String): Flow<Result<List<Users>>>
    suspend fun getFavoriteUsers(): Flow<Result<List<FavoriteUserEntity>>>
    suspend fun insertFavoriteUser(user: FavoriteUserEntity)
    suspend fun deleteFavoriteUser(username: String)
    suspend fun isUserFavorite(username: String): Flow<Result<Int>>
    fun getThemeSetting(): Flow<Boolean>
    suspend fun saveThemeSetting(isDarkModeActive: Boolean)
}