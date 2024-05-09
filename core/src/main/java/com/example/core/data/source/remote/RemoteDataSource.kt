package com.example.core.data.source.remote

import com.example.core.data.source.remote.response.DetailUser
import com.example.core.data.source.remote.response.Users
import com.example.core.data.source.remote.retrofit.ApiService
import com.example.core.domain.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource (private val apiService: ApiService) {

    suspend fun getSearchUsers(query: String): Flow<Result<List<Users>>> {
        return flow {
            emit(Result.Loading)
            try {
                val response = apiService.getSearchUsers(query)
                val items = response.items
                emit(Result.Success(items))
            } catch (e: Exception) {
                emit(Result.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getDetailUser(username: String): Flow<Result<DetailUser>> {
        return flow {
            emit(Result.Loading)
            try {
                val response = apiService.getDetailUser(username)
                emit(Result.Success(response))
            } catch (e: Exception) {
                emit(Result.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getUserFollowing(username: String): Flow<Result<List<Users>>> {
        return flow {
            emit(Result.Loading)
            try {
                val response = apiService.getUserFollowing(username)
                emit(Result.Success(response))
            } catch (e: Exception) {
                emit(Result.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getUserFollowers(username: String): Flow<Result<List<Users>>> {
        return flow {
            emit(Result.Loading)
            try {
                val response = apiService.getUserFollowers(username)
                emit(Result.Success(response))
            } catch (e: Exception) {
                emit(Result.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}