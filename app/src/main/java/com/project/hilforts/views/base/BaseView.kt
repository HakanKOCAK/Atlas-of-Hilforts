package com.project.hilforts.views.base

import android.content.Intent

import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.project.hilforts.models.HillfortModel
import com.project.hilforts.models.Location
import com.project.hilforts.views.editLocation.EditLocationView
import com.project.hilforts.views.hillfort.HillfortView
import com.project.hilforts.views.hillfortList.HillfortListView
import com.project.hilforts.views.loginSignup.LoginSignupView
import com.project.hilforts.views.map.HillfortMapView
import com.project.hilforts.views.settings.SettingsView
import org.jetbrains.anko.AnkoLogger

val IMAGE_REQUEST1 = 1
val IMAGE_REQUEST2 = 2
val IMAGE_REQUEST3 = 3
val IMAGE_REQUEST4 = 4
val LOCATION_REQUEST = 5

enum class VIEW {
    LOCATION, HILLFORT, MAPS, LIST, SETTINGS, LOGINSIGNUP
}

open abstract class BaseView() : AppCompatActivity(), AnkoLogger {

    var basePresenter: BasePresenter? = null

    fun navigateTo(view: VIEW, code: Int = 0, key: String = "", value: Parcelable? = null) {
        var intent = Intent(this, HillfortListView::class.java)

        when (view){
            VIEW.LOCATION -> intent = Intent(this, EditLocationView::class.java)
            VIEW.HILLFORT -> intent = Intent(this, HillfortView::class.java)
            VIEW.MAPS -> intent = Intent(this, HillfortMapView::class.java)
            VIEW.LIST -> intent = Intent(this,HillfortListView::class.java)
            VIEW.SETTINGS -> intent = Intent(this, SettingsView::class.java)
            VIEW.LOGINSIGNUP -> intent = Intent(this, LoginSignupView::class.java)
        }
        if (key != "") {
            intent.putExtra(key, value)
        }
        startActivityForResult(intent, code)
    }

    fun initPresenter(presenter: BasePresenter): BasePresenter {
        basePresenter = presenter
        return presenter
    }

    fun init(toolbar: Toolbar, upEnabled: Boolean) {
        toolbar.title = title
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(upEnabled)
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            toolbar.title = "${title}: ${user.email}"
        }
    }

    override fun onDestroy() {
        basePresenter?.onDestroy()
        super.onDestroy()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            basePresenter?.doActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        basePresenter?.doRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    open fun showLocation(latitude : Double, longitude : Double) {}
    open fun showHillfort(hillfort: HillfortModel) {}
    open fun showLocation(location : Location) {}
    open fun showProgressLogin(){}
    open fun showProgressSignup(){}
    open fun hideProgressLogin(){}
    open fun hideProgressSignup(){}
    open fun showHillforts(hillforts: List<HillfortModel>) {}
}