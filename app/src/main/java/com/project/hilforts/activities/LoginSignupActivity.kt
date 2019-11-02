package com.project.hilforts.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.project.hilforts.R
import com.project.hilforts.main.MainApp
import com.project.hilforts.models.UserModel
import kotlinx.android.synthetic.main.login_activity.*
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast

class LoginSignupActivity : AppCompatActivity(){
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        app = application as MainApp
        
        login_text.setOnClickListener(){
            showLogIn()
        }

        signup_text.setOnClickListener(){
            showRegistration()
        }
    }

    private fun showRegistration(){
        signup_layout.visibility = View.VISIBLE
        login_layout.visibility = View.GONE
    }

    private fun showLogIn(){
        signup_layout.visibility = View.GONE
        login_layout.visibility = View.VISIBLE
    }

    fun onLogin(v: View){
        val email = login_email.text.toString()
        val password = login_password.text.toString()

        val user = app.users.findAll().find { u -> u.email == email && u.password == password }

        if(user != null){
            app.loggedInUserEmail = email
            app.loggedInUserPassword = password
            startActivityForResult<HillfortListActivity>(0)
        } else {
            toast("Check your credentials")
        }
    }
    fun onSignUp(v: View){
        val email = signup_email.text.toString()
        val password = signup_password.text.toString()
        val confirmPassword = signup_confirm_password.text.toString()

        if(password == confirmPassword){
            val user = UserModel(email, password, app.getDefaultHillforts())

            if(app.users.findAll().find { u -> u.email == email} == null) {
                app.loggedInUserEmail = email
                app.loggedInUserPassword = password
                app.users.add(user)
                startActivityForResult<HillfortListActivity>(0)
            } else {
                toast("User Already Exists")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}