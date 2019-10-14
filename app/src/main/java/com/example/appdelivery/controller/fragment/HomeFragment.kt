package com.example.appdelivery.controller.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appdelivery.R
import com.example.appdelivery.controller.adapter.HomeAdapter
import com.example.appdelivery.controller.viewModel.HomeViewModel

class HomeFragment : Fragment() {

    private val TAG = HomeFragment::class.simpleName

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var homeAdapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val recyclerView: RecyclerView = root.findViewById(R.id.fragmentHomeRecyclerView)
        homeAdapter = HomeAdapter(root.context, homeViewModel, findNavController())
        recyclerView.apply {
            adapter = homeAdapter
            layoutManager = LinearLayoutManager(root.context)
        }
        // findNavController().navigate(R.id.navigation_map)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        homeViewModel.allShipments.observe(this, Observer { shipments ->
            shipments?.let { homeAdapter.setShipments(shipments) }
        })
    }

}