package com.ithirteeng.secondpatternsclientproject.common.navigation.graph.client

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.navigation.MyAccountsMainDestination
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.main.navigation.MyAccountsDestination
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.main.ui.MyAccountsMainScreen

fun NavGraphBuilder.myAccountsGraph(
    navController: NavHostController,
    clientId: String,
) {
    navigation(
        startDestination = MyAccountsMainDestination.destinationWithArgs(clientId),
        route = MyAccountsDestination.route
    ) {
        composable(
            route = MyAccountsMainDestination.route,
            arguments = listOf(
                navArgument(MyAccountsMainDestination.CLIENT_ID) { type = NavType.StringType }
            )
        ) {
            MyAccountsMainScreen(clientId)
        }
    }
}
