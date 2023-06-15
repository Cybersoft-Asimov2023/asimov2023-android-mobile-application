package com.example.android.asimov2023.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.asimov2023.R
import com.example.android.asimov2023.retrofit.Model.CompetenceItem
import com.example.android.asimov2023.retrofit.Model.TeacherItem
import com.example.android.asimov2023.ui.main.CompetenceListFragment



class CompetenceAdapter(private val competenceList: List<CompetenceItem>) : RecyclerView.Adapter<CompetenceAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Define las vistas del CardView
        val titleTextView: TextView = itemView.findViewById(R.id.cardCompetenceTitle)
        val descriptionTextView: TextView = itemView.findViewById(R.id.cardCompetenceDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // Infla el archivo de diseño para crear una nueva instancia de MyViewHolder
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_competence, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // Actualiza las vistas en el MyViewHolder con los datos del elemento correspondiente de la lista
        val competence = competenceList[position]
        holder.titleTextView.text = competence.title
        holder.descriptionTextView.text = competence.description

    }

    override fun getItemCount(): Int {
        return competenceList.size
    }
}
