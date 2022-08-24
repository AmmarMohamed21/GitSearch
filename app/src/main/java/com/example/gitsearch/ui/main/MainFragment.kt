package com.example.gitsearch.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        val viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
        viewModel.getUsers().observe(viewLifecycleOwner, Observer{ users ->
            val recyclerview = binding.recyclerview
            recyclerview.layoutManager = LinearLayoutManager(this.context)
            val adapter = UsersAdapter(users)
            recyclerview.adapter = adapter

            //Prevent scrolling back to top
            if(users.size>30)
            {
                recyclerview.scrollToPosition(users.size-36)
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
                        viewModel.loadMore()
                    }
                }
            }
        })

        binding.buttonSearchUsers.setOnClickListener {
            viewModel.searchUsers(binding.editTextSearch.text.toString())
        }
    }

}