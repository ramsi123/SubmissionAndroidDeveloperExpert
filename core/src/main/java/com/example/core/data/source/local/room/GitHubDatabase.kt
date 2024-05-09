package com.example.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.core.data.source.local.entity.FavoriteUserEntity

@Database(entities = [FavoriteUserEntity::class], version = 1, exportSchema = true)
abstract class GitHubDatabase : RoomDatabase() {

    abstract fun gitHubDao(): GitHubDao
}