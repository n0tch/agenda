package com.gustavo.agenda.ui.eventDetail.presentation

import com.gustavo.agenda.domain.model.AgendaEvent
import com.gustavo.agenda.domain.model.EventDate

sealed class EventDetailState {
    class DateSelected(val eventDate: EventDate): EventDetailState()
    class ErrorOnSave(exception: Exception): EventDetailState()
    class InvalidDateSelected(exception: Exception): EventDetailState()
    object EventScheduled: EventDetailState()
    class ErrorOnScheduleEvent(exception: Exception): EventDetailState()
    class EventSaved(val agendaEvent: AgendaEvent): EventDetailState()
}
