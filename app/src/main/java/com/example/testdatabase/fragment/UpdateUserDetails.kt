package com.example.testdatabase.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.testdatabase.R
import com.example.testdatabase.data.UserDetails
import com.example.testdatabase.repositarty.UserRepositary

class UpdateUserDetails : Fragment() {

    private var userDetailsId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val backButton1 = view.findViewById<ImageView>(R.id.backArrow1)
        val userName = view.findViewById<EditText>(R.id.name)
        val userDegree = view.findViewById<EditText>(R.id.degree)
        val expr = view.findViewById<EditText>(R.id.experience)
        val updateButton = view.findViewById<Button>(R.id.updateButton)


        arguments?.let {
            userName.setText(it.getString("Name", ""))
            userDegree.setText(it.getString("Degree", ""))
            expr.setText(it.getInt("Expr", 0).toString())
            userDetailsId = it.getInt("ID")
        }


        updateButton.setOnClickListener {

            val user = UserDetails(
                id = userDetailsId,
                name = userName.text.toString(),
                degree = userDegree.text.toString(),
                experience = if (expr.text.isEmpty()) {
                    "0"
                } else {
                    expr.text.toString()
                }.toInt()
            )
            val repo = UserRepositary.getInstance(requireContext())
            repo.updateUserDetails(userDetails = user)

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, AddUserDetailsFragment.getInstance(),"addFragment")
                .commit()
            message("Details updated")
        }

        backButton1.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, AddUserDetailsFragment.getInstance(),"addFragment")
                .commit()
        }

    }


    private fun message(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun getInstance() = UpdateUserDetails()
    }
}