package com.project.hilforts.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HilfortModel(var id: Long = 0,
                        var title: String = "",
                        var description: String = "") : Parcelable