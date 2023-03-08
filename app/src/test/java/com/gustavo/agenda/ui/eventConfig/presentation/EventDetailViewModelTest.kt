package com.gustavo.agenda.ui.eventConfig.presentation

import androidx.lifecycle.Observer
import com.gustavo.agenda.LiveDataTest
import com.gustavo.agenda.common.mapper.Result
import com.gustavo.agenda.data.exception.InvalidEventDateException
import com.gustavo.agenda.domain.usecase.event.EventUseCase
import com.gustavo.agenda.domain.usecase.notification.ScheduleEventUseCase
import com.gustavo.agenda.mocks.EventDateMock
import io.mockk.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

internal class EventDetailViewModelTest: LiveDataTest() {

    private val scheduleEventUseCase = mockk<ScheduleEventUseCase>()
    private val eventUseCase = mockk<EventUseCase>()

    private val eventDateSelectedSlot = slot<EventDetailState.DateSelected>()
    private val invalidDateSelectedSlot = slot<EventDetailState.InvalidDateSelected>()
    private val eventDetailObserver = mockk<Observer<EventDetailState>>(relaxed = true)
    private val eventDetailViewModel = EventDetailViewModel(scheduleEventUseCase, eventUseCase)

    @BeforeEach
    fun setup(){
        eventDetailViewModel.getEventDetailState().observeForever(eventDetailObserver)
    }

    @AfterEach
    fun tearDown(){
        eventDetailViewModel.getEventDetailState().removeObserver(eventDetailObserver)
    }

    @Nested
    @DisplayName("Given update selected date Scenario")
    inner class GivenUpdateOnSelectedDateScenario {

        @Test
        fun `WHEN set selected date to live data it SHOULD update date state with selected date`(){
            val eventDate = EventDateMock.getDummyEventDate()

            every { eventUseCase.dateToDomain(eventDate.year, eventDate.month, eventDate.day) } returns Result.Success(eventDate)

            eventDetailViewModel.updateSelectedDate(eventDate.year, eventDate.month, eventDate.day)

            verify {
                eventDetailObserver.onChanged(capture(eventDateSelectedSlot))
            }

            assertEquals(eventDate.year, eventDateSelectedSlot.captured.eventDate.year)
            assertEquals(eventDate.month, eventDateSelectedSlot.captured.eventDate.month)
            assertEquals(eventDate.day, eventDateSelectedSlot.captured.eventDate.day)
        }

        @Test
        fun `WHEN update date with invalid values it SHOULD update invalid Date Selected Slot`(){
            val exception = InvalidEventDateException()

            every { eventUseCase.dateToDomain(null, null, null) } returns Result.Error(exception)

            eventDetailViewModel.updateSelectedDate(null, null, null)

            verify {
                eventDetailObserver.onChanged(capture(invalidDateSelectedSlot))
            }
        }

        @Test
        fun `WHEN SHOULD`(){

        }
    }

}