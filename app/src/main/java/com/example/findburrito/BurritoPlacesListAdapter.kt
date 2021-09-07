package com.example.findburrito

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.findburrito.databinding.ItemPlaceBinding
import com.example.yelp.BurritoPlacesListQuery

class BurritoPlacesListAdapter(val detailedView: (BurritoPlacesListQuery.Business) -> Unit) :
    RecyclerView.Adapter<BurritoPlaceViewHolder>() {

    private val burritoPlaces = arrayListOf<BurritoPlacesListQuery.Business>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BurritoPlaceViewHolder {
        return BurritoPlaceViewHolder(
            ItemPlaceBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BurritoPlaceViewHolder, position: Int) {
        holder.bind(burritoPlaces[position])
        holder.itemView.setOnClickListener {
            detailedView(burritoPlaces[position])
        }
    }

    override fun getItemCount(): Int {
        return burritoPlaces.size
    }

    fun setPlaces(placesList: List<BurritoPlacesListQuery.Business>) {
        burritoPlaces.clear()
        burritoPlaces.addAll(placesList)

//        Log.d("Places are", "setPlaces: $burritoPlaces")
        notifyDataSetChanged()
    }
}