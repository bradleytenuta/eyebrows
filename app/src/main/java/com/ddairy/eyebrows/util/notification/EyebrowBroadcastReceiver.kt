package com.ddairy.eyebrows.util.notification

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.ddairy.eyebrows.EyebrowsActivity
import com.ddairy.eyebrows.R

class EyebrowBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, p1: Intent) {
        // Gets the eyebrow properties from the intent
        val description: String? = p1.getStringExtra(NotificationConstants.eyebrowDescriptionKey)
        val id: String? = p1.getStringExtra(NotificationConstants.eyebrowIdKey)

        // TODO: check values are not null and also check eyebrow still exists.
        // TODO: if eyebrow was deleted, the notification will still play at its deadline.
        // TODO: find way to delete from alarm manager instead as updated the deadline will also lead to issues.
        // TODO: Remove from alarm manager when eyebrow is deleted.
        if (description == null || id == null) {
            return
        }

        // Create an explicit intent for the main activity.
        val intent = Intent(context, EyebrowsActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val builder: NotificationCompat.Builder = NotificationCompat.Builder(context, NotificationConstants.defaultChannelId)
            .setSmallIcon(R.mipmap.eyebrows_icon)
            .setContentTitle(context.resources.getString(R.string.notifications_title))
            .setContentText(description)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            // Set the intent that will fire when the user taps the notification
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            // The tag and id can be used to update or remove a notification later.
            // Calling the notify method will update the existing notification with the same tag and id if found.
            // Shows the notification.
            notify(id, 1, builder.build())
        }
    }
}