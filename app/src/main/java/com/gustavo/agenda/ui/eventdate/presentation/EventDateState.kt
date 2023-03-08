package com.gustavo.agenda.ui.eventdate.presentation

import com.gustavo.agenda.domain.model.AgendaEvent

sealed class EventDateState {
    class DateSelected(val year: Int, val month: Int, val day: Int): EventDateState()
    class AgendaEvents(val agendaEvents: List<AgendaEvent>): EventDateState()
    class ErrorLoadingAgendaEvents(val exception: Exception): EventDateState()
}
