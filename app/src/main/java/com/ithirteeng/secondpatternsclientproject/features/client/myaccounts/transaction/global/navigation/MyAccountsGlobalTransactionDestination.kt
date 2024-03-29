package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.global.navigation

import com.ithirteeng.secondpatternsclientproject.common.navigation.Destination

object MyAccountsGlobalTransactionDestination : Destination() {

    const val ACCOUNT_ID = "accountId"

    override var arguments: List<String> = listOf(ACCOUNT_ID)
}