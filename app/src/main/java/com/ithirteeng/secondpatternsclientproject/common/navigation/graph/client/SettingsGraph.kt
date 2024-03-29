package com.ithirteeng.secondpatternsclientproject.common.navigation.graph.client

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ithirteeng.secondpatternsclientproject.features.common.login.navigation.LoginDestination
import com.ithirteeng.secondpatternsclientproject.features.common.settings.navigation.SettingsDestination
import com.ithirteeng.secondpatternsclientproject.features.common.settings.ui.SettingsScreen

fun NavGraphBuilder.settingsGraph(
    navController: NavHostController,
    bigNavController: NavHostController,
) {
    composable(
        route = SettingsDestination.route,
        arguments = listOf(
            navArgument(SettingsDestination.CLIENT_ID) { type = NavType.StringType }
        )
    ) {
        SettingsScreen(
            navigateToLoginScreen = {
                bigNavController.navigate(LoginDestination.destination) {
                    popUpTo(bigNavController.graph.id) {
                        inclusive = true
                    }
                }
            }
        )
    }
}
