package com.ithirteeng.secondpatternsclientproject.features.client.myloans.programinfo.navigation

import com.ithirteeng.secondpatternsclientproject.common.navigation.Destination

object ProgramInfoDestination : Destination() {

    const val PROGRAM_ID = "program_id"

    override var arguments: List<String> = listOf(PROGRAM_ID)
}