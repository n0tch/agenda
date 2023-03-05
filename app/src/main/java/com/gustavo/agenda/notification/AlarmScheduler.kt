package com.gustavo.agenda.notification

import android.app.AlarmManager
import android.app.PendingIntent

class AlarmScheduler(
    private val alarmManager: AlarmManager
) {
    fun schedule(alarmAt: Long, pendingIntent: PendingIntent){
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            alarmAt,
            pendingIntent
        )
    }
}