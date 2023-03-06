package com.gustavo.agenda.di

import android.app.AlarmManager
import android.content.Context
import com.gustavo.agenda.notification.AlarmScheduler
import com.gustavo.agenda.notification.NotificationBuilder
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val notificationModule = module {
    factory {
        androidContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }
    factory { AlarmScheduler(get()) }
    factory { NotificationBuilder(get()) }
}