package com.project.hilforts.views.hillfortList

import com.project.hilforts.views.map.HillfortMapView
import com.project.hilforts.views.loginSignup.LoginSignupView
import com.project.hilforts.views.settings.SettingsView
import com.project.hilforts.main.MainApp
import com.project.hilforts.models.HillfortModel
import com.project.hilforts.views.hillfort.HillfortView
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult

class HillfortListPresenter(val view: HillfortListView) {

    var app: MainApp

    init{
        app = view.application as MainApp
    }

    fun getHillforts() = app.users.getUserHillforts(app.loggedInUserEmail)

    fun doShowHillfortsMap() {
        view.startActivity<HillfortMapView>()
    }

    fun doAddHillfort(){
        view.startActivityForResult<HillfortView>(0)
    }

    fun doShowSelectedScreen(){
        view.startActivityForResult<HillfortListView>(0)
    }

    fun doShowSettingsScreen(){
        view.startActivityForResult<SettingsView>(0)
    }

    fun doLogOut(){
        view.startActivityForResult<LoginSignupView>(0)
    }

    fun doEditHillfort(hillfort: HillfortModel) {
        view.startActivityForResult(view.intentFor<HillfortView>().putExtra("hillfort_edit", hillfort), 0)
    }

    fun doDeleteHillfort(hillfort: HillfortModel){
        app.users.deleteUserHillfort(app.loggedInUserEmail, hillfort)
        view.startActivityForResult<HillfortListView>(0)
    }
}