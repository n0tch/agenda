package com.gustavo.agenda.eventDate.business

import com.gustavo.agenda.common.AgendaEvent
import com.gustavo.agenda.common.mapper.*
import com.gustavo.agenda.eventDate.domain.model.EventDate
import com.gustavo.agenda.eventDate.domain.repository.EventDateRepository

class EventDateInteractor(
    private val eventDateRepository: EventDateRepository
) {

    fun getAgendaEventByDate(year: Int, month: Int, day: Int): Result<List<AgendaEvent>> = try {
        val eventDate = EventDate(year, month, day)
        Result.Success(eventDateRepository.getEventByDate(eventDate))
    } catch (exception: Exception) {
        Result.Error(exception)
    }
}