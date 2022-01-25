package com.example.testdatabase.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testdatabase.R
import com.example.testdatabase.adapter.UserListAdapter
import com.example.testdatabase.database.UserDetails
import com.example.testdatabase.databinding.FragmentAddBinding
import com.example.testdatabase.repositarty.UserRepositary
import com.example.testdatabase.viewmodel.MainModel

class AddUserDetailsFragment : Fragment() {

    lateinit var binding: FragmentAddBinding
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
                bundle.putInt("ID", item.id.toString().toInt())


                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.fragmentContainerView,
                        UpdateUserDetails::class.java,
                        bundle,
                        "updateFragment"
                    )
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
    ): View {
        binding = FragmentAddBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        searchBox(view)
        viewmodel = ViewModelProvider(
            this@AddUserDetailsFragment,
            ViewModelFactory(requireContext())
        ).get(MainModel::class.java)

        if (getAllListValues(view)) {
            recyclerView.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.GONE
        }

        binding.addbtn.setOnClickListener {

            Navigation.findNavController(view).navigate(R.id.move_to_save_fragment)

        }

    }


    private fun getAllListValues(view: View): Boolean {
        listAdapter = UserListAdapter(
            requireContext(),
            callback = userListListener
        )
        recyclerView = binding.recyclerList

        recyclerView.let {
            recyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            recyclerView.adapter = listAdapter
        }
        listAdapter.resetView(viewmodel.getAllUserData())
        return true
    }

    private fun deleteUserDetails(item: UserDetails) {
        val userRepo = UserRepositary.getInstance(requireContext())
        userRepo.deleteUserDetails(userDetails = item)
    }

//    private fun searchBox(view: View) {
//        binding.searchViewTxt.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//
//            }
//
//            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
//
//            }
//
//            override fun afterTextChanged(searchText: Editable) {
//                if (searchText.isNotEmpty()) {
//                    getSearchbyUserDetails(searchText.toString())
//                } else {
//                    getFilterSearchView()
//                }
//            }
//
//        })
//    }

    private fun getSearchbyUserDetails(searchText: String) {
        val userRepo = UserRepositary.getInstance(requireContext())
        val result = userRepo.updateSearchUserDetails(name = searchText)

        listAdapter.resetView(result)
    }

    private fun getFilterSearchView() {
        val userDetailsArrayAdapter = UserRepositary.getInstance(requireContext())
        val result = userDetailsArrayAdapter.getUserRepos()
        userDetailsArrayAdapter.let {
            listAdapter.resetView(result)
        }
    }


    override fun onResume() {
        super.onResume()
    }

}