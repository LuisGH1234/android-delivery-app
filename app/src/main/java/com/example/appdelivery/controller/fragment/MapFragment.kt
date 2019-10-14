package com.example.appdelivery.controller.fragment

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.appdelivery.R
import com.example.appdelivery.controller.viewModel.MapViewModel
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException

class MapFragment : Fragment() {

    private val TAG = "MapFragment"
    private lateinit var searchButton: Button
    private lateinit var helperText: TextView

    private lateinit var mMapView: MapView
    private lateinit var googleMap: GoogleMap

    private val defaultLatLang = LatLng(-12.087414, -77.050178)
    private val defaultZoom = 18f
    private var mLocationPermissionGranted = false
    private val permissionRequestAccessFineLocation = 1

    private val geocoder get() = Geocoder(activity)
    private var addressText: String? = null

    private lateinit var viewModel: MapViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupMap(savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_map, container, false)

        mMapView = view.findViewById(R.id.mapView)
        mMapView.onCreate(savedInstanceState)
        mMapView.onResume() // needed to get the map to display immediately

        activity?.let { MapsInitializer.initialize(it.applicationContext) }
        mMapView.getMapAsync(onMapReady)

        searchButton = view.findViewById(R.id.search_button)
        searchButton.setOnClickListener(clickListener)

        helperText = view.findViewById(R.id.helper_text)

        setupViewModel()
        return view
    }

    private fun setupMap(savedInstanceState: Bundle?) {
        arguments?.let {
            val address = it.getString("address")
            if (address != null) addressText = address
        }
    }

    private fun setupViewModel() {
        viewModel =
            ViewModelProviders.of(this).get(MapViewModel::class.java)
        viewModel.addressText.observe(this, Observer { text ->
            helperText.text = if (addressText != null) addressText else text
        })
    }

    private val clickListener = View.OnClickListener {
        Log.i(TAG, "Button: pressed")
        viewModel.fetchShipment(::geoLocate)
        /*if (mLocationPermissionGranted) viewModel.fetchShipment()
        else {
            Log.d(TAG, "PermissionGranted: no access")
            getLocationPermission()
        }*/
    }

    // https://stackoverflow.com/questions/19353255/how-to-put-google-maps-v2-on-a-fragment-using-viewpager
    private val onMapReady = OnMapReadyCallback {
        googleMap = it

        // Add a marker in UPC San Isidro and move the camera
        val upcSI = defaultLatLang
        googleMap.addMarker(MarkerOptions().position(upcSI).title("UPC San Isidro"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(upcSI, defaultZoom))

        googleMap.uiSettings.isZoomControlsEnabled = false

        // Request permission
        // getLocationPermission() // NOT Implemented yet
    }

    private fun geoLocate(text: String) {
        Log.d(TAG, "Geolocate: geolocating")
        try {
            val addressList = getFromLocationName(text)

            if (addressList.isNotEmpty()) {
                Log.i(TAG, "Geolocate: Result - ${addressList[0]}")
                val address = addressList[0]

                if (addressHasLatLng(address)) {
                    val latLng = LatLng(address.latitude, address.longitude)
                    googleMap.addMarker(MarkerOptions().title(address.locality)
                        .position(latLng).snippet(address.getAddressLine(0)))
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, defaultZoom))
                } else Log.d(TAG,
                    "Geolocate: Result - address has not longitude and/or latitude")
            }
        } catch (ex: IOException) {
            Log.e(TAG, "Geolocate: IOExeption - ${ex.message}")
        }
    }

    private fun getFromLocationName(searchValue: String, maxResults: Int = 1)
            : MutableList<Address> {
        return geocoder.getFromLocationName(searchValue, maxResults)
    }

    private fun addressHasLatLng(address: Address): Boolean {
        return address.hasLatitude() && address.hasLongitude()
    }

    override fun onResume() {
        super.onResume()
        mMapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mMapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mMapView.onLowMemory()
    }
}