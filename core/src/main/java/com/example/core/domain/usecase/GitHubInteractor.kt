package com.example.core.domain.usecase

import com.example.core.data.source.local.entity.FavoriteUserEntity
import com.example.core.data.source.remote.response.DetailUser
import com.example.core.data.source.remote.response.Users
import com.example.core.domain.model.Result
import com.example.core.domain.repository.IGitHubRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GitHubInteractor @Inject constructor(private val gitHubRepository: IGitHubRepository) :
    GitHubUseCase {

    override suspend fun findSearchUsers(username: String): Flow<Result<List<Users>>> = gitHubRepository.findSearchUsers(username)

    override suspend fun getDetailUser(username: String): Flow<Result<DetailUser>> = gitHubRepository.getDetailUser(username)

    override suspend fun getUserFollowing(username: String): Flow<Result<List<Users>>> = gitHubRepository.getUserFollowing(username)

    override suspend fun getUserFollowers(username: String): Flow<Result<List<Users>>> = gitHubRepository.getUserFollowers(username)

    override suspend fun getFavoriteUsers(): Flow<Result<List<FavoriteUserEntity>>> = gitHubRepository.getFavoriteUsers()

    override suspend fun insertFavoriteUser(user: FavoriteUserEntity) = gitHubRepository.insertFavoriteUser(user)

    override suspend fun deleteFavoriteUser(username: String) = gitHubRepository.deleteFavoriteUser(username)

    override suspend fun isUserFavorite(username: String): Flow<Result<Int>> = gitHubRepository.isUserFavorite(username)

    override fun getThemeSetting(): Flow<Boolean> = gitHubRepository.getThemeSetting()

    override suspend fun saveThemeSetting(isDarkModeActive: Boolean) = gitHubRepository.saveThemeSetting(isDarkModeActive)
}