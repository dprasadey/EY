package com.example.ey.ui.main.fragments.service

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ey.R
import com.example.ey.databinding.FragmentHomeBinding
import com.example.ey.databinding.FragmentServiceBinding
import com.example.ey.ui.main.fragments.home.MainViewModel
import com.example.ey.ui.main.fragments.home.adapter.FavAdapter
import com.example.ey.ui.main.fragments.service.adapter.ServiceAdapter

class ServiceFragment : Fragment() {

    var viewModel: ServiceViewModel? = null
    var recyclerViewAdapter: ServiceAdapter? = null
    private var _binding: FragmentServiceBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentServiceBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(requireActivity()).get<ServiceViewModel>(ServiceViewModel::class.java)
        val recyclerLayoutManager = LinearLayoutManager(activity)
        binding.rvService.layoutManager = recyclerLayoutManager

        val dividerItemDecoration = DividerItemDecoration(
            binding.rvService.context,0

        )
        binding.rvService.addItemDecoration(dividerItemDecoration)
        binding.rvService.adapter = recyclerViewAdapter

        viewModel!!.userMutableLiveData.observe(viewLifecycleOwner, {
            recyclerViewAdapter = activity?.let { it1 -> ServiceAdapter(it1, it!!) }
            binding.rvService.adapter = recyclerViewAdapter
        })

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}