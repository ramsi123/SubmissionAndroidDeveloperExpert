package com.example.submissionandroiddeveloperexpert.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.core.domain.usecase.GitHubUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(private val gitHubUseCase: GitHubUseCase) : ViewModel() {

    fun getThemeSetting(): LiveData<Boolean> {
        return gitHubUseCase.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            gitHubUseCase.saveThemeSetting(isDarkModeActive)
        }
    }

}