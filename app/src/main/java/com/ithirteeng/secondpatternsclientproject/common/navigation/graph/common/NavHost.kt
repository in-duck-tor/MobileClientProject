package com.ithirteeng.secondpatternsclientproject.common.navigation.graph.common

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ithirteeng.secondpatternsclientproject.features.client.main.navigation.MainClientDestination
import com.ithirteeng.secondpatternsclientproject.features.common.registration.navigation.RegistrationDestination
import com.ithirteeng.secondpatternsclientproject.features.common.splash.navigation.SplashDestination
import com.ithirteeng.secondpatternsclientproject.features.common.splash.ui.SplashScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = SplashDestination.route) {
        composable(SplashDestination.route) {
            SplashScreen(
                onAuthorized = {
                    navController.navigate(
                        MainClientDestination.destinationWithArgs(it)
                    ) {
                        popUpTo(SplashDestination.destination) { inclusive = true }
                    }
                },
                onUnauthorized = {
                    navController.navigate(RegistrationDestination.destination) {
                        popUpTo(SplashDestination.destination) { inclusive = true }
                    }
                }
            )
        }

        authGraph(navController)
        mainClientGraph(navController)
    }
}