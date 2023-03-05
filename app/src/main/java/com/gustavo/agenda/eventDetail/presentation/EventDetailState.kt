package com.gustavo.agenda.eventDetail.presentation

sealed class EventDetailState {
    class DateSelected(val day: Int, val month: Int, val year: Int): EventDetailState()

}
