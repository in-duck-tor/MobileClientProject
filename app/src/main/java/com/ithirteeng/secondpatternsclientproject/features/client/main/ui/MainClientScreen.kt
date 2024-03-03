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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
) {
    val context = LocalContext.current
    LaunchedEffect(null) {
        observeEffects(
            viewModel = viewModel,
            context = context
        )
    }
    viewModel.processEvent(MainClientEvent.Init)
    when (val state = viewModel.state.collectAsState().value) {
        is MainClientState.Content -> MainContent(
            state = state,
            eventListener = viewModel::processEvent
        )

        is MainClientState.Loading -> CircularProgressIndicator()
    }
}

@Composable
private fun MainContent(
    state: MainClientState.Content,
    eventListener: (MainClientEvent) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(modifier = Modifier.weight(1f)) {
            MainClientNavHost()
        }
        MainClientNavBar(
            state = state,
            eventListener = eventListener
        )
    }
}

private suspend fun observeEffects(
    viewModel: MainClientViewModel,
    context: Context,
) {
    viewModel.effectsFlow.collect { effect ->
        when (effect) {
            is MainClientEffect.ShowError -> Toast.makeText(
                context,
                context.getString(effect.stringResource),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}