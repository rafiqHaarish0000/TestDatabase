package com.example.testdatabase.fragment

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testdatabase.viewmodel.MainModel
import com.example.testdatabase.repositarty.UserRepositary

class ViewModelFactory internal constructor(private val context: Context) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.canonicalName == MainModel::class.java.canonicalName) {
            return MainModel(UserRepositary.getInstance(context = context)) as T
        } else {
            throw Exception("No Data available")
        }
    }
}
