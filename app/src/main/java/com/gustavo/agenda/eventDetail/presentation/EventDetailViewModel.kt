package com.gustavo.agenda.eventDetail.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gustavo.agenda.common.AgendaEvent
import com.gustavo.agenda.common.mapper.Result
import com.gustavo.agenda.eventDate.domain.model.EventDate
import com.gustavo.agenda.eventDetail.business.EventDetailInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EventDetailViewModel(
    private val eventDetailInteractor: EventDetailInteractor
): ViewModel() {

    private val eventDetailState by lazy { MutableLiveData<EventDetailState>() }
    private lateinit var eventDate: EventDate

    fun getEventDetailState(): LiveData<EventDetailState> = eventDetailState

    fun saveEventDate(day: Int?, month: Int?, year: Int?) {
        val eventDateResult = eventDetailInteractor.dateToDomain(year, month, day)
        handleEventDate(eventDateResult)
    }

    private fun handleEventDate(eventDateResult: Result<EventDate>) = when(eventDateResult){
        is Result.Success -> with(eventDateResult.data){
            eventDetailState.value = EventDetailState.DateSelected(eventDateResult.data)
            eventDate = eventDateResult.data
        }
        is Result.Error -> {
            eventDetailState.value = EventDetailState.InvalidDateSelected(eventDateResult.exception)
        }
    }

    fun scheduleEvent(eventName: String, eventDescription: String, hour: Int, minute: Int){
        viewModelScope.launch(Dispatchers.IO){
            val eventTime = eventDetailInteractor.timeToDomain(hour, minute)
            val eventDetail = eventDetailInteractor.infoToEventDetailDomain(eventName, eventDescription)

            val result = eventDetailInteractor.scheduleEvent(
                eventDate,
                eventTime,
                eventDetail)
            withContext(Dispatchers.Main){
                handleEventSaved(result)
            }
        }
    }

    private fun handleEventSaved(result: Result<AgendaEvent>) = when(result){
        is Result.Success -> {
            eventDetailState.value = EventDetailState.EventSaved(result.data)
        }
        is Result.Error -> {
            eventDetailState.value = EventDetailState.ErrorOnSave(result.exception)
        }
    }
}