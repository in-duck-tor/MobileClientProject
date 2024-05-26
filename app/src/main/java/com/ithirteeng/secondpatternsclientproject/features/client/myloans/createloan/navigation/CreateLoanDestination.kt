package com.ithirteeng.secondpatternsclientproject.features.client.myloans.createloan.navigation

import com.ithirteeng.secondpatternsclientproject.common.navigation.Destination

object CreateLoanDestination : Destination() {

    const val PROGRAM_ID = "program_id"

    override var arguments: List<String> = listOf(PROGRAM_ID)
}