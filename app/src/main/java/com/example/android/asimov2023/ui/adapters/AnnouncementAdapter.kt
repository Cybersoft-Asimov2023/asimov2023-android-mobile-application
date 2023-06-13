package com.example.android.asimov2023.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.asimov2023.R
import com.example.android.asimov2023.retrofit.Model.AnnouncementItem

class AnnouncementAdapter(private val announcementList: MutableList<AnnouncementItem> = mutableListOf()) : RecyclerView.Adapter<AnnouncementAdapter.MyViewHolder>() {

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
        // Actualiza las vistas en el MyViewHolder con los datos del elemento correspondiente de la lista
        val announcement = announcementList[position]
        holder.titleTextView.text = announcement.title
        holder.descriptionTextView.text = announcement.description

    }

    override fun getItemCount(): Int {
        return announcementList.size
    }

    fun updateData(newData: List<AnnouncementItem>) {
        announcementList.clear()
        announcementList.addAll(newData)
        notifyDataSetChanged()
    }
}