package com.project.hilforts.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.project.hilforts.R
import kotlinx.android.synthetic.main.login_activity.*
import org.jetbrains.anko.startActivityForResult

class LoginSignupActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        login_text.setOnClickListener(){
            showLogIn()
        }

        signup_text.setOnClickListener(){
            showRegistration()
        }

        login_button.setOnClickListener(){
            startActivityForResult<HillfortListActivity>(0)
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
}