package com.project.hilforts.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.project.hilforts.R
import kotlinx.android.synthetic.main.activity_hilforts.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast

class HilfortsActivity : AppCompatActivity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hilforts)
        info("Hilforts Activity Started")

        btnAdd.setOnClickListener() {
            val hilfortTitle = hilfortTitle.text.toString()
            if(hilfortTitle.isEmpty()){
                toast("Please Enter a Title")
            } else {
                info("add Button Pressed: $hilfortTitle")
            }
            info("Add button pressed.")
        }
    }
}
