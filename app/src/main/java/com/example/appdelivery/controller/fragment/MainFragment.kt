package com.example.appdelivery.controller.fragment

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Button
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appdelivery.controller.activitiy.DeliveryActivity
import com.example.appdelivery.R
import com.example.appdelivery.controller.adapter.MainAdapter
import com.example.appdelivery.controller.viewModel.MainViewModel
import com.example.appdelivery.database.entity.Shipment

class MainFragment : Fragment() {

    companion object {
        const val newMainActivityRequestCode = 1
        fun newInstance() = MainFragment()
    }

    private val TAG = MainFragment::class.simpleName

    private lateinit var viewModel: MainViewModel
    private lateinit var mainAdapter: MainAdapter
    private lateinit var brandImageView: ImageView
    private lateinit var startButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)

        brandImageView = view.findViewById(R.id.brandingImageView)
        startButton = view.findViewById(R.id.startButton)

        startButton.setOnClickListener(startButtonClickListener)

        Glide.with(this).load(R.drawable.delivery_brand).into(brandImageView)

        val recyclerView = view.findViewById<RecyclerView>(R.id.lastFiveRecyclerView)
        mainAdapter = MainAdapter(view.context)
        recyclerView.apply {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(view.context)
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.lastFiveShipments.observe(this, Observer { shipments ->
            shipments?.let { mainAdapter.setShipments(it) }
        })
    }

    private val startButtonClickListener = View.OnClickListener {
        val intent = Intent(it.context, DeliveryActivity::class.java)
        // startActivity(intent)
        this.activity!!.startActivity(intent)
        this.activity!!.finish()
    }

}
