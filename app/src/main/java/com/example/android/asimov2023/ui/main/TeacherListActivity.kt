package com.example.android.asimov2023.ui.main

import TeacherAdapter
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.asimov2023.R
import com.example.android.asimov2023.retrofit.Model.TeacherItem
import com.example.android.asimov2023.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TeacherListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TeacherAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_list)

        setupViews()
    }
    private fun setupViews(){

        recyclerView = findViewById(R.id.recycler_view_teachers)
        recyclerView.layoutManager = LinearLayoutManager(this)
        loadTeachers { teachersList ->
            adapter = TeacherAdapter(teachersList)
            recyclerView.adapter = adapter
        }

    }
    private fun loadTeachers(callback: (List<TeacherItem>) -> Unit){
        val getShared = getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
        val id = getShared.getInt("id", 0)
        val directorToken = getShared.getString("token", null)
        val teachersInterface = RetrofitClient.getTeachersInterface()
        val retrofitData = teachersInterface.getTeachers("Bearer $directorToken",id)
        Log.d("ID", id.toString())
        retrofitData.enqueue(object : Callback<List<TeacherItem>?> {
            override fun onResponse(
                call: Call<List<TeacherItem>?>,
                response: Response<List<TeacherItem>?>
            ) {

                val teachersList = response.body()
                if (teachersList != null) {
                    Log.d("TeachersList", "SUCCESS" )
                    callback(teachersList)
                }else {
                    callback(emptyList())
                }
            }

            override fun onFailure(call: Call<List<TeacherItem>?>, t: Throwable) {
                Log.d("TeachersList", "failure"+t.message)
                callback(emptyList())
            }
        })


    }



}