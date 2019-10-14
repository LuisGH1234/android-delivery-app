package com.example.appdelivery.network.service

import com.example.appdelivery.database.entity.Shipment
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface IApiService {
    @Headers("Accept: application/json")
    @GET("servicio/api/entregas")
    fun getShipment(): Call<Shipment>
}