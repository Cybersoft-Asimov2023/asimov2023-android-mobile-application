package com.example.android.asimov2023

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.android.asimov2023.ui.main.MenuActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_main)
        //testing log in
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)

    }

}
