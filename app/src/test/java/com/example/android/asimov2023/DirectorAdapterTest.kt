package com.example.android.asimov2023

import com.example.android.asimov2023.retrofit.Model.DirectorItem
import com.example.android.asimov2023.retrofit.Model.TeacherItem
import com.example.android.asimov2023.retrofit.RetrofitClient
import junit.framework.TestCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.simple.JSONObject
import org.junit.Test
import retrofit2.Response

class DirectorItemTest {

    //este token expira, se debe generar otro (postman->sing-in)
     var generated_token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRlcnNvbkBnbWFpbC5jb20iLCJpYXQiOjE3MDA1ODA5NDQsImV4cCI6MTcwMTE4NTc0NH0.sywo6Ra4gMXnhCJHd3JAW-4AELBqeYN9hRrzMjNZrsU"
    @Test
    fun testAddTeacher() = runBlocking {
        val api = RetrofitClient.getTeachersInterface()
        val jsonObject = JSONObject().apply {
            put("id", 1)
            put("age", 30)
            put("email", "corr@example.com")
            put("first_name", "Nombre1")
            put("last_name", "Apellido2")
            put("password", "contraseña")
            put("phone", "123456789")
            put("point", 100)
        }

        val requestBody = RequestBody.create("application/json".toMediaTypeOrNull(), jsonObject.toString())
        val directorId = 1
        val response: Response<TeacherItem> = api.addTeacher(requestBody,directorId).execute()
        if (response.isSuccessful) {
            val responseBody = response.body()
            TestCase.assertNotNull(responseBody)
        } else {
            TestCase.fail("La solicitud no fue exitosa. Código de error: ${response.code()}")
        }
    }



    @Test
    fun testDirectorItemProperties() {
        // Crear una instancia de DirectorItem
        val directorItem = DirectorItem(
            id = 1,
            first_name = "Alexandra",
            last_name = "Ahuanari",
            age = 20,
            email = "alexandra@gmail.com",
            password = "alexandra123",
            phone = "999111333",
            roles = listOf("Director", "Manager"),
            token = "token123"
        )

        // Probar las propiedades de la instancia
        assertEquals(1, directorItem.id)
        assertEquals("Alexandra", directorItem.first_name)
        assertEquals("Ahuanari", directorItem.last_name)
        assertEquals(20, directorItem.age)
        assertEquals("alexandra@gmail.com", directorItem.email)
        assertEquals("alexandra123", directorItem.password)
        assertEquals("999111333", directorItem.phone)
        assertEquals(listOf("Director", "Manager"), directorItem.roles)
        assertEquals("token123", directorItem.token)
    }

    @Test
    fun testDirectorItemEquality() {
        val directorItem1 = DirectorItem(
            id = 1,
            first_name = "Alexandra",
            last_name = "Ahuanari",
            age = 20,
            email = "alexandr@gmail.com",
            password = "alexandra123",
            phone = "999111333",
            roles = listOf("Director", "Manager"),
            token = "token123"
        )

        val directorItem2 = DirectorItem(
            id = 1,
            first_name = "Alexandra",
            last_name = "Ahuanari",
            age = 20,
            email = "alexandr@gmail.com",
            password = "alexandra123",
            phone = "999111333",
            roles = listOf("Director", "Manager"),
            token = "token123"
        )

        // Verificar que dos instancias con los mismos valores sean iguales
        assertEquals(directorItem1, directorItem2)
    }
}
