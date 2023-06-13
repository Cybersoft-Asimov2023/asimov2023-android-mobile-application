package com.example.android.asimov2023.ui.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.asimov2023.R
import com.example.android.asimov2023.retrofit.Model.Courses

class CourseAdapter(private val courseList: List<Courses>):RecyclerView.Adapter<CourseAdapter.MyViewHolder>(){
    inner class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val nameCourse:TextView=itemView.findViewById(R.id.txtCrsName);
        val descriptionCourse:TextView=itemView.findViewById(R.id.txtCrsDescription);
        val moreCourseButton:Button=itemView.findViewById(R.id.btnToCourseInfo);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.card_course,parent,false);
        return MyViewHolder(view);
    }

    override fun getItemCount(): Int {
        return courseList.size;
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val course=courseList[position];
        holder.nameCourse.text=course.name;
        holder.descriptionCourse.text=course.description;
    }
}