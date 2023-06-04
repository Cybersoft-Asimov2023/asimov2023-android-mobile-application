package com.example.android.asimov2023.retrofit.Interface;

import com.example.android.asimov2023.retrofit.Model.CourseCompetence;
import com.example.android.asimov2023.retrofit.Model.CourseItem;
import com.example.android.asimov2023.retrofit.Model.Courses;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CourseService {
    @GET("/api/v1/courses/{courseId}/items")
    Call<List<CourseItem>> getCourseItems(@Path("courseId") int courseId);

    @GET("/api/v1/courses/{id}")
    Call<Courses> getCourseById(@Path("id") int id);

    @GET("/api/v1/courses/{courseId}/competences")
    Call<List<CourseCompetence>> getCourseCompetences(@Path("courseId") int courseId);
}
