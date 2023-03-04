package com.gustavo.agenda

import android.app.Application
import com.gustavo.agenda.di.agendaModule
import com.gustavo.agenda.di.eventModule
import org.koin.core.context.startKoin
import org.koin.android.ext.koin.androidContext

class AgendaApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AgendaApplication)
            modules(listOf(
                agendaModule,
                eventModule
            ))
        }
    }

}
