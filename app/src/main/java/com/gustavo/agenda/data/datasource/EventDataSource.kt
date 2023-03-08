package com.gustavo.agenda.data.datasource

import com.gustavo.agenda.domain.model.AgendaEvent
import com.gustavo.agenda.common.repository.AgendaPreferences
import com.gustavo.agenda.domain.model.EventDate
import com.gustavo.agenda.data.repository.EventRepository

class EventDataSource(
    private val agendaPreferences: AgendaPreferences
): EventRepository {
    override fun saveEvent(agendaEvent: AgendaEvent) =
        agendaPreferences.saveEvent(agendaEvent)

    override fun getEventByDate(eventDate: EventDate): List<AgendaEvent> =
        agendaPreferences.getEventsByDay(eventDate)
}
