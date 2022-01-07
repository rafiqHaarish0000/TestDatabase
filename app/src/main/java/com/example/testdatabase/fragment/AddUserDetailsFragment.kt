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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testdatabase.R
import com.example.testdatabase.adapter.UserListAdapter
import com.example.testdatabase.database.UserDetails
import com.example.testdatabase.viewmodel.MainModel
import com.example.testdatabase.repositarty.UserRepositary
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AddUserDetailsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var listAdapter: UserListAdapter
    private lateinit var viewmodel: MainModel
    private lateinit var searchText: EditText


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
                        R.id.fragmentContainer,
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
    ): View? {
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val addButton = view.findViewById<FloatingActionButton>(R.id.addButton)
        searchBox(view)
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
                .replace(
                    R.id.fragmentContainer,
                    SaveUserDetailsFragment.getInstance(),
                    "savedFragment"
                )
                .commit()
        }

    }

    companion object {
        fun getInstance() = AddUserDetailsFragment()
    }

    private fun getAllListValues(view: View): Boolean {

        listAdapter = UserListAdapter(
            requireContext(),
            callback = userListListener
        )

        recyclerView = view.findViewById(R.id.recyclerList)

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

    private fun searchBox(view: View) {
        searchText = view.findViewById(R.id.searchViewTxt)

        searchText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(searchText: Editable) {
                if (searchText.isNotEmpty()) {
                    getSearchbyUserDetails(searchText.toString())
                } else {
                    getFilterSearchView()
                }
            }

        })
    }

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



//    private fun getSearchbyUserDetails(searchText:String){
//        val searchText = searchText
//        viewmodel.getSearchUserDetails(userName = searchText).observe(this@AddUserDetailsFragment, Observer {list->
//            list?.let {
//                Log.e("List = ", list.toString())
//            }
//
//        })
//    }

    override fun onResume() {
        super.onResume()
    }

}