package com.gustavo.agenda.eventDate.domain.repository

import com.gustavo.agenda.common.AgendaEvent
import com.gustavo.agenda.common.repository.AgendaPreferences
import com.gustavo.agenda.eventDate.domain.model.EventDate

class EventDateRepository(
    private val agendaPreferences: AgendaPreferences
) {

    fun getEventByDate(eventDate: EventDate): List<AgendaEvent> {
        return agendaPreferences.getEventsByDay(eventDate)
    }
}