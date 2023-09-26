import com.example.android.asimov2023.retrofit.Model.CompetenceItem
import junit.framework.TestCase.assertEquals
import org.junit.Test


class CompetenceItemTest {

    @Test
    fun testReadCompetenceItemData() {
        // Valores iniciales
        val initialId = 1
        val initialTitle = "Programming"
        val initialDescription = "Software development skills"

        // Crear una instancia de CompetenceItem
        val competenceItem = CompetenceItem(
            id = initialId,
            title = initialTitle,
            description = initialDescription
        )

        // Leer los datos de la instancia
        val id = competenceItem.id
        val title = competenceItem.title
        val description = competenceItem.description

        // Verificar si los datos se leen correctamente
        assertEquals(initialId, id)
        assertEquals(initialTitle, title)
        assertEquals(initialDescription, description)
    }
}
