package com.example.submissionandroiddeveloperexpert.di

import com.example.core.domain.usecase.GitHubUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteModuleDependencies {

    fun gitHubUseCase(): GitHubUseCase
}