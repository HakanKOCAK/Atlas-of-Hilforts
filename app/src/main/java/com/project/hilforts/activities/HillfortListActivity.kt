package com.project.hilforts.activities

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.project.hilforts.R
import com.project.hilforts.main.MainApp
import com.project.hilforts.models.HillfortModel
import kotlinx.android.synthetic.main.activity_hilfort_list.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.card_hillfort.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult

class HillfortListActivity : AppCompatActivity(), HillfortListener, NavigationView.OnNavigationItemSelectedListener{
    lateinit var app: MainApp

    companion object {
        var isHome = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hilfort_list)
        app = application as MainApp

        setSupportActionBar(toolBar)
        val actionBar = supportActionBar

        if(HillfortListActivity.isHome){
            actionBar?.title = "Home"
        } else {
            actionBar?.title = "Edit"
        }
        val drawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolBar,
            (R.string.open),
            (R.string.close)
        ){

        }

        drawerToggle.isDrawerIndicatorEnabled = true
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = HillfortAdapter(app.hillforts.findAll(), this)
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId){
            R.id.home -> {
                HillfortListActivity.isHome = true
                startActivityForResult<HillfortListActivity>(0)
            }
            R.id.add -> {
                startActivityForResult<HillfortActivity>(0)
            }
            R.id.edit -> {
                HillfortListActivity.isHome = false
                startActivityForResult<HillfortListActivity>(0)
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onHillfortClick(hillfort: HillfortModel) {
        if(HillfortListActivity.isHome){
            return
        }
        startActivityForResult(intentFor<HillfortActivity>().putExtra("hillfort_edit", hillfort), 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        recyclerView.adapter?.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }
}