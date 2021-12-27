package com.example.testdatabase.repositarty

import android.content.Context
import com.example.testdatabase.data.AppDatabase
import com.example.testdatabase.data.UserDetails
import java.util.concurrent.Callable
import java.util.concurrent.Executors

class UserRepositary(context: Context) {

    private val userDao = AppDatabase.createDataBase(context).userDao()

    fun getUserRepos(): List<UserDetails> {
        return Executors.newSingleThreadExecutor().submit(Callable {
            userDao.getAll()
        }).get()
    }

    fun insertUserDetails(userDetails: UserDetails) {
        Thread {
            userDao.getInsertData(userDetails)
        }.start()
    }

    fun deleteUserDetails(userDetails: UserDetails){
        Thread{
            userDao.getDeleteData(userDetails)
        }.start()
    }

    fun updateUserDetails(userDetails: UserDetails){
        return Executors.newSingleThreadExecutor().submit(Callable {
                userDao.getUpdateUserDetails(userDetails)
        }).get()
    }

    companion object {
        fun getInstance(context: Context) = UserRepositary(context)
    }
}