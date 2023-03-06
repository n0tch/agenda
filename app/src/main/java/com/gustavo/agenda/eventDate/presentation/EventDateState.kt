package com.gustavo.agenda.eventDate.presentation

import com.gustavo.agenda.common.AgendaEvent

sealed class EventDateState {
    class DateSelected(val year: Int, val month: Int, val day: Int): EventDateState()
    class AgendaEvents(val agendaEvents: List<AgendaEvent>): EventDateState()
    class ErrorLoadingAgendaEvents(val exception: Exception): EventDateState()
}
