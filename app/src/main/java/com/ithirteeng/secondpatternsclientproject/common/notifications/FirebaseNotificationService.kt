package com.ithirteeng.secondpatternsclientproject.common.notifications

import android.annotation.SuppressLint
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.ithirteeng.secondpatternsclientproject.data.notifications.model.AppRegistrationModel
import com.ithirteeng.secondpatternsclientproject.data.notifications.service.NotificationService
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class FirebaseNotificationService : FirebaseMessagingService() {

    private val notificationManager: AppNotificationManager by inject()

    //private val notificationService: NotificationService by inject()

    @OptIn(DelicateCoroutinesApi::class)
    override fun onNewToken(token: String) {
        super.onNewToken(token)
//        Log.d("Bullshit", token)
//        GlobalScope.launch {
//            notificationService.registerAppForNotifications(AppRegistrationModel(
//                registrationToken = token,
//                applicationId = "com.ithirteeng.secondpatternsclientproject"
//            ))
//        }
        //todo sendToServer
    }

    override fun onMessageReceived(message: RemoteMessage) {
        Log.d(TAG, "Message received")
        val data = message.notification?.let { getDataFromNotification(it) }
        data?.let { createAndShowNotification(it) }
    }

    private fun getDataFromNotification(notification: RemoteMessage.Notification): NotificationData? =
        if (notification.isEmpty()) {
            null
        } else {
            NotificationData(
                title = notification.title.orEmpty(),
                body = notification.body.orEmpty(),
            )
        }

    private fun createAndShowNotification(data: NotificationData) {
        val appNotification = AppNotification(
            id = data.hashCode(),
            title = data.title,
            message = data.body,
            channelId = CHANNEL_ID,
            channelName = CHANNEL_NAME,
        )

        notificationManager.sendNotification(
            appNotification = appNotification,
            context = this,
        )
    }

    private fun RemoteMessage.Notification.isEmpty() =
        this.title.isNullOrBlank() || this.body.isNullOrBlank()

    private companion object {
        private const val TAG = "AppNotificationsService"
        private const val CHANNEL_ID = "preventorium notification channel id"
        private const val CHANNEL_NAME = "preventorium notification channel name"
    }
}