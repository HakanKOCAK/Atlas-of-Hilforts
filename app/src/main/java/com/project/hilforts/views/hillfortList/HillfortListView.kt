package com.project.hilforts.views.hillfortList

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.StrictMode
import android.view.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.project.hilforts.R
import com.project.hilforts.models.HillfortModel
import com.project.hilforts.views.base.BaseView
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.activity_hilfort_list.*
import kotlinx.android.synthetic.main.app_bar_main.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class HillfortListView : BaseView(),
    HillfortListener, NavigationView.OnNavigationItemSelectedListener{
    lateinit var presenter: HillfortListPresenter

    companion object {
        var homeScreen = true
        var editScreen = false
        var deleteScreen = false
        var shareScreen = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hilfort_list)
        setSupportActionBar(toolBar)
        super.init(toolBar, false)

        val builder: StrictMode.VmPolicy.Builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())

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

        val searchItem = menu?.findItem(R.id.item_search)
        if (searchItem != null){
            val searchView = searchItem.actionView as SearchView
            searchView.queryHint = "Search Hillfort"

            searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
                override fun onQueryTextChange(newText: String?): Boolean {

                    if (newText!!.isNotEmpty()){
                        val search = newText.toLowerCase()
                        presenter.doFilterHillforts(search)
                    } else {
                        presenter.loadHillforts()
                    }
                    return true
                }

                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

            })
        }
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
                homeScreen = true
                editScreen = false
                deleteScreen = false
                shareScreen = false
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
                homeScreen = false
                editScreen = true
                deleteScreen = false
                shareScreen = false
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
                homeScreen = false
                editScreen = false
                deleteScreen = true
                shareScreen = false
                if(bottom_navigation.menu.getItem(0).isChecked){
                    presenter.loadHillforts()
                } else {
                    presenter.loadFavoriteHillforts()
                }
            }
            R.id.share -> {
                homeScreen = false
                editScreen = false
                deleteScreen = false
                shareScreen = true
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
        if(homeScreen){
            presenter.doAddOrDeleteToFavorites(hillfort)
            if(bottom_navigation.menu.getItem(0).isChecked){
                presenter.loadHillforts()
            } else {
                presenter.loadFavoriteHillforts()
            }

        } else if (editScreen){
            presenter.doEditHillfort(hillfort)
            if(bottom_navigation.menu.getItem(0).isChecked){
                presenter.loadHillforts()
            } else {
                presenter.loadFavoriteHillforts()
            }
        } else if (deleteScreen) {
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
        } else {
            var str = "Hillfort: ${hillfort.title}"
            if (hillfort.description != ""){
                str += "\nDescription: ${hillfort.description}"
            }

            if (hillfort.additionalNote != ""){
                str += "\nAdditional Note: ${hillfort.additionalNote}"
            }

            if (hillfort.rate != 0){
                str += "\nMy Rate: ${hillfort.rate}"
            }

            if (hillfort.visited){
                str += "\nVisited: ${hillfort.dateVisited}"
            }

            str += "\nLocation: ${hillfort.location.lat}/${hillfort.location.lng}"

            var imageUri: Uri
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.putExtra(Intent.EXTRA_TEXT, str)
            if(hillfort.image1 != ""){
                imageUri = Uri.parse(hillfort.image1)
                Picasso.get().load(imageUri).into(object : Target {
                    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                        shareIntent.type = "image/*"
                        shareIntent.putExtra(Intent.EXTRA_STREAM, getBitmapFromView(bitmap))
                    }
                    override fun onPrepareLoad(placeHolderDrawable: Drawable?) { }
                    override fun onBitmapFailed(e: java.lang.Exception?, errorDrawable: Drawable?) { }
                })
            } else if (hillfort.image2 != ""){
                imageUri = Uri.parse(hillfort.image2)
                Picasso.get().load(imageUri).into(object : Target {
                    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                        shareIntent.type = "image/*"
                        shareIntent.putExtra(Intent.EXTRA_STREAM, getBitmapFromView(bitmap))
                    }
                    override fun onPrepareLoad(placeHolderDrawable: Drawable?) { }
                    override fun onBitmapFailed(e: java.lang.Exception?, errorDrawable: Drawable?) { }
                })
            } else if (hillfort.image3 != ""){
                imageUri = Uri.parse(hillfort.image3)
                Picasso.get().load(imageUri).into(object : Target {
                    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                        shareIntent.type = "image/*"
                        shareIntent.putExtra(Intent.EXTRA_STREAM, getBitmapFromView(bitmap))
                    }
                    override fun onPrepareLoad(placeHolderDrawable: Drawable?) { }
                    override fun onBitmapFailed(e: java.lang.Exception?, errorDrawable: Drawable?) { }
                })
            } else if (hillfort.image4 != "") {
                imageUri = Uri.parse(hillfort.image4)
                Picasso.get().load(imageUri).into(object : Target {
                    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                        shareIntent.type = "image/*"
                        shareIntent.putExtra(Intent.EXTRA_STREAM, getBitmapFromView(bitmap))
                    }
                    override fun onPrepareLoad(placeHolderDrawable: Drawable?) { }
                    override fun onBitmapFailed(e: java.lang.Exception?, errorDrawable: Drawable?) { }
                })
            } else {
                shareIntent.type = "text/plain"
            }

            startActivity(Intent.createChooser(shareIntent,"Share via"))
        }
    }
    fun getBitmapFromView(bmp: Bitmap?): Uri? {
        var bmpUri: Uri? = null
        try {
            val file = File(this.externalCacheDir, System.currentTimeMillis().toString() + ".jpg")

            val out = FileOutputStream(file)
            bmp?.compress(Bitmap.CompressFormat.JPEG, 90, out)
            out.close()
            bmpUri = Uri.fromFile(file)

        } catch (e: IOException) {
            e.printStackTrace()
        }
        return bmpUri
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        presenter.loadHillforts()
        super.onActivityResult(requestCode, resultCode, data)
    }
}