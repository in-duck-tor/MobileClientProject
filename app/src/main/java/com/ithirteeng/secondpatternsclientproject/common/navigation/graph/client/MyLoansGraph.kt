package com.ithirteeng.secondpatternsclientproject.common.navigation.graph.client

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.ithirteeng.secondpatternsclientproject.features.client.myloans.main.navigation.MyLoansMainDestination
import com.ithirteeng.secondpatternsclientproject.features.client.myloans.main.ui.MyLoansScreen
import com.ithirteeng.secondpatternsclientproject.features.client.myloans.navigation.MyLoansDestination

fun NavGraphBuilder.myLoansGraph(
    navController: NavHostController,
    clientId: String,
) {
    navigation(
        startDestination = MyLoansMainDestination.destinationWithArgs(clientId),
        route = MyLoansDestination.route
    ) {
        composable(
            route = MyLoansMainDestination.route,
            arguments = listOf(
                navArgument(MyLoansMainDestination.CLIENT_ID) { type = NavType.StringType }
            )
        ) {
            MyLoansScreen(clientId)
        }
    }
}
