package com.project.hilforts.views.hillfort

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.maps.GoogleMap
import com.project.hilforts.R
import com.project.hilforts.helpers.readImageFromPath
import com.project.hilforts.models.HillfortModel
import com.project.hilforts.views.base.BaseView
import kotlinx.android.synthetic.main.activity_hillforts.*
import kotlinx.android.synthetic.main.activity_hillforts.description
import kotlinx.android.synthetic.main.activity_hillforts.hillfortTitle
import kotlinx.android.synthetic.main.activity_hillforts.visited
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast

class HillfortView : BaseView(), AnkoLogger {

    lateinit var presenter: HillfortPresenter
    var hillfort = HillfortModel()
    lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillforts)
        init(toolbarAdd)

        presenter = initPresenter (HillfortPresenter(this)) as HillfortPresenter

        hillfortImage1.setOnClickListener(){
            presenter.doSelectImage(1)
        }

        hillfortImage2.setOnClickListener(){
            presenter.doSelectImage(2)
        }

        hillfortImage3.setOnClickListener(){
            presenter.doSelectImage(3)
        }

        hillfortImage4.setOnClickListener(){
            presenter.doSelectImage(4)
        }

        visited.setOnClickListener(){
            val str = presenter.doSetVisited()
            textViewForVisited.setText(str)
        }

        mapView.getMapAsync {
            presenter.doConfigureMap(it)
            it.setOnMapClickListener { presenter.doSetLocation() }
        }
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync {
            map = it
            presenter.doConfigureMap(map)
        }
    }

    override fun showHillfort(hillfort: HillfortModel) {
        hillfortTitle.setText(hillfort.title)
        description.setText(hillfort.description)
        additionalNote.setText(hillfort.additionalNote)
        if(hillfort.image1 != ""){hillfortImage1.setImageBitmap(readImageFromPath(this, hillfort.image1))}
        if(hillfort.image2 != ""){hillfortImage2.setImageBitmap(readImageFromPath(this, hillfort.image2))}
        if(hillfort.image3 != ""){hillfortImage3.setImageBitmap(readImageFromPath(this, hillfort.image3))}
        if(hillfort.image4 != ""){hillfortImage4.setImageBitmap(readImageFromPath(this, hillfort.image4))}

        if(hillfort.visited){
            visited.isChecked = true
            textViewForVisited.setText(hillfort.dateVisited)
        } else {
            visited.isChecked = false
            textViewForVisited.setText("")
        }

        lat.setText("%.6f".format(hillfort.lat))
        lng.setText("%.6f".format(hillfort.lng))
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_hillfort, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.item_cancel -> {
                presenter.doCancel()
            }
            R.id.item_save -> {
                if(hillfortTitle.text.toString().isEmpty()){
                    toast(R.string.enter_hillfort_title)
                } else {
                    presenter.doAddOrSave(hillfortTitle.text.toString(), description.text.toString(), additionalNote.text.toString())
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            presenter.doActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onBackPressed() {
        presenter.doCancel()
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
        presenter.doResartLocationUpdates()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }
}