package com.harera.market_location

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MarketLocation : FragmentActivity(), OnMapReadyCallback {
    private var mMap: GoogleMap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_market_location)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap

        val hyperMarketLocation = LatLng(29.066694349888117, 31.108040190221338)
        mMap!!.addMarker(MarkerOptions().position(hyperMarketLocation).title(getString(R.string.app_name)))
        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(hyperMarketLocation))
    }
}