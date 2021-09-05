package com.example.findburrito

import androidx.recyclerview.widget.RecyclerView
import com.example.findburrito.databinding.ItemPlaceBinding

class BurritoPlaceViewHolder(private val binding: ItemPlaceBinding) : RecyclerView.ViewHolder(binding.root){

    fun bind() {
        binding.placeName.text = "Place 1"
        binding.placeAddress.text = "Address 1"
        binding.placePrice.text = "$$"
    }
}