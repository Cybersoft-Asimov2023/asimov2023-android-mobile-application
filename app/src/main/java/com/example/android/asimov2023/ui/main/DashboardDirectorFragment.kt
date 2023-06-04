package com.example.android.asimov2023.ui.main

import TeacherListFragment
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.android.asimov2023.R

class DashboardDirectorFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard_director, container, false)
        setupViews(view)
        return view

    }
    private fun setupViews(view:View){
        val btnMeetTeachers= view.findViewById<Button>(R.id.btnMeetAllTeachers);
        btnMeetTeachers.setOnClickListener(){
            Log.d("BOTON","DONE")
            val fragment=TeacherListFragment()
            val transaction=requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

}