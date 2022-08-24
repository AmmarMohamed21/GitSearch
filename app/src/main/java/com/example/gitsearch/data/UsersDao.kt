package com.example.gitsearch.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.repackaged.com.google.common.collect.ImmutableMap
import com.example.gitsearch.services.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.QueryMap

class UsersDao constructor(private val retrofitService: RetrofitService) {
    private var usersList = mutableListOf<User>()
    private val users = MutableLiveData<List<User>>()
    private val userProfile = MutableLiveData<User>()

    init {
        users.value = usersList
    }


    fun searchForUsers(searchedName: String, pageNo:String, isLoadMore:Boolean = false)
    {
        val queryMap = mapOf<String,String>("q" to searchedName, "page" to pageNo)
        val response = retrofitService.getSearchUsers(queryMap)
        response.enqueue(object : Callback<UsersList> {
            override fun onResponse(call: Call<UsersList>, response: Response<UsersList>) {
                if(!isLoadMore)
                {
                   usersList.clear()
                }
                response.body()?.uList?.let { usersList.addAll(it) }
                users.postValue(usersList)
            }

            override fun onFailure(call: Call<UsersList>, t: Throwable) {
                //errorMessage.postValue(t.message)
            }
        })
    }

    fun getUserProfile(username: String)
    {
        val response = retrofitService.getUserProfile(username)
        response.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                userProfile.postValue(response.body())
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                val errorUser:User = User("Check your internet connection and try again.","","","",0,0,"")
                userProfile.postValue(errorUser)
            }
        })
    }

    fun clearProfileData(){
        val cleanUser:User = User("","","Loading...","",0,0,"")
        userProfile.postValue(cleanUser)
    }

    fun getUsers() = users as LiveData<List<User>>
    fun getUserProfile () = userProfile as LiveData<User>

}