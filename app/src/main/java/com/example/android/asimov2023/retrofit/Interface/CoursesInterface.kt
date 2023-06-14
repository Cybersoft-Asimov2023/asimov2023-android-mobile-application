package com.example.android.asimov2023.retrofit.Interface

import com.example.android.asimov2023.retrofit.Model.CourseItem
import com.example.android.asimov2023.retrofit.Model.Courses
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface CoursesInterface {
    @GET("courses")
    fun getCourses(
        @Header("Authorization") authorization: String): Call<List<Courses>>

    @GET("courses/{courseId}/items")
    fun getCourseItems(@Header("Authorization") authorization: String,@Path("courseId") courseId:Int):Call<List<CourseItem>>
}