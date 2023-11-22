import androidx.fragment.app.FragmentManager
import com.example.android.asimov2023.retrofit.Model.TeacherItem
import com.example.android.asimov2023.retrofit.RetrofitClient
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class TeacherAdapterTest {

    //este token expira, se debe generar otro (postman->sing-in)
    var generated_token
            = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRlcnNvbkBnbWFpbC5jb20iLCJpYXQiOjE3MDA1ODMxMTksImV4cCI6MTcwMTE4NzkxOX0.OfSmLYRHczEfaUeyM6ibFybQD-eYSXx_NtQ8PYtfudE"
    //US012 - Visualizar perfil de docente
    @Test
    fun getTeacherbyId() = runBlocking{

            val teachersInterface = RetrofitClient.getTeachersInterface()
            val teacherId = 1
            val response = teachersInterface.getTeacher(generated_token, teacherId).execute()

            Assert.assertTrue(response.isSuccessful)

            val responseBody = response.body()
            Assert.assertNotNull(responseBody)
            Assert.assertEquals(1, responseBody?.id)
            Assert.assertEquals("Cristhian", responseBody?.first_name)
            Assert.assertEquals("Gomez", responseBody?.last_name)
            Assert.assertEquals(0, responseBody?.point)
            Assert.assertEquals(35, responseBody?.age)
            Assert.assertEquals("cris@gmail.com", responseBody?.email)
            Assert.assertEquals("123456789", responseBody?.phone)
            Assert.assertEquals(2, responseBody?.director_id)
            Assert.assertEquals(listOf("ROLE_TEACHER"), responseBody?.roles)
    }

    //US005 - Listar a los docentes
    @Test
    fun testListTeachers() {
        val directorsInterface = RetrofitClient.getTeachersInterface()
        val directorId = 1

        val response = directorsInterface.getTeachers(generated_token, directorId).execute()
        if (response.isSuccessful){
            val responseBody = response.body()
            Assert.assertNotNull(responseBody)
        }
    }



    @Test
    fun testGetItemCount() {
        val teachersList = listOf(
            TeacherItem(
                id = 1,
                age = 30,
                email = "correo@example.com",
                first_name = "Nombre1",
                last_name = "Apellido2",
                password = "contraseña",
                phone = "123456789",
                point = 100,
                roles = listOf("ROLE_TEACHER"),
                token = "token",
                director_id = 2
            ),
            TeacherItem(
                id = 2,
                age = 20,
                email = "correo@example.com",
                first_name = "Nombre2",
                last_name = "Apellido2",
                password = "contraseña",
                phone = "123456789",
                point = 100,
                roles = listOf("ROLE_TEACHER"),
                token = "token",
                director_id = 2
            )
        )

        // Crear una instancia del adaptador utilizando la lista de maestros de ejemplo
        val adapter = TeacherAdapter(teachersList, MockFragmentManager())

        // Llamar a la función getItemCount para obtener la cantidad de elementos en el adaptador
        val itemCount = adapter.itemCount

        // Verificar que la cantidad de elementos en el adaptador sea igual a la cantidad de maestros en la lista
        assertEquals(teachersList.size, itemCount)
    }
}

class MockFragmentManager : FragmentManager() {
}