package com.example.testdatabase

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.testdatabase.fragment.AddUserDetailsFragment
import com.example.testdatabase.fragment.SaveUserDetailsFragment
import com.example.testdatabase.fragment.TAG
import com.example.testdatabase.fragment.UpdateUserDetails
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private lateinit var bundle: Bundle
    private var arrayofFragment = arrayListOf<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        arrayofFragment = arrayListOf<Fragment>(
            SaveUserDetailsFragment.getInstance(),
            AddUserDetailsFragment.getInstance(),
            UpdateUserDetails.getInstance()
        )
        if (savedInstanceState == null) {
            isStart(AddUserDetailsFragment.getInstance(), this)
        } else {
            savedInstanceState.let {
                if (it.getString("key", "").isNotEmpty()) {
                    for (item in arrayofFragment) {
                        if (it.getString("Key").equals(item.javaClass.canonicalName)) {
                            isStart(item, this)
                        }
                    }
                }
            }
        }

    }

    private fun isStart(fragment: Fragment, context: Context) {
        val supportFragmentManager = supportFragmentManager
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragmentContainer, fragment, "addFragment")
        fragmentTransaction.commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        bundle = Bundle()
        bundle.getString("key")
        for (item in arrayofFragment) {
            if (bundle.getString("Key").equals(item.javaClass.canonicalName)) {
                isStart(item, this)
                Log.i(TAG, "onDestroy: $item")
            }
        }
    }

    override fun onPause() {
        super.onPause()
        bundle = Bundle()
        bundle.getString("key")
        for (item in arrayofFragment) {
            if (bundle.getString("Key").equals(item.javaClass.canonicalName)) {
                isStart(item, this)
                Log.i(TAG, "onDestroy: $item")
            }
        }
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
        showExitAlert("Exit", "Are you want to exit from this application?")

    }



}
