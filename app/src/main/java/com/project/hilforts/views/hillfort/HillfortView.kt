package com.project.hilforts.views.hillfort

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import com.google.android.gms.maps.GoogleMap
import com.project.hilforts.R
import com.project.hilforts.helpers.readImageFromPath
import com.project.hilforts.models.HillfortModel
import com.project.hilforts.models.Location
import com.project.hilforts.views.base.BaseView
import kotlinx.android.synthetic.main.activity_hillforts.*
import kotlinx.android.synthetic.main.activity_hillforts.description
import kotlinx.android.synthetic.main.activity_hillforts.hillfortTitle
import kotlinx.android.synthetic.main.activity_hillforts.visited
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast

class HillfortView : BaseView(), AnkoLogger {

    lateinit var presenter: HillfortPresenter
    lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillforts)
        super.init(toolbarAdd, true)

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

        star_outline1.setOnClickListener(){
            star_outline1.visibility = View.GONE
            star_outline2.visibility = View.VISIBLE
            star_outline3.visibility = View.VISIBLE
            star_outline4.visibility = View.VISIBLE
            star_outline5.visibility = View.VISIBLE
            star1.visibility = View.VISIBLE
            star2.visibility = View.GONE
            star3.visibility = View.GONE
            star4.visibility = View.GONE
            star5.visibility = View.GONE
            presenter.doSetRate("outline", 1)
        }

        star1.setOnClickListener(){
            star_outline2.visibility = View.VISIBLE
            star_outline3.visibility = View.VISIBLE
            star_outline4.visibility = View.VISIBLE
            star_outline5.visibility = View.VISIBLE
            star2.visibility = View.GONE
            star3.visibility = View.GONE
            star4.visibility = View.GONE
            star5.visibility = View.GONE
            presenter.doSetRate("star", 1)
        }

        star_outline2.setOnClickListener(){
            star_outline1.visibility = View.GONE
            star_outline2.visibility = View.GONE
            star_outline3.visibility = View.VISIBLE
            star_outline4.visibility = View.VISIBLE
            star_outline5.visibility = View.VISIBLE
            star1.visibility = View.VISIBLE
            star2.visibility = View.VISIBLE
            star3.visibility = View.GONE
            star4.visibility = View.GONE
            star5.visibility = View.GONE
            presenter.doSetRate("outline", 2)
        }

        star2.setOnClickListener(){
            star_outline3.visibility = View.VISIBLE
            star_outline4.visibility = View.VISIBLE
            star_outline5.visibility = View.VISIBLE
            star3.visibility = View.GONE
            star4.visibility = View.GONE
            star5.visibility = View.GONE
            presenter.doSetRate("star", 2)
        }

        star_outline3.setOnClickListener(){
            star_outline1.visibility = View.GONE
            star_outline2.visibility = View.GONE
            star_outline3.visibility = View.GONE
            star_outline4.visibility = View.VISIBLE
            star_outline5.visibility = View.VISIBLE
            star1.visibility = View.VISIBLE
            star2.visibility = View.VISIBLE
            star3.visibility = View.VISIBLE
            star4.visibility = View.GONE
            star5.visibility = View.GONE
            presenter.doSetRate("outline", 3)
        }

        star3.setOnClickListener(){
            star_outline4.visibility = View.VISIBLE
            star_outline5.visibility = View.VISIBLE
            star4.visibility = View.GONE
            star5.visibility = View.GONE
            presenter.doSetRate("star", 3)
        }

        star_outline4.setOnClickListener(){
            star_outline1.visibility = View.GONE
            star_outline2.visibility = View.GONE
            star_outline3.visibility = View.GONE
            star_outline4.visibility = View.GONE
            star_outline5.visibility = View.VISIBLE
            star1.visibility = View.VISIBLE
            star2.visibility = View.VISIBLE
            star3.visibility = View.VISIBLE
            star4.visibility = View.VISIBLE
            star5.visibility = View.GONE
            presenter.doSetRate("outline", 4)
        }

        star4.setOnClickListener(){
            star_outline5.visibility = View.VISIBLE
            star5.visibility = View.GONE
            presenter.doSetRate("star", 4)
        }

        star_outline5.setOnClickListener(){
            star_outline1.visibility = View.GONE
            star_outline2.visibility = View.GONE
            star_outline3.visibility = View.GONE
            star_outline4.visibility = View.GONE
            star_outline5.visibility = View.GONE
            star1.visibility = View.VISIBLE
            star2.visibility = View.VISIBLE
            star3.visibility = View.VISIBLE
            star4.visibility = View.VISIBLE
            star5.visibility = View.VISIBLE
            presenter.doSetRate("outline", 5)
        }

        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync {
            presenter.doConfigureMap(it)
            it.setOnMapClickListener { presenter.doSetLocation() }
        }
    }

    override fun showHillfort(hillfort: HillfortModel) {
        hillfortTitle.setText(hillfort.title)
        description.setText(hillfort.description)
        additionalNote.setText(hillfort.additionalNote)

        if(hillfort.image1 != ""){Glide.with(getApplicationContext()).load(hillfort.image1).into(hillfortImage1)}
        if(hillfort.image2 != ""){Glide.with(getApplicationContext()).load(hillfort.image2).into(hillfortImage2)}
        if(hillfort.image3 != ""){Glide.with(getApplicationContext()).load(hillfort.image3).into(hillfortImage3)}
        if(hillfort.image4 != ""){Glide.with(getApplicationContext()).load(hillfort.image4).into(hillfortImage4)}

        if(hillfort.visited){
            visited.isChecked = true
            textViewForVisited.setText(hillfort.dateVisited)
        } else {
            visited.isChecked = false
            textViewForVisited.setText("")
        }


        if (hillfort.rate == 0) {
            star_outline1.visibility = View.VISIBLE
            star_outline2.visibility = View.VISIBLE
            star_outline3.visibility = View.VISIBLE
            star_outline4.visibility = View.VISIBLE
            star_outline5.visibility = View.VISIBLE
            star1.visibility = View.GONE
            star2.visibility = View.GONE
            star3.visibility = View.GONE
            star4.visibility = View.GONE
            star5.visibility = View.GONE
        } else if(hillfort.rate == 1){
            star_outline1.visibility = View.GONE
            star_outline2.visibility = View.VISIBLE
            star_outline3.visibility = View.VISIBLE
            star_outline4.visibility = View.VISIBLE
            star_outline5.visibility = View.VISIBLE
            star1.visibility = View.VISIBLE
            star2.visibility = View.GONE
            star3.visibility = View.GONE
            star4.visibility = View.GONE
            star5.visibility = View.GONE
        } else if (hillfort.rate == 2){
            star_outline1.visibility = View.GONE
            star_outline2.visibility = View.GONE
            star_outline3.visibility = View.VISIBLE
            star_outline4.visibility = View.VISIBLE
            star_outline5.visibility = View.VISIBLE
            star1.visibility = View.VISIBLE
            star2.visibility = View.VISIBLE
            star3.visibility = View.GONE
            star4.visibility = View.GONE
            star5.visibility = View.GONE
        } else if (hillfort.rate == 3){
            star_outline1.visibility = View.GONE
            star_outline2.visibility = View.GONE
            star_outline3.visibility = View.GONE
            star_outline4.visibility = View.VISIBLE
            star_outline5.visibility = View.VISIBLE
            star1.visibility = View.VISIBLE
            star2.visibility = View.VISIBLE
            star3.visibility = View.VISIBLE
            star4.visibility = View.GONE
            star5.visibility = View.GONE
        } else if (hillfort.rate == 4){
            star_outline1.visibility = View.GONE
            star_outline2.visibility = View.GONE
            star_outline3.visibility = View.GONE
            star_outline4.visibility = View.GONE
            star_outline5.visibility = View.VISIBLE
            star1.visibility = View.VISIBLE
            star2.visibility = View.VISIBLE
            star3.visibility = View.VISIBLE
            star4.visibility = View.VISIBLE
            star5.visibility = View.GONE
        } else {
            star_outline1.visibility = View.GONE
            star_outline2.visibility = View.GONE
            star_outline3.visibility = View.GONE
            star_outline4.visibility = View.GONE
            star_outline5.visibility = View.GONE
            star1.visibility = View.VISIBLE
            star2.visibility = View.VISIBLE
            star3.visibility = View.VISIBLE
            star4.visibility = View.VISIBLE
            star5.visibility = View.VISIBLE
        }

        this.showLocation(hillfort.location)
    }

    override fun showLocation(location: Location) {
        lat.setText("%.6f".format(location.lat))
        lng.setText("%.6f".format(location.lng))
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
                    info ("DoaddorsaveCalled")
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