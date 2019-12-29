package com.project.hilforts.views.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.project.hilforts.R
import com.project.hilforts.views.loginSignup.LoginSignupView

class SplashView: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val background = object : Thread (){
            override fun run(){
                try {
                    Thread.sleep(4000)

                    val intent = Intent(baseContext, LoginSignupView::class.java)
                    startActivity(intent)
                } catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }
        background.start()
    }
}