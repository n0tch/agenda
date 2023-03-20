package com.gustavo.agenda.core.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import com.gustavo.agenda.R
import com.gustavo.agenda.core.notification.ReminderNotification.Companion.NOTIFICATION_AGENDA_KEY
import com.gustavo.agenda.data.transformation.getDateInMillis
import com.gustavo.agenda.domain.model.AgendaEvent
import com.gustavo.agenda.ui.eventdate.presentation.EventDateFragment.Companion.AGENDA_EVENT_KEY

class ReminderNotificationService : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val agendaEvent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            intent.getParcelableExtra(NOTIFICATION_AGENDA_KEY, AgendaEvent::class.java)
        else
            intent.getParcelableExtra(NOTIFICATION_AGENDA_KEY)

        showNotification(context, agendaEvent)
    }

    private fun showNotification(context: Context, agendaEvent: AgendaEvent?) {

        buildNotification(context)

        val notification = NotificationCompat.Builder(context, ReminderNotification.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_alarm_on)
            .setContentTitle(agendaEvent?.eventDetail?.name)
            .setContentText(agendaEvent?.eventDetail?.description)
            .setContentIntent(createPendingActivityToAction(context))
            .setAutoCancel(true)
            .addAction(
                R.drawable.ic_alarm_on,
                context.getString(R.string.notification_open_detail),
                createDetailPendingIntent(context, agendaEvent)
            ).build()

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(
            agendaEvent?.getDateInMillis()?.toInt() ?: 0,
            notification
        )
    }

    private fun buildNotification(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                ReminderNotification.CHANNEL_ID,
                ReminderNotification.NOTIFICATION_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = ReminderNotification.NOTIFICATION_NAME
            }

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createPendingActivityToAction(context: Context): PendingIntent =
        NavDeepLinkBuilder(context)
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.agendaFragment)
            .createPendingIntent()

    private fun createDetailPendingIntent(
        context: Context,
        agendaEvent: AgendaEvent?
    ): PendingIntent {
        val bundle = Bundle().apply {
            putParcelable(AGENDA_EVENT_KEY, agendaEvent)
        }
        return NavDeepLinkBuilder(context)
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.eventDetailFragment)
            .setArguments(bundle)
            .createPendingIntent()
    }
}