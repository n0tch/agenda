package com.gustavo.agenda.core.notification

import android.app.AlarmManager
import android.app.PendingIntent

class AlarmScheduler(
    private val alarmManager: AlarmManager
) {
    fun schedule(alarmAt: Long, pendingIntent: PendingIntent) = try {
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            alarmAt,
            pendingIntent
        )
        true
    } catch (exception: Exception) {
        false
    }
}