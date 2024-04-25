package com.example.submissionandroiddeveloperexpert.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.model.Result
import com.example.core.data.source.remote.response.Users
import com.example.core.domain.usecase.GitHubUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val gitHubUseCase: GitHubUseCase) : ViewModel() {

    suspend fun findSearchUsers(username: String): LiveData<Result<List<Users>>> {
        return gitHubUseCase.findSearchUsers(username).asLiveData()
    }
}