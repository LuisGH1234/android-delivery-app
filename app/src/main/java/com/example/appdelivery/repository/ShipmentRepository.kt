package com.example.appdelivery.repository

import androidx.lifecycle.LiveData
import com.example.appdelivery.database.dao.ShipmentDao
import com.example.appdelivery.database.entity.Shipment
import com.example.appdelivery.network.ShipmentApi
import retrofit2.Response

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class ShipmentRepository (private val shipmentDao: ShipmentDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allShipments: LiveData<List<Shipment>> = shipmentDao.getAll()

    val lastFive: LiveData<List<Shipment>> = shipmentDao.getLastFive()

    // The suspend modifier tells the compiler that this must be called from a
    // coroutine or another suspend function.
    suspend fun insert(shipment: Shipment) {
        shipmentDao.insert(shipment)
    }

    suspend fun updateRating(id: Int, rating: Int) {
        shipmentDao.updateRating(id, rating)
    }

    companion object {
        fun fetchShipment(onSuccess: (Response<Shipment>) -> Unit) {
            ShipmentApi.getShipment(onSuccess)
        }
    }
}