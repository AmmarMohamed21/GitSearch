package com.example.gitsearch.data

import com.example.gitsearch.services.RetrofitService

class Database private constructor() {

    var usersDao = UsersDao(RetrofitService.getInstance())
        private set

    companion object {
        // @Volatile - Writes to this property are immediately visible to other threads
        @Volatile private var instance: Database? = null

        // The only way to get hold of the FakeDatabase object
        fun getInstance() =
        // Already instantiated? - return the instance
            // Otherwise instantiate in a thread-safe manner
            instance ?: synchronized(this) {
                // If it's still not instantiated, finally create an object
                // also set the "instance" property to be the currently created one
                instance ?: Database().also { instance = it }
            }
    }
}