package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transfer.navigation

import com.ithirteeng.secondpatternsclientproject.common.navigation.Destination

object MyAccountsTransferDestination: Destination() {

    const val CLIENT_ID = "clientId"
    const val ACCOUNT_ID = "accountId"

    override var arguments: List<String> = listOf(CLIENT_ID, ACCOUNT_ID)
}