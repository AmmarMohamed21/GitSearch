package com.example.gitsearch.data

import retrofit2.Call

class MainRepository private constructor(private val usersDao: UsersDao) {


    fun loadUserProfile(username: String): Call<User> {
        return usersDao.getUserProfile(username)
    }

    fun getSearchUsers(queryMap: Map<String, String>): Call<UsersList>
    {
        return usersDao.searchForUsers(queryMap)
    }


    companion object {
        // Singleton instantiation you already know and love
        @Volatile private var instance: MainRepository? = null

        fun getInstance(usersDao: UsersDao) =
            instance ?: synchronized(this) {
                instance ?: MainRepository(usersDao).also { instance = it }
            }
    }
}