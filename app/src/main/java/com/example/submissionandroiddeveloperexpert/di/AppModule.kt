package com.example.submissionandroiddeveloperexpert.di

import com.example.core.domain.usecase.GitHubInteractor
import com.example.core.domain.usecase.GitHubUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun provideGitHubUseCase(gitHubInteractor: GitHubInteractor): GitHubUseCase
}