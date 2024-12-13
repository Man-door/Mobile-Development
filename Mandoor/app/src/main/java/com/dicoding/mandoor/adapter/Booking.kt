package com.dicoding.mandoor.adapter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Booking(
    val customerImage: Int,
    val customerName: String,
    val serviceType: String,
    val price: String,
    val status: String,
): Parcelable

