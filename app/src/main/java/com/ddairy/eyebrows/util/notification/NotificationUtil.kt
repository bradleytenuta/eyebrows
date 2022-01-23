package com.ddairy.eyebrows.util.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import com.ddairy.eyebrows.R

class NotificationUtil {

    companion object {
        /**
         * Creates the default notification channel. This should be called at the beginning of the app.
         * It does not matter if this gets called multiple times.
         */
        fun initialise(context: Context) {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name = context.resources.getString(R.string.notifications_channel_name)
                val descriptionText = context.resources.getString(R.string.notifications_channel_description)
                val importance = NotificationManager.IMPORTANCE_HIGH
                val channel = NotificationChannel(NotificationConstants.defaultChannelId, name, importance).apply {
                    description = descriptionText
                }
                // Register the channel with the system
                val notificationManager: NotificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }
        }
    }
}