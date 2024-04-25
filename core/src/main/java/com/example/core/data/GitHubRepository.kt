package com.example.core.data

import com.example.core.data.source.local.LocalDataSource
import com.example.core.data.source.local.entity.FavoriteUserEntity
import com.example.core.data.source.remote.RemoteDataSource
import com.example.core.data.source.remote.response.DetailUser
import com.example.core.data.source.remote.response.Users
import com.example.core.domain.model.Result
import com.example.core.domain.repository.IGitHubRepository
import com.example.core.util.SettingPreferences
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GitHubRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val pref: SettingPreferences
) : IGitHubRepository {

    override suspend fun findSearchUsers(
        username: String
    ): Flow<Result<List<Users>>> {
        return remoteDataSource.getSearchUsers(username)
    }

    override suspend fun getDetailUser(
        username: String
    ): Flow<Result<DetailUser>> {
        return remoteDataSource.getDetailUser(username)
    }

    override suspend fun getUserFollowing(username: String): Flow<Result<List<Users>>> {
        return remoteDataSource.getUserFollowing(username)
    }

    override suspend fun getUserFollowers(username: String): Flow<Result<List<Users>>> {
        return remoteDataSource.getUserFollowers(username)
    }

    override suspend fun getFavoriteUsers(): Flow<Result<List<FavoriteUserEntity>>> {
        return localDataSource.getFavoriteUsers()
    }

    override suspend fun insertFavoriteUser(user: FavoriteUserEntity) {
        localDataSource.insertFavoriteUser(user)
    }

    override suspend fun deleteFavoriteUser(username: String) {
        localDataSource.deleteFavoriteUser(username)
    }

    override suspend fun isUserFavorite(username: String): Flow<Result<Int>> {
        return localDataSource.isUserFavorite(username)
    }

    override fun getThemeSetting(): Flow<Boolean> {
        return pref.getThemeSetting()
    }

    override suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        pref.saveThemeSetting(isDarkModeActive)
    }

    companion object {
        @Volatile
        private var instance: GitHubRepository? = null
        fun getInstance(
            remoteDataSource: RemoteDataSource,
            localDataSource: LocalDataSource,
            pref: SettingPreferences
        ): GitHubRepository =
            instance ?: synchronized(this) {
                instance ?: GitHubRepository(remoteDataSource, localDataSource, pref)
            }.also { instance = it }
    }

}