package com.example.testdatabase.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.testdatabase.R
import com.example.testdatabase.data.UserDetails
import com.example.testdatabase.mainmodel.MainModel
import com.example.testdatabase.repositarty.UserRepositary

internal val TAG = SaveFragment::class.java.canonicalName

class SaveFragment : Fragment(), View.OnClickListener {

    private lateinit var observer: Observer<UserDetails>
    private lateinit var viewModel: MainModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_save, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            this@SaveFragment,
            ViewModelFactory(requireContext())
        ).get(MainModel::class.java)
        setValues()

        val backArrow = view.findViewById(R.id.backArrow) as ImageView
        val add = view.findViewById(R.id.saveButton) as Button

        backArrow.setOnClickListener(this)

        add.setOnClickListener {
            val name = view.findViewById(R.id.editTextTextPersonName) as EditText
            val degree = view.findViewById(R.id.editTextTextPersonName2) as EditText
            val experience = view.findViewById(R.id.editTextTextPersonName3) as EditText

            viewModel.userData.postValue(
                UserDetails(
                    experience = experience.text.toString().toInt(),
                    name = name.text.toString(),
                    degree = degree.text.toString()
                )
            )

        }
    }

    companion object {
        fun getInstance() = SaveFragment()
    }

    override fun onClick(view: View) {

        when (view.id) {
            R.id.backArrow -> {
                requireActivity().supportFragmentManager.popBackStack()
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, AddFragment.getInstance())
                    .commit()
            }
        }
    }

    private fun setValues() {
        observer = Observer {
            val userRepositary = UserRepositary.getInstance(requireContext())
            userRepositary.insertUserDetails(userDetails = it)
            Log.i(TAG, "setValues: $userRepositary")
        }
        viewModel.userData.observe(viewLifecycleOwner, observer)
        val userDetails = viewModel.getAllUserData()
        Log.i(TAG, "setValues: $userDetails")

    }

}