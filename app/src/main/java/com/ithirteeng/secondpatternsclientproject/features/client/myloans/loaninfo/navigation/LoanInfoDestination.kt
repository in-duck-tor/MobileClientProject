package com.ithirteeng.secondpatternsclientproject.features.client.myloans.loaninfo.navigation

import com.ithirteeng.secondpatternsclientproject.common.navigation.Destination

object LoanInfoDestination : Destination() {

    const val LOAN_ID = "loan_id"

    override var arguments: List<String> = listOf(LOAN_ID)
}