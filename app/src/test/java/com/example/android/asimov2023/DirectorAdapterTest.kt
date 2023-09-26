package com.example.android.asimov2023

import com.example.android.asimov2023.retrofit.Model.DirectorItem
import junit.framework.TestCase.assertEquals
import org.junit.Test

class DirectorItemTest {

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
