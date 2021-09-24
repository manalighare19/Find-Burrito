package com.example.findburrito

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.navigation.fragment.FragmentNavigator
import androidx.recyclerview.widget.RecyclerView
import com.example.findburrito.databinding.ItemPlaceBinding
import com.example.yelp.BurritoPlacesListQuery

class BurritoPlacesListAdapter(private val detailedView: (BurritoPlacesListQuery.Business, place: View) -> Unit) :
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
        ViewCompat.setTransitionName(holder.itemView, "Row_$position")

        holder.itemView.setOnClickListener {
            detailedView.invoke(burritoPlaces[position], holder.itemView)
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