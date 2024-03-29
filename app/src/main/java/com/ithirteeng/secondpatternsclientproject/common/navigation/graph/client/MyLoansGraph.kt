package com.ithirteeng.secondpatternsclientproject.common.navigation.graph.client

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.ithirteeng.secondpatternsclientproject.features.client.myloans.createloan.navigation.CreateLoanDestination
import com.ithirteeng.secondpatternsclientproject.features.client.myloans.createloan.ui.CreateLoanScreen
import com.ithirteeng.secondpatternsclientproject.features.client.myloans.loaninfo.navigation.LoanInfoDestination
import com.ithirteeng.secondpatternsclientproject.features.client.myloans.loaninfo.ui.LoanInfoScreen
import com.ithirteeng.secondpatternsclientproject.features.client.myloans.main.navigation.MyLoansMainDestination
import com.ithirteeng.secondpatternsclientproject.features.client.myloans.main.ui.MyLoansMainScreen
import com.ithirteeng.secondpatternsclientproject.features.client.myloans.navigation.MyLoansDestination

fun NavGraphBuilder.myLoansGraph(
    navController: NavHostController,
    clientId: String,
) {
    navigation(
        startDestination = MyLoansMainDestination.destinationWithArgs(clientId),
        route = MyLoansDestination.route
    ) {
        main(navController)
        createLoan(navController)
        loanInfo()
    }
}

private fun NavGraphBuilder.main(
    navController: NavHostController,
) {
    composable(
        route = MyLoansMainDestination.route,
        arguments = listOf(
            navArgument(MyLoansMainDestination.CLIENT_ID) { type = NavType.StringType }
        )
    ) {
        MyLoansMainScreen(
            navigateToLoanInfoScreen = {
                navController.navigate(LoanInfoDestination.destinationWithArgs(it))
            },
            navigateToProgramInfoScreen = {

            }
        )
    }
}

private fun NavGraphBuilder.createLoan(
    navController: NavHostController,
) {
    composable(CreateLoanDestination.route) {
        CreateLoanScreen(
            navigateUp = {
                navController.navigateUp()
            }
        )
    }
}

private fun NavGraphBuilder.loanInfo() {
    composable(
        route = LoanInfoDestination.route,
        arguments = listOf(navArgument(LoanInfoDestination.LOAN_ID) { NavType.StringType })
    ) { navBackStackEntry ->
        val loanId =
            requireNotNull(navBackStackEntry.arguments?.getInt(LoanInfoDestination.LOAN_ID)) {
                "Loan Id is required!"
            }
        LoanInfoScreen(
            loanId = loanId,
        )
    }
}
