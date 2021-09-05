package com.example.findburrito

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.findburrito.databinding.ItemPlaceBinding

class BurritoPlacesListAdapter:RecyclerView.Adapter<BurritoPlaceViewHolder>(){

    private val burritoPlaces = mutableListOf("1", "2")
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
        holder.bind()
    }

    override fun getItemCount(): Int {
       return burritoPlaces.size
    }
}