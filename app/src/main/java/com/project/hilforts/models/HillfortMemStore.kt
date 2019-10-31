package com.project.hilforts.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class HillfortMemStore : HillfortStore, AnkoLogger{
    val hillforts = ArrayList<HillfortModel>()

    override fun findAll(): List<HillfortModel> {
        return hillforts
    }

    override fun create(hillfort: HillfortModel) {
        hillfort.id = getId()
        hillforts.add(hillfort)
        logAll()
    }

    override fun update(hillfort: HillfortModel) {
        var foundHillfort: HillfortModel? = hillforts.find { p -> p.id == hillfort.id }
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
            foundHillfort.dateVisited = hillfort.dateVisited
            foundHillfort.additionalNote = hillfort.additionalNote
            logAll()
        }
    }

    fun logAll(){
        hillforts.forEach{info { "${it}" }}
    }
}