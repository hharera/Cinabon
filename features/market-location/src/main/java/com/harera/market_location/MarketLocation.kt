package com.harera.market_location

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
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

        val hyperMarketLocation = LatLng(28.924572599601724, 30.977850336925275)
        mMap!!.addMarker(
            MarkerOptions()
                .position(hyperMarketLocation)
                .title(getString(R.string.app_name))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))
        )
        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(hyperMarketLocation, 20f))
    }
}