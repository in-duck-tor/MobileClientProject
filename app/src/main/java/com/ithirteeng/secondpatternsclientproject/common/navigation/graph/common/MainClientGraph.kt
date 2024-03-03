package com.ithirteeng.secondpatternsclientproject.common.navigation.graph.common

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ithirteeng.secondpatternsclientproject.features.client.main.navigation.MainClientDestination
import com.ithirteeng.secondpatternsclientproject.features.client.main.ui.MainClientScreen

fun NavGraphBuilder.mainClientGraph(navController: NavHostController) {

    composable(
        route = MainClientDestination.route,
        arguments = listOf(
            navArgument(MainClientDestination.CLIENT_ID) { type = NavType.StringType }
        )
    ) { navBackStackEntry ->
        val clientId =
            requireNotNull(navBackStackEntry.arguments?.getString(MainClientDestination.CLIENT_ID)) {
                "Client Id is required!"
            }
        MainClientScreen(clientId)
    }
}
