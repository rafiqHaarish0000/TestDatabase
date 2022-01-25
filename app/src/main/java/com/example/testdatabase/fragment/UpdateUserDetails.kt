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
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.testdatabase.R
import com.example.testdatabase.database.UserDetails
import com.example.testdatabase.databinding.FragmentUpdateBinding
import com.example.testdatabase.repositarty.UserRepositary

class UpdateUserDetails : Fragment() {
lateinit var binding:FragmentUpdateBinding
    private var userDetailsId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpdateBinding.inflate(layoutInflater)
        return binding.root
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
            Navigation.findNavController(view).navigate(R.id.move_to_save_fragment)
            message("Data saved..!")
        }

        backButton1.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.move_to_add_fragment)
        }

    }


    private fun message(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

}