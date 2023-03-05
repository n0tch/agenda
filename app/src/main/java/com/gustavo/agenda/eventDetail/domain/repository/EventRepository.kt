package com.gustavo.agenda.eventDetail.domain.repository

import com.gustavo.agenda.eventDetail.presentation.EventDetailState
import com.gustavo.agenda.notification.AlarmScheduler
import com.gustavo.agenda.notification.NotificationBuilder
import java.util.Calendar

class EventRepository(
    private val notificationBuilder: NotificationBuilder,
    private val alarmScheduler: AlarmScheduler
) {
    fun saveEvent(
        dateSelected: EventDetailState.DateSelected,
        eventName: String,
        eventDetail: String,
        hour: Int,
        minute: Int
    ) {

        val scheduleTime = buildTime(dateSelected.day, dateSelected.month, dateSelected.year, hour, minute)
        val notificationPendingIntent = notificationBuilder.createPendingIntent(eventName, eventDetail)
        alarmScheduler.schedule(scheduleTime, notificationPendingIntent)
    }

    private fun buildTime(day: Int, month: Int, year: Int, hour: Int, minute: Int): Long {
        return Calendar.getInstance().apply {
            set(year, month, day, hour, minute, 0)
        }.timeInMillis
    }
}