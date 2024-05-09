package com.example.submissionandroiddeveloperexpert.di

import com.example.core.domain.usecase.GitHubInteractor
import com.example.core.domain.usecase.GitHubUseCase
import com.example.submissionandroiddeveloperexpert.detail.DetailUserViewModel
import com.example.submissionandroiddeveloperexpert.follow.FollowViewModel
import com.example.submissionandroiddeveloperexpert.main.MainViewModel
import com.example.submissionandroiddeveloperexpert.settings.SettingViewModel
import com.example.submissionandroiddeveloperexpert.splashscreen.SplashScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<GitHubUseCase> { GitHubInteractor(get()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { FollowViewModel(get()) }
    viewModel { DetailUserViewModel(get()) }
    viewModel { SettingViewModel(get()) }
    viewModel { SplashScreenViewModel(get()) }
}