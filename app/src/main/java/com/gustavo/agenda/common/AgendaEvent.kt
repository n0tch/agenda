package com.gustavo.agenda.common

import com.gustavo.agenda.eventDate.domain.model.EventDate
import com.gustavo.agenda.eventDate.domain.model.EventTime
import com.gustavo.agenda.eventDetail.domain.model.EventDetail

data class AgendaEvent(
    val eventDate: EventDate,
    val eventTime: EventTime,
    val eventDetail: EventDetail
)
