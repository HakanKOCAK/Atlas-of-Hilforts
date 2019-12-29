package com.project.hilforts.views.hillfortList

import com.project.hilforts.views.map.HillfortMapView
import com.project.hilforts.views.loginSignup.LoginSignupView
import com.project.hilforts.views.settings.SettingsView
import com.project.hilforts.main.MainApp
import com.project.hilforts.models.HillfortModel
import com.project.hilforts.views.base.BasePresenter
import com.project.hilforts.views.base.BaseView
import com.project.hilforts.views.base.VIEW
import com.project.hilforts.views.hillfort.HillfortView
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult

class HillfortListPresenter(view: BaseView) : BasePresenter(view) {

    fun loadHillforts() {
        view?.showHillforts(app.users.getUserHillforts(app.loggedInUserEmail))
    }

    fun doShowHillfortsMap() {
        view?.navigateTo(VIEW.MAPS)
    }

    fun doAddHillfort(){
        view?.navigateTo(VIEW.HILLFORT)
    }

    fun doShowSelectedScreen(){
        view?.navigateTo(VIEW.LIST)
    }

    fun doShowSettingsScreen(){
        view?.navigateTo(VIEW.SETTINGS)
    }

    fun doLogOut(){
        view?.navigateTo(VIEW.LOGINSIGNUP)
    }

    fun doEditHillfort(hillfort: HillfortModel) {
        view?.navigateTo(VIEW.HILLFORT, 0, "hillfort_edit", hillfort)
    }

    fun doDeleteHillfort(hillfort: HillfortModel){
        app.users.deleteUserHillfort(app.loggedInUserEmail, hillfort)
        view?.navigateTo(VIEW.LIST)
    }
}