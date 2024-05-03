package com.ithirteeng.secondpatternsclientproject.data.notifications.di

import com.ithirteeng.secondpatternsclientproject.common.network.createRetrofitService
import com.ithirteeng.secondpatternsclientproject.common.network.model.ConnectionType
import com.ithirteeng.secondpatternsclientproject.data.notifications.service.NotificationService
import org.koin.core.qualifier.named
import org.koin.dsl.module

val notificationsDataModule = module {
    single {
        createRetrofitService<NotificationService>(get(named(ConnectionType.AUTHORIZED.name)))
    }
}