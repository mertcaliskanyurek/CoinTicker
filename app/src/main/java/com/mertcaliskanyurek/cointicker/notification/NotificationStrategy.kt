package com.mertcaliskanyurek.cointicker.notification

import android.app.Notification

interface NotificationStrategy {
    fun buildNotification(title: String?, text: String?, smallIcon: Int): Notification?
    fun notify(id: Int, notification: Notification?)
}