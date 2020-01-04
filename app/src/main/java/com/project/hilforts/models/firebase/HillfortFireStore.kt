package com.project.hilforts.models.firebase

import android.content.Context
import android.graphics.Bitmap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
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

    override fun findFavorites(): List<HillfortModel> {
        return hillforts.filter { h -> h.favorite == true }
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
            db.child("users").child(userId).child("hillforts").child(key).setValue(hillfort)
            updateImage1(hillfort)
            updateImage2(hillfort)
            updateImage3(hillfort)
            updateImage4(hillfort)
        }
    }

    override fun update(hillfort: HillfortModel) {
        val foundHillfort: HillfortModel? = hillforts.find {h -> h.fbId == hillfort.fbId}
        if (foundHillfort != null){
            foundHillfort.title = hillfort.title
            foundHillfort.description = hillfort.description
            foundHillfort.visited = hillfort.visited
            foundHillfort.image1 = hillfort.image1
            foundHillfort.image2 = hillfort.image2
            foundHillfort.image3 = hillfort.image3
            foundHillfort.image4 = hillfort.image4
            foundHillfort.favorite = hillfort.favorite
            foundHillfort.location = hillfort.location
            foundHillfort.dateVisited = hillfort.dateVisited
            foundHillfort.additionalNote = hillfort.additionalNote
        }
        db.child("users").child(userId).child("hillforts").child(hillfort.fbId).setValue(hillfort)

        if ((hillfort.image1.length) > 0 && (hillfort.image1[0] != 'h')) {
            updateImage1(hillfort)
        }
        if ((hillfort.image2.length) > 0 && (hillfort.image2[0] != 'h')) {
            updateImage2(hillfort)
        }
        if ((hillfort.image3.length) > 0 && (hillfort.image3[0] != 'h')){
            updateImage3(hillfort)
        }
        if ((hillfort.image4.length) > 0 && (hillfort.image4[0] != 'h')){
            updateImage4(hillfort)
        }
    }

    override fun delete(hillfort: HillfortModel) {
        db.child("users").child(userId).child("hillforts").child(hillfort.fbId).removeValue()
        hillforts.remove(hillfort)
    }

    override fun clear() {
        hillforts.clear()
    }

    fun updateImage1(hillfort: HillfortModel){
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

    fun updateImage2(hillfort: HillfortModel){
        if (hillfort.image2 != "" ) {
            val fileName = File(hillfort.image2)
            val imageName = fileName.getName()

            val imageRef = st.child(userId + '/' + imageName)
            val baos = ByteArrayOutputStream()

            val bitmap = readImageFromPath(context, hillfort.image2)

            bitmap?.let {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val data = baos.toByteArray()
                val uploadTask = imageRef.putBytes(data)
                uploadTask.addOnFailureListener {
                    println(it.message)
                }.addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
                        hillfort.image2 = it.toString()
                        db.child("users").child(userId).child("hillforts").child(hillfort.fbId).setValue(hillfort)
                    }
                }
            }
        }
    }

    fun updateImage3(hillfort: HillfortModel){
        if (hillfort.image3 != "" ) {
            val fileName = File(hillfort.image3)
            val imageName = fileName.getName()

            val imageRef = st.child(userId + '/' + imageName)
            val baos = ByteArrayOutputStream()

            val bitmap = readImageFromPath(context, hillfort.image3)

            bitmap?.let {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val data = baos.toByteArray()
                val uploadTask = imageRef.putBytes(data)
                uploadTask.addOnFailureListener {
                    println(it.message)
                }.addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
                        hillfort.image3 = it.toString()
                        db.child("users").child(userId).child("hillforts").child(hillfort.fbId).setValue(hillfort)
                    }
                }
            }
        }
    }

    fun updateImage4(hillfort: HillfortModel){
        if (hillfort.image4 != "" ) {
            val fileName = File(hillfort.image4)
            val imageName = fileName.getName()

            val imageRef = st.child(userId + '/' + imageName)
            val baos = ByteArrayOutputStream()

            val bitmap = readImageFromPath(context, hillfort.image4)

            bitmap?.let {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val data = baos.toByteArray()
                val uploadTask = imageRef.putBytes(data)
                uploadTask.addOnFailureListener {
                    println(it.message)
                }.addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
                        hillfort.image4 = it.toString()
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
                dataSnapshot!!.children.mapNotNullTo(hillforts){it.getValue<HillfortModel>(HillfortModel::class.java)}
                hillfortsReady()
            }
        }
        userId = FirebaseAuth.getInstance().currentUser!!.uid
        db = FirebaseDatabase.getInstance().reference
        st = FirebaseStorage.getInstance().reference
        hillforts.clear()
        db.child("users").child(userId).child("hillforts").addListenerForSingleValueEvent(valueEventListener)
    }
}