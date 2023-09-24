package com.example.android.asimov2023
import android.content.Context
import androidx.fragment.app.testing.FragmentScenario
import org.junit.Test
import org.mockito.Mock
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.android.asimov2023.retrofit.Interface.CompetencesInterface
import com.example.android.asimov2023.retrofit.Model.CompetenceItem
import com.example.android.asimov2023.ui.main.CourseDetailsFragment
import org.json.simple.JSONObject
import org.junit.Before
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class CourseDetailsFragmentTest2 {

    private lateinit var courseDetailsFragment: CourseDetailsFragment

    @Mock
    private lateinit var mockAnnouncementInterface: CompetencesInterface

    @Mock
    private lateinit var mockCall: Call<CompetenceItem>

    @Captor
    private lateinit var callbackCaptor: ArgumentCaptor<Callback<CompetenceItem>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        // Configura el escenario del fragmento con un contexto simulado
        val scenario = FragmentScenario.launchInContainer(CourseDetailsFragment::class.java)
        scenario.onFragment { fragment ->
            // Aquí puedes interactuar con el fragmento y proporcionar un contexto simulado si es necesario
            // Por ejemplo, puedes establecer una preferencia compartida simulada:
            val sharedPreferences = fragment.requireContext().getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
            sharedPreferences.edit().putString("token", "your_fake_token").apply()
        }
        courseDetailsFragment = CourseDetailsFragment()
    }

    @Test
    fun testAddCompetence() {
        // Configurar datos de prueba
        val cId = 1
        val json = JSONObject()
        json.put("title", "Título de la competencia")
        json.put("description", "Descripción de la competencia")



        // Llamar al método addCompetence
        courseDetailsFragment.addCompetence(cId, json)

        // Verificar que se haya llamado a enqueue en el mockCall con un Callback
        verify(mockCall).enqueue(callbackCaptor.capture())

        // Simular una respuesta exitosa del servidor
        val responseBody = CompetenceItem(1,"Complejidad aritmetica","lorem imp sun")
        val response = Response.success(responseBody)

        // Llamar al Callback con la respuesta simulada
        callbackCaptor.value.onResponse(mockCall, response)

        // Verificar que se haya llamado al método linkCompetenceCourse con los argumentos correctos
        verify(courseDetailsFragment).linkCompetenceCourse(cId, responseBody.id)
    }
}





