package com.example.findburrito

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import com.example.findburrito.databinding.FragmentBurritoPlacesListBinding
import com.example.yelp.BurritoPlacesListQuery


class BurritoPlacesList : Fragment() {

    private lateinit var binding: FragmentBurritoPlacesListBinding
    private val burritoPlacesAdapter = BurritoPlacesListAdapter{burritoPlaceDetails: BurritoPlacesListQuery.Business -> openDetailedView(burritoPlaceDetails)  }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        (activity as? AppCompatActivity)?.supportActionBar?.title = "Burrito Places"
        // Inflate the layout for this fragment
        binding = setBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenResumed {
            val response = try {
                apolloClient.query(BurritoPlacesListQuery()).await()
            } catch (e: ApolloException) {
                Log.d("Places List", "Failure", e)
                null
            }

            val burritoPlaces = response?.data?.search?.business?.filterNotNull()
            if (burritoPlaces != null && !response.hasErrors()) {
                burritoPlacesAdapter.setPlaces(burritoPlaces)
            }
        }

        binding.placesRecycler.apply {
            adapter = burritoPlacesAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentBurritoPlacesListBinding =
        FragmentBurritoPlacesListBinding.inflate(inflater, container, false)

    private fun openDetailedView(burritoPlaceDetails: BurritoPlacesListQuery.Business) {
        findNavController().navigate(
            BurritoPlacesListDirections.actionBurritoPlacesListToBurritoPlaceDetailsFragment(
                placeId = burritoPlaceDetails.id
            )
        )
    }
}