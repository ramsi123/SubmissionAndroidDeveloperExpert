package com.example.core.data.source.local

import com.example.core.data.source.local.entity.FavoriteUserEntity
import com.example.core.data.source.local.room.GitHubDao
import com.example.core.domain.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LocalDataSource (private val gitHubDao: GitHubDao) {

    suspend fun insertFavoriteUser(user: FavoriteUserEntity) = gitHubDao.insertFavoriteUser(user)

    suspend fun deleteFavoriteUser(username: String) = gitHubDao.deleteFavoriteUser(username)

    suspend fun getFavoriteUsers(): Flow<Result<List<FavoriteUserEntity>>> {
        return flow {
            emit(Result.Loading)
            try {
                val data = gitHubDao.getFavoriteUsers()
                emit(Result.Success(data))
            } catch (e: Exception) {
                emit(Result.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun isUserFavorite(username: String): Flow<Result<Int>> {
        return flow {
            emit(Result.Loading)
            try {
                val data = gitHubDao.isUserFavorite(username)
                emit(Result.Success(data))
            } catch (e: Exception) {
                emit(Result.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}