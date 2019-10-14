package com.example.appdelivery.controller.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appdelivery.R
import com.example.appdelivery.database.entity.Shipment

class MainAdapter(context: Context)
    : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var shipments = emptyList<Shipment>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = inflater.inflate(R.layout.shipment_row, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = shipments.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = shipments[position]
        holder.from(current)
    }

    internal fun setShipments(shipmetList: List<Shipment>) {
        this.shipments = shipmetList
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val shipmentItemView: TextView = itemView.findViewById(R.id.addressTextView)

        fun from(shipment: Shipment) {
            shipmentItemView.text = shipment.address
        }
    }
}