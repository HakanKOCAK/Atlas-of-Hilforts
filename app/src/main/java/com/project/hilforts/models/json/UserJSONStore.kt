/*package com.project.hilforts.models.json

import android.content.Context
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.project.hilforts.helpers.exists
import com.project.hilforts.helpers.read
import com.project.hilforts.helpers.write
import com.project.hilforts.models.HillfortModel
import com.project.hilforts.models.UserModel
import com.project.hilforts.models.UserStore
import kotlin.collections.ArrayList


val JSON_FILE = "hillforts.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<java.util.ArrayList<UserModel>>() {}.type

class UserJSONStore : UserStore, AnkoLogger {
    var users = ArrayList<UserModel>()
    val context: Context
    var hillforts = mutableListOf<HillfortModel>()

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

    override fun deleteUserHillfort(userEmail: String, hillfort: HillfortModel) {
        getUserHillforts(userEmail).remove(hillfort)
        logAll(userEmail)
        serialize()
    }
    override fun createUserHillfort(userEmail: String, hillfort: HillfortModel) {
        getUserHillforts(userEmail).add(hillfort)
        logAll(userEmail)
        serialize()
    }

    override fun updateUserHillfort(userEmail: String, hillfort: HillfortModel) {
        var foundHillfort: HillfortModel? = getUserHillforts(userEmail).find { p -> p.fbId == hillfort.fbId }
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
        }
        serialize()
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

    override fun findHillfortById(userEmail: String, id: String): HillfortModel? {

        var foundHillfort: HillfortModel? = getUserHillforts(userEmail).find { it.id == id }
        return foundHillfort
    }


    private fun serialize() {
        val jsonString = gsonBuilder.toJson(users,
            listType
        )
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        users = Gson().fromJson(jsonString, listType)
    }

    override fun clear() {
        hillforts.clear()
    }
}*/