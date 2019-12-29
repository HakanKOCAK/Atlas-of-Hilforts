package com.project.hilforts.views.base

import android.content.Intent
import com.project.hilforts.main.MainApp

open class BasePresenter(var view: BaseView) {
    var app: MainApp =  view?.application as MainApp

    open fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
    }

    open fun doRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
    }

    open fun onDestroy() {

    }
}