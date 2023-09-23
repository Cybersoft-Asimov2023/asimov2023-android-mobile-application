package com.example.android.asimov2023.retrofit.tests


import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.android.asimov2023.retrofit.RetrofitClient
import com.example.android.asimov2023.ui.auth.SignInActivity
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.Response
import org.junit.Test
import org.json.simple.JSONObject

class ApiUnitTest : TestCase() {
    @Test
    fun testLogInValidation() {
        val api = RetrofitClient.getDirectorsInterface()
        val jsonObject = JSONObject()
        jsonObject["email"] = "anderson@gmail.com"
        jsonObject["password"] = "123"

        val requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString())

        val test = runBlocking {
            api.directorSignIn(requestBody).execute()
        }

        assertTrue(test.isSuccessful)
    }
}