package com.example.gitsearch.ui.main

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gitsearch.MainActivity
import com.example.gitsearch.R
import com.example.gitsearch.databinding.FragmentMainBinding
import com.example.gitsearch.utilities.InjectorUtils


class MainFragment : Fragment() {

    val viewModel: MainViewModel by lazy{
        val factory = InjectorUtils.provideQuotesViewModelFactory()
        ViewModelProvider(this, factory)[MainViewModel::class.java]
    }

    private var recyclerViewState:Parcelable? = null

    companion object {
        fun newInstance() = MainFragment()
    }

    private var _binding: FragmentMainBinding? = null

    private val binding get() = _binding!!

    private val adapter = UsersAdapter(emptyList())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerview = binding.recyclerview
        recyclerview.adapter = adapter

        viewModel.getUsers().observe(viewLifecycleOwner, Observer{ users ->

            binding.loading.loading.visibility = View.INVISIBLE
            binding.noInternet.noInternet.visibility = View.INVISIBLE
            recyclerview.visibility = View.VISIBLE
            adapter.updateList(users)

            if(users.isEmpty())
            {
                recyclerview.visibility = View.INVISIBLE
                binding.noResults.noResults.visibility = View.VISIBLE
            }

            //Scroll to beginning if new search
            if (users.size <= 30)
            {
                binding.recyclerview.layoutManager?.scrollToPosition(0)
            }

            else if(recyclerViewState != null) //Return to scrolling position
            {
                binding.recyclerview.layoutManager?.onRestoreInstanceState(recyclerViewState)
            }
        })


        binding.recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState==RecyclerView.SCROLL_STATE_IDLE)
                {
                    //load more only if count is divisible by 30
                    if(recyclerView.layoutManager?.itemCount != null && recyclerView.layoutManager?.itemCount!!%30 == 0)
                    {
                        val activity = activity as MainActivity
                        if(activity.isInternetConnected())
                        {
                            recyclerViewState = recyclerView.layoutManager!!.onSaveInstanceState()
                            viewModel.loadMore()
                        }
                        else
                        {
                            Toast.makeText(context, "No internet connection, check your internet connection and try again.", Toast.LENGTH_SHORT).show()
                        }

                    }
                }
            }
        })

        binding.buttonSearchUsers.setOnClickListener {


            binding.loading.loading.visibility = View.VISIBLE
            binding.noInternet.noInternet.visibility = View.INVISIBLE
            binding.noResults.noResults.visibility = View.INVISIBLE
            binding.recyclerview.visibility = View.INVISIBLE


            val activity = activity as MainActivity
            if(activity.isInternetConnected())
            {
                viewModel.searchUsers(binding.editTextSearch.text.toString(), 1, false)

            }
            else
            {
                binding.loading.loading.visibility = View.INVISIBLE
                binding.noInternet.noInternet.visibility = View.VISIBLE
                binding.recyclerview.visibility = View.INVISIBLE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        activity?.setTitle(R.string.app_name)
    }



}