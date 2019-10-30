package com.project.hilforts.main

import android.app.Application
import com.project.hilforts.models.HilfortMemStore
import com.project.hilforts.models.HilfortModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainApp : Application(), AnkoLogger {

    val hilforts = HilfortMemStore()

    override fun onCreate() {
        super.onCreate()
        info("Hilfort started")
        hilforts.create(HilfortModel(title = "One", description = "About One"))
        hilforts.create(HilfortModel(title = "Two", description = "About Two"))
        hilforts.create(HilfortModel(title = "Three", description = "About Three"))
        hilforts.create(HilfortModel(title = "Three", description = "About Three", visited = true))
    }
}