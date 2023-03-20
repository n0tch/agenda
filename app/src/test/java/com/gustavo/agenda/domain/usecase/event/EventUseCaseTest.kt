package com.gustavo.agenda.domain.usecase.event

import com.gustavo.agenda.common.mapper.Result
import com.gustavo.agenda.data.exception.InvalidEventDateException
import com.gustavo.agenda.data.repository.EventRepository
import com.gustavo.agenda.data.transformation.AgendaEventMapper
import com.gustavo.agenda.domain.model.AgendaEvent
import com.gustavo.agenda.mocks.AgendaEventMock
import com.gustavo.agenda.mocks.EventDateMock
import com.gustavo.agenda.mocks.EventDetailMock
import com.gustavo.agenda.mocks.EventTimeMock
import io.mockk.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.io.IOException

internal class EventUseCaseTest {

    private lateinit var eventUseCase: EventUseCase
    private val eventRepository = mockk<EventRepository>()
    private val agendaEventMapper = mockk<AgendaEventMapper>()

    @BeforeEach
    fun setup() {
        eventUseCase = EventUseCase(eventRepository, agendaEventMapper)
    }

    @Nested
    @DisplayName("Given Save Event Scenarios")
    inner class GivenSaveEventScenarios {
        private val eventDate = EventDateMock.getDummyEventDate()
        private val eventTime = EventTimeMock.getDummyTime()
        private val eventDetail = EventDetailMock.getDummyDetail()

        private val agendaEvent = AgendaEvent(eventDate, eventTime, eventDetail)

        @Test
        fun `WHEN save event it SHOULD call event repository save method with correct params`() {
            every {
                agendaEventMapper.mapToEventDateDomain(
                    eventDate.year,
                    eventDate.month,
                    eventDate.day
                )
            } returns eventDate
            every {
                agendaEventMapper.mapToEventTimeDomain(
                    eventTime.hour,
                    eventTime.minute,
                    eventTime.second
                )
            } returns eventTime
            every {
                agendaEventMapper.mapToEventDetailDomain(
                    eventDetail.name,
                    eventDetail.description
                )
            } returns eventDetail
            every { eventRepository.saveEvent(agendaEvent) } just runs

            val eventWasSaved = eventUseCase.saveEvent(
                eventDate,
                eventDetail.name,
                eventDetail.description,
                eventTime.hour,
                eventTime.minute
            ) as Result.Success

            verify { eventRepository.saveEvent(agendaEvent) }
            assertEquals(eventWasSaved.data, agendaEvent)
        }

        @Test
        fun `WHEN save event repository throws exception it SHOULD return result error with correct exception`() {
            val ioException = IOException()

            every {
                agendaEventMapper.mapToEventDateDomain(
                    eventDate.year,
                    eventDate.month,
                    eventDate.day
                )
            } returns eventDate
            every {
                agendaEventMapper.mapToEventTimeDomain(
                    eventTime.hour,
                    eventTime.minute,
                    eventTime.second
                )
            } returns eventTime
            every {
                agendaEventMapper.mapToEventDetailDomain(
                    eventDetail.name,
                    eventDetail.description
                )
            } returns eventDetail
            every { eventRepository.saveEvent(agendaEvent) } throws ioException

            val eventWasSaved = eventUseCase.saveEvent(
                eventDate,
                eventDetail.name,
                eventDetail.description,
                eventTime.hour,
                eventTime.minute
            ) as Result.Error

            verify { eventRepository.saveEvent(agendaEvent) }

            assertTrue(eventWasSaved.exception is IOException)
        }

        @Test
        fun `WHEN pass null date to mapper it SHOULD throw InvalidEventDateException exception`() {
            val exception = InvalidEventDateException()

            every {
                agendaEventMapper.mapToEventDateDomain(
                    eventDate.year,
                    eventDate.month,
                    eventDate.day
                )
            } throws exception
            every {
                agendaEventMapper.mapToEventTimeDomain(
                    eventTime.hour,
                    eventTime.minute,
                    eventTime.second
                )
            } returns eventTime
            every {
                agendaEventMapper.mapToEventDetailDomain(
                    eventDetail.name,
                    eventDetail.description
                )
            } returns eventDetail

            val eventWasSaved = eventUseCase.saveEvent(
                eventDate,
                eventDetail.name,
                eventDetail.description,
                eventTime.hour,
                eventTime.minute
            ) as Result.Error

            assertTrue(eventWasSaved.exception is InvalidEventDateException)
        }
    }

