package com.example.gitsearch.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gitsearch.R
import com.example.gitsearch.data.User
import io.getstream.avatarview.AvatarView
import io.getstream.avatarview.glide.loadImage

class UsersAdapter(private val usersList: List<User>) : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_design, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user:User = usersList[position]
        holder.usernameText.text = user.username
        holder.avatarImage.loadImage(user.avatarUrl)
    }

    override fun getItemCount(): Int {
        return usersList.size
    }


    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val usernameText: TextView = itemView.findViewById(R.id.username)
        val avatarImage: AvatarView = itemView.findViewById(R.id.avatar)
    }
}