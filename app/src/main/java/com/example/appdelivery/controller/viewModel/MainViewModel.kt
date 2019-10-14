package com.example.appdelivery.controller.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.appdelivery.database.DeliveryDatabase
import com.example.appdelivery.database.entity.Shipment
import com.example.appdelivery.repository.ShipmentRepository
import kotlinx.coroutines.launch

// Class extends AndroidViewModel and requires application as a parameter.
class MainViewModel(application: Application) : AndroidViewModel(application) {

    // The ViewModel maintains a reference to the repository to get data.
    private val repository: ShipmentRepository
    // LiveData gives us updated words when they change.
    val lastFiveShipments: LiveData<List<Shipment>>

    init {
        // Gets reference to WordDao from WordRoomDatabase to construct
        // the correct WordRepository.
        val shipmentDao = DeliveryDatabase.getDatabase(application, viewModelScope).shipmentDao()
        repository = ShipmentRepository(shipmentDao)
        lastFiveShipments = repository.lastFive
    }

    // The implementation of insert() is completely hidden from the UI.
    // We don't want insert to block the main thread, so we're launching a new
    // coroutine. ViewModels have a coroutine scope based on their lifecycle called
    // viewModelScope which we can use here.
    fun insert(shipment: Shipment) = viewModelScope.launch {
        repository.insert(shipment)
    }
}
