package com.example.findburrito

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo.coroutines.await
import com.example.findburrito.databinding.FragmentBurritoPlacesListBinding
import com.example.yelp.BurritoPlacesListQuery


class BurritoPlacesList : Fragment() {

    private lateinit var binding: FragmentBurritoPlacesListBinding
    private  val burritoPlacesAdapter = BurritoPlacesListAdapter()

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

        binding.placesRecycler.apply {
            adapter = burritoPlacesAdapter
            layoutManager = LinearLayoutManager(context)
        }

        lifecycleScope.launchWhenResumed {
            val response = apolloClient.query(BurritoPlacesListQuery()).await()

            Log.d("Places List", "Success: ${response.data}")
        }
    }

    private fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentBurritoPlacesListBinding =
        FragmentBurritoPlacesListBinding.inflate(inflater, container, false)

}