package com.example.android.asimov2023.retrofit.Interface

import com.example.android.asimov2023.retrofit.Model.CompetenceItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface CompetencesInterface {

    @GET("competences")
    fun getCompetences(
        @Header("Authorization") authorization: String,
    ): Call<List<CompetenceItem>>

}