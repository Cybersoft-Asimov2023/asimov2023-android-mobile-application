package com.example.android.asimov2023

import com.example.android.asimov2023.retrofit.Interface.CoursesInterface
import com.example.android.asimov2023.retrofit.Model.Courses
import com.example.android.asimov2023.ui.main.CoursesDirectorFragment
import org.json.simple.JSONObject
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers.any
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CourseDetailsFragmentUnitTest {

    @Mock
    private lateinit var mockCoursesInterface: CoursesInterface

    @Mock
    private lateinit var mockCall: Call<Courses>

    @Captor
    private lateinit var callbackCaptor: ArgumentCaptor<Callback<Courses>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testAddCourse_Success() {
        // Configurar datos de prueba
        val json = JSONObject() // Aquí configura tu objeto JSON como sea necesario

        // Configurar el comportamiento del mockCoursesInterface
        `when`(mockCoursesInterface.createCourse(any(), any()))
            .thenReturn(mockCall)

        // Llamar al método addCourse
        val courseDirectorsFragment = CoursesDirectorFragment() // Debes ajustar la creación del fragmento según tu implementación real
        courseDirectorsFragment.addCourse(json)

        // Verificar que se haya llamado a enqueue en el mockCall con un Callback
        verify(mockCall).enqueue(callbackCaptor.capture())

        // Simular una respuesta exitosa del servidor
        val responseBody = Courses(1,"dada","asdad",false)
        val response = Response.success(responseBody)

        // Llamar al Callback con la respuesta simulada
        callbackCaptor.value.onResponse(mockCall, response)

        // Verificar que se haya realizado alguna acción después de una respuesta exitosa
        // Puedes agregar verificaciones adicionales según tu implementación real
        // Por ejemplo, verifica que se haya llamado a loadAnnouncements
    }

    @Test
    fun testAddCourse_Failure() {
        // Configurar datos de prueba
        val json = JSONObject() // Aquí configura tu objeto JSON como sea necesario

        // Configurar el comportamiento del mockCoursesInterface
        `when`(mockCoursesInterface.createCourse(any(), any()))
            .thenReturn(mockCall)

        // Llamar al método addCourse
        val courseDirectorsFragment = CoursesDirectorFragment() // Debes ajustar la creación del fragmento según tu implementación real
        courseDirectorsFragment.addCourse(json)

        // Verificar que se haya llamado a enqueue en el mockCall con un Callback
        verify(mockCall).enqueue(callbackCaptor.capture())

        // Simular una respuesta de error del servidor
        callbackCaptor.value.onFailure(mockCall, Throwable("Simulated failure"))

        // Verificar que se haya realizado alguna acción después de una respuesta de error
        // Puedes agregar verificaciones adicionales según tu implementación real
        // Por ejemplo, verifica que se haya registrado un mensaje de error en los logs
    }
}