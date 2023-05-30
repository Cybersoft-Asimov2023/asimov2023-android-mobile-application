package com.example.android.asimov2023.ui.auth

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.android.asimov2023.R
import com.example.android.asimov2023.retrofit.Model.TeacherItem
import com.example.android.asimov2023.retrofit.RetrofitClient
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpTeacherFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up_teacher, container, false)

        val btEnter = view.findViewById<Button>(R.id.btnRegister)

        btEnter.setOnClickListener {
            val txtName = view.findViewById<TextView>(R.id.tboxRegFirstName)
            val txtLastName = view.findViewById<TextView>(R.id.tboxRegLastName)
            val txtPhone = view.findViewById<TextView>(R.id.tboxRegPhone)
            val txtBirthdate = view.findViewById<TextView>(R.id.tboxRegBirthdate)
            val txtEmail = view.findViewById<TextView>(R.id.tboxRegEmail)
            val txtPassword = view.findViewById<TextView>(R.id.tboxRegPass)

            val json = JSONObject()
            json.put("first_name", txtName.text.toString())
            json.put("last_name", txtLastName.text.toString())
            json.put("password", txtPassword.text.toString())
            json.put("email", txtEmail.text.toString())
            json.put("phone", txtPhone.text.toString())
            json.put("age", (txtBirthdate.text.toString().toInt()))
            json.put("point", 0)

            signUp(json)
        }

        return view
    }

    private fun signUp(json: JSONObject) {
        val teachersInterface = RetrofitClient.getTeachersInterface()
        val getShared = requireContext().getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
        val id = getShared.getInt("id", 0)

        val requestBody = RequestBody.create(MediaType.parse("application/json"), json.toString())
        val retrofitData = teachersInterface.addTeacher(requestBody,id)

        retrofitData.enqueue(object : Callback<TeacherItem?> {
            override fun onResponse(call: Call<TeacherItem?>, response: Response<TeacherItem?>) {
                Log.d("SignUp", "Success" + response.message())

                if (response.isSuccessful) {
                    goBack()
                }
            }

            override fun onFailure(call: Call<TeacherItem?>, t: Throwable) {
                Log.d("SignUpTeacher", "Failure" + t.message)
            }
        })
    }

    private fun goBack() {
        requireActivity().supportFragmentManager.popBackStack()
    }
}
