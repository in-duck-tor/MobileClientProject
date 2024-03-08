package com.ithirteeng.secondpatternsclientproject.features.client.settings.navigation

import com.ithirteeng.secondpatternsclientproject.common.navigation.Destination

object SettingsDestination : Destination() {

    const val CLIENT_ID = "clientId"

    override var arguments: List<String> = listOf(CLIENT_ID)
}