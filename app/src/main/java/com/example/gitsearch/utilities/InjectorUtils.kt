package com.example.gitsearch.utilities

import com.example.gitsearch.data.Database
import com.example.gitsearch.data.MainRepository
import com.example.gitsearch.ui.main.MainViewModelFactory

object InjectorUtils {

    // This will be called from QuotesActivity
    fun provideQuotesViewModelFactory(): MainViewModelFactory {
        // ViewModelFactory needs a repository, which in turn needs a DAO from a database
        // The whole dependency tree is constructed right here, in one place
        val mainRepository = MainRepository.getInstance(Database.getInstance().usersDao)
        return MainViewModelFactory(mainRepository)
    }
}