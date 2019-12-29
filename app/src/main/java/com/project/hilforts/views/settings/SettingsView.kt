package com.project.hilforts.views.settings

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

class SettingsView: AppCompatActivity(), AnkoLogger {

    lateinit var presenter: SettingsPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        presenter = SettingsPresenter(this)
        presenter.doSetView()
        toolbarSettings.title = "Settings"
        setSupportActionBar(toolbarSettings)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_settings, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.item_cancel -> {
                presenter.doCancel()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}