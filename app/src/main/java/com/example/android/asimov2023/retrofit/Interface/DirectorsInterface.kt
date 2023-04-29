package com.example.android.asimov2023.retrofit.Interface

import com.example.android.asimov2023.retrofit.Model.DirectorItem
import retrofit2.Call
import retrofit2.http.GET

interface DirectorsInterface {

    @GET("directors")
    fun getDirectors(): Call<List<DirectorItem>>

}