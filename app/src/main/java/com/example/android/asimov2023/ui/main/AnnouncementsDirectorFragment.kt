package com.example.android.asimov2023.ui.main

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import com.example.android.asimov2023.retrofit.Model.TeacherItem
import com.example.android.asimov2023.retrofit.RetrofitClient
import com.example.android.asimov2023.ui.adapters.AnnouncementAdapter
import com.example.android.asimov2023.ui.adapters.CompetenceAdapter
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AnnouncementsDirectorFragment : Fragment() {
    lateinit var alertdialog: AlertDialog

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AnnouncementAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_announcements_director, container, false)
        setupViews(view)
        return view

    }

    private fun setupViews(view: View) {

        val btEnter = view.findViewById<Button>(R.id.btnPublishAnnounce)
        recyclerView = view.findViewById(R.id.recycler_view_announcements_director)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        loadAnnouncements { announcementList ->
            adapter = AnnouncementAdapter(requireContext(),
                announcementList, true)
            recyclerView.adapter = adapter
        }



        btEnter.setOnClickListener {
            val txtTitle = view.findViewById<TextView>(R.id.txtTitleAnnounce)
            val txtDescription = view.findViewById<TextView>(R.id.txtDescriptionAnnounce)

            val json = JSONObject()
            json.put("title", txtTitle.text.toString())
            json.put("description", txtDescription.text.toString())

            addAnnouncement(json)
        }


    }

    private fun loadAnnouncements(callback: (MutableList<AnnouncementItem>) -> Unit) {
        val getShared = requireContext().getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
        val id = getShared.getInt("id", 0)
        val directorToken = getShared.getString("token", null)
        val announcementInterface = RetrofitClient.getAnnouncementInterface()
        val retrofitData = announcementInterface.getAnnouncements("Bearer $directorToken", id)
        Log.d("ID", id.toString())
        retrofitData.enqueue(object : Callback<MutableList<AnnouncementItem>?> {
            override fun onResponse(
                call: Call<MutableList<AnnouncementItem>?>,
                response: Response<MutableList<AnnouncementItem>?>
            ) {
                val announcementList = response.body()
                if (announcementList != null) {
                    Log.d("announcementList", "SUCCESS")
                    callback(announcementList)
                } else {
                    callback(mutableListOf())
                }
            }

            override fun onFailure(call: Call<MutableList<AnnouncementItem>?>, t: Throwable) {
                Log.d("announcementList", "failure" + t.message)
                callback(mutableListOf())
            }
        })
    }


    private fun addAnnouncement(json: JSONObject) {
        val announcementInterface = RetrofitClient.getAnnouncementInterface()
        val getShared = requireContext().getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
        val id = getShared.getInt("id", 0)
        val directorToken = getShared.getString("token", null)

        val requestBody = RequestBody.create(MediaType.parse("application/json"), json.toString())
        val retrofitData = announcementInterface.createAnnouncement(requestBody,id, "Bearer $directorToken")

        retrofitData.enqueue(object : Callback<AnnouncementItem?> {
            override fun onResponse(call: Call<AnnouncementItem?>, response: Response<AnnouncementItem?>) {

                if (response.isSuccessful) {
                    Log.d("CreateAnnouncement", "Success" + response.message())

                    // Después de agregar el anuncio, cargar los datos actualizados y actualizar el adaptador
                    loadAnnouncements { updatedList ->
                        adapter = AnnouncementAdapter(requireContext(),
                            updatedList, true)
                        recyclerView.adapter = adapter
                    }
                }
            }

            override fun onFailure(call: Call<AnnouncementItem?>, t: Throwable) {
                Log.d("CreateAnnouncement", "Failure" + t.message)
            }
        })
    }



}