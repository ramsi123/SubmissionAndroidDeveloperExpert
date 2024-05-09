package com.example.submissionandroiddeveloperexpert.follow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.model.Result
import com.example.core.data.source.remote.response.Users
import com.example.core.domain.usecase.GitHubUseCase

class FollowViewModel(private val gitHubUseCase: GitHubUseCase) : ViewModel() {

    suspend fun getUserFollowing(username: String): LiveData<Result<List<Users>>> {
        return gitHubUseCase.getUserFollowing(username).asLiveData()
    }

    suspend fun getUserFollowers(username: String): LiveData<Result<List<Users>>> {
        return gitHubUseCase.getUserFollowers(username).asLiveData()
    }

}