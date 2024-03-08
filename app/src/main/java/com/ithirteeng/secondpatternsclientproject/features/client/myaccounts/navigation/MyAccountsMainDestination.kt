package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.navigation

import com.ithirteeng.secondpatternsclientproject.common.navigation.Destination

object MyAccountsMainDestination : Destination() {
    
    const val CLIENT_ID = "clientId"

    override var arguments: List<String> = listOf(CLIENT_ID)
}