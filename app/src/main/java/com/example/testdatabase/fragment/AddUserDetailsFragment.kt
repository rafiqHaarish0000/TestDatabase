package com.example.testdatabase.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testdatabase.R
import com.example.testdatabase.adapter.UserListAdapter
import com.example.testdatabase.data.UserDetails
import com.example.testdatabase.mainmodel.MainModel
import com.example.testdatabase.repositarty.UserRepositary
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AddUserDetailsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var listAdapter: UserListAdapter
    private lateinit var viewmodel: MainModel

    private val userListListener: UserListAdapter.onItemClickListener =
        object : UserListAdapter.onItemClickListener {
            override fun getOnClickListener(position: Int) {
                Log.i(TAG, "getOnClickListener: $position")
            }

            override fun onUpdateUserDetails(item: UserDetails) {
                val bundle = Bundle()
                bundle.putString("Name", item.name)
                bundle.putString("Degree", item.degree)
                bundle.putInt("Expr", item.experience.toString().toInt())

                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, UpdateUserDetails::class.java, bundle, "")
                    .commit()
            }

            override fun onDeleteUserDetails(item: UserDetails) {
                deleteUserDetails(item)
            }

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val addButton = view.findViewById<FloatingActionButton>(R.id.addButton)
        viewmodel = ViewModelProvider(
            this@AddUserDetailsFragment,
            ViewModelFactory(requireContext())
        ).get(MainModel::class.java)

        if (getAllListValues(view)) {
            recyclerView.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.GONE
        }

        addButton.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, SaveUserDetailsFragment.getInstance())
                .commit()
        }
    }

    companion object {
        fun getInstance() = AddUserDetailsFragment()
    }

    private fun getAllListValues(view: View): Boolean {
        val userRepositary = UserRepositary.getInstance(requireContext())

//        userRepositary.getUserRepos()

        listAdapter = UserListAdapter(
            viewmodel.getAllUserData(),
            requireContext(),
            callback = userListListener
        )

        recyclerView = view.findViewById(R.id.recyclerList)

        recyclerView.let {
            recyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            recyclerView.adapter = listAdapter
        }
        return true
    }

    private fun deleteUserDetails(item: UserDetails) {
        val userRepo = UserRepositary.getInstance(requireContext())
        userRepo.deleteUserDetails(userDetails = item)
    }

    override fun onResume() {
        super.onResume()
    }

}