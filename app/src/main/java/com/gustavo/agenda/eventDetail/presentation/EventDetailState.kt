package com.gustavo.agenda.eventDetail.presentation

import com.gustavo.agenda.common.AgendaEvent
import com.gustavo.agenda.eventDate.domain.model.EventDate

sealed class EventDetailState {
    class DateSelected(val eventDate: EventDate): EventDetailState()
    class ErrorOnSave(exception: Exception): EventDetailState()
    class InvalidDateSelected(exception: Exception): EventDetailState()
    class EventSaved(val agendaEvent: AgendaEvent): EventDetailState()
}
