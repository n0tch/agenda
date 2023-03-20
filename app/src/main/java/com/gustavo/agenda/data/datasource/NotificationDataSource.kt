package com.gustavo.agenda.data.datasource

import com.gustavo.agenda.domain.model.AgendaEvent
import com.gustavo.agenda.data.repository.NotificationRepository
import com.gustavo.agenda.core.notification.AlarmScheduler
import com.gustavo.agenda.core.notification.ReminderNotification
import com.gustavo.agenda.data.transformation.getDateInMillis

class NotificationDataSource(
    private val alarmScheduler: AlarmScheduler,
    private val reminderNotification: ReminderNotification
) : NotificationRepository {

    override fun scheduleEvent(agendaEvent: AgendaEvent): Boolean {
        val notificationPendingIntent = reminderNotification
            .createNotificationPendingIntent(agendaEvent)

        return alarmScheduler.schedule(
            alarmAt = agendaEvent.getDateInMillis(),
            pendingIntent = notificationPendingIntent
        )
    }
}