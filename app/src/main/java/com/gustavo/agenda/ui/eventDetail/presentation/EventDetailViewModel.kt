package com.gustavo.agenda.ui.eventDetail.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gustavo.agenda.domain.model.AgendaEvent
import com.gustavo.agenda.common.mapper.Result
import com.gustavo.agenda.domain.model.EventDate
import com.gustavo.agenda.domain.usecase.event.EventUseCase
import com.gustavo.agenda.domain.usecase.notification.ScheduleEventUseCase
import kotlinx.coroutines.*

class EventDetailViewModel(
    private val scheduleEventUseCase: ScheduleEventUseCase,
    private val eventUseCase: EventUseCase
) : ViewModel() {

    private val eventDetailState by lazy { MutableLiveData<EventDetailState>() }
    private lateinit var eventDate: EventDate

    fun getEventDetailState(): LiveData<EventDetailState> = eventDetailState

    fun updateSelectedDate(year: Int?, month: Int?, day: Int?) {
        val eventDateResult = eventUseCase.dateToDomain(year, month, day)
        handleEventDate(eventDateResult)
    }

    fun scheduleEvent(eventName: String, eventDescription: String, hour: Int, minute: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val scheduleResult =
                scheduleEventUseCase(eventDate, eventName, eventDescription, hour, minute)

            withContext(Dispatchers.Main) {
                handleEventScheduled(scheduleResult)
            }
        }
    }

    fun saveEvent(eventName: String, eventDescription: String, hour: Int, minute: Int) {
        viewModelScope.launch(Dispatchers.IO) {

            val saveEventResult =
                eventUseCase.saveEvent(eventDate, eventName, eventDescription, hour, minute)

            withContext(Dispatchers.Main) {
                handleSaveEventResult(saveEventResult)
            }
        }
    }

    private fun handleEventDate(eventDateResult: Result<EventDate>) = when (eventDateResult) {
        is Result.Success -> with(eventDateResult.data) {
            eventDetailState.value = EventDetailState.DateSelected(eventDateResult.data)
            eventDate = eventDateResult.data
        }
        is Result.Error -> {
            eventDetailState.value = EventDetailState.InvalidDateSelected(eventDateResult.exception)
        }
    }

    private fun handleEventScheduled(result: Result<AgendaEvent>) =
        when (result) {
            is Result.Success -> {
                eventDetailState.value = EventDetailState.EventScheduled
            }
            is Result.Error -> {
                eventDetailState.value = EventDetailState.ErrorOnScheduleEvent(result.exception)
            }
        }

    private fun handleSaveEventResult(saveEventResult: Result<AgendaEvent>) =
        when (saveEventResult) {
            is Result.Success -> {
                eventDetailState.value = EventDetailState.EventSaved(saveEventResult.data)
            }
            is Result.Error -> {
                eventDetailState.value =
                    EventDetailState.InvalidDateSelected(saveEventResult.exception)
            }
        }
}