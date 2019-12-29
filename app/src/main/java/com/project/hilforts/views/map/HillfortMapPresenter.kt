package com.project.hilforts.views.map

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.project.hilforts.main.MainApp

class HillfortMapPresenter(val view: HillfortMapView) {

    var app: MainApp

    init{
        app = view.application as MainApp
    }

    fun doPopulateMap(map: GoogleMap) {
        map.uiSettings.setZoomControlsEnabled(true)
        map.setOnMarkerClickListener(view)
        app.users.getUserHillforts(app.loggedInUserEmail).forEach {
            val loc = LatLng(it.lat, it.lng)
            val options = MarkerOptions().title(it.title).position(loc)
            map.addMarker(options).tag = it.id
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.zoom))
        }
    }

    fun doMarkerSelected(marker: Marker) {
        val tag = marker.tag as String
        val hillfort = app.users.findHillfortById(userEmail = app.loggedInUserEmail, id = tag)
        if (hillfort != null) view.showHillfort(hillfort)
    }
}