package com.project.hilforts.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class HillfortMemStore : HillfortStore, AnkoLogger{
    val hilforts = ArrayList<HillfortModel>()

    override fun findAll(): List<HillfortModel> {
        return hilforts
    }

    override fun create(hilfort: HillfortModel) {
        hilfort.id = getId()
        hilforts.add(hilfort)
        logAll()
    }

    override fun update(hillfort: HillfortModel) {
        var foundHillfort: HillfortModel? = hilforts.find { p -> p.id == hillfort.id }
        if(foundHillfort != null){
            foundHillfort.title = hillfort.title
            foundHillfort.description = hillfort.description
            foundHillfort.image1 = hillfort.image1
            foundHillfort.image2 = hillfort.image2
            foundHillfort.image3 = hillfort.image3
            foundHillfort.image4 = hillfort.image4
            foundHillfort.visited = hillfort.visited
            foundHillfort.lat = hillfort.lat
            foundHillfort.lng = hillfort.lng
            foundHillfort.zoom = hillfort.zoom
            logAll()
        }
    }

    fun logAll(){
        hilforts.forEach{info { "${it}" }}
    }
}