package com.example.testdatabase

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.testdatabase.databinding.ActivityMainBinding
import com.example.testdatabase.fragment.AddUserDetailsFragment
import com.example.testdatabase.fragment.SaveUserDetailsFragment
import com.example.testdatabase.fragment.TAG
import com.example.testdatabase.fragment.UpdateUserDetails
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navHost.navController
    }

    private fun showExitAlert(title: String, description: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(description)
        builder.setPositiveButton("Exit") { dialog, which ->
            dialog.dismiss()
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            startActivity(intent)
        }
        builder.setNegativeButton("Cancel") { dialog, which ->
            dialog.dismiss()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
        override fun onBackPressed() {
            Log.i(TAG, "onBackPressed: " + "success")
            showExitAlert("Exit","Do you want to close this application?")
        }

    }


