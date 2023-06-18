package com.example.android.asimov2023.ui.adapters
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.example.android.asimov2023.R
import com.example.android.asimov2023.retrofit.Model.Courses
import com.example.android.asimov2023.ui.main.CourseDetailsFragment
import com.google.android.material.navigation.NavigationView
import com.example.android.asimov2023.ui.main.CourseListFragment
import com.example.android.asimov2023.ui.main.DashboardDirectorFragment

class CourseAdapter(private val courseList: List<Courses>,private val supportFragmentManager: FragmentManager):RecyclerView.Adapter<CourseAdapter.MyViewHolder>(){

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
        holder.moreCourseButton.setOnClickListener{

            val fragment= CourseDetailsFragment()
            val bundle=Bundle()
            bundle.putInt("courseId",course.id)
            fragment.arguments=bundle
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()


        }
    }
}
