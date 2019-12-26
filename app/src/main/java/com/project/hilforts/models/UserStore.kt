package com.project.hilforts.models

interface UserStore {
    fun add(user: UserModel)
    fun findAll(): ArrayList<UserModel>
    fun updateUserHillfort(userEmail: String, hillfort: HillfortModel)
    fun createUserHillfort(userEmail: String, hillfort: HillfortModel)
    fun getUserHillforts(userEmail: String): ArrayList<HillfortModel>
    fun deleteUserHillfort(userEmail: String, hillfort: HillfortModel)
    fun findHillfortById(userEmail: String, id: String) : HillfortModel?
}