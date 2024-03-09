package com.ithirteeng.secondpatternsclientproject.common.navigation.graph.client

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.accountinfo.navigation.MyAccountsAccountInfoDestination
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.accountinfo.ui.AccountInfoScreen
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.createaccount.navigation.MyAccountsCreateAccountDestination
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.createaccount.ui.CreateAccountScreen
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.main.navigation.MyAccountsMainDestination
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.main.ui.MyAccountsMainScreen
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.navigation.MyAccountsDestination
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.navigation.MyAccountsTransactionDestination
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.ui.AccountsTransferScreen

fun NavGraphBuilder.myAccountsGraph(
    navController: NavHostController,
    clientId: String,
) {
    navigation(
        startDestination = MyAccountsMainDestination.destinationWithArgs(clientId),
        route = MyAccountsDestination.route
    ) {
        main(navController, clientId)
        accountInfo(navController, clientId)
        createAccount(navController, clientId)
        transfer(navController, clientId)
    }
}

private fun NavGraphBuilder.main(
    navController: NavHostController,
    clientId: String,
) {
    composable(
        route = MyAccountsMainDestination.route,
        arguments = listOf(
            navArgument(MyAccountsMainDestination.CLIENT_ID) { type = NavType.StringType }
        )
    ) {
        MyAccountsMainScreen(
            clientId = clientId,
            navigateToCreateAccountScreen = { clientId ->
                navController.navigate(
                    MyAccountsCreateAccountDestination.destinationWithArgs(clientId)
                )
            },
            navigateToAccountInfoScreen = { clientId, accountId ->
                navController.navigate(
                    MyAccountsAccountInfoDestination.destinationWithArgs(clientId, accountId)
                )
            }
        )
    }
}

private fun NavGraphBuilder.createAccount(
    navController: NavHostController,
    clientId: String,
) {
    composable(
        route = MyAccountsCreateAccountDestination.route,
        arguments = listOf(
            navArgument(MyAccountsCreateAccountDestination.CLIENT_ID) { type = NavType.StringType }
        )
    ) {
        CreateAccountScreen(
            clientId = clientId,
            navigateUp = {
                navController.navigateUp()
            }
        )
    }
}

private fun NavGraphBuilder.accountInfo(
    navController: NavHostController,
    clientId: String,
) {
    composable(
        route = MyAccountsAccountInfoDestination.route,
        arguments = listOf(
            navArgument(MyAccountsAccountInfoDestination.CLIENT_ID) { type = NavType.StringType },
            navArgument(MyAccountsAccountInfoDestination.ACCOUNT_ID) { type = NavType.StringType },
        )
    ) { navBackStackEntry ->
        val accountId =
            requireNotNull(navBackStackEntry.arguments?.getString(MyAccountsAccountInfoDestination.ACCOUNT_ID)) {
                "Client Id is required!"
            }
        AccountInfoScreen(clientId, accountId, navigateToTransactionScreen = {})
    }
}

private fun NavGraphBuilder.transfer(
    navController: NavHostController,
    clientId: String,
) {
    composable(
        route = MyAccountsTransactionDestination.route,
        arguments = listOf(
            navArgument(MyAccountsTransactionDestination.CLIENT_ID) { type = NavType.StringType },
            navArgument(MyAccountsTransactionDestination.ACCOUNT_ID) { type = NavType.StringType },
        )
    ) { navBackStackEntry ->
        val accountId =
            requireNotNull(navBackStackEntry.arguments?.getString(MyAccountsTransactionDestination.ACCOUNT_ID)) {
                "Client Id is required!"
            }
        AccountsTransferScreen(clientId, accountId)
    }
}
