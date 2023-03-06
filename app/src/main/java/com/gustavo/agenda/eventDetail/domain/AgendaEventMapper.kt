package com.gustavo.agenda.eventDetail.domain

import com.gustavo.agenda.common.AgendaEvent
import com.gustavo.agenda.eventDate.domain.model.EventDate
import com.gustavo.agenda.eventDate.domain.model.EventTime
import com.gustavo.agenda.eventDetail.domain.exception.InvalidEventDateException
import com.gustavo.agenda.eventDetail.domain.model.EventDetail

class AgendaEventMapper {
    fun mapToAgendaEventDomain(
        eventDate: EventDate,
        eventTime: EventTime,
        eventDetail: EventDetail
    ) = AgendaEvent(eventDate, eventTime, eventDetail)

    fun mapToEventDateDomain(year: Int?, month: Int?, day: Int?): EventDate {
        if(year != null && month != null && day != null){
            return EventDate(year, month, day)
        } else {
            throw InvalidEventDateException()
        }
    }

    fun mapToEventTimeDomain(hour: Int, minute: Int, second: Int) = EventTime(hour, minute, second)

    fun mapToEventDetailDomain(eventName: String, eventDescription: String): EventDetail =
        EventDetail(eventName, eventDescription)
}