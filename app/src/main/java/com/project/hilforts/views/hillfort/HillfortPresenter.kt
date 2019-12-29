package com.project.hilforts.views.hillfort

import android.content.Intent
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

    var defaultLocation = Location(54.189,-4.557, 5.2f)
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
        if (edit == false) {
            view?.navigateTo(VIEW.LOCATION, LOCATION_REQUEST, "location", defaultLocation)
        } else {
            view?.navigateTo(
                VIEW.LOCATION,
                LOCATION_REQUEST,
                "location",
                Location(hillfort.lat, hillfort.lng, hillfort.zoom)
            )
        }
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
            }
        }
    }


}