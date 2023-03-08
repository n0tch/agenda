package com.gustavo.agenda.mocks

import com.gustavo.agenda.domain.model.EventDate

object EventDateMock {

    private const val year = 2023
    private const val month = 10
    private const val day = 3

    fun getDummyEventDate() = EventDate(year, month, day)

}