package com.example.core.data.source.remote.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class GitHubResponse(

	@field:SerializedName("items")
	val items: List<Users>
)

@Keep
data class Users(

	@field:SerializedName("login")
	val username: String,

	@field:SerializedName("avatar_url")
	val avatarUrl: String
)

@Keep
data class DetailUser(

	@field:SerializedName("login")
	val username: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("bio")
	val bio: String,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("following")
	val following: Int,

	@field:SerializedName("followers")
	val followers: Int
)
