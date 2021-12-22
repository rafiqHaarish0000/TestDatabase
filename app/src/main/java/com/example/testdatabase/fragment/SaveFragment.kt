package com.example.testdatabase.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.testdatabase.R
import com.example.testdatabase.data.UserDetails
import com.example.testdatabase.mainmodel.MainModel
import com.example.testdatabase.repositarty.UserRepositary
import kotlinx.android.synthetic.main.list_item.*
import java.util.*

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

            getValidation(view)
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
//        val userDetails = viewModel.getAllUserData()
//        Log.i(TAG, "setValues: $userDetails")

    }

    private fun message(toast: String) {
        Toast.makeText(requireContext(), toast, Toast.LENGTH_SHORT).show()
    }


    private fun getValidation(view: View) {
        val name = view.findViewById(R.id.editTextTextPersonName) as EditText
        val degree = view.findViewById(R.id.editTextTextPersonName2) as EditText
        val experience = view.findViewById(R.id.editTextTextPersonName3) as EditText
        val listOfValues = arrayListOf(name, degree, experience)

        if (getDetails(listOfValues)) {
            if (validationInfo(view)) {
                getData(view)
            }
        }

    }
    private fun getData(view: View) {
        val name = view.findViewById(R.id.editTextTextPersonName) as EditText
        val degree = view.findViewById(R.id.editTextTextPersonName2) as EditText
        val experience = view.findViewById(R.id.editTextTextPersonName3) as EditText

        viewModel.userData.postValue(
            UserDetails(
                experience = if (experience.text.isEmpty()) {
                    "0"
                } else {
                    experience.text.toString()
                }.toInt(),
                name = name.text.toString(),
                degree = degree.text.toString()
            )
        )
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, AddFragment.getInstance())
            .commit()
        message("Your details has been saved..!")
    }

    private fun getDetails(listOfValues: ArrayList<EditText>): Boolean {
        var flag = true
        for (item in listOfValues) {
            if (item.text.isEmpty()) {
                item.error = "Required invalid"
                flag = false
            } else {
                message("Your details is invalid")
            }
        }
        return flag
    }

    private fun validationInfo(view: View): Boolean {
        val name = view.findViewById(R.id.editTextTextPersonName) as EditText
        val degree = view.findViewById(R.id.editTextTextPersonName2) as EditText
        val experience = view.findViewById(R.id.editTextTextPersonName3) as EditText
        var flag = true

        if (name.text.toString().isEmpty()) {
            name.error = "Username required"
            flag = false
        }
        if (degree.text.toString().isEmpty()) {
            name.error = "Degree required"
            flag = false
        }
        if (experience.text.toString().isEmpty()) {
            name.error = "Experience required"
            flag = false
        }

        return flag
    }

}