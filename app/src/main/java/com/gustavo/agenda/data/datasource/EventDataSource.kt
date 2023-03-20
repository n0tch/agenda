package com.gustavo.agenda.data.datasource

import com.gustavo.agenda.data.repository.EventRepository
import com.gustavo.agenda.domain.model.AgendaEvent
import com.gustavo.agenda.domain.model.EventDate

class EventDataSource(
    private val agendaPreferences: AgendaPreferences
): EventRepository {
    override fun saveEvent(agendaEvent: AgendaEvent) =
        agendaPreferences.saveEvent(agendaEvent)

    override fun getEventByDate(eventDate: EventDate): List<AgendaEvent> =
        agendaPreferences.getEventsByDay(eventDate)
}
