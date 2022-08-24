package com.example.gitsearch.data

import android.util.Log

class MainRepository private constructor(private val usersDao: UsersDao) {

    private var _currentPageNo=1
    private var _currentSearchedName=""

    fun getUsers() = usersDao.getUsers()

    fun getUserProfile() = usersDao.getUserProfile()

    fun searchUsers(username: String) {
        _currentPageNo=1
        _currentSearchedName=username
        usersDao.searchForUsers(username, "1")
    }
    fun loadMore() {
        _currentPageNo++
        usersDao.searchForUsers(_currentSearchedName, _currentPageNo.toString(), isLoadMore = true)
    }

    fun loadUserProfile(username: String) = usersDao.getUserProfile(username)

    fun clearProfileData() = usersDao.clearProfileData()



    companion object {
        // Singleton instantiation you already know and love
        @Volatile private var instance: MainRepository? = null

        fun getInstance(usersDao: UsersDao) =
            instance ?: synchronized(this) {
                instance ?: MainRepository(usersDao).also { instance = it }
            }
    }
}