package com.example.android.asimov2023.retrofit.Interface

import com.example.android.asimov2023.retrofit.Model.Courses
import retrofit2.Call
import retrofit2.http.GET

interface CoursesInterface {
    @GET("courses")
    fun getCourses():Call<List<Courses>>
}