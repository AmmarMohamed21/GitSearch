package com.example.gitsearch.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gitsearch.services.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersDao constructor(private val retrofitService: RetrofitService) {
    private var usersList = mutableListOf<User>()
    private val users = MutableLiveData<List<User>>()
    private val userProfile = MutableLiveData<User>()

    init {
        users.value = usersList
    }


    fun searchForUsers(queryMap: Map<String, String>): Call<UsersList> {
        return retrofitService.getSearchUsers(queryMap)
    }

    fun getUserProfile(username: String): Call<User> {
        return retrofitService.getUserProfile(username)
    }


}