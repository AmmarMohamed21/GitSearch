package com.example.gitsearch.ui.main

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gitsearch.databinding.FragmentMainBinding
import com.example.gitsearch.utilities.InjectorUtils


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var _binding: FragmentMainBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val factory = InjectorUtils.provideQuotesViewModelFactory()
        val viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        viewModel.getUsers().observe(viewLifecycleOwner, Observer{ users ->
            val recyclerview = binding.recyclerview
            recyclerview.layoutManager = LinearLayoutManager(this.context)
            val adapter = UsersAdapter(users)
            recyclerview.adapter = adapter
            if(users.size>30)
            {
                recyclerview.scrollToPosition(users.size-31)
            }
        })


        binding.recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState==RecyclerView.SCROLL_STATE_IDLE) {
                    viewModel.loadMore()
                }
            }
        })

        binding.buttonSearchUsers.setOnClickListener {
            viewModel.searchUsers(binding.editTextSearch.text.toString())
        }
    }

}