package com.project.hilforts.models.firebase

import android.content.Context
import android.graphics.Bitmap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.StorageReference
import com.project.hilforts.helpers.readImageFromPath
import com.project.hilforts.models.HillfortModel
import com.project.hilforts.models.HillfortStore
import org.jetbrains.anko.AnkoLogger
import java.io.ByteArrayOutputStream
import java.io.File

class HillfortFireStore(val context: Context): HillfortStore, AnkoLogger{

    val hillforts =  ArrayList<HillfortModel>()
    lateinit var userId: String
    lateinit var db: DatabaseReference
    lateinit var st: StorageReference

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
            foundHillfort.visited = hillfort.visited
            foundHillfort.location = hillfort.location
            foundHillfort.dateVisited = hillfort.dateVisited
            foundHillfort.additionalNote = hillfort.additionalNote
        }
        db.child("user").child(userId).child("hillforts").child(hillfort.fbId).setValue(hillfort)
        if ((hillfort.image1.length) > 0 && (hillfort.image1[0] != 'h')) {
            updateImage(hillfort)
        }

        /*if ((hillfort.image1.length) > 0 && (hillfort.image1[0] != 'h')) {
            updateImage(hillfort, 1)
        } else if ((hillfort.image2.length) > 0 && (hillfort.image2[0] != 'h')){
            updateImage(hillfort, 2)
        } else if ((hillfort.image3.length) > 0 && (hillfort.image3[0] != 'h')){
            updateImage(hillfort, 3)
        } else if ((hillfort.image4.length) > 0 && (hillfort.image4[0] != 'h')){
            updateImage(hillfort, 4)
        }*/
    }

    override fun delete(hillfort: HillfortModel) {
        db.child("user").child(userId).child("hillforts").child(hillfort.fbId).removeValue()
        hillforts.remove(hillfort)
    }

    override fun clear() {
        hillforts.clear()
    }

    /*fun updateImage(hillfort: HillfortModel, num: Int){

        var fileName = File(hillfort.image1)

        var bitmap = readImageFromPath(context, hillfort.image1)

        if(num == 1){
            fileName = File(hillfort.image1)
            bitmap = readImageFromPath(context, hillfort.image1)
        } else if(num == 2){
            fileName = File(hillfort.image2)
            bitmap = readImageFromPath(context, hillfort.image2)
        } else if(num == 3){
            fileName = File(hillfort.image3)
            bitmap = readImageFromPath(context, hillfort.image3)
        } else if(num == 4) {
            fileName = File(hillfort.image4)
            bitmap = readImageFromPath(context, hillfort.image4)
        }

        if (hillfort.image1 != "" || hillfort.image2 != "" || hillfort.image3 != "" || hillfort.image4 != "") {
            val imageName = fileName.getName()

            val imageRef = st.child(userId + '/' + imageName)
            val baos = ByteArrayOutputStream()

            bitmap?.let {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val data = baos.toByteArray()
                val uploadTask = imageRef.putBytes(data)
                uploadTask.addOnFailureListener {
                    println(it.message)
                }.addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
                        hillfort.image1 = it.toString()
                        db.child("users").child(userId).child("hillforts").child(hillfort.fbId).setValue(hillfort)
                    }
                }
            }
        }
    }*/

    fun updateImage(hillfort: HillfortModel){


        if (hillfort.image1 != "" ) {
            val fileName = File(hillfort.image1)
            val imageName = fileName.getName()

            val imageRef = st.child(userId + '/' + imageName)
            val baos = ByteArrayOutputStream()

            val bitmap = readImageFromPath(context, hillfort.image1)

            bitmap?.let {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val data = baos.toByteArray()
                val uploadTask = imageRef.putBytes(data)
                uploadTask.addOnFailureListener {
                    println(it.message)
                }.addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
                        hillfort.image1 = it.toString()
                        db.child("users").child(userId).child("hillforts").child(hillfort.fbId).setValue(hillfort)
                    }
                }
            }
        }
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