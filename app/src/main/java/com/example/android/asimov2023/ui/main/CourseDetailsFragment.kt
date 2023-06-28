package com.example.android.asimov2023.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
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
import com.example.android.asimov2023.ui.auth.SignUpTeacherFragment
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback
import kotlin.math.roundToInt


class CourseDetailsFragment : Fragment() {
    private lateinit var recyclerViewCompetence: RecyclerView
    private lateinit var adapterCompetence: CompetenceAdapter
    private lateinit var recyclerViewItems: RecyclerView
    private lateinit var adapterCourseItem: CourseItemsAdapter
    private lateinit var textCourseName:TextView
    private lateinit var textDescription:TextView
    private lateinit var pbCourseProgress:ProgressBar
    private lateinit var tvCourseProgress:TextView
    @SuppressLint("MissingInflatedId")
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

        val btnAddItem=view.findViewById<Button>(R.id.btn_CreateItem)
        pbCourseProgress=view.findViewById(R.id.courseProgressBar)
        tvCourseProgress=view.findViewById(R.id.lblProgressPercent)
        recyclerViewCompetence.layoutManager=LinearLayoutManager(requireContext())
        recyclerViewItems.layoutManager=LinearLayoutManager(requireContext())
        loadInitial(id){
            course->
            if (course != null) {
                textCourseName.text=course.name
            }
            if (course != null) {
                textDescription.text=course.description
            }
        }
        btnAddItem.setOnClickListener{
            val it_name=view.findViewById<TextView>(R.id.et_ItemName)
            val it_desc=view.findViewById<TextView>(R.id.et_ItemDescription)
            val json = JSONObject()
            json.put("name",it_name.text.toString())
            json.put("description",it_desc.text.toString())
            json.put("state",false)
            addItem(json,id)
            it_name.text=""
            it_desc.text=""
        }
        loadCompetences(id){
            competenceList->
            adapterCompetence= CompetenceAdapter(competenceList!!)
            recyclerViewCompetence.adapter=adapterCompetence
        }
        loadItems(id){
            itemList->
            if(itemList!!.size>0){
                val cantItem= itemList?.size
                var itemNF=0
                if (itemList != null) {
                    itemList.forEach { item->
                        run {
                            if (!item.state) {
                                itemNF++
                            }
                        }
                    }
                }
                tvCourseProgress.text=((itemNF.toDouble()/cantItem!!)*100).roundToInt().toString()
                pbCourseProgress.progress=((itemNF.toDouble()/cantItem!!)*100).roundToInt()
            }

            adapterCourseItem= CourseItemsAdapter(itemList!!,requireContext())
            recyclerViewItems.adapter=adapterCourseItem
        }


        val btEnter = view.findViewById<Button>(R.id.btnPublishCompetence)
        btEnter.setOnClickListener {
            val txtTitle = view.findViewById<TextView>(R.id.txtTitleCompetence)
            val txtDescription = view.findViewById<TextView>(R.id.txtDescriptionCompetence)

            val json = JSONObject()
            json.put("title", txtTitle.text.toString())
            json.put("description", txtDescription.text.toString())

            addCompetence(id,json)
        }


    }

    private fun addItem(json: JSONObject,courseId:Int) {
        val courseInterface=RetrofitClient.getCoursesInterface()
        val getShared=requireContext().getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
        val teacherToken=getShared.getString("token",null)
        val requestBody=RequestBody.create(MediaType.parse("application/json"),json.toString())
        val retrofitData=courseInterface.createItem(requestBody,"Bearer $teacherToken",courseId)
        retrofitData.enqueue(object:retrofit2.Callback<CourseItem?>{
            override fun onResponse(call: Call<CourseItem?>, response: Response<CourseItem?>) {
                if(response.isSuccessful){
                    loadItems(courseId){
                        itemList->adapterCourseItem= CourseItemsAdapter(itemList!!,requireContext())
                        recyclerViewItems.adapter=adapterCourseItem
                        Log.d("Item","Succesfull adding item")
                    }
                }


            }

            override fun onFailure(call: Call<CourseItem?>, t: Throwable) {
                Log.d("Item","Succesfull")
            }
        })

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
    private fun loadItems(cId:Int,callback: (MutableList<CourseItem>?)->Unit){
        val getShared=requireContext().getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
        val teacherToken = getShared.getString("token", null)
        val competenceInterface=RetrofitClient.getCoursesInterface()
        val retrofitData=competenceInterface.getCourseItems("Bearer $teacherToken",cId)
        retrofitData.enqueue(object :retrofit2.Callback<MutableList<CourseItem>?>{
            override fun onResponse(
                call: Call<MutableList<CourseItem>?>,
                response: Response<MutableList<CourseItem>?>
            ) {
                val itemList=response.body()
                if (itemList != null) {
                    Log.d("itemList", "OK" )
                    callback(itemList)
                } else {
                    callback(mutableListOf())
                }
            }

            override fun onFailure(call: Call<MutableList<CourseItem>?>, t: Throwable) {
                Log.d("itemList", "failure" + t.message)
                callback(mutableListOf())
            }

        })
    }

    private fun addCompetence(cId:Int,json: JSONObject) {
        val announcementInterface = RetrofitClient.getCompetencesInterface()
        val getShared = requireContext().getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
        val directorToken = getShared.getString("token", null)

        val requestBody = RequestBody.create(MediaType.parse("application/json"), json.toString())
        val retrofitData = announcementInterface.createCompetence(requestBody, "Bearer $directorToken")

        retrofitData.enqueue(object : retrofit2.Callback<CompetenceItem?> {
            override fun onResponse(call: Call<CompetenceItem?>, response: Response<CompetenceItem?>) {

                if (response.isSuccessful) {
                    Log.d("CreateAnnouncement", "Success" + response.message())
                    response.body()?.let { linkCompetenceCourse(cId, it.id ) }


                }
            }

            override fun onFailure(call: Call<CompetenceItem?>, t: Throwable) {
                Log.d("CreateAnnouncement", "Failure" + t.message)
            }
        })
    }
    private fun linkCompetenceCourse (cId:Int, competenceId:Int){
        val announcementInterface = RetrofitClient.getCompetencesInterface()
        val getShared = requireContext().getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
        val directorToken = getShared.getString("token", null)

        val retrofitData = announcementInterface.linkCompetenceCourse(cId, competenceId,"Bearer $directorToken")
        retrofitData.enqueue(object : retrofit2.Callback<Unit?> {
            override fun onResponse(call: Call<Unit?>, response: Response<Unit?>) {
                if (response.isSuccessful) {
                    Log.d("Link", "Success" + response.message())

                    // Después de agregar el anuncio, cargar los datos actualizados y actualizar el adaptador
                    loadCompetences(cId) { competenceList ->
                        adapterCompetence = CompetenceAdapter(competenceList!!)
                        recyclerViewCompetence.adapter = adapterCompetence
                    }
                }
            }

            override fun onFailure(call: Call<Unit?>, t: Throwable) {
                Log.d("Link", "error" )
            }
        })
    }


}