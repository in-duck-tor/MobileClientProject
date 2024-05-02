package com.ithirteeng.secondpatternsclientproject.common.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import androidx.core.app.NotificationCompat
import com.ithirteeng.secondpatternsclientproject.R

class AppNotificationManager(private val notificationManager: NotificationManager) {

    fun sendNotification(appNotification: AppNotification, context: Context) {
        initChannel(appNotification.channelId, appNotification.channelName)
        notificationManager.notify(
            appNotification.id,
            NotificationCompat.Builder(context, appNotification.channelId)
                .setSmallIcon(R.drawable.icon_my_loans)
                .setColor(Color.CYAN)
                .setContentTitle(appNotification.title)
                .setContentText(appNotification.message)
                .setStyle(NotificationCompat.BigTextStyle().bigText(appNotification.message))
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_SOUND)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build(),
        )
    }

    private fun initChannel(channelId: String, channelName: String) {
        val channel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)
    }
}
