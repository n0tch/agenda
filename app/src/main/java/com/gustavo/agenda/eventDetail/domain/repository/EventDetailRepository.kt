package com.gustavo.agenda.eventDetail.domain.repository

import com.gustavo.agenda.common.AgendaEvent
import com.gustavo.agenda.common.mapper.getDateInMillis
import com.gustavo.agenda.common.repository.AgendaPreferences
import com.gustavo.agenda.eventDate.domain.model.EventDate
import com.gustavo.agenda.notification.AlarmScheduler
import com.gustavo.agenda.notification.NotificationBuilder

class EventDetailRepository(
    private val notificationBuilder: NotificationBuilder,
    private val alarmScheduler: AlarmScheduler,
    private val agendaPreferences: AgendaPreferences
) {
    fun scheduleEvent(agendaEvent: AgendaEvent) {
        val notificationPendingIntent = notificationBuilder
            .createPendingIntent(agendaEvent.eventDetail.name, agendaEvent.eventDetail.description)

        alarmScheduler.schedule(
            agendaEvent.getDateInMillis(),
            notificationPendingIntent
        )
    }

    fun saveEvent(agendaEvent: AgendaEvent) {
        agendaPreferences.saveEvent(agendaEvent)
    }
}