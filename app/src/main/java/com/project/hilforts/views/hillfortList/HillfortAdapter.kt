package com.project.hilforts.views.hillfortList

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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
            itemView.location.text = "Location: ${hillfort.location.lat}, ${hillfort.location.lng}"
            if(hillfort.image1 != ""){
                Glide.with(itemView.context).load(hillfort.image1).into(itemView.imageIcon)
            } else if (hillfort.image2 != ""){
                Glide.with(itemView.context).load(hillfort.image2).into(itemView.imageIcon)
            } else if (hillfort.image3 != ""){
                Glide.with(itemView.context).load(hillfort.image3).into(itemView.imageIcon)
            } else if (hillfort.image4 != "") {
                Glide.with(itemView.context).load(hillfort.image4).into(itemView.imageIcon)
            }

            itemView.setOnClickListener{ listener.onHillfortClick(hillfort)}
            if(hillfort.visited){
                itemView.visited.setText("Visited")
                itemView.setBackgroundColor(Color.parseColor("#BDFEA2"))
            } else{
                itemView.visited.setText("Not Visited")
                itemView.setBackgroundColor(Color.parseColor("#ffffff"))
            }

            if(HillfortListView.isHome){
                itemView.visited.visibility = View.VISIBLE
                if(hillfort.favorite){
                    itemView.favorite_icon.visibility = View.VISIBLE
                    itemView.not_favorite_icon.visibility = View.GONE
                } else {
                    itemView.favorite_icon.visibility = View.GONE
                    itemView.not_favorite_icon.visibility = View.VISIBLE
                }
                itemView.edit_icon.visibility = View.GONE
                itemView.delete_icon.visibility = View.GONE
                if(hillfort.visited) {
                    itemView.favorite_icon.setBackgroundColor(Color.parseColor("#BDFEA2"))
                    itemView.not_favorite_icon.setBackgroundColor(Color.parseColor("#BDFEA2"))
                }else{
                    itemView.favorite_icon.setBackgroundColor(Color.parseColor("#ffffff"))
                    itemView.not_favorite_icon.setBackgroundColor(Color.parseColor("#ffffff"))
                }
            } else if (HillfortListView.isEditing){
                itemView.favorite_icon.visibility = View.GONE
                itemView.not_favorite_icon.visibility = View.GONE
                itemView.visited.visibility = View.GONE
                itemView.edit_icon.visibility = View.VISIBLE
                itemView.delete_icon.visibility = View.GONE
                if(hillfort.visited){
                    itemView.edit_icon.setBackgroundColor(Color.parseColor("#BDFEA2"))
                } else {
                    itemView.edit_icon.setBackgroundColor(Color.parseColor("#ffffff"))
                }
            } else {
                itemView.favorite_icon.visibility = View.GONE
                itemView.not_favorite_icon.visibility = View.GONE
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