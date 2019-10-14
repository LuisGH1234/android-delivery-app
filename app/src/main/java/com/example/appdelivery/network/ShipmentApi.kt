package com.example.appdelivery.network

import android.util.Log
import com.example.appdelivery.database.entity.Shipment
import com.example.appdelivery.network.service.IApiService
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ShipmentApi {
    companion object {
        private const val TAG = "ConsorcioApi"
        private const val baseUrl = "https://analytics.consorciohbo.com.pe"

        private val request get() = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        private fun onError(t: Throwable) {
            t.printStackTrace()
        }

        fun getShipment(onSuccess: (Response<Shipment>) -> Unit,
                        onErr: (Throwable) -> Unit = ::onError) {
            val service = request.create(IApiService::class.java)
            val call = service.getShipment()

            call.enqueue(object : Callback<Shipment> {
                override fun onFailure(call: Call<Shipment>, t: Throwable) {
                    onErr(t)
                }

                override fun onResponse(call: Call<Shipment>, response: Response<Shipment>) {
                    if (response.code() != 404)
                        onSuccess(response)
                    else Log.e(TAG, "404 - Not Found - ${response.message()}")
                }
            })
        }
    }
}