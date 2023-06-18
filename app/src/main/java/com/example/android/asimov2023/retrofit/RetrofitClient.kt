package com.example.android.asimov2023.retrofit

import com.example.android.asimov2023.retrofit.Interface.AnnouncementInterface
import com.example.android.asimov2023.retrofit.Interface.CompetencesInterface
import com.example.android.asimov2023.retrofit.Interface.CoursesInterface
import com.example.android.asimov2023.retrofit.Interface.DirectorsInterface
import com.example.android.asimov2023.retrofit.Interface.TeachersInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://asimov2023-api-production-ca64.up.railway.app/api/v1/"


    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getDirectorsInterface(): DirectorsInterface {
        return retrofit.create(DirectorsInterface::class.java)
    }

    fun getTeachersInterface(): TeachersInterface {
        return retrofit.create(TeachersInterface::class.java)
    }

    fun getCompetencesInterface(): CompetencesInterface {
        return retrofit.create(CompetencesInterface::class.java)
    }

    fun getAnnouncementInterface(): AnnouncementInterface {
        return retrofit.create(AnnouncementInterface::class.java)
    }
    fun getCoursesInterface():CoursesInterface{
        return retrofit.create(CoursesInterface::class.java)
    }
}

