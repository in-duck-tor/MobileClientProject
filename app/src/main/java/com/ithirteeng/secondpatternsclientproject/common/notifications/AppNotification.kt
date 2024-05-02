package com.ithirteeng.secondpatternsclientproject.common.notifications

import kotlin.random.Random

data class AppNotification(
    val id: Int = Random.nextInt(),
    val title: String,
    val message: String,
    val channelId: String,
    val channelName: String,
)
