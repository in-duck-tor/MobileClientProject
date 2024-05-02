package com.ithirteeng.secondpatternsclientproject.common.notifications

import android.app.NotificationManager
import android.content.Context
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val commonNotificationsModule = module {

    single { androidApplication().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager }
    singleOf(::AppNotificationManager)
}