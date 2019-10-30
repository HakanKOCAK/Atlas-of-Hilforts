package com.project.hilforts.models

import android.view.View
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class HilfortMemStore : HilfortStore, AnkoLogger{
    val hilforts = ArrayList<HilfortModel>()

    override fun findAll(): List<HilfortModel> {
        return hilforts
    }

    override fun create(hilfort: HilfortModel) {
        hilfort.id = getId()
        hilforts.add(hilfort)
        logAll()
    }

    override fun update(hilfort: HilfortModel) {
        var foundHilfort: HilfortModel? = hilforts.find { p -> p.id == hilfort.id }
        if(foundHilfort != null){
            foundHilfort.title = hilfort.title
            foundHilfort.description = hilfort.description
            foundHilfort.image1 = hilfort.image1
            foundHilfort.image2 = hilfort.image2
            foundHilfort.image3 = hilfort.image3
            foundHilfort.image4 = hilfort.image4
            foundHilfort.visited = hilfort.visited
            logAll()
        }
    }

    fun logAll(){
        hilforts.forEach{info { "${it}" }}
    }
}