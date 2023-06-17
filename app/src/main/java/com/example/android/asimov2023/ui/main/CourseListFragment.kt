package com.example.android.asimov2023.ui.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.asimov2023.R
import com.example.android.asimov2023.retrofit.Model.Courses
import com.example.android.asimov2023.retrofit.RetrofitClient
import com.example.android.asimov2023.ui.adapters.CourseAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CourseListFragment : Fragment() {
    private lateinit var recyclerView:RecyclerView
    private lateinit var adapter:CourseAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_course_list, container, false)
        setupViews(view)
        return view
    }
    private fun setupViews(view: View){
        recyclerView=view.findViewById(R.id.recyclerViewCourses)
        recyclerView.layoutManager=LinearLayoutManager(requireContext())
        loadCourses { coursesList->
            adapter= CourseAdapter(coursesList,requireActivity().supportFragmentManager)
            recyclerView.adapter = adapter
        }
    }
    private fun loadCourses(callback: (List<Courses>) -> Unit){
        val getShared = requireContext().getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
        val id = getShared.getInt("id", 0)
        val teacherToken = getShared.getString("token", null)
        val courseInterface=RetrofitClient.getCoursesInterface()
        val retrofitData=courseInterface.getCoursesByTeacherId("Bearer $teacherToken",id)
        retrofitData.enqueue(object :Callback<List<Courses>?>{
            override fun onResponse(
                call: Call<List<Courses>?>,
                response: Response<List<Courses>?>
            ) {
                val coursesList=response.body()

                Log.d("Courses","Succesful");
                if(coursesList!=null){
                    callback(coursesList)
                }
                else{
                    callback(emptyList())
                }
            }

            override fun onFailure(call: Call<List<Courses>?>, t: Throwable) {
                Log.d("Course list","failure "+t.message)
                callback(emptyList())
            }
        })
    }

}