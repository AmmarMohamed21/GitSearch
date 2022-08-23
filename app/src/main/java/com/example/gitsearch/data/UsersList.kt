package com.example.gitsearch.data

import com.google.gson.annotations.SerializedName

data class UsersList(@SerializedName("items") val uList : List<User>)
