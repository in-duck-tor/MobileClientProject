package com.ithirteeng.secondpatternsclientproject.common.navigation.graph.common

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.ithirteeng.secondpatternsclientproject.features.client.main.navigation.MainClientDestination
import com.ithirteeng.secondpatternsclientproject.features.common.login.navigation.LoginDestination
import com.ithirteeng.secondpatternsclientproject.features.common.login.ui.LoginScreen
import com.ithirteeng.secondpatternsclientproject.features.common.registration.navigation.RegistrationDestination
import com.ithirteeng.secondpatternsclientproject.features.common.registration.ui.RegistrationScreen

fun NavGraphBuilder.authGraph(navController: NavHostController) {
    registration(navController)
    login(navController)
}

fun NavGraphBuilder.registration(navController: NavHostController) {
    composable(RegistrationDestination.route) {
        RegistrationScreen(
            navigateToLoginScreen = {
                navController.navigate(LoginDestination.destination)
            },
            navigateToMainScreen = { isClient ->
                if (isClient) {
                    navController.navigate(MainClientDestination.destinationWithArgs("shits1")) {
                        popUpTo(navController.graph.id) { inclusive = true }
                    }
                } else {
                    //todo: implement admin
                }
            }
        )
    }
}

fun NavGraphBuilder.login(navController: NavHostController) {
    composable(LoginDestination.route) {
        LoginScreen(
            navigateToRegistrationScreen = {
                navController.navigate(RegistrationDestination.destination) {
                    popUpTo(navController.graph.id) { inclusive = true }
                }
            },
            navigateToMainScreen = { isClient ->
                if (isClient) {
                    navController.navigate(MainClientDestination.destinationWithArgs("shits2")) {
                        popUpTo(navController.graph.id) { inclusive = true }
                    }
                } else {
                    //todo: implement admin
                }
            }
        )
    }
}
