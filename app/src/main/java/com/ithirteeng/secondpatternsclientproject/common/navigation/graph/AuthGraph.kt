package com.ithirteeng.secondpatternsclientproject.common.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
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
                navController.navigate(LoginDestination.route)
            },
            navigateToMainScreen = {
                //todo: show error hz
            }
        )
    }
}

fun NavGraphBuilder.login(navController: NavHostController) {
    composable(LoginDestination.route) {
        LoginScreen(
            navigateToRegistrationScreen = {
                navController.navigate(RegistrationDestination.route) {
                    popUpTo(navController.graph.id) { inclusive = true }
                }
            },
            navigateToMainScreen = {
                //todo: show error hz
            }
        )
    }
}
