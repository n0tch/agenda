package com.gustavo.agenda.data.datasource

import com.gustavo.agenda.mocks.AgendaEventMock
import io.mockk.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class EventDataSourceTest {

    private val agendaPreferences = mockk<AgendaPreferences>()
    private val eventDataSource = EventDataSource(agendaPreferences)

    @Test
    fun `WHEN data source save agenda event it SHOULD call agenda preference correct method`() {
        val agendaEvent = AgendaEventMock.getDummyAgendaEvent()

        every { agendaPreferences.saveEvent(agendaEvent) } just runs
        eventDataSource.saveEvent(agendaEvent)

        verify { agendaPreferences.saveEvent(agendaEvent) }
    }

    @Test
    fun `WHEN exception SHOULD`(){
        val agendaEvent = AgendaEventMock.getDummyAgendaEvent()
        val exception = IndexOutOfBoundsException()

        every { agendaPreferences.saveEvent(agendaEvent) } throws exception

        assertThrows<IndexOutOfBoundsException> { eventDataSource.saveEvent(agendaEvent) }
    }
}
