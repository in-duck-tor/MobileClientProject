package com.ithirteeng.secondpatternsclientproject.features.client.main.navigation

import com.ithirteeng.secondpatternsclientproject.common.navigation.Destination

object MainClientDestination : Destination() {

    const val CLIENT_ID = "clientId"

    override var arguments: List<String> = listOf(CLIENT_ID)
}