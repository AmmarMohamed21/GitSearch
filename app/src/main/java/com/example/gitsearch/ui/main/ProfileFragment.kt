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
import com.example.gitsearch.databinding.FragmentProfileBinding
import com.example.gitsearch.utilities.InjectorUtils
import io.getstream.avatarview.glide.loadImage

private const val USER_ID = "user_id"

class ProfileFragment : Fragment() {

    private val viewModel by lazy{
        val factory = InjectorUtils.provideQuotesViewModelFactory()
        ViewModelProvider(this, factory)[MainViewModel::class.java]
    }

    private var userId: String? = null

    private var _binding: FragmentProfileBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userId = it.getString(USER_ID)
            Log.v("","userId")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUserProfile().observe(viewLifecycleOwner, Observer{ user ->
            Log.v("user test","${user.username}")
            binding.user=user
        })
        viewModel.loadUserProfile(userId!!)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


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
//        val factory = InjectorUtils.provideQuotesViewModelFactory()
//        val viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
//        viewModel.clearProfileData()
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