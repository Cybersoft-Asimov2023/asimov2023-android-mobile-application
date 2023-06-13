package com.example.android.asimov2023.retrofit.Interface

import com.example.android.asimov2023.retrofit.Model.AnnouncementItem
import com.example.android.asimov2023.retrofit.Model.TeacherItem
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface AnnouncementInterface {

    @GET("directors/{directorId}/announcements")
    fun getAnnouncements(
        @Header("Authorization") authorization: String,
        @Path("directorId") directorId: Int
    ): Call<List<AnnouncementItem>>

    @POST("directors/{directorId}/announcements")
    fun createAnnouncement(@Body credentials: RequestBody,  @Path("directorId") directorId: Int,  @Header("Authorization") authorization: String): Call<AnnouncementItem>
}