package com.project.hilforts.views.hillfortList

import com.google.firebase.auth.FirebaseAuth
import com.project.hilforts.views.map.HillfortMapView
import com.project.hilforts.views.loginSignup.LoginSignupView
import com.project.hilforts.views.settings.SettingsView
import com.project.hilforts.main.MainApp
import com.project.hilforts.models.HillfortModel
import com.project.hilforts.views.base.BasePresenter
import com.project.hilforts.views.base.BaseView
import com.project.hilforts.views.base.VIEW
import com.project.hilforts.views.hillfort.HillfortView
import org.jetbrains.anko.*

class HillfortListPresenter(view: BaseView) : BasePresenter(view) {

    fun loadHillforts() {
        doAsync {
            val hillforts = app.hillforts.findAll()
            uiThread {
                view?.showHillforts(hillforts)
            }
        }
    }

    fun loadFavoriteHillforts(){
        doAsync {
            val hillforts = app.hillforts.findFavorites()
            uiThread {
                view?.showHillforts(hillforts)
            }
        }
    }

    fun doShowHillfortsMap() {
        view?.navigateTo(VIEW.MAPS)
    }

    fun doAddHillfort(){
        view?.navigateTo(VIEW.HILLFORT)
    }

    fun doFilterHillforts(search: String){
        doAsync {
            var hillforts = app.hillforts.findAll()
            hillforts = hillforts.filter { h -> h.title.toLowerCase().contains(search)}
            uiThread {
                view?.showHillforts(hillforts)
            }
        }
    }
    fun doShowSettingsScreen(){
        view?.navigateTo(VIEW.SETTINGS)
    }

    fun doLogout() {
        FirebaseAuth.getInstance().signOut()
        app.hillforts.clear()
        view?.navigateTo(VIEW.LOGINSIGNUP)
    }

    fun doEditHillfort(hillfort: HillfortModel) {
        view?.navigateTo(VIEW.HILLFORT, 0, "hillfort_edit", hillfort)
    }

    fun doAddOrDeleteToFavorites(hillfort: HillfortModel){
        hillfort.favorite = !hillfort.favorite
        doAsync {
            app.hillforts.update(hillfort)
        }
    }

    fun doDeleteHillfort(hillfort: HillfortModel){
        doAsync {
            app.hillforts.delete(hillfort)
        }
    }
}