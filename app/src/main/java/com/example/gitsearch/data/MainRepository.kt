package com.example.gitsearch.data

class MainRepository private constructor(private val usersDao: UsersDao) {

    // This may seem redundant.
    // Imagine a code which also updates and checks the backend.
//    fun addQuote(user: User) {
//        usersDao.addQuote(user)
//    }

    fun getUsers() = usersDao.getUsers()

    fun searchUsers(username: String) = usersDao.searchForUsers(username)


    companion object {
        // Singleton instantiation you already know and love
        @Volatile private var instance: MainRepository? = null

        fun getInstance(usersDao: UsersDao) =
            instance ?: synchronized(this) {
                instance ?: MainRepository(usersDao).also { instance = it }
            }
    }
}