package com.example.android.asimov2023

import com.example.android.asimov2023.retrofit.Model.DirectorItem
import com.example.android.asimov2023.retrofit.RetrofitClient
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import okhttp3.RequestBody
import org.json.simple.JSONObject
import org.junit.Test
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import retrofit2.Response

class AuthUnitTest {

    //US007 - Iniciar sesión
    @Test
    fun testLogInValidation() = runBlocking {
        val api = RetrofitClient.getDirectorsInterface()
        val jsonObject = JSONObject().apply {
            put("email", "anderson@gmail.com")
            put("password", "123")
        }
        val requestBody = RequestBody.create("application/json".toMediaTypeOrNull(), jsonObject.toString())

        val response: Response<DirectorItem> = api.directorSignIn(requestBody).execute()
        if (response.isSuccessful) {

            val responseBody = response.body()
            TestCase.assertNotNull(responseBody)
        } else {

            TestCase.fail("La solicitud de inicio de sesión no fue exitosa. Código de error: ${response.code()}")
        }
    }




}