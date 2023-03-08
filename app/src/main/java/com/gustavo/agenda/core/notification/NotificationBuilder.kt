package com.gustavo.agenda.core.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build

class NotificationBuilder(
    private val context: Context
) {
    init {
        buildNotification()
    }

    fun createPendingIntent(title: String, description: String): PendingIntent {
        val intent = Intent(context, NotificationService::class.java).apply {
            putExtra(NotificationService.TITLE_EXTRA, title)
            putExtra(NotificationService.MESSAGE_EXTRA, description)
        }

        return PendingIntent.getBroadcast(
            context,
            NotificationService.NOTIFICATION_ID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    private fun buildNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NotificationService.CHANNEL_ID,
                NOTIFICATION_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = NOTIFICATION_DESC
            }

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        private const val NOTIFICATION_NAME = "agenda_notif_name"
        private const val NOTIFICATION_DESC = "agenda_notif_desc"
    }
}