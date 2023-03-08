package com.gustavo.agenda.ui.eventdate.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gustavo.agenda.domain.model.AgendaEvent
import com.gustavo.agenda.common.mapper.Result
import com.gustavo.agenda.domain.model.EventDate
import com.gustavo.agenda.domain.usecase.calendar.CalendarUseCase
import com.gustavo.agenda.domain.usecase.event.EventUseCase

class EventDateViewModel(
    private val eventUseCase: EventUseCase,
    private val calendarUseCase: CalendarUseCase
): ViewModel() {

    private val state by lazy { MutableLiveData<EventDateState>() }

    fun getEventDateState(): LiveData<EventDateState> = state

    fun getEvents(year: Int, month: Int, day: Int){
        val events = eventUseCase.getEventsByEventDate(year, month, day)
        handleAgendaEvents(events)
    }

    private fun handleAgendaEvents(result: Result<List<AgendaEvent>>) = when(result){
        is Result.Error ->
            state.value = EventDateState.ErrorLoadingAgendaEvents(result.exception)
        is Result.Success ->
            state.value = EventDateState.AgendaEvents(result.data)
    }

    fun getCurrentDate(): EventDate = calendarUseCase.getCurrentEventDate()

    fun updateSelectedDate(year: Int, month: Int, day: Int) {
        state.value = EventDateState.DateSelected(year, month, day)
    }
}
