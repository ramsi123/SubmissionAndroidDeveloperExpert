package com.example.submissionandroiddeveloperexpert.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.core.domain.model.Result
import com.example.core.data.source.local.entity.FavoriteUserEntity
import com.example.core.data.source.remote.response.DetailUser
import com.example.core.domain.usecase.GitHubUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailUserViewModel @Inject constructor(private val gitHubUseCase: GitHubUseCase) : ViewModel() {

    suspend fun getDetailUser(username: String): LiveData<Result<DetailUser>> {
        return gitHubUseCase.getDetailUser(username).asLiveData()
    }

    fun insertFavoriteUser(user: FavoriteUserEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            gitHubUseCase.insertFavoriteUser(user)
        }
    }

    fun deleteFavoriteUser(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            gitHubUseCase.deleteFavoriteUser(username)
        }
    }

    suspend fun isUserFavorite(username: String): LiveData<Result<Int>> {
        return gitHubUseCase.isUserFavorite(username).asLiveData()
    }

}