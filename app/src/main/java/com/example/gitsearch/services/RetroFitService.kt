package com.example.gitsearch.services

import com.example.gitsearch.data.User
import com.example.gitsearch.data.UsersList
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface RetrofitService {
    @GET("search/users")
    fun getSearchUsers(@QueryMap options : Map<String,String>): Call<UsersList>

    @GET("users/{username}")
    fun getUserProfile(@Path("username") username:String): Call<User>

    companion object {

        var retrofitService: RetrofitService? = null

        //Create the Retrofit service instance using the retrofit.
        fun getInstance(): RetrofitService {

            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.github.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}