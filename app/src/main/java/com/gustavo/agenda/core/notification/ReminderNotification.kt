package com.gustavo.agenda.core.notification

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.gustavo.agenda.domain.model.AgendaEvent

class ReminderNotification(
    private val context: Context
) {

    fun createNotificationPendingIntent(agendaEvent: AgendaEvent): PendingIntent {
        val intent = Intent(context, ReminderNotificationService::class.java).apply {
            putExtra(NOTIFICATION_AGENDA_KEY, agendaEvent)
        }

        return PendingIntent.getBroadcast(
            context,
            NOTIFICATION_ID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    companion object {
        const val CHANNEL_ID = "agenda_channel"
        private const val NOTIFICATION_ID = 1
        const val NOTIFICATION_NAME = "agenda_notification_name"
        const val NOTIFICATION_AGENDA_KEY = "NOTIFICATION_AGENDA_KEY"
        const val REQUEST_CODE = 123
    }
}