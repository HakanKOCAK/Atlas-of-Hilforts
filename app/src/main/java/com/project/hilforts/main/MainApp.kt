package com.project.hilforts.main

import android.app.Application
import com.project.hilforts.models.HillfortMemStore
import com.project.hilforts.models.HillfortModel
import org.jetbrains.anko.AnkoLogger

class MainApp : Application(), AnkoLogger {

    lateinit var hillforts: HillfortMemStore

    override fun onCreate() {
        super.onCreate()
        hillforts = HillfortMemStore()
        hillforts.create(HillfortModel(title = "One", description = "About One"))
        hillforts.create(HillfortModel(title = "Two", description = "About Two"))
        hillforts.create(HillfortModel(title = "Three", description = "About Three"))
        hillforts.create(HillfortModel(title = "Three", description = "About Three", visited = true))
    }
}