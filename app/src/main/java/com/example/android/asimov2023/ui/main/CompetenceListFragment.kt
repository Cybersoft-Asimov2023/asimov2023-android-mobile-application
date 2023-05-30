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
import com.example.android.asimov2023.retrofit.Model.CompetenceItem
import com.example.android.asimov2023.retrofit.Model.TeacherItem
import com.example.android.asimov2023.retrofit.RetrofitClient
import com.example.android.asimov2023.ui.adapters.CompetenceAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CompetenceListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CompetenceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_competence_list, container, false)
        setupViews(view)
        return view
    }

    private fun setupViews(view: View) {
        recyclerView = view.findViewById(R.id.recycler_view_competences)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        loadCompetences { competenceList ->
            adapter = CompetenceAdapter(competenceList)
            recyclerView.adapter = adapter
        }
    }

    private fun loadCompetences(callback: (List<CompetenceItem>) -> Unit) {
        val getShared = requireContext().getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
        val directorToken = getShared.getString("token", null)
        val competenceInterface = RetrofitClient.getCompetencesInterface()
        val retrofitData = competenceInterface.getCompetences("Bearer $directorToken")
        retrofitData.enqueue(object : Callback<List<CompetenceItem>?> {
            override fun onResponse(
                call: Call<List<CompetenceItem>?>,
                response: Response<List<CompetenceItem>?>
            ) {
                val competenceList = response.body()
                if (competenceList != null) {
                    Log.d("CompetenceList", "SUCCESS")
                    callback(competenceList)
                } else {
                    callback(emptyList())
                }
            }

            override fun onFailure(call: Call<List<CompetenceItem>?>, t: Throwable) {
                Log.d("CompetenceList", "failure" + t.message)
                callback(emptyList())
            }
        })
    }
}