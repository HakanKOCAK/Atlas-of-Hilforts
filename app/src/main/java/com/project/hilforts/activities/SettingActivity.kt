package com.project.hilforts.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.AnkoLogger
import com.project.hilforts.R
import com.project.hilforts.main.MainApp
import kotlinx.android.synthetic.main.activity_setting.*
import org.jetbrains.anko.info

class SettingActivity: AppCompatActivity(), AnkoLogger {
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        app =  application as MainApp

        toolbarSettings.title = "Settings"
        setSupportActionBar(toolbarSettings)

        var count = 0
        val hillforts = app.hillforts.findAll()
        for(h in hillforts){
            if(h.visited) count += 1
        }
        visited_hillforts.setText("Visited Hilforts: ${count}")
        total_hillforts.setText("Total Number of Hilforts: ${hillforts.size}")
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
}