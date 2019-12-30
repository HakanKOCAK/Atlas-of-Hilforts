package com.project.hilforts.views.loginSignup

import android.os.Bundle
import android.view.View
import com.project.hilforts.R
import com.project.hilforts.views.base.BaseView
import kotlinx.android.synthetic.main.login_activity.*

class LoginSignupView :  BaseView(){
    lateinit var presenter: LoginSignupPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

       presenter = initPresenter (LoginSignupPresenter(this)) as LoginSignupPresenter
        
        login_text.setOnClickListener(){
            presenter.doShowLogin()
        }

        signup_text.setOnClickListener(){
            presenter.doShowSignup()
        }

        progressBarLogin.visibility = View.GONE
        progressBarSignup.visibility = View.GONE
    }

    fun onLogin(v: View){
        presenter.doOnLogin()
    }

    fun onSignUp(v: View){
        presenter.doOnSignup()
    }

    override fun showProgressLogin() {
        progressBarLogin.visibility = View.VISIBLE
    }

    override fun hideProgressLogin() {
        progressBarLogin.visibility = View.GONE
    }

    override fun showProgressSignup() {
        progressBarSignup.visibility = View.VISIBLE
    }

    override fun hideProgressSignup() {
        progressBarSignup.visibility = View.VISIBLE
    }
}