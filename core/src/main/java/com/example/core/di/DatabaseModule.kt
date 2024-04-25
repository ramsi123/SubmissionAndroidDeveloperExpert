package com.example.core.di

import android.content.Context
import androidx.room.Room
import com.example.core.data.source.local.room.GitHubDao
import com.example.core.data.source.local.room.GitHubDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): GitHubDatabase = Room.databaseBuilder(
        context,
        GitHubDatabase::class.java, "News.db"
    ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideGitHubDao(database: GitHubDatabase): GitHubDao = database.gitHubDao()
}