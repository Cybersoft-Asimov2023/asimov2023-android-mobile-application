package com.example.android.asimov2023.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.example.android.asimov2023.R
import com.example.android.asimov2023.retrofit.Model.AnnouncementItem
import com.example.android.asimov2023.retrofit.Model.CompetenceItem
import com.example.android.asimov2023.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AnnouncementAdapter(private val context: Context, private val announcementList: MutableList<AnnouncementItem>, isDirector : Boolean) : RecyclerView.Adapter<AnnouncementAdapter.MyViewHolder>() {

    val isDirector = isDirector
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Define las vistas del CardView
        val titleTextView: TextView = itemView.findViewById(R.id.txtTitleItem)
        val descriptionTextView: TextView = itemView.findViewById(R.id.txtDescriptionItem)
        val deleteBtn: Button = itemView.findViewById(R.id.btnDeleteAnnounce)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // Infla el archivo de diseño para crear una nueva instancia de MyViewHolder
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_announcement, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val button: Button = holder.itemView.findViewById(R.id.btnDeleteAnnounce)
        if(!isDirector){
            button.visibility = View.GONE
        }

        // Actualiza las vistas en el MyViewHolder con los datos del elemento correspondiente de la lista
        val announcement = announcementList[position]
        holder.titleTextView.text = announcement.title
        holder.descriptionTextView.text = announcement.description

        holder.deleteBtn.setOnClickListener{

            val getShared = context.getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
            val directorToken = getShared.getString("token", null)
            val announcementInterface = RetrofitClient.getAnnouncementInterface()
            val retrofitData = announcementInterface.deleteAnnouncement(announcement.id, "Bearer $directorToken" )

            retrofitData.enqueue(object : Callback<Unit?> {
                override fun onResponse(call: Call<Unit?>, response: Response<Unit?>) {
                    if (response.isSuccessful) {
                        Log.d("Delete announcement", "Success" + response.message())
                        val removedPosition = announcementList.indexOf(announcement)
                        announcementList.remove(announcement)
                        notifyItemRemoved(removedPosition)
                        notifyItemRangeChanged(removedPosition, announcementList.size - removedPosition)


                    }
                }

                override fun onFailure(call: Call<Unit?>, t: Throwable) {

                    Log.d("Delete announcement", "ERROR"+ t.message)
                }
            })




        }


    }

    override fun getItemCount(): Int {
        return announcementList.size
    }





}