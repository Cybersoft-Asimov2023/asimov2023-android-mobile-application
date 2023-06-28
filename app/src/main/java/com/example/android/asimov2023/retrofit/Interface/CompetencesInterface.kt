package com.example.android.asimov2023.retrofit.Interface

import com.example.android.asimov2023.retrofit.Model.AnnouncementItem
import com.example.android.asimov2023.retrofit.Model.CompetenceItem
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface CompetencesInterface {

    @GET("competences")
    fun getCompetences(
        @Header("Authorization") authorization: String,
    ): Call<List<CompetenceItem>>
    @GET("courses/{courseId}/competences")
    fun getCompetencesbyCourseId(@Header("Authorization") authorization: String,@Path("courseId") courseId:Int):Call<List<CompetenceItem>>

    @POST("competences")
    fun createCompetence(@Body credentials: RequestBody, @Header("Authorization") authorization: String): Call<CompetenceItem>

    @POST("courses/{courseId}/competence/{competenceId}")
    fun linkCompetenceCourse(@Path("courseId") courseId: Int,@Path("competenceId") competenceId: Int,@Header("Authorization") authorization: String): Call<Unit>
}