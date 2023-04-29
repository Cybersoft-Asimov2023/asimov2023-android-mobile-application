package com.example.android.asimov2023

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.android.asimov2023.retrofit.Model.DirectorItem
import com.example.android.asimov2023.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getDirectors()

    }

    private fun getDirectors(){

        val directorsInterface = RetrofitClient.getDirectorsInterface()
        val retrofitData = directorsInterface.getDirectors()

        retrofitData.enqueue(object : Callback<List<DirectorItem>?> {
            override fun onResponse(
                call: Call<List<DirectorItem>?>,
                response: Response<List<DirectorItem>?>
            ) {
                val responseBody = response.body()!!

                val myStringBuilder = StringBuilder()
                for(data in responseBody){
                    myStringBuilder.append(data.id)
                        .append(" ")
                        .append(data.first_name)
                        .append(" ")
                        .append(data.last_name)
                    myStringBuilder.append("\n")
                }
                findViewById<TextView>(R.id.txtId).text = myStringBuilder
            }
            override fun onFailure(call: Call<List<DirectorItem>?>, t: Throwable) {
                Log.d("MainActivity", "failure"+t.message)
            }
        })



    }
}
