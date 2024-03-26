package com.ithirteeng.secondpatternsclientproject.features.client.main.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ithirteeng.secondpatternsclientproject.common.navigation.graph.client.MainClientNavHost
import com.ithirteeng.secondpatternsclientproject.features.client.main.presentation.MainClientViewModel
import com.ithirteeng.secondpatternsclientproject.features.client.main.presentation.model.MainClientEffect
import com.ithirteeng.secondpatternsclientproject.features.client.main.presentation.model.MainClientEvent
import com.ithirteeng.secondpatternsclientproject.features.client.main.presentation.model.MainClientState
import com.ithirteeng.secondpatternsclientproject.features.client.main.ui.components.MainClientNavBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainClientScreen(
    clientId: String,
    viewModel: MainClientViewModel = koinViewModel(),
    bigNavHostController: NavHostController
) {
    val context = LocalContext.current
    val navHostController = rememberNavController()

    LaunchedEffect(null) {
        observeEffects(
            viewModel = viewModel,
            context = context,
            navHostController = navHostController
        )
    }
    viewModel.processEvent(MainClientEvent.Init)
    when (val state = viewModel.state.collectAsState().value) {
        is MainClientState.Content -> MainContent(
            state = state,
            eventListener = viewModel::processEvent,
            navHostController = navHostController,
            clientId = clientId,
            bigNavHostController = bigNavHostController
        )

        is MainClientState.Loading -> CircularProgressIndicator()
    }
}

@Composable
private fun MainContent(
    state: MainClientState.Content,
    eventListener: (MainClientEvent) -> Unit,
    navHostController: NavHostController,
    bigNavHostController: NavHostController,
    clientId: String,
) {

    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(modifier = Modifier.weight(1f)) {
            MainClientNavHost(
                navController = navHostController,
                clientId = clientId,
                bigNavController = bigNavHostController,
            )
        }
        MainClientNavBar(
            state = state,
            eventListener = { eventListener(it) },
            currentDestination = currentDestination
        )
    }
}

private suspend fun observeEffects(
    viewModel: MainClientViewModel,
    context: Context,
    navHostController: NavHostController,
) {
    viewModel.effectsFlow.collect { effect ->
        when (effect) {
            is MainClientEffect.ShowError -> Toast.makeText(
                context,
                context.getString(effect.stringResource),
                Toast.LENGTH_SHORT
            ).show()

            is MainClientEffect.NavigateToClientScreen -> {
                navHostController.navigate(effect.route) {
                    popUpTo(effect.route) {
                        inclusive = true
                        saveState = true
                    }
                }
            }
        }
    }
}
