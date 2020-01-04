package com.project.hilforts.main

import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.project.hilforts.models.HillfortModel
import com.project.hilforts.models.HillfortStore
import com.project.hilforts.models.Location
import com.project.hilforts.models.firebase.HillfortFireStore
import org.jetbrains.anko.AnkoLogger

class MainApp : Application(), AnkoLogger {

    lateinit var hillforts: HillfortStore

    lateinit var loggedInUserEmail: String
    lateinit var loggedInUserPassword: String

    override fun onCreate() {
        super.onCreate()
        hillforts = HillfortFireStore(applicationContext)
    }

    fun setDefaultHillforts(){
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        val db = FirebaseDatabase.getInstance().reference

        val key1 = db.child("users").child(userId).child("hillforts").push().key
        key1?.let{
            val hillfort = HillfortModel(fbId = key1, title = "Ballinkillin", description = "Contour Fort", location = Location(lat = 52.653029, lng = -6.9333, zoom = 13f))
            db.child("users").child(userId).child("hillforts").child(key1).setValue(hillfort)
        }

        val key2 = db.child("users").child(userId).child("hillforts").push().key
        key2?.let{
            val hillfort = HillfortModel(fbId = key2, title = "Gortacrossig", description = "Promontory Fort", location = Location(lat = 51.48747, lng = -9.21048, zoom = 13f))
            db.child("users").child(userId).child("hillforts").child(key2).setValue(hillfort)
        }

        val key3 = db.child("users").child(userId).child("hillforts").push().key
        key3?.let {
            val hillfort = HillfortModel(fbId = key3, title = "Largysillagh", description = "Promontory Fort", location = Location(lat = 54.62661, lng = -8.51306, zoom = 13f))
            db.child("users").child(userId).child("hillforts").child(key3).setValue(hillfort)
        }

        val key4 = db.child("users").child(userId).child("hillforts").push().key
        key4?.let{
            val hillfort = HillfortModel(fbId = key4, title = "Earlspark", description = "Contour Fort", location = Location(lat = 53.17213, lng = -8.54161, zoom = 13f))
            db.child("users").child(userId).child("hillforts").child(key4).setValue(hillfort)
        }

        val key5 = db.child("users").child(userId).child("hillforts").push().key
        key5?.let {
            val hillfort = HillfortModel(fbId = key5, title = "Muckross", description = "Promontory Fort", location = Location(lat = 54.60892, lng = -8.59217, zoom = 13f))
            db.child("users").child(userId).child("hillforts").child(key5).setValue(hillfort)
        }
    }
}