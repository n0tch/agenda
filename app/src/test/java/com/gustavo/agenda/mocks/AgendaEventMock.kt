package com.gustavo.agenda.mocks

import com.gustavo.agenda.domain.model.AgendaEvent

object AgendaEventMock {
    fun getDummyAgendaEvent() = AgendaEvent(
        EventDateMock.getDummyEventDate(),
        EventTimeMock.getDummyTime(),
        EventDetailMock.getDummyDetail()
    )

    fun getDummyList() = arrayListOf(
        getDummyAgendaEvent(),
        getDummyAgendaEvent()
    )
}