package com.example.findburrito

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.apollographql.apollo.coroutines.await
import com.example.findburrito.databinding.FragmentBurritoPlaceDetailsBinding
import com.example.yelp.BurritoPlacesListQuery
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class BurritoPlaceDetailsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentBurritoPlaceDetailsBinding
    private lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBurritoPlaceDetailsBinding.inflate(inflater, container, false)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.placeAddress.text = "Place"
        binding.placePrice.text = "price"
        binding.placePhone.text = "phone"

    }

    override fun onMapReady(googleMap: GoogleMap) {
       // map = googleMap
        val home = LatLng(40.7226553, -74.0361504)
        googleMap.addMarker(
            MarkerOptions()
                .position(home)
                .title("Home")
        )
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(home))

    }
}