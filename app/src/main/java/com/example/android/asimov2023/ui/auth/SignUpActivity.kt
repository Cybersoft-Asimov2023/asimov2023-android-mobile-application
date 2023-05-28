package com.example.android.asimov2023.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import com.example.android.asimov2023.R
import com.example.android.asimov2023.retrofit.Model.DirectorItem
import com.example.android.asimov2023.retrofit.RetrofitClient
import com.example.android.asimov2023.ui.main.TeacherListActivity
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        setupViews()
    }


    private fun setupViews(){
        val btEnter = findViewById<Button>(R.id.btnRegister)

        btEnter.setOnClickListener() {
            val txtName = findViewById<TextView>(R.id.tboxRegFirstName)
            val txtLastName = findViewById<TextView>(R.id.tboxRegLastName)
            val txtPhone = findViewById<TextView>(R.id.tboxRegPhone)
            val txtBirthdate = findViewById<TextView>(R.id.tboxRegBirthdate)
            val txtEmail = findViewById<TextView>(R.id.tboxRegEmail)
            val txtPassword = findViewById<TextView>(R.id.tboxRegPass)

            val json = JSONObject()
            json.put("first_name", txtName.text.toString())
            json.put("last_name", txtLastName.text.toString())
            json.put("password", txtPassword.text.toString())
            json.put("email", txtEmail.text.toString())
            json.put("phone", txtPhone.text.toString())
            json.put("age", (txtBirthdate.text.toString().toInt()))

            signUp(json)
        }
    }

    private fun signUp(json:JSONObject){

        val directorsInterface = RetrofitClient.getDirectorsInterface()

        val requestBody = RequestBody.create(MediaType.parse("application/json"), json.toString())
        val retrofitData = directorsInterface.directorSignUp(requestBody)

        retrofitData.enqueue(object : Callback<DirectorItem?> {
            override fun onResponse(call: Call<DirectorItem?>, response: Response<DirectorItem?>) {
                Log.d("SignUp", "Succes"+response.message())

                if(response.isSuccessful){
                    startLogInActivity()
                }
            }

            override fun onFailure(call: Call<DirectorItem?>, t: Throwable) {
                Log.d("SignUp", "failure"+t.message)
            }
        })


    }
    private fun startLogInActivity() {
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
        finish()

    }
}