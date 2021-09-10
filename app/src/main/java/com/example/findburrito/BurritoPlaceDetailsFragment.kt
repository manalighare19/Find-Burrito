package com.example.findburrito

import android.os.Bundle
import android.util.Log
import android.util.TimeFormatException
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.apollographql.apollo.coroutines.await
import com.example.findburrito.databinding.FragmentBurritoPlaceDetailsBinding
import com.example.yelp.BurritoPlaceDetailsQuery
import com.example.yelp.BurritoPlacesListQuery
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions

class BurritoPlaceDetailsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentBurritoPlaceDetailsBinding

    var lat: Double = 0.0
    var long: Double = 0.0

    private val args: BurritoPlaceDetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding = FragmentBurritoPlaceDetailsBinding.inflate(inflater, container, false)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

//    fun setValues(){
//        lifecycleScope.launchWhenResumed {
//
//        }
//    }

    override fun onMapReady(googleMap: GoogleMap) {
        lifecycleScope.launchWhenResumed {
            val response = apolloClient.query(BurritoPlaceDetailsQuery(args.placeId!!)).await()

            val burritoPlaceDetails = response.data?.business

            if (burritoPlaceDetails != null && !response.hasErrors()) {
                binding.placeAddress.text = burritoPlaceDetails.location?.formatted_address
                binding.placePrice.text = burritoPlaceDetails.price
                binding.placePhone.text =
                    getString(R.string.bullet_format, burritoPlaceDetails.display_phone)
                lat = burritoPlaceDetails.coordinates?.latitude!!
                long = burritoPlaceDetails.coordinates.longitude!!

            }
            (activity as? AppCompatActivity)?.supportActionBar?.title = burritoPlaceDetails?.name

//            Log.d("LatLong", "$lat, $long")
            val place = LatLng(lat, long)
            googleMap.addMarker(
                MarkerOptions()
                    .position(place)
                    .title(burritoPlaceDetails?.name)
            )

            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place,15f))
            //googleMap.moveCamera(CameraUpdateFactory.zoomTo(12f))


        }


    }
}
