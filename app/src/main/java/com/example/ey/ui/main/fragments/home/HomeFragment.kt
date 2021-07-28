package com.example.ey.ui.main.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ey.databinding.FragmentHomeBinding
import com.example.ey.ui.main.fragments.home.adapter.FavAdapter


class HomeFragment : Fragment() {
    var viewModel: MainViewModel? = null
    var recyclerViewAdapter: FavAdapter? = null
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(requireActivity()).get<MainViewModel>(MainViewModel::class.java)
        val recyclerLayoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
        binding.rvFav.layoutManager = recyclerLayoutManager

        val dividerItemDecoration = DividerItemDecoration(
            binding.rvFav.context,
            0
        )
        binding.rvFav.addItemDecoration(dividerItemDecoration)
        binding.rvFav.adapter = recyclerViewAdapter

        viewModel!!.userMutableLiveData.observe(viewLifecycleOwner, {
            recyclerViewAdapter = activity?.let { it1 -> FavAdapter(it1, it!!) }
            binding.rvFav.adapter = recyclerViewAdapter
        })

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}