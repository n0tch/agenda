package com.gustavo.agenda.data.datasource

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.gustavo.agenda.data.datasource.AgendaPreferences.Companion.AGENDA_EVENT_SHARED_KEY
import com.gustavo.agenda.data.datasource.AgendaPreferences.Companion.PREFERENCE_FILE_NAME
import com.gustavo.agenda.domain.model.AgendaEvent
import com.gustavo.agenda.domain.model.EventDate
import com.gustavo.agenda.mocks.AgendaEventMock
import com.gustavo.agenda.mocks.EventDateMock
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class AgendaPreferencesTest {

    private val context = mockk<Context>()
    private val gson = mockk<Gson>()

    private val agendaPreferences = AgendaPreferences(context, gson)
    private val sharedPreferences = mockk<SharedPreferences>()
    private val editor = mockk<SharedPreferences.Editor>(relaxed = true)

    @BeforeEach
    fun setup() {
        every {
            context.getSharedPreferences(PREFERENCE_FILE_NAME, Context.MODE_PRIVATE)
        } returns sharedPreferences

        every { sharedPreferences.edit() } returns editor

    }

    @Test
    fun `WHEN get save agendaEvent List it SHOULD return correct list value`() {
        val eventDate = EventDateMock.getDummyEventDate()
        val agendaEventList = AgendaEventMock.getDummyList()
        val dummyJson = ""

        val agendaEventKey = buildAgendaSharedKey(eventDate)

        every {
            sharedPreferences.getString(agendaEventKey, "")
        } returns dummyJson

        every {
            gson.fromJson<List<AgendaEvent>?>("", object : TypeToken<List<AgendaEvent>>() {}.type)
        } returns agendaEventList

        val output = agendaPreferences.getEventsByDay(eventDate)

        verify {
            sharedPreferences.getString(agendaEventKey, "")
        }

        assertEquals(agendaEventList, output)
    }

    @Test
    fun `WHEN build shared file entry it SHOULD add year, month and day to the end of it separated by underlines`() {
        val agendaEvent = AgendaEventMock.getDummyAgendaEvent()
        val agendaEventKey = AGENDA_EVENT_SHARED_KEY + listOf(
            agendaEvent.eventDate.year,
            agendaEvent.eventDate.month,
            agendaEvent.eventDate.day
        ).joinToString(prefix = "_", separator = "_")

        val output = agendaPreferences.getPreferenceKey(agendaEvent.eventDate)

        assertEquals(output, agendaEventKey)
    }

    private fun buildAgendaSharedKey(eventDate: EventDate) = AGENDA_EVENT_SHARED_KEY + listOf(
        eventDate.year,
        eventDate.month,
        eventDate.day
    ).joinToString(prefix = "_", separator = "_")
}