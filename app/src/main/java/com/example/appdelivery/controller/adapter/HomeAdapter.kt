package com.example.appdelivery.controller.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.FragmentTransitionImpl
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.appdelivery.R
import com.example.appdelivery.controller.fragment.MapFragment
import com.example.appdelivery.controller.viewModel.HomeViewModel
import com.example.appdelivery.database.entity.Shipment

class HomeAdapter(context: Context,
                  val viewModel: HomeViewModel,
                  val navController: NavController)
    :RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    companion object {
        const val MAX_STARS = 5
    }

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var shipments = emptyList<Shipment>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = inflater.inflate(R.layout.delivery_row, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = shipments.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = shipments[position]
        holder.from(current)
    }

    internal fun setShipments(shipmentList: List<Shipment>) {
        this.shipments = shipmentList
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView) {
        private val addressTextView: TextView = itemView.findViewById(R.id.addressTextView)
        private val iconOne: ImageView = itemView.findViewById(R.id.startIcon1)
        private val iconTwo: ImageView = itemView.findViewById(R.id.startIcon2)
        private val iconThree: ImageView = itemView.findViewById(R.id.startIcon3)
        private val iconFour: ImageView = itemView.findViewById(R.id.startIcon4)
        private val iconFive: ImageView = itemView.findViewById(R.id.startIcon5)

        private val icons: List<ImageView> = listOf(iconOne, iconTwo, iconThree, iconFour, iconFive)

        fun from(shipment: Shipment) {
            val text = "${shipment.address} - ${shipment.id}"
            addressTextView.text = text

            if (shipment.id != null) {
                iconOne.setOnClickListener(onIconClick(shipment, 1))
                iconTwo.setOnClickListener(onIconClick(shipment, 2))
                iconThree.setOnClickListener(onIconClick(shipment, 3))
                iconFour.setOnClickListener(onIconClick(shipment, 4))
                iconFive.setOnClickListener(onIconClick(shipment, 5))
            }

            addressTextView.setOnClickListener(onTextClick)

            for (i: Int in 0..MAX_STARS - 1) {
                if (i <= shipment.rating - 1)
                    icons[i].setImageResource(R.drawable.ic_star_black_24dp)
                else icons[i].setImageResource(R.drawable.ic_star_border_black_24dp)
            }
        }

        private val onTextClick = View.OnClickListener {
            val bundle = Bundle()
            bundle.putString("address", addressTextView.text.toString())
            navController.navigate(R.id.navigation_map, bundle)
        }

        private fun onIconClick(shipment: Shipment, value: Int) = View.OnClickListener {
            if(shipment.rating == value) return@OnClickListener
            viewModel.updateRating(shipment.id!!, value)
        }
    }
}