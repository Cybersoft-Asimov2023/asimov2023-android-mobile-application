package com.example.android.asimov2023

import com.example.android.asimov2023.retrofit.Model.Courses
import com.example.android.asimov2023.retrofit.Model.TeacherItem
import com.example.android.asimov2023.retrofit.RetrofitClient
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.simple.JSONObject
import org.junit.Test
import retrofit2.Response

class CourseUnitTest {

    //este token expira, se debe generar otro (postman->sing-in)
    var generated_token
    = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRlcnNvbkBnbWFpbC5jb20iLCJpYXQiOjE3MDA2MjEzMDcsImV4cCI6MTcwMTIyNjEwN30.ESF9hWAsBto-nr-PcLVbsmjqhM86CbdgdHLFT85faaY"
     // US013 - Ingresar cursos
    @Test
    fun testAddCourse() = runBlocking {
        val api = RetrofitClient.getCoursesInterface()
        val jsonObject = JSONObject().apply {
            put("name", "CursoC")
            put("description", "este es un curso de prueba")
            put("state", true)
        }

        val requestBody = RequestBody.create("application/json".toMediaTypeOrNull(), jsonObject.toString())
        val response: Response<Courses> = api.createCourse(requestBody,"Bearer $generated_token").execute()
        if (response.isSuccessful) {
            val responseBody = response.body()
            TestCase.assertNotNull(responseBody)
        } else {
            TestCase.fail("La solicitud no fue exitosa. Código de error: ${response.code()}")
        }
    }


}