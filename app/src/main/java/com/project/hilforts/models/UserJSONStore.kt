package com.project.hilforts.models

import android.content.Context
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.project.hilforts.helpers.exists
import com.project.hilforts.helpers.read
import com.project.hilforts.helpers.write
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random


val JSON_FILE = "placemarks.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<java.util.ArrayList<UserModel>>() {}.type

fun generateRandomId(): String {
    return UUID.randomUUID().toString()
}

class UserJSONStore : UserStore, AnkoLogger {

    var users = ArrayList<UserModel>()
    val context: Context

    constructor (context: Context) {
        this.context = context
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }
    override fun findAll(): ArrayList<UserModel>{
        return users
    }

    override fun add(user: UserModel) {
        users.add(user)
        serialize()
    }

    override fun createUserHillfort(userEmail: String, hillfort: HillfortModel) {
        hillfort.id = generateRandomId()
        getUserHillforts(userEmail).add(hillfort)
        logAll(userEmail)
        serialize()
    }

    override fun updateUserHillfort(userEmail: String, hillfort: HillfortModel) {
        var foundHillfort: HillfortModel? = getUserHillforts(userEmail).find { p -> p.id == hillfort.id }
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
            logAll(userEmail)
            serialize()
        }
    }

    fun logAll(userEmail: String){
        getUserHillforts(userEmail).forEach{info { "${it}" }}
    }

    override fun getUserHillforts(userEmail: String): ArrayList<HillfortModel> {

        var user = users.find { u -> u.email == userEmail }
        if(user != null){
            return user.hillfortList
        }

        return ArrayList<HillfortModel>()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(users, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        users = Gson().fromJson(jsonString, listType)
    }
}