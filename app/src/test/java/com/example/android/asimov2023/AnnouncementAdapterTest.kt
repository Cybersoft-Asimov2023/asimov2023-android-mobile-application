package com.example.android.asimov2023

import com.example.android.asimov2023.retrofit.Model.AnnouncementItem
import junit.framework.TestCase.assertEquals
import org.junit.Test


class AnnouncementItemTest {

    @Test
    fun testAnnouncementItemProperties() {
        // Crear una instancia de AnnouncementItem
        val announcementItem = AnnouncementItem(
            id = 1,
            title = "Sample Announcement",
            description = "This is a test announcement."
        )

        // Probar las propiedades de la instancia
        assertEquals(1, announcementItem.id)
        assertEquals("Sample Announcement", announcementItem.title)
        assertEquals("This is a test announcement.", announcementItem.description)
    }

    @Test
    fun testAnnouncementItemEquality() {
        val announcementItem1 = AnnouncementItem(
            id = 1,
            title = "Sample Announcement",
            description = "This is a test announcement."
        )

        val announcementItem2 = AnnouncementItem(
            id = 1,
            title = "Sample Announcement",
            description = "This is a test announcement."
        )

        // Verificar que dos instancias con los mismos valores sean iguales
        assertEquals(announcementItem1, announcementItem2)
    }
}
