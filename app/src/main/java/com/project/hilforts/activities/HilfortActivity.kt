package com.project.hilforts.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.project.hilforts.R
import com.project.hilforts.helpers.readImage
import com.project.hilforts.helpers.readImageFromPath
import com.project.hilforts.helpers.showImagePicker
import com.project.hilforts.main.MainApp
import com.project.hilforts.models.HilfortModel
import kotlinx.android.synthetic.main.activity_hilforts.*
import kotlinx.android.synthetic.main.activity_hilforts.description
import kotlinx.android.synthetic.main.activity_hilforts.hilfortTitle
import kotlinx.android.synthetic.main.card_hilfort.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast

class HilfortActivity : AppCompatActivity(), AnkoLogger {

    var hilfort = HilfortModel()
    lateinit var app: MainApp
    val IMAGE_REQUEST1 = 1
    val IMAGE_REQUEST2 = 2
    val IMAGE_REQUEST3 = 3
    val IMAGE_REQUEST4 = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hilforts)
        app = application as MainApp

        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)

        var edit = false

        if(intent.hasExtra("hilfort_edit")){
            edit = true
            hilfort = intent.extras?.getParcelable<HilfortModel>("hilfort_edit")!!
            hilfortTitle.setText(hilfort.title)
            description.setText(hilfort.description)

            if(hilfort.image1 != ""){hilfortImage1.setImageBitmap(readImageFromPath(this, hilfort.image1))}
            if(hilfort.image2 != ""){hilfortImage2.setImageBitmap(readImageFromPath(this, hilfort.image2))}
            if(hilfort.image3 != ""){hilfortImage3.setImageBitmap(readImageFromPath(this, hilfort.image3))}
            if(hilfort.image4 != ""){hilfortImage4.setImageBitmap(readImageFromPath(this, hilfort.image4))}

            btnAdd.setText(R.string.save_hilfort)
        }

        hilfortImage1.setOnClickListener(){
            showImagePicker(this, IMAGE_REQUEST1)
        }

        hilfortImage2.setOnClickListener(){
            showImagePicker(this, IMAGE_REQUEST2)
        }

        hilfortImage3.setOnClickListener(){
            showImagePicker(this, IMAGE_REQUEST3)
        }

        hilfortImage4.setOnClickListener(){
            showImagePicker(this, IMAGE_REQUEST4)
        }

        btnAdd.setOnClickListener() {
            hilfort.title = hilfortTitle.text.toString()
            hilfort.description = description.text.toString()
            if(hilfort.title.isEmpty()){
                toast(R.string.enter_hilfort_title)
            } else {

                if(edit){
                    app.hilforts.update(hilfort.copy())
                } else {
                    app.hilforts.create(hilfort.copy())
                }
            }
            setResult(AppCompatActivity.RESULT_OK)
            finish()
        }

        visited.setOnClickListener(){
            if(visited.isChecked){
                hilfort.visited = true
            } else{
                hilfort.visited = false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_hilfort, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQUEST1 -> {
                if (data != null) {
                    hilfort.image1 = data.getData().toString()
                    hilfortImage1.setImageBitmap(readImage(this, resultCode, data))
                }
            }
            IMAGE_REQUEST2 -> {
                if (data != null) {
                    hilfort.image2 = data.getData().toString()
                    hilfortImage2.setImageBitmap(readImage(this, resultCode, data))
                }
            }
            IMAGE_REQUEST3 -> {
                if (data != null) {
                    hilfort.image3 = data.getData().toString()
                    hilfortImage3.setImageBitmap(readImage(this, resultCode, data))
                }
            }
            IMAGE_REQUEST4 -> {
                if (data != null) {
                    hilfort.image4 = data.getData().toString()
                    hilfortImage4.setImageBitmap(readImage(this, resultCode, data))
                }
            }
        }
    }
}
