package com.gustavo.agenda.eventDetail.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gustavo.agenda.eventDate.model.EventDate
import com.gustavo.agenda.eventDetail.domain.repository.EventRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EventDetailViewModel(
    private val eventRepository: EventRepository
): ViewModel() {

    private val eventDetailState by lazy { MutableLiveData<EventDetailState>() }

    fun saveEventDate(day: Int?, month: Int?, year: Int?) {
        eventDetailState.value = EventDetailState.DateSelected(
            day = day ?: EventDate.UNDEFINED_DATE,
            month = month ?: EventDate.UNDEFINED_DATE,
            year = year ?: EventDate.UNDEFINED_DATE
        )
    }

    fun getEventDetailState(): LiveData<EventDetailState> = eventDetailState

    fun saveEventDetail(eventName: String, eventDetail: String, hour: Int, minute: Int){
        viewModelScope.launch(Dispatchers.IO){
            eventRepository.saveEvent(
                eventDetailState.value as EventDetailState.DateSelected,
                eventName,
                eventDetail,
                hour,
                minute
            )
        }
    }
}