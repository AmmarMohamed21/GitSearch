package com.example.gitsearch.utilities

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import com.example.gitsearch.R
import io.getstream.avatarview.AvatarView
import io.getstream.avatarview.glide.loadImage

object ProfileBindingAdapter {
    @JvmStatic
    @BindingAdapter("android:followersCount", "android:followingCount")
    fun setFollowingText(view: TextView, followers: Int?, following: Int?) {
        view.text = "${followers?: "0"} followers . ${following?:"0"} following"
    }

    @JvmStatic
    @BindingAdapter("android:imageUrl")
    fun loadImage(view: AvatarView, url: String?) {
        view.loadImage(url)
    }

    @JvmStatic
    @BindingAdapter("android:usernameOnClick")
    fun usernameOnClick(view: CardView, username: String?) {
        view.setOnClickListener{
            val bundle = Bundle()
            bundle.putString("user_id", username)
            view.findNavController().navigate(R.id.action_mainFragment_to_profileFragment, bundle)
        }
    }

}