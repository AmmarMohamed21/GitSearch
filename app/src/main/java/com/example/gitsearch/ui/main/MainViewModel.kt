package com.example.gitsearch.ui.main

import androidx.lifecycle.ViewModel
import com.example.gitsearch.data.User
import com.example.gitsearch.data.MainRepository

class MainViewModel(private val mainRepository: MainRepository)
    : ViewModel() {

    fun getUsers() = mainRepository.getUsers()
    fun searchUsers(username:String) = mainRepository.searchUsers(username)
    fun loadMore() = mainRepository.loadMore()
    fun loadUserProfile(username:String) = mainRepository.loadUserProfile(username)
    fun getUserProfile() = mainRepository.getUserProfile()
    fun clearProfileData() = mainRepository.clearProfileData()

}