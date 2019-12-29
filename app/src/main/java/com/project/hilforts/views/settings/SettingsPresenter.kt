package com.project.hilforts.views.settings

import com.project.hilforts.main.MainApp
import kotlinx.android.synthetic.main.activity_setting.*

class SettingsPresenter(val view: SettingsView) {


    lateinit var app: MainApp

    init{
        app = view.application as MainApp
    }

    fun doSetView(){
        var count = 0
        val hillforts = app.users.getUserHillforts(app.loggedInUserEmail)
        for(h in hillforts){
            if(h.visited) count += 1
        }

        view.settings_email.setText("E-mail: ${app.loggedInUserEmail}")
        view.settings_password.setText("Password: ${app.loggedInUserPassword}")
        view.visited_hillforts.setText("Visited Hilforts: ${count}")
        view.total_hillforts.setText("Total Number of Hilforts: ${hillforts.size}")
    }
    fun doCancel() {
        view.finish()
    }
}