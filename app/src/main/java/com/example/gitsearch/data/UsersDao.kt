package com.example.gitsearch.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gitsearch.services.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersDao constructor(private val retrofitService: RetrofitService) {
    private val usersList = mutableListOf<User>()
    private val users = MutableLiveData<List<User>>()

    init {
        users.value = usersList
    }

//    fun addQuote(user: User) {
//        usersList.add(user)
//        users.value = usersList
//    }

    fun searchForUsers(searchedName: String)
    {
        val response = retrofitService.getSearchUsers()
        response.enqueue(object : Callback<UsersList> {
            override fun onResponse(call: Call<UsersList>, response: Response<UsersList>) {
                users.postValue(response.body()?.uList)
            }

            override fun onFailure(call: Call<UsersList>, t: Throwable) {
                //errorMessage.postValue(t.message)
            }
        })
    }

    fun getUsers() = users as LiveData<List<User>>
}