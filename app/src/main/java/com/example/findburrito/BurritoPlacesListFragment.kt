package com.example.findburrito

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import com.example.findburrito.databinding.FragmentBurritoPlacesListBinding
import com.example.yelp.BurritoPlacesListQuery
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng


class BurritoPlacesList : Fragment() {

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getCurrentLocation()
//                Log.d("Permission", "onActivityCreated: Granted")
            } else {
                showLocationPermissionsDialog()
//                Log.d("Permission", "onActivityCreated: Denied")
            }
        }

    private lateinit var lastLocation: Location
    private var fusedLocationProviderClient: FusedLocationProviderClient? = null

    private lateinit var binding: FragmentBurritoPlacesListBinding

    private val burritoPlacesAdapter = BurritoPlacesListAdapter{ burritoPlaceDetails: BurritoPlacesListQuery.Business, place: View ->
        openDetailedView(burritoPlaceDetails, place)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())
    }

    override fun onStart() {
        super.onStart()
        requestPermission()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Burrito Places"

        binding = FragmentBurritoPlacesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.placesRecycler.apply {
            adapter = burritoPlacesAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun openDetailedView(burritoPlaceDetails: BurritoPlacesListQuery.Business, place: View) {

        val placeDetails = burritoPlaceDetails.let {
            Place(
                id = it.id,
                name = it.name,
                price = it.price,
                display_phone = it.display_phone,
                location = it.location?.formatted_address
            )
        }

        val extras = FragmentNavigator.Extras.Builder()
            .addSharedElement( place, "detail")
            .build()

        findNavController().navigate(
            BurritoPlacesListDirections.actionBurritoPlacesListToBurritoPlaceDetailsFragment(
                placeId = burritoPlaceDetails.id,
                place = placeDetails
            ), extras
        )
    }

    private fun requestPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
//                Log.d("Permission", "requestPermission: GRANTED")
                getCurrentLocation()
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                ACCESS_FINE_LOCATION
            ) -> {
                showLocationPermissionsDialog()
//                Log.d("Permission", "requestPermission: Additional rational should be displayed")
            }
            else -> {
//                Log.d("Permission", "requestPermission: Permission has not been asked yet")
                requestPermissionLauncher.launch(
                    ACCESS_FINE_LOCATION
                )
            }
        }
    }


    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        fusedLocationProviderClient?.lastLocation?.addOnSuccessListener { location ->
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
//                    Log.d("currentLatLang", " $currentLatLng")

                lifecycleScope.launchWhenResumed {
                    val response = try {
                        apolloClient.query(
                            BurritoPlacesListQuery(
                                "burrito",
                                currentLatLng.latitude,
                                currentLatLng.longitude,
                                20000.0
                            )
                        ).await()
                    } catch (e: ApolloException) {
                        Log.d("Places List", "Failure", e)
                        return@launchWhenResumed
                    }
//                    Log.d("Place List", "Success ${response.data}")

                    val burritoPlaces = response.data?.search?.business?.filterNotNull()
                    if (burritoPlaces != null && !response.hasErrors()) {
                        burritoPlacesAdapter.setPlaces(burritoPlaces)
                    }
                }
            }
        }
    }

    private fun showLocationPermissionsDialog() {
        AlertDialog.Builder(context)
            .setMessage("To search best burrito places near you please allow access to your location.")
            .setPositiveButton("Settings") { _, _ ->
                startActivity(
                    Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.fromParts("package", context?.packageName, null)
                    )
                )
            }
            .show()
    }
}