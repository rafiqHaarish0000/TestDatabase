package com.example.testdatabase.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testdatabase.database.UserDetails
import com.example.testdatabase.repositarty.UserRepositary

class MainModel(private val repositary: UserRepositary) : ViewModel() {

    val userData = MutableLiveData<UserDetails>()

    fun getAllUserData(): ArrayList<UserDetails> {
      return  repositary.getUserRepos() as ArrayList
    }


}