package com.project.hilforts.views.loginSignup

import android.view.View
import com.project.hilforts.main.MainApp
import com.project.hilforts.models.UserModel
import com.project.hilforts.views.base.BasePresenter
import com.project.hilforts.views.base.BaseView
import com.project.hilforts.views.base.VIEW
import com.project.hilforts.views.hillfortList.HillfortListView
import kotlinx.android.synthetic.main.login_activity.*
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast

class LoginSignupPresenter(view: BaseView) : BasePresenter(view)  {

    fun doShowLogin(){
        view?.signup_layout.visibility = View.GONE
        view?.login_layout.visibility = View.VISIBLE
    }

    fun doShowSignup(){
        view?.signup_layout.visibility = View.VISIBLE
        view?.login_layout.visibility = View.GONE
    }

    fun doOnLogin(){
        val email = view?.login_email.text.toString()
        val password = view?.login_password.text.toString()

        val user = app.users.findAll().find { u -> u.email == email && u.password == password }

        if(user != null){
            app.loggedInUserEmail = email
            app.loggedInUserPassword = password
            view?.navigateTo(VIEW.LIST)
        } else {
            view?.toast("Check your credentials")
        }
    }

    fun doOnSignup(){
        val email = view?.signup_email.text.toString()
        val password = view?.signup_password.text.toString()
        val confirmPassword = view?.signup_confirm_password.text.toString()

        if (email = "" || password = "" || confirmPassword = ""){
            view?.toast("Please Fill the Required Fields")
        }else if(!isEmailValid(email)){
            view?.toast("Please Enter a Valid Email Address")
        }else if (password != confirmPassword){
            view?.toast("Passwords do not match")
        }else (password == confirmPassword){
            val user = UserModel(email, password, app.getDefaultHillforts())

            if(app.users.findAll().find { u -> u.email == email} == null) {
                app.loggedInUserEmail = email
                app.loggedInUserPassword = password
                app.users.add(user)
                view?.navigateTo(VIEW.LIST)
            } else {
                view?.toast("User Already Exists")
            }
        }
    }

    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}