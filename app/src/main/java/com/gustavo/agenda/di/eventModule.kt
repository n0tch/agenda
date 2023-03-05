package com.gustavo.agenda.di

import android.app.AlarmManager
import android.content.Context
import com.gustavo.agenda.eventDetail.domain.repository.EventRepository
import com.gustavo.agenda.eventDetail.presentation.EventDetailViewModel
import com.gustavo.agenda.notification.AlarmScheduler
import com.gustavo.agenda.notification.NotificationBuilder
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val eventModule = module {
    factory { androidContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager }
    factory { AlarmScheduler(get()) }
    factory { NotificationBuilder(get()) }
    factory { EventRepository(get(), get()) }
    viewModel { EventDetailViewModel(get()) }
}
