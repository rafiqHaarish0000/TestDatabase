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
import com.example.testdatabase.database.UserDetails
import com.example.testdatabase.viewmodel.MainModel
import com.example.testdatabase.repositarty.UserRepositary
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.list_item.*
import java.util.*

internal val TAG = SaveUserDetailsFragment::class.java.canonicalName

class SaveUserDetailsFragment : Fragment(), View.OnClickListener {

    private lateinit var observer: Observer<UserDetails>
    private lateinit var viewModel: MainModel
    private lateinit var addbtn:Button
    private lateinit var backArrowBtn:ImageView

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
            this@SaveUserDetailsFragment,
            ViewModelFactory(requireContext())
        ).get(MainModel::class.java)
        setValues()

        backArrowBtn = view.findViewById(R.id.backArrow) as ImageView
        addbtn = view.findViewById(R.id.saveButton) as Button

        backArrowBtn.setOnClickListener(this)
        addbtn.setOnClickListener{

            if(validationInfo(view)){

                val name = view.findViewById(R.id.editTextTextPersonName) as EditText
                val degree = view.findViewById(R.id.editTextTextPersonName2) as EditText
                val experience = view.findViewById(R.id.editTextTextPersonName3) as EditText

                viewModel.userData.postValue(
                    UserDetails(
                        experience = if (experience.text.toString().isEmpty()) {
                            "0"
                        } else {
                            experience.text.toString()
                        }.toInt(),
                        name = name.text.toString(),
                        degree = degree.text.toString()
                    )
                )
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, AddUserDetailsFragment.getInstance(),"addFragment")
                    .commit()
                message("Your details has been saved..!")
            }
        }
    }

    companion object {
        fun getInstance() = SaveUserDetailsFragment()
    }

    override fun onClick(view: View) {

        when (view.id) {
            R.id.backArrow -> {
                requireActivity().supportFragmentManager.popBackStack()
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, AddUserDetailsFragment.getInstance(),"addFragment")
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

    }

    private fun validationInfo(view: View): Boolean {

        val name = view.findViewById(R.id.editTextTextPersonName) as EditText
        val degree = view.findViewById(R.id.editTextTextPersonName2) as EditText
        val experience = view.findViewById(R.id.editTextTextPersonName3) as EditText
        val listofvalues = arrayListOf(name, degree, experience)

        var flag = true

        if (name.text.toString().isEmpty()) {
            name.error = "Username required"
            flag = false
        }
        if (degree.text.toString().isEmpty()) {
            degree.error = "Degree required"
            flag = false
        }
        if (experience.text.toString().isEmpty()) {
            experience.error = "Experience required"
            flag = false
        }

        for (item in listofvalues) {
            if (item.text.isEmpty()) {
                item.error = "Required fields"
                flag = false
            }
        }
        return flag
    }

    private fun message(toast: String) {
        Toast.makeText(requireContext(), toast, Toast.LENGTH_SHORT).show()
    }

}