package com.project.hilforts.views.map

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.project.hilforts.R
import com.project.hilforts.helpers.readImageFromPath
import com.project.hilforts.main.MainApp
import com.project.hilforts.models.HillfortModel

import kotlinx.android.synthetic.main.activity_hillfort_maps.*
import kotlinx.android.synthetic.main.content_hillfort_maps.*

class HillfortMapView : AppCompatActivity(), GoogleMap.OnMarkerClickListener {

    lateinit var presenter: HillfortMapPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort_maps)
        setSupportActionBar(toolbar)

        presenter = HillfortMapPresenter(this)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync {
            presenter.doPopulateMap(it)
        }
    }

    fun showHillfort(hillfort: HillfortModel){
        currentTitle.text = hillfort!!.title
        currentDescription.text = hillfort!!.description
        if (hillfort!!.image1 != ""){
            currentImage.setImageBitmap(readImageFromPath(this, hillfort!!.image1))
        } else if (hillfort!!.image2 != ""){
            currentImage.setImageBitmap(readImageFromPath(this, hillfort!!.image2))
        } else if (hillfort!!.image3 != ""){
            currentImage.setImageBitmap(readImageFromPath(this, hillfort!!.image3))
        } else {
            currentImage.setImageBitmap(readImageFromPath(this, hillfort!!.image4))
        }
    }
    override fun onMarkerClick(marker: Marker): Boolean {
        presenter.doMarkerSelected(marker)
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }
}
