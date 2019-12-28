package com.project.hilforts.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.project.hilforts.R
import com.project.hilforts.helpers.readImageFromPath
import com.project.hilforts.models.HillfortModel
import kotlinx.android.synthetic.main.activity_hillforts.*
import kotlinx.android.synthetic.main.activity_hillforts.description
import kotlinx.android.synthetic.main.activity_hillforts.hillfortTitle
import kotlinx.android.synthetic.main.activity_hillforts.visited
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast

class HillfortActivity : AppCompatActivity(), AnkoLogger {

    lateinit var presenter: HillfortPresenter
    var hillfort = HillfortModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillforts)
        toolbarAdd.title = "Add/Edit"
        setSupportActionBar(toolbarAdd)

        presenter = HillfortPresenter(this)

        hillfortImage1.setOnClickListener(){
            presenter.doSelectImage(1)
        }

        hillfortImage2.setOnClickListener(){
            presenter.doSelectImage(2)
        }

        hillfortImage3.setOnClickListener(){
            presenter.doSelectImage(3)
        }

        hillfortImage4.setOnClickListener(){
            presenter.doSelectImage(4)
        }

        visited.setOnClickListener(){
            val str = presenter.doSetVisited()
            textViewForVisited.setText(str)
        }

        btnAdd.setOnClickListener() {
            if(hillfortTitle.text.toString().isEmpty()){
                toast(R.string.enter_hillfort_title)
            } else {
                presenter.doAddOrSave(hillfortTitle.text.toString(), description.text.toString(), additionalNote.text.toString())
            }
        }

        hillfortLocation.setOnClickListener{
            presenter.doSetLocation()
        }
    }

    fun showHillfort(hillfort: HillfortModel) {
        hillfortTitle.setText(hillfort.title)
        description.setText(hillfort.description)
        additionalNote.setText(hillfort.additionalNote)
        if(hillfort.image1 != ""){hillfortImage1.setImageBitmap(readImageFromPath(this, hillfort.image1))}
        if(hillfort.image2 != ""){hillfortImage2.setImageBitmap(readImageFromPath(this, hillfort.image2))}
        if(hillfort.image3 != ""){hillfortImage3.setImageBitmap(readImageFromPath(this, hillfort.image3))}
        if(hillfort.image4 != ""){hillfortImage4.setImageBitmap(readImageFromPath(this, hillfort.image4))}

        if(hillfort.visited){
            visited.isChecked = true
            textViewForVisited.setText(hillfort.dateVisited)
        } else {
            visited.isChecked = false
            textViewForVisited.setText("")
        }
        btnAdd.setText(R.string.save_hillfort)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_hillfort, menu)
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
        if (data != null) {
            presenter.doActivityResult(requestCode, resultCode, data)
        }
    }
}