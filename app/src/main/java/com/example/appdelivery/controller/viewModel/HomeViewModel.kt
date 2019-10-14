package com.example.appdelivery.controller.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.example.appdelivery.database.DeliveryDatabase
import com.example.appdelivery.database.entity.Shipment
import com.example.appdelivery.repository.ShipmentRepository
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ShipmentRepository
    val allShipments: LiveData<List<Shipment>>

    init {
        val shipmentDao = DeliveryDatabase.getDatabase(application, viewModelScope).shipmentDao()
        repository = ShipmentRepository(shipmentDao)
        allShipments = repository.allShipments
    }

    fun updateRating(id: Int, rating: Int) = viewModelScope.launch {
        repository.updateRating(id, rating)
    }
}