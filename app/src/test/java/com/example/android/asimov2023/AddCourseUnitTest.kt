package com.example.android.asimov2023

import com.example.android.asimov2023.retrofit.Interface.CoursesInterface
import com.example.android.asimov2023.retrofit.Model.CourseItem
import com.example.android.asimov2023.ui.main.CourseDetailsFragment
import org.json.simple.JSONObject

import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers.any
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CourseDetailsFragmentTest {

    private lateinit var courseDetailsFragment: CourseDetailsFragment

    @Mock
    private lateinit var mockCourseInterface: CoursesInterface

    @Mock
    private lateinit var mockCall: Call<CourseItem>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        courseDetailsFragment = CourseDetailsFragment()

        // Aquí debes configurar el RetrofitClient para usar el mockCourseInterface en lugar de la implementación real
        // Esto puede requerir una refactorización del código para que RetrofitClient sea inyectable o reemplazable por mocks
    }


    @Captor
    private lateinit var callbackCaptor: ArgumentCaptor<Callback<CourseItem>>

    @Test
    fun testAddItem() {
        // Configurar los datos de prueba
        val courseId = 1
        val json = JSONObject()
        json.put("name", "Nombre del Item")
        json.put("description", "Descripción del Item")
        json.put("state", false)

        // Configurar el comportamiento del mockCall
        `when`(mockCourseInterface.createItem(any(), any(), any()))
            .thenReturn(mockCall)

        // Llamar a la función addItem
        courseDetailsFragment.addItem(json, courseId)

        // Verificar que se haya llamado a enqueue en el mockCall con un Callback
        verify(mockCall).enqueue(callbackCaptor.capture())

        // Simular una respuesta exitosa del servidor (puedes personalizar esto según tus necesidades)
        val responseBody = CourseItem(1,"Matematica","Numeros",false)
        val response = Response.success(responseBody)

        // Llamar manualmente al Callback con la respuesta simulada
        callbackCaptor.value.onResponse(mockCall, response)

        // Verificar que la carga de items se ha realizado después de agregar el elemento (puedes personalizar esto según tus necesidades)
        verify(courseDetailsFragment).loadItems(courseId, any())
    }
}
