package com.example.submissionandroiddeveloperexpert.splashscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.usecase.GitHubUseCase

class SplashScreenViewModel(private val gitHubUseCase: GitHubUseCase) : ViewModel() {

    fun getThemeSetting(): LiveData<Boolean> {
        return gitHubUseCase.getThemeSetting().asLiveData()
    }

}