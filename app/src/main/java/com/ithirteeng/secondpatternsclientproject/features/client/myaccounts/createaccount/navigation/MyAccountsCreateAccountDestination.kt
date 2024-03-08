package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.createaccount.navigation

import com.ithirteeng.secondpatternsclientproject.common.navigation.Destination

object MyAccountsCreateAccountDestination : Destination() {

    const val CLIENT_ID = "clientId"

    override var arguments: List<String> = listOf(CLIENT_ID)
}