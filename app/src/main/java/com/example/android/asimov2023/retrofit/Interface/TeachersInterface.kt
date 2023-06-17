package com.example.android.asimov2023.retrofit.Interface

import com.example.android.asimov2023.retrofit.Model.DirectorItem
import com.example.android.asimov2023.retrofit.Model.TeacherItem
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface TeachersInterface {

    @GET("directors/{directorId}/teachers")
    fun getTeachers(
        @Header("Authorization") authorization: String,
        @Path("directorId") directorId: Int
    ): Call<List<TeacherItem>>

    @GET("teachers/{teacherId}")
    fun getTeacher(@Header("Authorization") authorization: String,
                   @Path("teacherId") teacherId: Int):Call<TeacherItem>



    @POST("teachers/auth/sign-up/{directorId}")
    fun addTeacher(
        @Body credentials: RequestBody,
        @Path("directorId") directorId: Int
    ): Call<TeacherItem>

    @POST("teachers/auth/sign-in")
    fun teacherSignIn(@Body credentials: RequestBody): Call<TeacherItem>
}