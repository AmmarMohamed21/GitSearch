package com.example.gitsearch.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.gitsearch.R
import com.example.gitsearch.data.User
import com.example.gitsearch.databinding.ItemUserBinding
import io.getstream.avatarview.AvatarView
import io.getstream.avatarview.glide.loadImage

class UsersAdapter(private var usersList: List<User>) : RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user:User = usersList[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    fun updateList(users: List<User>?) {
        users?.let{
            usersList = it
            notifyDataSetChanged()
        }
    }


    class UserViewHolder(var binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user:User){
            binding.user=user
        }
    }
}