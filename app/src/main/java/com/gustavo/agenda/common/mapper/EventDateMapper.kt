package com.gustavo.agenda.common.mapper

import com.gustavo.agenda.common.AgendaEvent
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