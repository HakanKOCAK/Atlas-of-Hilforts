package com.project.hilforts.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HilfortModel(var id: Long = 0,
                        var title: String = "",
                        var description: String = "",
                        var image1: String = "",
                        var image2: String = "",
                        var image3: String = "",
                        var image4: String = "",
                        var visited: Boolean = false) : Parcelable