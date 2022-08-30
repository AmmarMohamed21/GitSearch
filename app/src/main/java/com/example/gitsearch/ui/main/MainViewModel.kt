package com.example.gitsearch.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gitsearch.data.User
import com.example.gitsearch.data.MainRepository
import com.example.gitsearch.data.UsersList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val mainRepository: MainRepository)
    : ViewModel() {

    private val users = MutableLiveData<List<User>>()
    private val userProfile = MutableLiveData<User>()
    private val usersList = mutableListOf<User>()

    private var _currentPageNo=1
    private var _currentSearchedName=""

    fun getUsers() = users as LiveData<List<User>>
    fun searchUsers(username:String, pageNo:Int, isLoadMore:Boolean = false) {
        _currentSearchedName=username
        val queryMap = mapOf<String,String>("q" to username, "page" to pageNo.toString())
        val response = mainRepository.getSearchUsers(queryMap)
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
            }
        })
    }
    fun loadMore() {
        _currentPageNo++
        searchUsers(_currentSearchedName, _currentPageNo, true)
    }

    fun loadUserProfile(username:String){
        val response:Call<User> = mainRepository.loadUserProfile(username)
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

    fun getUserProfile() = userProfile as LiveData<User>
}