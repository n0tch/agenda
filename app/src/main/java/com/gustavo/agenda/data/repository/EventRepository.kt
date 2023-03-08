package com.gustavo.agenda.data.repository

import com.gustavo.agenda.domain.model.AgendaEvent
import com.gustavo.agenda.domain.model.EventDate

interface EventRepository {
    fun saveEvent(agendaEvent: AgendaEvent)

    fun getEventByDate(eventDate: EventDate): List<AgendaEvent>
}