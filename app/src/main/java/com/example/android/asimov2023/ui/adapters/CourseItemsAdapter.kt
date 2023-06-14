package com.example.android.asimov2023.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.asimov2023.R
import com.example.android.asimov2023.retrofit.Model.CourseItem

class CourseItemsAdapter(private val courseItemsList:List<CourseItem>): RecyclerView.Adapter<CourseItemsAdapter.MyViewHolder>() {
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textCourseItem:TextView=itemView.findViewById(R.id.lblItemName)
        val btnCourseItem:Button=itemView.findViewById(R.id.btnItemState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_course_items, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return courseItemsList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val courseItem=courseItemsList[position]
        holder.textCourseItem.text=courseItem.name

    }


}