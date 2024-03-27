package com.ithirteeng.secondpatternsclientproject.common.navigation.graph.client

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.accountinfo.navigation.MyAccountsAccountInfoDestination
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.accountinfo.stub.AccountInfoStubScreen
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.accountinfo.ui.AccountInfoScreen
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.createaccount.navigation.MyAccountsCreateAccountDestination
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.createaccount.stub.CreateAccountStubScreen
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.createaccount.ui.CreateAccountScreen
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.main.navigation.MyAccountsMainDestination
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.main.stub.AccountsMainStubScreen
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.main.ui.MyAccountsMainScreen
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.navigation.MyAccountsDestination
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.self.navigation.MyAccountsSelfTransactionDestination
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.self.stub.AccountsTransactionStubScreen
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.self.ui.AccountsSelfTransactionScreen

fun NavGraphBuilder.myAccountsGraph(
    navController: NavHostController,
    clientId: String,
) {
    navigation(
        startDestination = MyAccountsMainDestination.destinationWithArgs(clientId),
        route = MyAccountsDestination.route
    ) {
//        mainStub(navController, clientId)
//        accountInfoStub(navController, clientId)
//        createAccountStub(navController, clientId)
//        transactionStub(navController, clientId)

        main(navController, clientId)
        accountInfo(navController, clientId)
        createAccount(navController)
        transaction(navController, clientId)


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
) {
    composable(
        route = MyAccountsCreateAccountDestination.route,
        arguments = listOf(
            navArgument(MyAccountsCreateAccountDestination.CLIENT_ID) { type = NavType.StringType }
        )
    ) {
        CreateAccountScreen(
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
        AccountInfoScreen(
            clientId = clientId,
            accountNumber = accountId,
            navigateToTransactionScreen = {
                navController.navigate(
                    MyAccountsSelfTransactionDestination.destinationWithArgs(
                        clientId,
                        accountId,
                    )
                )
            }
        )
    }
}

private fun NavGraphBuilder.transaction(
    navController: NavHostController,
    clientId: String,
) {
    composable(
        route = MyAccountsSelfTransactionDestination.route,
        arguments = listOf(
            navArgument(MyAccountsSelfTransactionDestination.CLIENT_ID) {
                type = NavType.StringType
            },
            navArgument(MyAccountsSelfTransactionDestination.ACCOUNT_ID) {
                type = NavType.StringType
            },
        )
    ) { navBackStackEntry ->
        val accountId =
            requireNotNull(
                navBackStackEntry.arguments?.getString(
                    MyAccountsSelfTransactionDestination.ACCOUNT_ID
                )
            ) {
                "Client Id is required!"
            }
        AccountsSelfTransactionScreen(
            accountId = accountId,
            navigateUp = {
                navController.navigate(MyAccountsMainDestination.destinationWithArgs(clientId)) {
                    popUpTo(navController.graph.id) { inclusive = true }
                }
            }
        )
    }
}

private fun NavGraphBuilder.mainStub(
    navController: NavHostController,
    clientId: String,
) {
    composable(
        route = MyAccountsMainDestination.route,
        arguments = listOf(
            navArgument(MyAccountsMainDestination.CLIENT_ID) { type = NavType.StringType }
        )
    ) {
        AccountsMainStubScreen(
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

private fun NavGraphBuilder.createAccountStub(
    navController: NavHostController,
    clientId: String,
) {
    composable(
        route = MyAccountsCreateAccountDestination.route,
        arguments = listOf(
            navArgument(MyAccountsCreateAccountDestination.CLIENT_ID) { type = NavType.StringType }
        )
    ) {
        CreateAccountStubScreen(
            clientId = clientId,
            navigateUp = {
                navController.navigateUp()
            }
        )
    }
}

private fun NavGraphBuilder.accountInfoStub(
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
        AccountInfoStubScreen(
            clientId = clientId,
            accountNumber = accountId,
            navigateToTransactionScreen = {
                navController.navigate(
                    MyAccountsSelfTransactionDestination.destinationWithArgs(
                        clientId,
                        accountId,
                    )
                )
            }
        )
    }
}

private fun NavGraphBuilder.transactionStub(
    navController: NavHostController,
    clientId: String,
) {
    composable(
        route = MyAccountsSelfTransactionDestination.route,
        arguments = listOf(
            navArgument(MyAccountsSelfTransactionDestination.CLIENT_ID) {
                type = NavType.StringType
            },
            navArgument(MyAccountsSelfTransactionDestination.ACCOUNT_ID) {
                type = NavType.StringType
            },
        )
    ) { navBackStackEntry ->
        val accountId =
            requireNotNull(
                navBackStackEntry.arguments?.getString(
                    MyAccountsSelfTransactionDestination.ACCOUNT_ID
                )
            ) {
                "Client Id is required!"
            }
        AccountsTransactionStubScreen(
            accountId = accountId,
            navigateUp = {
                navController.navigateUp()
            }
        )
    }
}
