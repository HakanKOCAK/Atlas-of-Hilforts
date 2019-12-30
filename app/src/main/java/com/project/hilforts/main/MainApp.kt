package com.project.hilforts.main

import android.app.Application
import com.google.firebase.auth.FirebaseUser
import com.project.hilforts.firebase.HillfortFireStore
import com.project.hilforts.models.HillfortModel
import com.project.hilforts.models.HillfortStore
import org.jetbrains.anko.AnkoLogger
import java.util.*
import kotlin.collections.ArrayList

class MainApp : Application(), AnkoLogger {

    lateinit var hillforts: HillfortStore

    lateinit var loggedInUserEmail: String
    lateinit var loggedInUserPassword: String

    lateinit var user: FirebaseUser

    override fun onCreate() {
        super.onCreate()
        hillforts = HillfortFireStore(applicationContext)
    }

    /*fun getDefaultHillforts(): ArrayList<HillfortModel> {
        var hillforts = ArrayList<HillfortModel>()

        hillforts.add(HillfortModel(title = "Ballinkillin", description = "Contour Fort", lat = 52.653029, lng = -6.9333, zoom = 13f))
        hillforts.add(HillfortModel(title = "Gortacrossig", description = "Promontory Fort", lat = 51.48747, lng = -9.21048, zoom = 13f))
        hillforts.add(HillfortModel(title = "Largysillagh", description = "Promontory Fort", lat = 54.62661, lng = -8.51306, zoom = 13f))
        hillforts.add(HillfortModel(title = "Earlspark", description = "Contour Fort", lat = 53.17213, lng = -8.54161, zoom = 13f))
        hillforts.add(HillfortModel(title = "Muckross", description = "Promontory Fort", lat = 54.60892, lng = -8.59217, zoom = 13f))
        hillforts.add(HillfortModel(title = "Howth", description = "Promontory Fort", lat = 53.36498, lng = -6.05573, zoom = 13f))
        hillforts.add(HillfortModel(title = "Culoort", description = "Promontory Fort", lat = 55.33215, lng = -7.35183, zoom = 13f))
        hillforts.add(HillfortModel(title = "Redford Glebe", description = "Contour Fort", lat = 55.29004, lng = -7.12054, zoom = 13f))
        hillforts.add(HillfortModel(title = "Knockanacuig", description = "Contour Fort", lat = 52.27088, lng = -9.72976, zoom = 13f))
        hillforts.add(HillfortModel(title = "Kill Hill", description = "Multiple Enclosure Hillfort", lat = 53.24759, lng = -6.57669, zoom = 13f))
        hillforts.add(HillfortModel(title = "Clonmantagh", description = "Level Terrain Fort", lat = 52.73984, lng = -7.50817, zoom = 13f))

        return hillforts
    }*/
}