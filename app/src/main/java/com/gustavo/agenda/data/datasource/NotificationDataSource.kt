package com.gustavo.agenda.data.datasource

import com.gustavo.agenda.domain.model.AgendaEvent
import com.gustavo.agenda.data.transformation.getDateInMillis
import com.gustavo.agenda.data.repository.NotificationRepository
import com.gustavo.agenda.core.notification.AlarmScheduler
import com.gustavo.agenda.core.notification.NotificationBuilder

class NotificationDataSource(
    private val notificationBuilder: NotificationBuilder,
    private val alarmScheduler: AlarmScheduler
): NotificationRepository {

    override fun scheduleEvent(agendaEvent: AgendaEvent): Boolean {
        val notificationPendingIntent = notificationBuilder
            .createPendingIntent(agendaEvent.eventDetail.name, agendaEvent.eventDetail.description)

        return alarmScheduler.schedule(
            agendaEvent.getDateInMillis(),
            notificationPendingIntent
        )
    }
}