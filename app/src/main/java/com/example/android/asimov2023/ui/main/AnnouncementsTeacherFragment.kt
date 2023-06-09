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
import com.example.android.asimov2023.retrofit.Model.AnnouncementItem
import com.example.android.asimov2023.retrofit.RetrofitClient
import com.example.android.asimov2023.ui.adapters.AnnouncementAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AnnouncementsTeacherFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AnnouncementAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_announcements_teacher, container, false)
        setupViews(view)
        return view

    }

    private fun setupViews(view: View) {
        recyclerView = view.findViewById(R.id.recycler_view_announcements_director)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        loadAnnouncements { announcementList ->
            adapter = AnnouncementAdapter(requireContext(),
                announcementList, false)
            recyclerView.adapter = adapter
        }


    }

    private fun loadAnnouncements(callback: (MutableList<AnnouncementItem>) -> Unit) {
        val getShared = requireContext().getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
        val id = getShared.getInt("director_id", 0)
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
}