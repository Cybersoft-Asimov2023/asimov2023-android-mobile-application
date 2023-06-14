package com.example.android.asimov2023.ui.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.asimov2023.R
import com.example.android.asimov2023.retrofit.Model.CompetenceItem
import com.example.android.asimov2023.retrofit.Model.CourseItem
import com.example.android.asimov2023.retrofit.Model.Courses
import com.example.android.asimov2023.retrofit.Model.TeacherItem
import com.example.android.asimov2023.retrofit.RetrofitClient
import com.example.android.asimov2023.ui.adapters.CompetenceAdapter
import com.example.android.asimov2023.ui.adapters.CourseItemsAdapter
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback



class CourseDetailsFragment : Fragment() {
    private lateinit var recyclerViewCompetence: RecyclerView
    private lateinit var adapterCompetence: CompetenceAdapter
    private lateinit var recyclerViewItems: RecyclerView
    private lateinit var adapterCourseItem: CourseItemsAdapter
    private lateinit var textCourseName:TextView
    private lateinit var textDescription:TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val courseId=arguments?.getInt("courseId",-1)
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_course_details, container, false)
        recyclerViewCompetence=view.findViewById(R.id.rvCourseCompetences)
        recyclerViewItems=view.findViewById(R.id.rvCourseItems)
        textCourseName=view.findViewById(R.id.lblCourseName)
        textDescription=view.findViewById(R.id.txtCourseDescription)
        if (courseId != null) {
            setupViews(view,courseId)
        }
        return view
    }
    private fun setupViews(view:View,id:Int){
        recyclerViewCompetence.layoutManager=LinearLayoutManager(requireContext())
        loadInitial(id){
            course->
            if (course != null) {
                textCourseName.text=course.name
            }
            if (course != null) {
                textDescription.text=course.description
            }
        }
        loadCompetences(id){
            competenceList->
            adapterCompetence= CompetenceAdapter(competenceList!!)
            recyclerViewCompetence.adapter=adapterCompetence
        }
        loadItems(id){
            itemList->
            adapterCourseItem= CourseItemsAdapter(itemList!!)
            recyclerViewItems.adapter=adapterCourseItem
        }

    }
    private fun loadInitial(id: Int,callback:(Courses?)->Unit){
        val getShared=requireContext().getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
        val teacherToken = getShared.getString("token", null)
        val courseInterface=RetrofitClient.getCoursesInterface()
        val retrofitData=courseInterface.getCourseById("Bearer $teacherToken",id)
        retrofitData.enqueue(object :retrofit2.Callback<Courses?>{
            override fun onResponse(call: Call<Courses?>, response: Response<Courses?>) {
                val course=response.body()

                Log.d("course", "SUCCESS")
                callback(course)

            }

            override fun onFailure(call: Call<Courses?>, t: Throwable) {
                Log.d("course", "SUCCESS")
                callback(null)
            }

        })
    }
    private fun loadCompetences(cId:Int,callback: (List<CompetenceItem>?)->Unit){
        val getShared=requireContext().getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
        val id = getShared.getInt("id", 0)
        val teacherToken = getShared.getString("token", null)
        val competenceInterface=RetrofitClient.getCompetencesInterface()
        val retrofitData=competenceInterface.getCompetencesbyCourseId("Bearer $teacherToken",cId)
        retrofitData.enqueue(object : retrofit2.Callback<List<CompetenceItem>?> {
            override fun onResponse(
                call: Call<List<CompetenceItem>?>,
                response: Response<List<CompetenceItem>?>
            ) {
                val competenceList=response.body()
                if (competenceList != null) {
                    Log.d("competenceList", "SUCCESS")
                    callback(competenceList)
                } else {
                    callback(emptyList())
                }
            }

            override fun onFailure(call: Call<List<CompetenceItem>?>, t: Throwable) {
                Log.d("competenceList", "failure" + t.message)
                callback(emptyList())
            }

        })
    }
    private fun loadItems(cId:Int,callback: (List<CourseItem>?)->Unit){
        val getShared=requireContext().getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
        val id = getShared.getInt("id", 0)
        val teacherToken = getShared.getString("token", null)
        val competenceInterface=RetrofitClient.getCoursesInterface()
        val retrofitData=competenceInterface.getCourseItems("Bearer $teacherToken",cId)
        retrofitData.enqueue(object :retrofit2.Callback<List<CourseItem>?>{
            override fun onResponse(
                call: Call<List<CourseItem>?>,
                response: Response<List<CourseItem>?>
            ) {
                val itemList=response.body()
                if (itemList != null) {
                    Log.d("itemList", "SUCCESS")
                    callback(itemList)
                } else {
                    callback(emptyList())
                }
            }

            override fun onFailure(call: Call<List<CourseItem>?>, t: Throwable) {
                Log.d("itemList", "failure" + t.message)
                callback(emptyList())
            }

        })
    }

}