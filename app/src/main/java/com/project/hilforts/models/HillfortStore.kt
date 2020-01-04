package com.project.hilforts.models

interface HillfortStore{
    fun findAll(): List<HillfortModel>
    fun findFavorites(): List<HillfortModel>
    fun create(hillfort: HillfortModel)
    fun update(hillfort: HillfortModel)
    fun delete(hillfort: HillfortModel)
    fun findById(fbId: String) : HillfortModel?
    fun clear()
}