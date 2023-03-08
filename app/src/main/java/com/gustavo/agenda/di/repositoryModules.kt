package com.gustavo.agenda.di

import com.gustavo.agenda.data.datasource.EventDataSource
import com.gustavo.agenda.data.repository.EventRepository
import com.gustavo.agenda.data.datasource.NotificationDataSource
import com.gustavo.agenda.data.repository.NotificationRepository
import org.koin.dsl.module

val repositoryModules = module {
    factory<NotificationRepository> { NotificationDataSource(get(), get()) }
    factory<EventRepository> { EventDataSource(get()) }
}