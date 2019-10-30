package com.project.hilforts.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.hilforts.R
import com.project.hilforts.models.HilfortModel
import kotlinx.android.synthetic.main.card_hilfort.view.*

interface HilfortListener{
    fun onHilfortClick(hilfort: HilfortModel)
}

class HilfortAdapter constructor(
    private var hilforts: List<HilfortModel>,
    private val listener: HilfortListener
) : RecyclerView.Adapter<HilfortAdapter.MainHolder> (){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent?.context).inflate(
                R.layout.card_hilfort,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val hilfort = hilforts[holder.adapterPosition]
        holder.bind(hilfort, listener)
    }

    override fun getItemCount(): Int = hilforts.size

    class MainHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(hilfort: HilfortModel, listener: HilfortListener){
            itemView.hilfortTitle.text = hilfort.title
            itemView.description.text = hilfort.description
            itemView.setOnClickListener{ listener.onHilfortClick(hilfort)}
            if(hilfort.visited){
                itemView.visited.isChecked = true
            } else{
                itemView.visited.isChecked = false
            }
        }
    }
}