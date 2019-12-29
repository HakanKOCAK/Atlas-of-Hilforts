package com.project.hilforts.views.hillfortList

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.project.hilforts.R
import com.project.hilforts.models.HillfortModel
import kotlinx.android.synthetic.main.activity_hilfort_list.*
import kotlinx.android.synthetic.main.app_bar_main.*

class HillfortListView : AppCompatActivity(),
    HillfortListener, NavigationView.OnNavigationItemSelectedListener{
    lateinit var presenter: HillfortListPresenter

    companion object {
        var isHome = true
        var isEditing = false
        var isDeleting = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hilfort_list)
        setSupportActionBar(toolBar)
        val actionBar = supportActionBar

        presenter = HillfortListPresenter(this)

        if(isHome){
            actionBar?.title = "Home"
        } else if(isEditing) {
            actionBar?.title = "Edit"
        } else {
            actionBar?.title = "Delete"
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
        recyclerView.adapter =
            HillfortAdapter(presenter.getHillforts(), this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_map -> presenter.doShowHillfortsMap()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId){
            R.id.home -> {
                isHome = true
                isEditing = false
                isDeleting = false
                presenter.doShowSelectedScreen()
            }
            R.id.add -> {
                presenter.doAddHillfort()
            }
            R.id.edit -> {
                isHome = false
                isEditing = true
                isDeleting = false
                presenter.doShowSelectedScreen()
            }
            R.id.settings -> {
                presenter.doShowSettingsScreen()
            }
            R.id.logout -> {
                presenter.doLogOut()
            }
            R.id.delete -> {
                isHome = false
                isEditing = false
                isDeleting = true
                presenter.doShowSelectedScreen()
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
        if(isHome){
            return
        } else if (isEditing){
           presenter.doEditHillfort(hillfort)
        } else {
            val alertDialog = AlertDialog.Builder(this@HillfortListView)
            alertDialog.setTitle("${hillfort.title}")
            alertDialog.setMessage("Do you want to delete Hillfort?")

            alertDialog.setPositiveButton("Yes") { dialog, which ->
                presenter.doDeleteHillfort(hillfort)
            }
            alertDialog.setNeutralButton("Cancel"){dialog, which ->
                dialog.dismiss()
            }
            alertDialog.show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        recyclerView.adapter?.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }
}