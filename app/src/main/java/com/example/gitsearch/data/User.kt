package com.example.gitsearch.data

import com.google.gson.annotations.SerializedName

data class User(@SerializedName("login") val username: String,
                @SerializedName("avatar_url") val avatarUrl: String,
                @SerializedName("name") val name: String?,
                @SerializedName("bio") val bio: String?,
                @SerializedName("followers") val followers: Int?,
                @SerializedName("following") val following: Int?,
                @SerializedName("location") val location: String?,
)

