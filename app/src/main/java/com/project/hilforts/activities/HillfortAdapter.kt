package com.project.hilforts.activities

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.hilforts.R
import com.project.hilforts.helpers.readImageFromPath
import com.project.hilforts.models.HillfortModel
import kotlinx.android.synthetic.main.card_hillfort.view.*

interface HillfortListener{
    fun onHillfortClick(hillfort: HillfortModel)
}

class HillfortAdapter constructor(
    private var hillforts: List<HillfortModel>,
    private val listener: HillfortListener
) : RecyclerView.Adapter<HillfortAdapter.MainHolder> (){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent?.context).inflate(
                R.layout.card_hillfort,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val hillfort = hillforts[holder.adapterPosition]
        holder.bind(hillfort, listener)
    }

    override fun getItemCount(): Int = hillforts.size

    class MainHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(hillfort: HillfortModel, listener: HillfortListener){
            itemView.hillfortTitle.text = hillfort.title
            itemView.location.text = "Location: ${hillfort.lat}, ${hillfort.lng}"
            if(hillfort.image1 != ""){
                itemView.imageIcon.setImageBitmap(readImageFromPath(itemView.context, hillfort.image1))
            } else if (hillfort.image2 != ""){
                itemView.imageIcon.setImageBitmap(readImageFromPath(itemView.context, hillfort.image2))
            } else if (hillfort.image3 != ""){
                itemView.imageIcon.setImageBitmap(readImageFromPath(itemView.context, hillfort.image3))
            } else {
                itemView.imageIcon.setImageBitmap(readImageFromPath(itemView.context, hillfort.image4))
            }

            itemView.setOnClickListener{ listener.onHillfortClick(hillfort)}
            if(hillfort.visited){
                itemView.visited.setText("Visited")
                itemView.setBackgroundColor(Color.parseColor("#BDFEA2"))
            } else{
                itemView.visited.setText("Not Visited")
                itemView.setBackgroundColor(Color.parseColor("#ffffff"))
            }

            if(HillfortListActivity.isHome){
                itemView.visited.visibility = View.VISIBLE
                itemView.edit_icon.visibility = View.GONE
                itemView.delete_icon.visibility = View.GONE
            } else if (HillfortListActivity.isEditing){
                itemView.visited.visibility = View.GONE
                itemView.edit_icon.visibility = View.VISIBLE
                itemView.delete_icon.visibility = View.GONE
                if(hillfort.visited){
                    itemView.edit_icon.setBackgroundColor(Color.parseColor("#BDFEA2"))
                    itemView.delete_icon.setBackgroundColor(Color.parseColor("#BDFEA2"))
                } else {
                    itemView.edit_icon.setBackgroundColor(Color.parseColor("#ffffff"))
                    itemView.delete_icon.setBackgroundColor(Color.parseColor("#ffffff"))
                }
            } else {
                itemView.visited.visibility = View.GONE
                itemView.edit_icon.visibility = View.GONE
                itemView.delete_icon.visibility = View.VISIBLE
                if(hillfort.visited){
                    itemView.delete_icon.setBackgroundColor(Color.parseColor("#BDFEA2"))
                } else {
                    itemView.delete_icon.setBackgroundColor(Color.parseColor("#ffffff"))
                }
            }
        }
    }
}