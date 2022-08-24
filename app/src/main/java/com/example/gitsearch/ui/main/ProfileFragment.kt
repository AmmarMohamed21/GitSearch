package com.example.gitsearch.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitsearch.R
import com.example.gitsearch.databinding.FragmentMainBinding
import com.example.gitsearch.databinding.FragmentProfileBinding
import com.example.gitsearch.utilities.InjectorUtils
import io.getstream.avatarview.glide.loadImage

private const val USER_ID = "user_id"

class ProfileFragment : Fragment() {
    private var userId: String? = null

    private var _binding: FragmentProfileBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userId = it.getString(USER_ID)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val factory = InjectorUtils.provideQuotesViewModelFactory()
        val viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
        viewModel.getUserProfile().observe(viewLifecycleOwner, Observer{ user ->
            binding.username.text = user.username
            binding.avatar.loadImage(user.avatarUrl)
            binding.name.text = user.name
            binding.bio.text = user.bio ?: ""
            binding.following.text = "${user.followers?: "0"} followers . ${user.following?:"0"} following"
            binding.location.text = user.location ?: ""

        })
        viewModel.loadUserProfile(userId!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onDestroy() {

        super.onDestroy()
        //Clearing Profile Data for re Open
        val factory = InjectorUtils.provideQuotesViewModelFactory()
        val viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
        viewModel.clearProfileData()
    }

    companion object {
        @JvmStatic
        fun newInstance(userId: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(USER_ID, userId)
                }
            }
    }
}