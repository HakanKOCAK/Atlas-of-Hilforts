package com.project.hilforts.views.settings

import com.project.hilforts.main.MainApp
import com.project.hilforts.views.base.BasePresenter
import com.project.hilforts.views.base.BaseView
import kotlinx.android.synthetic.main.activity_setting.*

class SettingsPresenter(view: BaseView) : BasePresenter(view) {

    fun doSetView(){
        var count = 0
        val hillforts = app.hillforts.findAll()
        for(h in hillforts){
            if(h.visited) count += 1
        }

        view?.settings_email.setText("E-mail: ${app.loggedInUserEmail}")
        view?.settings_password.setText("Password: ${app.loggedInUserPassword}")
        view?.visited_hillforts.setText("Visited Hilforts: ${count}")
        view?.total_hillforts.setText("Total Number of Hilforts: ${hillforts.size}")
    }
    fun doCancel() {
        view?.finish()
    }
}