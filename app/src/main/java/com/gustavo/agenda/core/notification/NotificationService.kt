package com.gustavo.agenda.core.notification

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.gustavo.agenda.R

class NotificationService(
//    private val context: Context
): BroadcastReceiver() {
//    private val intent = Intent(context, AlertDetails::class.java).apply {
//        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//    }
    override fun onReceive(context: Context, intent: Intent) {
        val notification = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(intent.getStringExtra(TITLE_EXTRA))
                .setContentText(intent.getStringExtra(MESSAGE_EXTRA))
                .setChannelId(CHANNEL_ID)
//                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()
        } else {
            NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(intent.getStringExtra(TITLE_EXTRA))
                .setContentText(intent.getStringExtra(MESSAGE_EXTRA))
                .build()
        }

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(NOTIFICATION_ID, notification)
    }

    companion object {
        const val NOTIFICATION_ID = 1
        const val CHANNEL_ID = "agenda_channel"
        const val TITLE_EXTRA = "agenda_title_extra"
        const val MESSAGE_EXTRA = "agenda_message_extra"
    }
}