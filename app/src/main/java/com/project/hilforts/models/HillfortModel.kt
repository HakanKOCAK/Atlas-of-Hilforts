package com.project.hilforts.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Embedded
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HillfortModel(var fbId: String="",
                         var title: String = "",
                         var description: String = "",
                         var image1: String = "",
                         var image2: String = "",
                         var image3: String = "",
                         var image4: String = "",
                         var visited: Boolean = false,
                         var dateVisited:String = "",
                         var additionalNote: String = "",
                         @Embedded var location : Location = Location()) : Parcelable

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable