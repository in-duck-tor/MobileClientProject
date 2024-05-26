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
import com.ithirteeng.secondpatternsclientproject.features.client.myloans.programinfo.navigation.ProgramInfoDestination
import com.ithirteeng.secondpatternsclientproject.features.client.myloans.programinfo.ui.ProgramInfoScreen

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
        programInfo(navController)
        loanInfo(navController)
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
                navController.navigate(ProgramInfoDestination.destinationWithArgs(it))
            }
        )
    }
}

private fun NavGraphBuilder.createLoan(
    navController: NavHostController,
) {
    composable(
        route = CreateLoanDestination.route,
        arguments = listOf(navArgument(CreateLoanDestination.PROGRAM_ID) { NavType.StringType })
    ) { navBackStackEntry ->
        val programId =
            requireNotNull(navBackStackEntry.arguments?.getString(CreateLoanDestination.PROGRAM_ID)) {
                "Program Id is required!"
            }.toLong()
        CreateLoanScreen(
            programId = programId,
            navigateUp = {
                navController.navigateUp()
            }
        )
    }
}

private fun NavGraphBuilder.loanInfo(
    navController: NavHostController
) {
    composable(
        route = LoanInfoDestination.route,
        arguments = listOf(navArgument(LoanInfoDestination.LOAN_ID) { NavType.StringType })
    ) { navBackStackEntry ->
        val loanId =
            requireNotNull(navBackStackEntry.arguments?.getString(LoanInfoDestination.LOAN_ID)) {
                "Loan Id is required!"
            }.toLong()
        LoanInfoScreen(
            loanId = loanId,
            closeSelf = {
                navController.navigateUp()
            }
        )
    }
}

private fun NavGraphBuilder.programInfo(navController: NavHostController) {
    composable(
        route = ProgramInfoDestination.route,
        arguments = listOf(navArgument(ProgramInfoDestination.PROGRAM_ID) { NavType.StringType })
    ) { navBackStackEntry ->
        val programId =
            requireNotNull(navBackStackEntry.arguments?.getString(ProgramInfoDestination.PROGRAM_ID)) {
                "Program Id is required!"
            }.toLong()
        ProgramInfoScreen(
            programId = programId,
            navigateToCreateLoanScreen = {
                navController.navigate(CreateLoanDestination.destinationWithArgs(it))
            }
        )
    }
}
