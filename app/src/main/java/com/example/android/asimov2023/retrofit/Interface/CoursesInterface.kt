package com.example.android.asimov2023.retrofit.Interface

import com.example.android.asimov2023.retrofit.Model.AnnouncementItem
import com.example.android.asimov2023.retrofit.Model.CourseItem
import com.example.android.asimov2023.retrofit.Model.Courses
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface CoursesInterface {
    @GET("courses")
    fun getCourses(
        @Header("Authorization") authorization: String): Call<List<Courses>>

    @GET("courses/{courseId}/items")
    fun getCourseItems(@Header("Authorization") authorization: String,@Path("courseId") courseId:Int):Call<MutableList<CourseItem>>

    @GET("courses/{courseId}")
    fun getCourseById(@Header("Authorization") authorization: String,@Path("courseId") courseId:Int):Call<Courses>

    @GET("teachers/{teacherId}/courses")
    fun getCoursesByTeacherId(@Header("Authorization") authorization: String,@Path("teacherId") teacherId:Int):Call<List<Courses>>

    @POST("courses/{courseId}/items")
    fun createItem(@Body credentials: RequestBody,@Header("Authorization") authorization: String,@Path("courseId") courseId:Int):Call<CourseItem>

    @POST("courses")
    fun createCourse(@Body credentials: RequestBody, @Header("Authorization") authorization: String): Call<Courses>
}