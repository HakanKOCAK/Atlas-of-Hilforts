package com.project.hilforts.views.loginSignup

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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
    }

    fun onLogin(v: View){
        presenter.doOnLogin()
    }

    fun onSignUp(v: View){
        presenter.doOnSignup()
    }
}