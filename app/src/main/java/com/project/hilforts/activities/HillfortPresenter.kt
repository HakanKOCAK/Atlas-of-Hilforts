package com.project.hilforts.activities

import android.content.Intent
import com.project.hilforts.helpers.showImagePicker
import com.project.hilforts.main.MainApp
import com.project.hilforts.models.HillfortModel
import com.project.hilforts.models.Location
import kotlinx.android.synthetic.main.activity_hillforts.*
import org.jetbrains.anko.intentFor
import java.text.SimpleDateFormat
import java.util.*

class HillfortPresenter(val view: HillfortActivity) {

    lateinit var app: MainApp
    var hillfort = HillfortModel()
    val IMAGE_REQUEST1 = 1
    val IMAGE_REQUEST2 = 2
    val IMAGE_REQUEST3 = 3
    val IMAGE_REQUEST4 = 4

    var location = Location(54.189,-4.557, 5.2f)
    val LOCATION_REQUEST = 5

    var edit = false

    init {
        app = view.application as MainApp
        if (view.intent.hasExtra("hillfort_edit")) {
            edit = true
            hillfort = view.intent.extras?.getParcelable<HillfortModel>("hillfort_edit")!!
            view.showHillfort(hillfort)
        }
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
        view.finish()
    }

    fun doCancel() {
        view.finish()
    }

    fun doDelete() {
        app.users.deleteUserHillfort(app.loggedInUserEmail,hillfort)
        view.finish()
    }

    fun doSelectImage(num: Int) {
        if (num == 1){
            showImagePicker(view, IMAGE_REQUEST1)
        }else if (num == 2){
            showImagePicker(view, IMAGE_REQUEST2)
        }else if(num == 3){
            showImagePicker(view, IMAGE_REQUEST3)
        }else{
            showImagePicker(view, IMAGE_REQUEST4)
        }
    }

    fun doSetLocation() {
        if (hillfort.zoom != 0f) {
            location.lat = hillfort.lat
            location.lng = hillfort.lng
            location.zoom = hillfort.zoom
        }
        view.startActivityForResult(view.intentFor<MapActivity>().putExtra("location", location), LOCATION_REQUEST)
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
    fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            IMAGE_REQUEST1 -> {
                hillfort.image1 = data.data.toString()
                view.showHillfort(hillfort)
            }
            IMAGE_REQUEST2 -> {
                if (data != null) {
                    hillfort.image2 = data.getData().toString()
                    view.showHillfort(hillfort)
                }
            }
            IMAGE_REQUEST3 -> {
                if (data != null) {
                    hillfort.image3 = data.getData().toString()
                    view.showHillfort(hillfort)
                }
            }
            IMAGE_REQUEST4 -> {
                if (data != null) {
                    hillfort.image4 = data.getData().toString()
                    view.showHillfort(hillfort)
                }
            }
            LOCATION_REQUEST -> {
                location = data.extras?.getParcelable<Location>("location")!!
                hillfort.lat = location.lat
                hillfort.lng = location.lng
                hillfort.zoom = location.zoom
            }
        }
    }


}