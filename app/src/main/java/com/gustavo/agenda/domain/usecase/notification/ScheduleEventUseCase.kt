package com.gustavo.agenda.domain.usecase.notification

import com.gustavo.agenda.domain.model.AgendaEvent
import com.gustavo.agenda.common.mapper.Result
import com.gustavo.agenda.domain.model.EventDate
import com.gustavo.agenda.data.repository.NotificationRepository
import com.gustavo.agenda.data.transformation.AgendaEventMapper

class ScheduleEventUseCase(
    private val notificationRepository: NotificationRepository,
    private val mapper: AgendaEventMapper
) {

    operator fun invoke(
        dateSelected: EventDate,
        eventName: String,
        eventDescription: String,
        hour: Int,
        minute: Int,
    ): Result<AgendaEvent> = try {
        val eventDate = mapper.mapToEventDateDomain(dateSelected.year, dateSelected.month, dateSelected.day)
        val eventTime = mapper.mapToEventTimeDomain(hour, minute)
        val eventDetail = mapper.mapToEventDetailDomain(eventName, eventDescription)

        val agendaEvent = mapper.mapToAgendaEventDomain(eventDate, eventTime, eventDetail)

        val scheduled = notificationRepository.scheduleEvent(agendaEvent)
        Result.Success(agendaEvent)
    } catch (exception: Exception){
        Result.Error(exception)
    }

}
