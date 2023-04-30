package com.example.android.asimov2023.retrofit.Interface

import com.example.android.asimov2023.retrofit.Model.DirectorItem
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface DirectorsInterface {

    @GET("directors")
    fun getDirectors(): Call<List<DirectorItem>>

    @POST("directors/auth/sign-in")
    fun directorSignIn(@Body credentials: RequestBody): Call<DirectorItem>


}