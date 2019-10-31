package com.project.hilforts.main

import android.app.Application
import com.project.hilforts.models.HillfortMemStore
import com.project.hilforts.models.HillfortModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainApp : Application(), AnkoLogger {

    val hillforts = HillfortMemStore()

    override fun onCreate() {
        super.onCreate()
        info("Hilfort started")
        hillforts.create(HillfortModel(title = "One", description = "About One"))
        hillforts.create(HillfortModel(title = "Two", description = "About Two"))
        hillforts.create(HillfortModel(title = "Three", description = "About Three"))
        hillforts.create(HillfortModel(title = "Three", description = "About Three", visited = true))
    }
}