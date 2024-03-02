package com.ithirteeng.secondpatternsclientproject.common.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ithirteeng.secondpatternsclientproject.common.navigation.graph.authGraph
import com.ithirteeng.secondpatternsclientproject.features.common.registration.navigation.RegistrationDestination
import com.ithirteeng.secondpatternsclientproject.features.common.splash.navigation.SplashDestination
import com.ithirteeng.secondpatternsclientproject.features.common.splash.ui.SplashScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = SplashDestination.route) {
        composable(SplashDestination.route) {
            SplashScreen(
                onAuthorized = {
                    // todo navigate to main screen + popup
                },
                onUnauthorized = {
                    navController.navigate(RegistrationDestination.destination) {
                        popUpTo(SplashDestination.destination) { inclusive = true }
                    }
                }
            )
        }

        authGraph(navController)
    }
}