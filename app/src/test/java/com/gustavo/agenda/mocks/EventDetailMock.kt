package com.gustavo.agenda.mocks

import com.gustavo.agenda.domain.model.EventDetail

object EventDetailMock {

    private const val eventName = "My notification"
    private const val eventDescription = "Remember something"

    fun getDummyDetail() = EventDetail(eventName, eventDescription)
}