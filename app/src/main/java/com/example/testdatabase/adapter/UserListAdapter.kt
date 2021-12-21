package com.example.testdatabase.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.testdatabase.R
import com.example.testdatabase.data.UserDetails

class UserListAdapter(private val list:ArrayList<UserDetails>,var context: Context) :
    RecyclerView.Adapter<UserListAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    var item = list[position]
    }

    override fun getItemCount(): Int {
        return list.size
    }
}