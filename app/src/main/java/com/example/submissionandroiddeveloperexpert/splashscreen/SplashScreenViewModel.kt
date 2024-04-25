package com.example.submissionandroiddeveloperexpert.splashscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.usecase.GitHubUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(private val gitHubUseCase: GitHubUseCase) : ViewModel() {

    fun getThemeSetting(): LiveData<Boolean> {
        return gitHubUseCase.getThemeSetting().asLiveData()
    }

}