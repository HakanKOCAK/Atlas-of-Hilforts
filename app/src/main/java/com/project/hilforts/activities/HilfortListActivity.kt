package com.project.hilforts.activities

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.hilforts.R
import com.project.hilforts.main.MainApp
import com.project.hilforts.models.HilfortModel
import kotlinx.android.synthetic.main.activity_hilforts_list.*
import kotlinx.android.synthetic.main.card_hilfort.view.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult

class HilfortListActivity : AppCompatActivity(), HilfortListener{
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hilforts_list)
        app = application as MainApp

        toolbar.title = title
        setSupportActionBar(toolbar)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = HilfortAdapter(app.hilforts.findAll(), this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean{
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.item_add -> startActivityForResult<HilfortActivity>(0)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onHilfortClick(hilfort: HilfortModel) {
        startActivityForResult(intentFor<HilfortActivity>().putExtra("hilfort_edit", hilfort), 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        recyclerView.adapter?.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }
}
