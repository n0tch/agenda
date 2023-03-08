package com.gustavo.agenda.mocks

import com.gustavo.agenda.domain.model.EventTime

object EventTimeMock {

    private const val hour = 12
    private const val minute = 12
    private const val second = 0

    fun getDummyTime() = EventTime(hour, minute, second)
}