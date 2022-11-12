package com.mertcaliskanyurek.cointicker.notification

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat


class NotificationStrategyBelowApi26(
    private val context: Context
) : NotificationStrategy{

    private val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


    override fun buildNotification(title: String?, text: String?, smallIcon: Int): Notification {
        return NotificationCompat.Builder(context)
            .setSmallIcon(smallIcon)
            .setContentTitle(title)
            .setContentText(text)
            .build()
    }

    override fun notify(id: Int, notification: Notification?) {
        notificationManager.notify(
            id,
            notification
        )
    }
}