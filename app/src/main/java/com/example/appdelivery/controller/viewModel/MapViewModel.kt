package com.example.appdelivery.controller.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.example.appdelivery.database.DeliveryDatabase
import com.example.appdelivery.database.entity.Shipment
import com.example.appdelivery.repository.ShipmentRepository
import kotlinx.coroutines.launch

class MapViewModel(application: Application) : AndroidViewModel(application) {

    /*companion object {
        val INSTANCE: MapViewModel? = null
    }*/

    private val _addressText = MutableLiveData<String>().apply {
        value = "Default Text"
    }
    val addressText: LiveData<String> = _addressText

    private val repository: ShipmentRepository

    init {
        val shipmentDao = DeliveryDatabase.getDatabase(application, viewModelScope).shipmentDao()
        repository = ShipmentRepository(shipmentDao)
    }

    fun fetchShipment(callback: (text: String) -> Unit) {
        ShipmentRepository.fetchShipment {
            it.body()?.let { shipment ->
                _addressText.value = shipment.address
                callback(shipment.address)
                viewModelScope.launch {
                    shipment.id = null
                    repository.insert(shipment)
                }
            }
        }
    }
}