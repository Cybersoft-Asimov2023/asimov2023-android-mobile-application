import androidx.fragment.app.FragmentManager
import com.example.android.asimov2023.retrofit.Model.TeacherItem
import org.junit.Test
import org.junit.Assert.assertEquals

class TeacherAdapterTest {

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