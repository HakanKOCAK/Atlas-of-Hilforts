
package com.project.hilforts.views.map

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.project.hilforts.main.MainApp
import com.project.hilforts.models.HillfortModel
import com.project.hilforts.views.base.BasePresenter
import com.project.hilforts.views.base.BaseView

class HillfortMapPresenter(view: BaseView) : BasePresenter(view){

    fun doPopulateMap(map: GoogleMap, hillforts: List<HillfortModel>) {
        map.uiSettings.setZoomControlsEnabled(true)
        hillforts.forEach {
            val loc = LatLng(it.lat, it.lng)
            val options = MarkerOptions().title(it.title).position(loc)
            map.addMarker(options).tag = it
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.zoom))
        }
    }

    fun doMarkerSelected(marker: Marker) {
        val hillfort = marker.tag as HillfortModel
        if (hillfort != null) view?.showHillfort(hillfort)
    }

    fun loadHillforts() {
        view?.showHillforts(app.users.getUserHillforts(app.loggedInUserEmail))
    }
}