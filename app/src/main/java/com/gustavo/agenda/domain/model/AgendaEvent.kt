package com.gustavo.agenda.domain.model

data class AgendaEvent(
    val eventDate: EventDate,
    val eventTime: EventTime,
    val eventDetail: EventDetail
)
