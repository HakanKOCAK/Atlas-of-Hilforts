package com.project.hilforts.activities

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.hilforts.R
import com.project.hilforts.main.MainApp
import com.project.hilforts.models.HillfortModel
import kotlinx.android.synthetic.main.activity_hillforts_list.*
import kotlinx.android.synthetic.main.card_hillfort.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult

class HillfortListActivity : AppCompatActivity(), HillfortListener{
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillforts_list)
        app = application as MainApp

        toolbar.title = title
        setSupportActionBar(toolbar)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = HillfortAdapter(app.hillforts.findAll(), this)

        /*visited.setOnClickListener(){
            if(visited.isChecked){
                hillfort.visited = true
            } else{
                hillfort.visited = false
            }
        }*/
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean{
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.item_add -> startActivityForResult<HillfortActivity>(0)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onHillfortClick(hillfort: HillfortModel) {
        startActivityForResult(intentFor<HillfortActivity>().putExtra("hillfort_edit", hillfort), 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        recyclerView.adapter?.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }
}
