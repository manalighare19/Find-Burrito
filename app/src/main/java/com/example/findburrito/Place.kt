package com.example.findburrito

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Place (
    val id: String?,
    val location: String?,
    val display_phone: String?,
    val price: String?,
    val name: String?
) : Parcelable
