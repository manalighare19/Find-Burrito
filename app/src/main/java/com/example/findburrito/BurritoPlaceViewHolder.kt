package com.example.findburrito

import androidx.recyclerview.widget.RecyclerView
import com.example.findburrito.databinding.ItemPlaceBinding
import com.example.yelp.BurritoPlacesListQuery

class BurritoPlaceViewHolder(private val binding: ItemPlaceBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(burritoPlaces: BurritoPlacesListQuery.Business) {
        binding.placeName.text = burritoPlaces.name
        if (burritoPlaces.location != null) {
            binding.placeAddress.text = burritoPlaces.location.formatted_address
        }
        binding.placePrice.text = burritoPlaces.price

        val phone = burritoPlaces.display_phone
        binding.placePhone.text = itemView.context.getString(R.string.bullet_format, phone)
    }
}