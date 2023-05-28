package com.example.android.asimov2023.retrofit.Interface

import com.example.android.asimov2023.retrofit.Model.TeacherItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface TeachersInterface {

    @GET("directors/{directorId}/teachers")
    fun getTeachers(
        @Header("Authorization") authorization: String,
        @Path("directorId") directorId: Int
    ): Call<List<TeacherItem>>


}