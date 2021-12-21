package com.example.testdatabase.mainmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testdatabase.data.UserDetails
import com.example.testdatabase.repositarty.UserRepositary

class MainModel(private val repositary: UserRepositary) : ViewModel() {

    val userData = MutableLiveData<UserDetails>()

    fun getAllUserData(): List<UserDetails> {
      return  repositary.getUserRepos()
    }


}