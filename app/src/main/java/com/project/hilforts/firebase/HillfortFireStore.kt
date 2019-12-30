package com.project.hilforts.firebase

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.project.hilforts.models.HillfortModel
import com.project.hilforts.models.HillfortStore
import org.jetbrains.anko.AnkoLogger

class HillfortFireStore(val context: Context): HillfortStore, AnkoLogger{

    val hillforts =  ArrayList<HillfortModel>()
    lateinit var userId: String
    lateinit var db: DatabaseReference

    override fun findAll(): List<HillfortModel> {
        return hillforts
    }

    override fun findById(fbId: String): HillfortModel? {
        val foundHillfort: HillfortModel? = hillforts.find {h -> h.fbId == fbId}
        return foundHillfort
    }

    override fun create(hillfort: HillfortModel) {
        val key = db.child("users").child(userId).child("hillforts").push().key
        key?.let {
            hillfort.fbId = key
            hillforts.add(hillfort)
            db.child("user").child(userId).child("hillforts").child(key).setValue(hillfort)
        }
    }

    override fun update(hillfort: HillfortModel) {
        val foundHillfort: HillfortModel? = hillforts.find {h -> h.fbId == hillfort.fbId}
        if (foundHillfort != null){
            foundHillfort.title = hillfort.title
            foundHillfort.description = hillfort.description
            foundHillfort.image1 = hillfort.image1
            foundHillfort.image2 = hillfort.image2
            foundHillfort.image3 = hillfort.image3
            foundHillfort.image4 = hillfort.image4
            foundHillfort.visited = hillfort.visited
            foundHillfort.location = hillfort.location
            foundHillfort.dateVisited = hillfort.dateVisited
            foundHillfort.additionalNote = hillfort.additionalNote
        }
        db.child("user").child(userId).child("hillforts").child(hillfort.fbId).setValue(hillfort)
    }

    override fun delete(hillfort: HillfortModel) {
        db.child("user").child(userId).child("hillforts").child(hillfort.fbId).removeValue()
        hillforts.remove(hillfort)
    }

    override fun clear() {
        hillforts.clear()
    }

    fun fetchHillforts(hillfortsReady: () -> Unit) {
        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(dataSnapshot: DatabaseError) {}
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot!!.children.mapNotNullTo(hillforts){it.getValue(HillfortModel::class.java) as HillfortModel}
                hillfortsReady()
            }
        }
        userId = FirebaseAuth.getInstance().currentUser!!.uid
        db = FirebaseDatabase.getInstance().reference
        hillforts.clear()
        db.child("users").child(userId).child("hillforts").addListenerForSingleValueEvent(valueEventListener)
    }
}