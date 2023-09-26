package com.example.android.asimov2023.ui.auth

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.android.asimov2023.R
import com.example.android.asimov2023.retrofit.Model.DirectorItem
import com.example.android.asimov2023.retrofit.Model.TeacherItem
import com.example.android.asimov2023.retrofit.RetrofitClient
import com.example.android.asimov2023.ui.main.MenuActivity
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInActivity : AppCompatActivity() {
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        setupViews()
    }

    private fun setupViews(){
        val btEnter = findViewById<Button>(R.id.btnEnter)
        val btGotoRegister =findViewById<Button>(R.id.btnGoToRegister)

        btEnter.setOnClickListener() {
            val txtEmail = findViewById<TextView>(R.id.txtEmail)
            val txtPassword = findViewById<TextView>(R.id.txtPassword)
            logIn(txtEmail.text.toString(), txtPassword.text.toString())
        }
        btGotoRegister.setOnClickListener(){
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            //finish()
        }
    }


     fun logIn(email:String, password:String){

         if (email.isEmpty() || password.isEmpty()) {
             // Manejo del error cuando la entrada está vacía
             throw IllegalArgumentException("Entrada vacía")
         }

        val json = JSONObject()
        json.put("email", email)
        json.put("password", password)

        logInDirector(json)



    }
    private fun logInDirector(json: JSONObject){

        val directorsInterface = RetrofitClient.getDirectorsInterface()

        val requestBody = RequestBody.create(
            "application/json".toMediaTypeOrNull(),
            json.toString()
        )
        val retrofitData = directorsInterface.directorSignIn(requestBody)

        retrofitData.enqueue(object : Callback<DirectorItem?> {
            override fun onResponse(call: Call<DirectorItem?>, response: Response<DirectorItem?>) {

                val directorInfo = response.body()
                if (directorInfo != null) {
                    Log.d("Directors", directorInfo.token)
                    val txttoken = findViewById<TextView>(R.id.txtToken)

                    //saves token in user phone
                    val sharedPreferences = getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
                    sharedPreferences.edit().apply {
                        putString("token", directorInfo.token)
                        putInt("id", directorInfo.id)
                        apply()
                    }
                    // retrieves token from user phone
                    val getShared = getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
                    val token = getShared.getString("token", null)

                    txttoken.text = "TEST_TOKEN: " + token

                    if(response.isSuccessful){
                        startMenuActivity(true)
                    }

                }
                else{
                    logInTeacher(json)
                }

            }
            override fun onFailure(call: Call<DirectorItem?>, t: Throwable) {
                Log.d("MainActivity", "failure"+t.message)
            }
        })

    }
    private fun logInTeacher(json: JSONObject){

        val teacherInterface = RetrofitClient.getTeachersInterface()
        val requestBody = RequestBody.create(
            "application/json".toMediaTypeOrNull(),
            json.toString()
        )
        val retrofitData = teacherInterface.teacherSignIn(requestBody)

        retrofitData.enqueue(object : Callback<TeacherItem?> {
            override fun onResponse(call: Call<TeacherItem?>, response: Response<TeacherItem?>) {

                val teacherInfo = response.body()
                if (teacherInfo != null) {
                    Log.d("Teacher", teacherInfo.token)
                    val txttoken = findViewById<TextView>(R.id.txtToken)

                    //saves token in user phone
                    val sharedPreferences = getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
                    sharedPreferences.edit().apply {
                        putString("token", teacherInfo.token)
                        putInt("id", teacherInfo.id)
                        apply()
                    }
                    getTeacher()

                    // retrieves token from user phone
                    val getShared = getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
                    val token = getShared.getString("token", null)
                    Log.d("Toker",token.toString())
                    txttoken.text = "TEST_TOKEN: " + token

                    if(response.isSuccessful){
                        startMenuActivity(false)
                    }

                }else{
                    Toast.makeText(this@SignInActivity, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                }


            }
            override fun onFailure(call: Call<TeacherItem?>, t: Throwable) {
                Log.d("MainActivity", "failure"+t.message)
            }
        })
    }

    private fun getTeacher(){

        val getShared = getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
        val token = getShared.getString("token", null)
        val id = getShared.getInt("id", 0)

        val teacherInterface = RetrofitClient.getTeachersInterface()
        val retrofitData = teacherInterface.getTeacher("Bearer $token", id)

        retrofitData.enqueue(object : Callback<TeacherItem?> {
            override fun onResponse(call: Call<TeacherItem?>, response: Response<TeacherItem?>) {

                val teacherInfo = response.body()
                if (teacherInfo != null) {
                    Log.d("DIRECTOR ID", teacherInfo.director_id.toString())
                    //saves token in user phone
                    val sharedPreferences = getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
                    sharedPreferences.edit().apply {
                        putInt("director_id", teacherInfo.director_id)
                        apply()
                    }
                }
            }
            override fun onFailure(call: Call<TeacherItem?>, t: Throwable) {
                Log.d("MainActivity", "failure"+t.message)
            }
        })
    }


    private fun startMenuActivity(isDirector: Boolean) {
        val intent = Intent(this, MenuActivity::class.java)
        intent.putExtra("IS_DIRECTOR", isDirector)
        startActivity(intent)
        finish()

    }

}