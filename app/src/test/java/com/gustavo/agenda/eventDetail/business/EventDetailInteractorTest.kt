package com.gustavo.agenda.eventDetail.business

import com.gustavo.agenda.common.AgendaEvent
import com.gustavo.agenda.common.mapper.Result
import com.gustavo.agenda.eventDate.domain.model.EventDate
import com.gustavo.agenda.eventDate.domain.model.EventTime
import com.gustavo.agenda.eventDetail.domain.AgendaEventMapper
import com.gustavo.agenda.eventDetail.domain.exception.InvalidEventDateException
import com.gustavo.agenda.eventDetail.domain.model.EventDetail
import com.gustavo.agenda.eventDetail.domain.repository.EventDetailRepository
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class EventDetailInteractorTest {

    private val eventRepository = mockk<EventDetailRepository>()
    private val agendaEventMapper = mockk<AgendaEventMapper>()
    val eventDetailInteractor = EventDetailInteractor(eventRepository, agendaEventMapper)

    @Nested
    @DisplayName("Given A Domain Test Mappers")
    inner class GivenADomainTestMappers {

        @Test
        fun `WHEN request to map time values into event time it SHOULD return eventTime with  correct values`() {
            val hour = 13
            val minute = 59
            val second = 0

            val eventTime = EventTime(hour, minute, second)

            every { agendaEventMapper.mapToEventTimeDomain(hour, minute, second) } returns eventTime

            val output = eventDetailInteractor.timeToDomain(hour, minute)

            assertEquals(hour, output.hour)
            assertEquals(minute, output.minute)
            assertEquals(second, output.second)
        }

        @Test
        fun `WHEN request to map date values into event date it SHOULD return eventDate with correct values`() {
            val day = 1
            val month = 2
            val year = 2023

            val eventDate = EventDate(year, month, day)

            every { agendaEventMapper.mapToEventDateDomain(year, month, day) } returns eventDate

            val output = eventDetailInteractor.dateToDomain(year, month, day) as Result.Success

            assertEquals(year, output.data.year)
            assertEquals(month, output.data.month)
            assertEquals(day, output.data.day)
        }


        @Test
        fun `WHEN day is null it SHOULD throw InvalidEventDateException`() {
            val year = 2023
            val month = 2
            val exception = InvalidEventDateException()

            every { agendaEventMapper.mapToEventDateDomain(year, month, null) } throws exception
            val output = eventDetailInteractor.dateToDomain(year, month, null) as Result.Error

            assertTrue(output.exception is InvalidEventDateException)
        }

        @Test
        fun `WHEN month is null it SHOULD throw InvalidEventDateException`() {
            val year = 2023
            val day = 2
            val exception = InvalidEventDateException()

            every { agendaEventMapper.mapToEventDateDomain(year, null, day) } throws exception
            val output = eventDetailInteractor.dateToDomain(year, null, day) as Result.Error

            assertTrue(output.exception is InvalidEventDateException)
        }

        @Test
        fun `WHEN year is null it SHOULD throw InvalidEventDateException`() {
            val month = 12
            val day = 2
            val exception = InvalidEventDateException()

            every { agendaEventMapper.mapToEventDateDomain(null, month, day) } throws exception
            val output = eventDetailInteractor.dateToDomain(null, month, day) as Result.Error

            assertTrue(output.exception is InvalidEventDateException)
        }

        @Test
        fun `WHEN request to map event detail it SHOULD return a EventDetail with correct values`() {
            val name = "name"
            val description = "description"
            val eventDetail = EventDetail(name, description)

            every {
                agendaEventMapper.mapToEventDetailDomain(
                    name,
                    description
                )
            } returns eventDetail
            val output = eventDetailInteractor.infoToEventDetailDomain(name, description)

            assertEquals(name, output.name)
            assertEquals(description, output.description)
        }
    }

    @Nested
    @DisplayName("Given A Schedule Event Scenario")
    inner class GivenAScheduleEventScenario {
        private val year = 2023
        private val month = 3
        private val day = 2
        private val hour = 12
        private val minute = 12
        private val second = 0

        private val eventDate = EventDate(year, month, day)
        private val eventTime = EventTime(hour, minute, second)
        private val eventDetail = EventDetail("Event NAme", "Event Description")
        private val agendaEvent = AgendaEvent(
            eventDate, eventTime, eventDetail
        )

        @BeforeEach
        fun setup(){
            every {
                agendaEventMapper.mapToAgendaEventDomain(
                    eventDate,
                    eventTime,
                    eventDetail
                )
            } returns agendaEvent
        }

        @Test
        fun `WHEN request to schedule event it SHOULD map values into agendaEvent and call schedule Event from repository`() {
            every { eventRepository.scheduleEvent(agendaEvent) } just Runs

            val output =
                eventDetailInteractor.scheduleEvent(
                    agendaEvent.eventDate,
                    agendaEvent.eventTime,
                    agendaEvent.eventDetail
                ) as Result.Success

            assertEquals(eventDate, output.data.eventDate)
            assertEquals(eventTime, output.data.eventTime)
            assertEquals(eventDetail, output.data.eventDetail)
        }

        @Test
        fun `WHEN SHOULD`(){
            every {  }
        }
    }
}