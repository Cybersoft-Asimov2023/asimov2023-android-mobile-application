package com.example.android.asimov2023.retrofit.Interface

import com.example.android.asimov2023.retrofit.Model.DirectorItem
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface DirectorsInterface {

    @GET("directors")
    fun getDirectors(): Call<List<DirectorItem>>

    @GET("directors/{directorId}")
    fun getDirectorById(@Header("Authorization") authorization: String, @Path("directorId") directorId:Int): Call<DirectorItem>
    @POST("directors/auth/sign-in")
    fun directorSignIn(@Body credentials: RequestBody): Call<DirectorItem>

    @POST("directors/auth/sign-up")
    fun directorSignUp(@Body credentials: RequestBody): Call<DirectorItem>

    @PUT("directors/{directorId}")
    fun updateDirectorData(@Header("Authorization") authorization: String, @Path("directorId") directorId:Int, @Body data: RequestBody): Call<DirectorItem>


}