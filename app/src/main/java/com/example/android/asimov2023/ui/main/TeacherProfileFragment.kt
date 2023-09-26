package com.example.android.asimov2023.ui.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.asimov2023.R
import com.example.android.asimov2023.retrofit.Model.Courses
import com.example.android.asimov2023.retrofit.Model.TeacherItem
import com.example.android.asimov2023.retrofit.RetrofitClient
import com.example.android.asimov2023.ui.adapters.CourseAdapter
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TeacherProfileFragment : Fragment() {

    private lateinit var courseAdapter: CourseAdapter
    private lateinit var txtTeacherName:TextView
    private lateinit var txtTeacherLastName:TextView
    private lateinit var txtFirstName:TextView
    private lateinit var txtLastName:TextView
    private lateinit var txtAge:TextView
    private lateinit var txtEmail:TextView
    private lateinit var txtPhone:TextView
    private lateinit var txtProgressBar:TextView
    private lateinit var pbProgressBar:ProgressBar
    private lateinit var txtPoint:TextView
    private lateinit var rvTeacherCourses:RecyclerView
    private lateinit var tvAddCourseName:TextView
    private lateinit var tvAddCourseDescription:TextView
    private lateinit var btnAddCourse:Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val id=arguments?.getInt("teacherId",-1)
        val view= inflater.inflate(R.layout.fragment_teacher_profile, container, false)
        txtTeacherName= view.findViewById(R.id.txtTCardFirstName)
        txtTeacherLastName =view.findViewById(R.id.txtTCardLastName)
        txtFirstName=view.findViewById(R.id.txtTFirstName)
        txtLastName=view.findViewById(R.id.txtTLastName)
        txtAge=view.findViewById(R.id.txtTAge)
        txtEmail=view.findViewById(R.id.txtTEmail)
        txtPhone=view.findViewById(R.id.txtTPhone)
        txtProgressBar=view.findViewById(R.id.txtTProgress)
        txtPoint=view.findViewById(R.id.txtTPoint)
        pbProgressBar=view.findViewById(R.id.progressBar)
        rvTeacherCourses=view.findViewById(R.id.recyclerCourses)
        btnAddCourse=view.findViewById(R.id.btnPublishCourse)
        if (id != null) {
            setupView(view,id)
        }
        return view
    }
    private fun setupView(view:View,id:Int){
        rvTeacherCourses.layoutManager= LinearLayoutManager(requireContext())
        btnAddCourse.setOnClickListener {
            val txtTitle = view.findViewById<TextView>(R.id.txtTitleAnnounce)
            val txtDescription = view.findViewById<TextView>(R.id.txtDescriptionAnnounce)
            val courseId=0

            val json = JSONObject()
            json.put("name", txtTitle.text.toString())
            json.put("description", txtDescription.text.toString())
            json.put("state", true)

            addCourse(json,id)


        }
        loadTeacher(id) { teacher->
            txtTeacherName.text=teacher.first_name
            txtTeacherLastName.text=teacher.last_name
            txtFirstName.text="First name: "+teacher.first_name
            txtLastName.text="Last name: "+teacher.last_name
            txtAge.text="Age: "+teacher.age
            txtEmail.text="Email: "+teacher.email
            txtPhone.text="Phone: "+teacher.phone
            txtPoint.text=teacher.point.toString()
        }

        loadCourses(id) { courses->
            courseAdapter= CourseAdapter(courses,requireActivity().supportFragmentManager)

        }
    }
    private fun loadTeacher(id:Int,callback: (TeacherItem)->Unit){
        val getShared = requireContext().getSharedPreferences("userPrefs", Context.MODE_PRIVATE)

        val teacherToken = getShared.getString("token", null)
        val teacherInterface=RetrofitClient.getTeachersInterface()
        val courseInterface=RetrofitClient.getCoursesInterface()

        val retrofitTeacher=teacherInterface.getTeacher("Bearer $teacherToken",id)

        retrofitTeacher.enqueue(object:Callback<TeacherItem?>{
            override fun onResponse(call: Call<TeacherItem?>, response: Response<TeacherItem?>) {
                val teacher=response.body()
                if(teacher!=null){
                    callback(teacher)
                }

            }

            override fun onFailure(call: Call<TeacherItem?>, t: Throwable) {
                Log.d("Teacher", "failure" + t.message)

            }

        })

    }
    private fun addCourse(json: JSONObject,id:Int) {
        val courseInterface = RetrofitClient.getCoursesInterface()
        val getShared = requireContext().getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
        val id = getShared.getInt("id", 0)
        val directorToken = getShared.getString("token", null)

        val requestBody = RequestBody.create(
            "application/json".toMediaTypeOrNull(),
            json.toString()
        )
        val retrofitData = courseInterface.createCourse(requestBody,"Bearer $directorToken")

        retrofitData.enqueue(object : Callback<Courses?> {
            override fun onResponse(call: Call<Courses?>, response: Response<Courses?>) {

                if (response.isSuccessful) {
                    Log.d("CreateAnnouncement", "Success" + response.message())
                    response.body()?.let { linkCourseTeacher(response.body()!!.id,id) }
                    // Después de agregar el anuncio, cargar los datos actualizados y actualizar el adaptador

                }
            }

            override fun onFailure(call: Call<Courses?>, t: Throwable) {
                Log.d("CreateAnnouncement", "Failure" + t.message)
            }
        })
    }
    private fun linkCourseTeacher(courseId:Int,teacherId:Int){
        val getShared = requireContext().getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
        val teacherToken = getShared.getString("token", null)
        val teacherInterface=RetrofitClient.getTeachersInterface()
        val retrofitData=teacherInterface.assignTeacherCourse("Bearer $teacherToken",teacherId,courseId)
        retrofitData.enqueue(object:Callback<Unit?>{
            override fun onResponse(call: Call<Unit?>, response: Response<Unit?>) {
                loadCourses(id) { updatedList ->
                    courseAdapter = CourseAdapter(
                        updatedList, requireActivity().supportFragmentManager )
                    rvTeacherCourses.adapter = courseAdapter
                }
            }

            override fun onFailure(call: Call<Unit?>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
    private fun loadCourses(id:Int,callback: (List<Courses>) -> Unit){
        val getShared = requireContext().getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
        val teacherToken = getShared.getString("token", null)
        val courseInterface=RetrofitClient.getCoursesInterface()
        val retrofitCourses=courseInterface.getCoursesByTeacherId("Bearer $teacherToken",id)
        retrofitCourses.enqueue(object: Callback<List<Courses>?>{
            override fun onResponse(
                call: Call<List<Courses>?>,
                response: Response<List<Courses>?>
            ) {
                val courses=response.body()
                if(courses!=null){
                    callback(courses)
                }
                else{
                    callback(emptyList())
                }
            }

            override fun onFailure(call: Call<List<Courses>?>, t: Throwable) {
                Log.d("Teacher Courses", "failure" + t.message)
                callback(emptyList())
            }

        })
    }


}