package com.gustavo.agenda.eventDetail.business

import com.gustavo.agenda.common.AgendaEvent
import com.gustavo.agenda.common.mapper.Result
import com.gustavo.agenda.eventDate.domain.model.EventDate
import com.gustavo.agenda.eventDate.domain.model.EventTime
import com.gustavo.agenda.eventDetail.domain.AgendaEventMapper
import com.gustavo.agenda.eventDetail.domain.exception.EventSaveException
import com.gustavo.agenda.eventDetail.domain.exception.InvalidEventDateException
import com.gustavo.agenda.eventDetail.domain.model.EventDetail
import com.gustavo.agenda.eventDetail.domain.repository.EventDetailRepository

class EventDetailInteractor(
    private val eventDetailRepository: EventDetailRepository,
    private val agendaEventMapper: AgendaEventMapper
) {

    fun scheduleEvent(
        dateSelected: EventDate,
        eventName: EventTime,
        eventDescription: EventDetail
    ): Result<AgendaEvent> = try {
        val agendaEvent = agendaEventMapper
            .mapToAgendaEventDomain(dateSelected, eventName, eventDescription)

        eventDetailRepository.scheduleEvent(agendaEvent)
        eventDetailRepository.saveEvent(agendaEvent)
        Result.Success(agendaEvent)
    } catch (exception: Exception){
        Result.Error(EventSaveException())
    }

    fun dateToDomain(year: Int?, month: Int?, day: Int?): Result<EventDate> = try {
        val eventDate = agendaEventMapper.mapToEventDateDomain(year, month, day)
        Result.Success(eventDate)
    } catch (exception: InvalidEventDateException){
        Result.Error(exception)
    }catch (exception: Exception){
        Result.Error(exception)
    }

    fun timeToDomain(hour: Int, minute: Int): EventTime = agendaEventMapper
        .mapToEventTimeDomain(hour, minute, 0)

    fun infoToEventDetailDomain(
        eventName: String,
        eventDescription: String
    ): EventDetail =
        agendaEventMapper.mapToEventDetailDomain(eventName, eventDescription)
}
