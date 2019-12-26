package com.project.hilforts.activities

import android.content.DialogInterface
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
import com.project.hilforts.main.MainApp
import com.project.hilforts.models.HillfortModel
import kotlinx.android.synthetic.main.activity_hilfort_list.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.card_hillfort.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult

class HillfortListActivity : AppCompatActivity(), HillfortListener, NavigationView.OnNavigationItemSelectedListener{
    lateinit var app: MainApp

    companion object {
        var isHome = true
        var isEditing = false
        var isDeleting = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hilfort_list)
        app = application as MainApp

        setSupportActionBar(toolBar)
        val actionBar = supportActionBar

        if(HillfortListActivity.isHome){
            actionBar?.title = "Home"
        } else if(HillfortListActivity.isEditing) {
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
        recyclerView.adapter = HillfortAdapter(app.users.getUserHillforts(app.loggedInUserEmail), this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_map -> startActivity<HillfortMapsActivity>()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId){
            R.id.home -> {
                HillfortListActivity.isHome = true
                HillfortListActivity.isEditing = false
                HillfortListActivity.isDeleting = false
                startActivityForResult<HillfortListActivity>(0)
            }
            R.id.add -> {
                startActivityForResult<HillfortActivity>(0)
            }
            R.id.edit -> {
                HillfortListActivity.isHome = false
                HillfortListActivity.isEditing = true
                HillfortListActivity.isDeleting = false
                startActivityForResult<HillfortListActivity>(0)
            }
            R.id.settings -> {
                startActivityForResult<SettingActivity>(0)
            }
            R.id.logout -> {
                startActivityForResult<LoginSignupActivity>(0)
            }
            R.id.delete -> {
                HillfortListActivity.isHome = false
                HillfortListActivity.isEditing = false
                HillfortListActivity.isDeleting = true
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
        } else if (HillfortListActivity.isEditing){
            startActivityForResult(intentFor<HillfortActivity>().putExtra("hillfort_edit", hillfort), 0)
        } else {
            val alertDialog = AlertDialog.Builder(this@HillfortListActivity)
            alertDialog.setTitle("${hillfort.title}")
            alertDialog.setMessage("Do you want to delete Hillfort?")

            alertDialog.setPositiveButton("Yes") { dialog, which ->
                app.users.deleteUserHillfort(app.loggedInUserEmail, hillfort)
                startActivityForResult<HillfortListActivity>(0)
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