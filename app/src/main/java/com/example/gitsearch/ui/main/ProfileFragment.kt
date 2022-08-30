package com.example.gitsearch.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitsearch.MainActivity
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

    override fun onResume() {
        super.onResume()
        activity?.setTitle(R.string.profile)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUserProfile().observe(viewLifecycleOwner, Observer{ user ->
            binding.user=user
            binding.loading.loading.visibility = View.INVISIBLE
            binding.noInternet.noInternet.visibility = View.INVISIBLE
            binding.profileScreen.visibility = View.VISIBLE
        })
        val activity = activity as MainActivity
        if(activity.isInternetConnected())
        {
            viewModel.loadUserProfile(userId!!)
            binding.loading.loading.visibility = View.VISIBLE
            binding.profileScreen.visibility = View.INVISIBLE
            binding.noInternet.noInternet.visibility = View.INVISIBLE
        }
        else
        {
            binding.loading.loading.visibility = View.INVISIBLE
            binding.profileScreen.visibility = View.INVISIBLE
            binding.noInternet.noInternet.visibility = View.VISIBLE
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val activity = activity as? MainActivity
        activity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        val activity = activity as? MainActivity
        activity?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setHasOptionsMenu(false)
        super.onDestroyView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val activity = activity as? MainActivity
        return when (item.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
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