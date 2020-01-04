package com.project.hilforts.views.hillfortList

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.project.hilforts.R
import com.project.hilforts.models.HillfortModel
import com.project.hilforts.views.base.BaseView
import kotlinx.android.synthetic.main.activity_hilfort_list.*
import kotlinx.android.synthetic.main.app_bar_main.*

class HillfortListView : BaseView(),
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
        super.init(toolBar, false)

        presenter = initPresenter(HillfortListPresenter(this)) as HillfortListPresenter

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

        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.item_home -> {
                    presenter.loadHillforts()
                    true
                }
                R.id.item_favorites -> {
                    presenter.loadFavoriteHillforts()
                    true
                }
                else -> false
            }
        }

        bottom_navigation.setOnNavigationItemReselectedListener {
            when(it.itemId){
                R.id.item_home -> {
                    presenter.loadHillforts()
                }
                R.id.item_favorites -> {
                    presenter.loadFavoriteHillforts()
                }
            }
        }
        nav_view.setNavigationItemSelectedListener(this)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        presenter.loadHillforts()
    }

    override fun showHillforts(hillforts: List<HillfortModel>) {
        recyclerView.adapter = HillfortAdapter(hillforts, this)
        recyclerView.adapter?.notifyDataSetChanged()
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
                if(bottom_navigation.menu.getItem(0).isChecked){
                    presenter.loadHillforts()
                } else {
                    presenter.loadFavoriteHillforts()
                }
            }
            R.id.add -> {
                presenter.doAddHillfort()
            }
            R.id.edit -> {
                isHome = false
                isEditing = true
                isDeleting = false
                if(bottom_navigation.menu.getItem(0).isChecked){
                    presenter.loadHillforts()
                } else {
                    presenter.loadFavoriteHillforts()
                }
            }
            R.id.settings -> {
                presenter.doShowSettingsScreen()
            }
            R.id.logout -> {
                presenter.doLogout()
            }
            R.id.delete -> {
                isHome = false
                isEditing = false
                isDeleting = true
                if(bottom_navigation.menu.getItem(0).isChecked){
                    presenter.loadHillforts()
                } else {
                    presenter.loadFavoriteHillforts()
                }
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onHillfortClick(hillfort: HillfortModel) {
        if(isHome){
            presenter.doAddOrDeleteToFavorites(hillfort)
            if(bottom_navigation.menu.getItem(0).isChecked){
                presenter.loadHillforts()
            } else {
                presenter.loadFavoriteHillforts()
            }

        } else if (isEditing){
            presenter.doEditHillfort(hillfort)
            if(bottom_navigation.menu.getItem(0).isChecked){
                presenter.loadHillforts()
            } else {
                presenter.loadFavoriteHillforts()
            }
        } else {
            val alertDialog = AlertDialog.Builder(this@HillfortListView)
            alertDialog.setTitle("${hillfort.title}")
            alertDialog.setMessage("Do you want to delete Hillfort?")

            alertDialog.setPositiveButton("Yes") { dialog, which ->
                presenter.doDeleteHillfort(hillfort)
                if(bottom_navigation.menu.getItem(0).isChecked){
                    presenter.loadHillforts()
                } else {
                    presenter.loadFavoriteHillforts()
                }
            }
            alertDialog.setNeutralButton("Cancel"){dialog, which ->
                dialog.dismiss()
            }
            alertDialog.show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        presenter.loadHillforts()
        super.onActivityResult(requestCode, resultCode, data)
    }
}