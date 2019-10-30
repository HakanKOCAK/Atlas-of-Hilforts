package com.project.hilforts.models

interface HilfortStore{
    fun findAll(): List<HilfortModel>
    fun create(hilfort: HilfortModel)
    fun update(hilfort: HilfortModel)
}