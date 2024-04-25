package com.example.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.model.Result
import com.example.core.data.source.local.entity.FavoriteUserEntity
import com.example.core.domain.usecase.GitHubUseCase

class FavoriteUserViewModel(private val gitHubUseCase: GitHubUseCase) : ViewModel() {

    suspend fun getFavoriteUsers(): LiveData<Result<List<FavoriteUserEntity>>> {
        return gitHubUseCase.getFavoriteUsers().asLiveData()
    }

}