    @Nested
    @DisplayName("Given Some Get Events By Day Scenarios")
    inner class GivenSomeGetEventsByDayScenarios {

        @Test
        fun `WHEN get events by date it SHOULD call repository and return result success with correct list`(){
            val eventDate = EventDateMock.getDummyEventDate()
            val agendaEventList = AgendaEventMock.getDummyList()

            every { eventRepository.getEventByDate(eventDate) } returns agendaEventList

            val output = eventUseCase.getEventsByEventDate(eventDate.year, eventDate.month, eventDate.day) as Result.Success

            verify { eventRepository.getEventByDate(eventDate) }
            assertEquals(output.data, agendaEventList)
        }

        @Test
        fun `WHEN repository throws an error it SHOULD return result error with exception on it`(){
            val exception = Exception()
            val eventDate = EventDateMock.getDummyEventDate()

            every { eventRepository.getEventByDate(eventDate) } throws exception

            val output = eventUseCase.getEventsByEventDate(eventDate.year, eventDate.month, eventDate.day) as Result.Error

            verify { eventRepository.getEventByDate(eventDate) }
            assertEquals(output.exception, exception)
        }
    }

    @Nested
    @DisplayName("Given Some Date To Domain Scenario")
    inner class GivenSomeDateToDomainScenario {

        @Test
        fun `WHEN it is passed a valid date to convert to domain it SHOULD return a result success with EventDate object`(){
            val validDate = EventDateMock.getDummyEventDate()

            every { agendaEventMapper.mapToEventDateDomain(validDate.year, validDate.month, validDate.day) } returns validDate

            val output = eventUseCase.dateToDomain(validDate.year, validDate.month, validDate.day) as Result.Success

            assertEquals(validDate.year, output.data.year)
            assertEquals(validDate.month, output.data.month)
            assertEquals(validDate.day, output.data.day)
        }

        @Test
        fun `WHEN invalid year it SHOULD throw InvalidEventDateException`(){
            val exception = InvalidEventDateException()
            val validDate = EventDateMock.getDummyEventDate()

            every { agendaEventMapper.mapToEventDateDomain(null, validDate.month, validDate.day) } throws exception

            val output = eventUseCase.dateToDomain(null, validDate.month, validDate.day) as Result.Error

            assertTrue(output.exception is InvalidEventDateException)
        }

        @Test
        fun `WHEN invalid month it SHOULD throw InvalidEventDateException`(){
            val exception = InvalidEventDateException()
            val validDate = EventDateMock.getDummyEventDate()

            every { agendaEventMapper.mapToEventDateDomain(validDate.year, null, validDate.day) } throws exception

            val output = eventUseCase.dateToDomain(validDate.year, null, validDate.day) as Result.Error

            assertTrue(output.exception is InvalidEventDateException)
        }

        @Test
        fun `WHEN invalid day it SHOULD throw InvalidEventDateException`(){
            val exception = InvalidEventDateException()
            val validDate = EventDateMock.getDummyEventDate()

            every { agendaEventMapper.mapToEventDateDomain(validDate.year, validDate.month, null) } throws exception

            val output = eventUseCase.dateToDomain(validDate.year, validDate.month, null) as Result.Error

            assertTrue(output.exception is InvalidEventDateException)
        }
    }
}