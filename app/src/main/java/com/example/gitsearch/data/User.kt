package com.example.gitsearch.data

import com.google.gson.annotations.SerializedName

data class User(@SerializedName("login") val username: String,
                @SerializedName("avatar_url") val avatarUrl: String)
