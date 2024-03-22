package com.ithirteeng.secondpatternsclientproject.common.navigation.graph.client

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ithirteeng.secondpatternsclientproject.features.client.settings.navigation.SettingsDestination
import com.ithirteeng.secondpatternsclientproject.features.client.settings.ui.SettingsScreen

fun NavGraphBuilder.settingsGraph(
    navController: NavHostController,
    clientId: String,
) {
    composable(
        route = SettingsDestination.route,
        arguments = listOf(
            navArgument(SettingsDestination.CLIENT_ID) { type = NavType.StringType }
        )
    ) {
        SettingsScreen()
    }
}
