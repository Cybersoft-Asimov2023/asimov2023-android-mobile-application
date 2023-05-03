package com.example.android.asimov2023.ui.auth

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import com.example.android.asimov2023.R
import com.example.android.asimov2023.retrofit.Model.DirectorItem
import com.example.android.asimov2023.retrofit.RetrofitClient
import okhttp3.MediaType
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
            finish()
        }
    }


    private fun logIn(email:String, password:String){
        val json = JSONObject()
        json.put("email", email)
        json.put("password", password)

        val directorsInterface = RetrofitClient.getDirectorsInterface()

        val requestBody = RequestBody.create(MediaType.parse("application/json"), json.toString())
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
                        apply()
                    }
                    // retrieves token from user phone
                    val getShared = getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
                    val token = getShared.getString("token", null)

                    txttoken.text = "TEST_TOKEN: " + token
                }

            }
            override fun onFailure(call: Call<DirectorItem?>, t: Throwable) {
                Log.d("MainActivity", "failure"+t.message)
            }
        })
    }

}