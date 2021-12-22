package com.example.testdatabase.adapter

import android.content.Context
import android.content.Intent
import android.text.BoringLayout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.testdatabase.R
import com.example.testdatabase.data.UserDetails
import com.example.testdatabase.fragment.TAG
import com.example.testdatabase.fragment.Update
import kotlin.math.log
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.Observer
import com.example.testdatabase.repositarty.UserRepositary


class UserListAdapter(
    private var list: ArrayList<UserDetails>,
    var context: Context,
    val callback: onItemClickListener
) :
    RecyclerView.Adapter<UserListAdapter.ViewHolder>() {


    class ViewHolder(itemView: View, private val callback: onItemClickListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            callback.getOnClickListener(adapterPosition)

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view, callback)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.itemView.findViewById<TextView>(R.id.userName1).text = item.name
        holder.itemView.findViewById<TextView>(R.id.degree1).text = item.degree
        holder.itemView.findViewById<TextView>(R.id.experience1).text = item.experience.toString()
        holder.itemView.findViewById<Button>(R.id.update).isVisible = item.flag
        holder.itemView.findViewById<Button>(R.id.delete).isVisible = item.flag

        holder.itemView.findViewById<Button>(R.id.update).setOnClickListener {
            Log.i(TAG, "onBindViewHolder: "+"Hello")
            callback.onUpdateUserDetails(item)
        }

        holder.itemView.findViewById<Button>(R.id.delete).setOnClickListener {
            callback.onDeleteUserDetails(item)
            list.removeAt(position)
            notifyItemRemoved(position)
        }

        holder.itemView.setOnClickListener {
            Log.i(TAG, "onListClick: $position")

//            if(item.flag){
//            }else{
//                Log.i(TAG, "onBindViewHolder: "+item.flag)
//                item.flag = true
//                notifyItemChanged(position)
//            }
            item.flag =  !item.flag
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface onItemClickListener {
        fun getOnClickListener(position: Int)
        fun onUpdateUserDetails(item: UserDetails)
        fun onDeleteUserDetails(item: UserDetails)
    }
}