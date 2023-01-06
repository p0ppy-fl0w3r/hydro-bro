package com.fl0w3r.core.hydro.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.fl0w3r.core.hydro.R


fun showNotification(
    context: Context,
    channelId: String,
    channelName: String,
    notificationId: Int,
    contentTitle: String
) {

    val notificationBuilder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.cat)
        .setContentTitle(contentTitle)
        .setContentText("Drink water.")
        .setPriority(NotificationCompat.PRIORITY_MAX)
        .setCategory(NotificationCompat.CATEGORY_ALARM)

    val notification = notificationBuilder.build()
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


    notificationManager.createNotificationChannel(
        NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_HIGH,
        ).apply {
            description = "Shows a reminder to drink water."
        }
    )

    notificationManager.notify(notificationId, notification)
}
