package com.example.gitsearch.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitsearch.utilities.InjectorUtils
import com.example.gitsearch.databinding.FragmentMainBinding

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

        // TODO: Use the ViewModel
        val factory = InjectorUtils.provideQuotesViewModelFactory()
        val viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        viewModel.getUsers().observe(viewLifecycleOwner, Observer{ users ->
            val recyclerview = binding.recyclerview
            recyclerview.layoutManager = LinearLayoutManager(this.context)
            val adapter = UsersAdapter(users)
            recyclerview.adapter = adapter
        })

        binding.buttonSearchUsers.setOnClickListener {
//            val user = User(binding.editTextSearch.text.toString(), "author")
//            viewModel.addQuote(user)
            Log.v("", "clicked")
            viewModel.searchUsers(binding.editTextSearch.text.toString())
            //binding.editTextSearch.setText("")
        }
    }

}