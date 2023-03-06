package com.gustavo.agenda.eventDate.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gustavo.agenda.common.AgendaEvent
import com.gustavo.agenda.common.mapper.Result
import com.gustavo.agenda.eventDate.business.EventDateInteractor
import com.gustavo.agenda.eventDate.domain.model.EventDate
import java.util.*

class EventDateViewModel(
    private val eventDateInteractor: EventDateInteractor
): ViewModel() {

    private val state by lazy { MutableLiveData<EventDateState>() }

    fun getEventDateState(): LiveData<EventDateState> = state

    fun getEvents(year: Int, month: Int, day: Int){
        val result = eventDateInteractor.getAgendaEventByDate(year, month, day)

        handleAgendaEvents(result)
    }

    private fun handleAgendaEvents(result: Result<List<AgendaEvent>>) = when(result){
        is Result.Error ->
            state.value = EventDateState.ErrorLoadingAgendaEvents(result.exception)
        is Result.Success ->
            state.value = EventDateState.AgendaEvents(result.data)
    }

    fun getCurrentDate(): EventDate {
        val calendar = Calendar.getInstance()

        return EventDate(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    fun updateSelectedDate(year: Int, month: Int, day: Int) {
        state.value = EventDateState.DateSelected(year, month, day)
    }
}
