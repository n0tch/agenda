package com.gustavo.agenda.di

import com.gustavo.agenda.domain.usecase.calendar.CalendarUseCase
import com.gustavo.agenda.domain.usecase.event.EventUseCase
import com.gustavo.agenda.domain.usecase.notification.ScheduleEventUseCase
import org.koin.dsl.module

val useCaseModules = module {
    factory { ScheduleEventUseCase(get(), get()) }
    factory { EventUseCase(get(), get()) }
    factory { CalendarUseCase() }
}