package com.example.android.asimov2023.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.asimov2023.R
import com.example.android.asimov2023.retrofit.Model.AnnouncementItem
import com.example.android.asimov2023.retrofit.Model.CompetenceItem

class AnnouncementAdapter(private val announcementList: List<AnnouncementItem>, isDirector : Boolean) : RecyclerView.Adapter<AnnouncementAdapter.MyViewHolder>() {

    val isDirector = isDirector
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Define las vistas del CardView
        val titleTextView: TextView = itemView.findViewById(R.id.txtTitleItem)
        val descriptionTextView: TextView = itemView.findViewById(R.id.txtDescriptionItem)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // Infla el archivo de diseño para crear una nueva instancia de MyViewHolder
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_announcement, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val button: Button = holder.itemView.findViewById(R.id.btnDeleteAnnounce)
        if(!isDirector){
            button.visibility = View.GONE
        }

        // Actualiza las vistas en el MyViewHolder con los datos del elemento correspondiente de la lista
        val announcement = announcementList[position]
        holder.titleTextView.text = announcement.title
        holder.descriptionTextView.text = announcement.description

    }

    override fun getItemCount(): Int {
        return announcementList.size
    }


}