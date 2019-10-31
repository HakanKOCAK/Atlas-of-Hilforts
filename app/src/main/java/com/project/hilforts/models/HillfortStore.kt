package com.project.hilforts.models

interface HillfortStore{
    fun findAll(): List<HillfortModel>
    fun create(hillfort: HillfortModel)
    fun update(hillfort: HillfortModel)
}