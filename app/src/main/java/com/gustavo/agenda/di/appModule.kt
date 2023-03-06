package com.gustavo.agenda.di

import com.google.gson.Gson
import com.gustavo.agenda.common.repository.AgendaPreferences
import com.gustavo.agenda.eventDetail.domain.AgendaEventMapper
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    factory { Gson() }
    factory { AgendaEventMapper() }
    factory { AgendaPreferences(androidContext(), get()) }
}