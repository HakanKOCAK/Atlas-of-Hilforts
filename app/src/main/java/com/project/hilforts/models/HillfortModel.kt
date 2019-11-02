package com.project.hilforts.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HillfortModel(var id: String="",
                         var title: String = "",
                         var description: String = "",
                         var image1: String = "",
                         var image2: String = "",
                         var image3: String = "",
                         var image4: String = "",
                         var visited: Boolean = false,
                         var lat : Double = 0.0,
                         var lng: Double = 0.0,
                         var zoom: Float = 0f,
                         var dateVisited:String = "",
                         var additionalNote: String = "") : Parcelable

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable