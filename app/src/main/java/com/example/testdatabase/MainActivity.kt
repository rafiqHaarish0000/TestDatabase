package com.example.testdatabase

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.testdatabase.fragment.AddUserDetailsFragment
import com.example.testdatabase.fragment.SaveUserDetailsFragment
import com.example.testdatabase.fragment.UpdateUserDetails

class MainActivity : AppCompatActivity() {
    private lateinit var bundle: Bundle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val arrayofFragment = arrayListOf<Fragment>(
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
        fragmentTransaction.replace(R.id.fragmentContainer, fragment, "addFragment")
        fragmentTransaction.commit()
    }

    override fun onDestroy() {
        bundle = Bundle()
        bundle.getString("key")
        super.onDestroy()
    }

    override fun onPause() {
        super.onPause()
        bundle.getString("key")
    }

}