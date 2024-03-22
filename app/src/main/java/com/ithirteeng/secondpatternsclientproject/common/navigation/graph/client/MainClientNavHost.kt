package com.ithirteeng.secondpatternsclientproject.common.navigation.graph.client

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.navigation.MyAccountsDestination

@Composable
fun MainClientNavHost(
    navController: NavHostController,
    clientId: String,
) {
    NavHost(
        navController = navController,
        startDestination = MyAccountsDestination.route
    ) {
        myAccountsGraph(navController, clientId)
        myLoansGraph(navController, clientId)
        settingsGraph(navController)
    }
}
