package com.gustavo.agenda.data.transformation

import com.gustavo.agenda.domain.model.AgendaEvent
import java.util.Calendar

fun AgendaEvent.getDateInMillis() : Long {
    return Calendar.getInstance().apply {
        set(
            eventDate.year,
            eventDate.month,
            eventDate.day,
            eventTime.hour,
            eventTime.minute,
            eventTime.second
        )
    }.timeInMillis
}