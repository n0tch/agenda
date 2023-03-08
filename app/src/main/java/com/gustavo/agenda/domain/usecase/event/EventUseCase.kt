package com.gustavo.agenda.domain.usecase.event

import com.gustavo.agenda.domain.model.AgendaEvent
import com.gustavo.agenda.common.mapper.Result
import com.gustavo.agenda.domain.model.EventDate
import com.gustavo.agenda.data.repository.EventRepository
import com.gustavo.agenda.data.transformation.AgendaEventMapper
import com.gustavo.agenda.data.exception.InvalidEventDateException

class EventUseCase(
    private val eventRepository: EventRepository,
    private val mapper: AgendaEventMapper
) {

    fun saveEvent(
        eventDate: EventDate,
        eventName: String,
        eventDescription: String,
        hour: Int,
        minute: Int,
    ): Result<AgendaEvent> = try {
        val agendaEvent = AgendaEvent(
            eventDate = mapper.mapToEventDateDomain(eventDate.year, eventDate.month, eventDate.day),
            eventTime = mapper.mapToEventTimeDomain(hour, minute),
            eventDetail = mapper.mapToEventDetailDomain(eventName, eventDescription)
        )
        eventRepository.saveEvent(agendaEvent)
        Result.Success(agendaEvent)
    } catch (exception: InvalidEventDateException) {
        Result.Error(exception)
    } catch (exception: Exception) {
        Result.Error(exception)
    }

    fun getEventsByEventDate(year: Int, month: Int, day: Int): Result<List<AgendaEvent>> = try {
        val eventDate = EventDate(year, month, day)
        Result.Success(eventRepository.getEventByDate(eventDate))
    } catch (exception: Exception) {
        Result.Error(exception)
    }

    fun dateToDomain(year: Int?, month: Int?, day: Int?): Result<EventDate> = try {
        val eventDate = mapper.mapToEventDateDomain(year, month, day)
        Result.Success(eventDate)
    } catch (exception: InvalidEventDateException) {
        Result.Error(exception)
    } catch (exception: Exception) {
        Result.Error(exception)
    }

}