package com.project.hilforts.views.loginSignup

import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.project.hilforts.models.firebase.HillfortFireStore
import com.project.hilforts.views.base.BasePresenter
import com.project.hilforts.views.base.BaseView
import com.project.hilforts.views.base.VIEW
import kotlinx.android.synthetic.main.login_activity.*
import org.jetbrains.anko.toast

class LoginSignupPresenter(view: BaseView) : BasePresenter(view)  {

    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var fireStore: HillfortFireStore? = null


    init{
        if(app.hillforts is HillfortFireStore){
            fireStore = app.hillforts as HillfortFireStore
        }
    }
    fun doShowLogin(){
        view?.signup_layout.visibility = View.GONE
        view?.login_layout.visibility = View.VISIBLE
    }

    fun doShowSignup(){
        view?.signup_layout.visibility = View.VISIBLE
        view?.login_layout.visibility = View.GONE
    }

    fun doOnLogin(){
        view?.showProgressLogin()
        view?.hideProgressSignup()
        val email = view?.login_email.text.toString()
        val password = view?.login_password.text.toString()

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(view!!) { task ->
            if (task.isSuccessful) {
                app.loggedInUserEmail = email
                app.loggedInUserPassword = password
                if(fireStore != null){
                    fireStore!!.fetchHillforts {
                        view?.hideProgressLogin()
                        view?.navigateTo(VIEW.LIST)
                    }
                } else {
                    view?.hideProgressLogin()
                    view?.navigateTo(VIEW.LIST)
                }
            } else {
                view?.hideProgressLogin()
                view?.toast("Login Failed: ${task.exception?.message}")
            }
        }
    }

    fun doOnSignup(){
        view?.hideProgressLogin()
        view?.showProgressSignup()
        val email = view?.signup_email.text.toString()
        val password = view?.signup_password.text.toString()
        val confirmPassword = view?.signup_confirm_password.text.toString()

        if (email == "" || password == "" || confirmPassword == ""){
            view?.toast("Please Fill the Required Fields")
        }else if(!isEmailValid(email)){
            view?.toast("Please Enter a Valid Email Address")
        }else if (password != confirmPassword){
            view?.toast("Passwords do not match")
        }else if (password == confirmPassword){

            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(view!!) { task ->
                if (task.isSuccessful) {
                    app.loggedInUserEmail = email
                    app.loggedInUserPassword = password
                    view?.hideProgressSignup()
                    view?.navigateTo(VIEW.LIST)
                } else {
                    view?.hideProgressSignup()
                    view?.toast("Sign Up Failed: ${task.exception?.message}")
                }

            }
        }
    }

    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun doLogout() {
        FirebaseAuth.getInstance().signOut()
        app.hillforts.clear()
        view?.navigateTo(VIEW.LOGINSIGNUP)
    }
}