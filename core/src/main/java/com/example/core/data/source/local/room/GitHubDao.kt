package com.example.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core.data.source.local.entity.FavoriteUserEntity

@Dao
interface GitHubDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavoriteUser(user: FavoriteUserEntity)

    @Query("DELETE FROM favorite_user WHERE username = :username")
    suspend fun deleteFavoriteUser(username: String)

    @Query("SELECT * FROM favorite_user")
    fun getFavoriteUsers(): List<FavoriteUserEntity>

    @Query("SELECT EXISTS(SELECT * FROM favorite_user WHERE username = :username)")
    suspend fun isUserFavorite(username: String): Int

}