package com.project.hilforts.views.hillfort

import android.annotation.SuppressLint
import android.content.Intent
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.project.hilforts.helpers.checkLocationPermissions
import com.project.hilforts.helpers.createDefaultLocationRequest
import com.project.hilforts.helpers.isPermissionGranted
import com.project.hilforts.helpers.showImagePicker
import com.project.hilforts.main.MainApp
import com.project.hilforts.models.HillfortModel
import com.project.hilforts.models.Location
import com.project.hilforts.views.base.*
import com.project.hilforts.views.editLocation.EditLocationView
import org.jetbrains.anko.intentFor
import java.text.SimpleDateFormat
import java.util.*

class HillfortPresenter(view: BaseView) : BasePresenter(view) {

    var hillfort = HillfortModel()
    var map: GoogleMap? = null

    var defaultLocation = Location(53.389,-7.311, 5.2f)
    var edit = false

    var locationService: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(view)
    val locationRequest = createDefaultLocationRequest()

    init {
        if (view.intent.hasExtra("hillfort_edit")) {
            edit = true
            hillfort = view.intent.extras?.getParcelable<HillfortModel>("hillfort_edit")!!
            view.showHillfort(hillfort)
        } else {
            if (checkLocationPermissions(view)) {
                doSetCurrentLocation()
            }
        }
    }

    fun doConfigureMap(m: GoogleMap) {
        map = m
        locationUpdate(hillfort.lat, hillfort.lng)
    }

    fun locationUpdate(lat: Double, lng: Double) {
        hillfort.lat = lat
        hillfort.lng = lng
        hillfort.zoom = 10f
        map?.clear()
        map?.uiSettings?.setZoomControlsEnabled(true)
        val options = MarkerOptions().title(hillfort.title).position(LatLng(hillfort.lat, hillfort.lng))
        map?.addMarker(options)
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(hillfort.lat, hillfort.lng), hillfort.zoom))
        view?.showHillfort(hillfort)
    }

    fun doAddOrSave(title: String, description: String, additionalNote: String) {
        hillfort.title = title
        hillfort.description = description
        hillfort.additionalNote = additionalNote
        if (edit) {
            app.users.updateUserHillfort(app.loggedInUserEmail, hillfort.copy())
        } else {
            app.users.createUserHillfort(app.loggedInUserEmail, hillfort.copy())
        }
        view?.finish()
    }

    fun doCancel() {
        view?.finish()
    }

    fun doDelete() {
        app.users.deleteUserHillfort(app.loggedInUserEmail,hillfort)
        view?.finish()
    }

    fun doSelectImage(num: Int) {
        if (num == 1){
            view?.let{
                showImagePicker(view!!, IMAGE_REQUEST1)
            }
        }else if (num == 2){
            view?.let{
                showImagePicker(view!!, IMAGE_REQUEST2)
            }
        }else if(num == 3){
            view?.let{
                showImagePicker(view!!, IMAGE_REQUEST3)
            }
        }else{
            view?.let{
                showImagePicker(view!!, IMAGE_REQUEST4)
            }
        }
    }

    fun doSetLocation() {
        view?.navigateTo(VIEW.LOCATION, LOCATION_REQUEST, "location", Location(hillfort.lat, hillfort.lng, hillfort.zoom))
    }

    fun doSetVisited() : String{
        hillfort.visited = !hillfort.visited

        if(hillfort.visited){
            val date = Calendar.getInstance().time
            val formatter = SimpleDateFormat.getDateTimeInstance() //or use getDateInstance()
            val formatedDate = formatter.format(date)
            hillfort.dateVisited = formatedDate
            return formatedDate
        } else {
            hillfort.dateVisited = ""
            return ""
        }
    }
    override fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            IMAGE_REQUEST1 -> {
                hillfort.image1 = data.data.toString()
                view?.showHillfort(hillfort)
            }
            IMAGE_REQUEST2 -> {
                if (data != null) {
                    hillfort.image2 = data.getData().toString()
                    view?.showHillfort(hillfort)
                }
            }
            IMAGE_REQUEST3 -> {
                if (data != null) {
                    hillfort.image3 = data.getData().toString()
                    view?.showHillfort(hillfort)
                }
            }
            IMAGE_REQUEST4 -> {
                if (data != null) {
                    hillfort.image4 = data.getData().toString()
                    view?.showHillfort(hillfort)
                }
            }
            LOCATION_REQUEST -> {
                val location = data.extras?.getParcelable<Location>("location")!!
                hillfort.lat = location.lat
                hillfort.lng = location.lng
                hillfort.zoom = location.zoom
                locationUpdate(hillfort.lat, hillfort.lng)
            }
        }
    }

    override fun doRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (isPermissionGranted(requestCode, grantResults)) {
            doSetCurrentLocation()
        } else {
            locationUpdate(defaultLocation.lat, defaultLocation.lng)
        }
    }

    @SuppressLint("MissingPermission")
    fun doSetCurrentLocation() {
        locationService.lastLocation.addOnSuccessListener {
            locationUpdate(it.latitude, it.longitude)
        }
    }

    @SuppressLint("MissingPermission")
    fun doResartLocationUpdates() {
        var locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                if (locationResult != null && locationResult.locations != null) {
                    val l = locationResult.locations.last()
                    locationUpdate(l.latitude, l.longitude)
                }
            }
        }
        if (!edit) {
            locationService.requestLocationUpdates(locationRequest, locationCallback, null)
        }
    }
}