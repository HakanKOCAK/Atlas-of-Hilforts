package com.project.hilforts.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.project.hilforts.R
import com.project.hilforts.main.MainApp
import com.project.hilforts.models.HilfortModel
import kotlinx.android.synthetic.main.activity_hilforts.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast

class HilfortActivity : AppCompatActivity(), AnkoLogger {

    var hilfort = HilfortModel()
    lateinit var app: MainApp

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
            btnAdd.setText(R.string.save_hilfort)
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
}
