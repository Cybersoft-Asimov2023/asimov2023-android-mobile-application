package com.example.android.asimov2023.ui.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.asimov2023.R
import com.example.android.asimov2023.retrofit.Model.AnnouncementItem
import com.example.android.asimov2023.retrofit.Model.CourseItem
import com.example.android.asimov2023.retrofit.Model.Courses
import com.example.android.asimov2023.retrofit.RetrofitClient
import com.example.android.asimov2023.ui.adapters.AnnouncementAdapter
import com.example.android.asimov2023.ui.adapters.CourseAdapter
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CoursesDirectorFragment : Fragment() {


    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CourseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_courses_director, container, false)
        setupViews(view)
        return view
    }

    private fun setupViews(view: View) {

        recyclerView = view.findViewById(R.id.recyclerViewCourses)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        loadAnnouncements { announcementList ->
            adapter = CourseAdapter( announcementList, requireActivity().supportFragmentManager)
            recyclerView.adapter = adapter
        }
        val btEnter = view.findViewById<Button>(R.id.btnPublishCourse)
        btEnter.setOnClickListener {
            val txtTitle = view.findViewById<TextView>(R.id.txtTitleAnnounce)
            val txtDescription = view.findViewById<TextView>(R.id.txtDescriptionAnnounce)

            val json = JSONObject()
            json.put("name", txtTitle.text.toString())
            json.put("description", txtDescription.text.toString())
            json.put("state", true)

            addCourse(json)
        }


    }

    private fun loadAnnouncements(callback: (List<Courses>) -> Unit) {
        val getShared = requireContext().getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
        val id = getShared.getInt("id", 0)
        val directorToken = getShared.getString("token", null)
        val announcementInterface = RetrofitClient.getCoursesInterface()
        val retrofitData = announcementInterface.getCourses("Bearer $directorToken")
        Log.d("ID", id.toString())
        retrofitData.enqueue(object : Callback<List<Courses>?> {
            override fun onResponse(
                call: Call<List<Courses>?>,
                response: Response<List<Courses>?>
            ) {
                val announcementList = response.body()
                if (announcementList != null) {
                    Log.d("announcementList", "SUCCESS")
                    callback(announcementList)
                } else {
                    callback(emptyList())
                }
            }

            override fun onFailure(call: Call<List<Courses>?>, t: Throwable) {
                Log.d("announcementList", "failure" + t.message)
                callback(emptyList())
            }
        })
    }

    private fun addCourse(json: JSONObject) {
        val courseInterface = RetrofitClient.getCoursesInterface()
        val getShared = requireContext().getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
        val id = getShared.getInt("id", 0)
        val directorToken = getShared.getString("token", null)

        val requestBody = RequestBody.create(MediaType.parse("application/json"), json.toString())
        val retrofitData = courseInterface.createCourse(requestBody,"Bearer $directorToken")

        retrofitData.enqueue(object : Callback<Courses?> {
            override fun onResponse(call: Call<Courses?>, response: Response<Courses?>) {

                if (response.isSuccessful) {
                    Log.d("CreateAnnouncement", "Success" + response.message())

                    // Después de agregar el anuncio, cargar los datos actualizados y actualizar el adaptador
                    loadAnnouncements { updatedList ->
                        adapter = CourseAdapter(
                            updatedList, requireActivity().supportFragmentManager )
                        recyclerView.adapter = adapter
                    }
                }
            }

            override fun onFailure(call: Call<Courses?>, t: Throwable) {
                Log.d("CreateAnnouncement", "Failure" + t.message)
            }
        })
    }
}