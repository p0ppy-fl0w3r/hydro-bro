package com.fl0w3r.core.hydro.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.fl0w3r.core.hydro.notification.showNotification

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

        var remarks = intent.getStringExtra("remarks")
        if (remarks.isNullOrEmpty()) {
            remarks = "It's time to drink some water!"
        }

        showNotification(
            context = context,
            channelId = "water_alarm",
            channelName = "Hydro Alarm",
            notificationId = 69420,
            contentTitle = "Drink water!!",
            contentText = remarks
        )
    }
}