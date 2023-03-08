package com.gustavo.agenda.domain.usecase.calendar

import com.gustavo.agenda.domain.model.EventDate
import java.util.Calendar

class CalendarUseCase {

    fun getCurrentEventDate(): EventDate {
        val calendar = Calendar.getInstance()

        return EventDate(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }
}