package com.ithirteeng.secondpatternsclientproject.features.common.auth.ui

import android.app.PendingIntent
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import com.ithirteeng.secondpatternsclientproject.features.common.auth.presentation.AuthViewModel
import com.ithirteeng.secondpatternsclientproject.features.common.auth.presentation.model.AuthEffect
import com.ithirteeng.secondpatternsclientproject.features.common.auth.presentation.model.AuthEvent
import org.koin.androidx.compose.koinViewModel

@Composable
fun AuthScreen(
    navigateToMainScreen: (isClient: Boolean) -> Unit,
    viewModel: AuthViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartIntentSenderForResult()) {
            it.data?.let { intent ->
                viewModel.processEvent(AuthEvent.IntentResultCame(intent))
            }

        }
    LaunchedEffect(null) {
        observeEffects(viewModel, navigateToMainScreen, context)
    }
    Box(contentAlignment = Alignment.Center) {
        Button(onClick = {
            val pendIntent = PendingIntent.getActivity(
                context,
                1001001,
                viewModel.getIntent(),
                PendingIntent.FLAG_IMMUTABLE
            )
            launcher.launch(
                IntentSenderRequest.Builder(pendIntent)
                    .build()
            )
        }) {
            Text(text = "AUTHORIZE")
        }
    }
}

private suspend fun observeEffects(
    viewModel: AuthViewModel,
    navigateToMainScreen: (isClient: Boolean) -> Unit,
    context: Context,
) {
    viewModel.effectsFlow.collect { effect ->
        when (effect) {
            is AuthEffect.NavigateToMainScreen -> navigateToMainScreen(true)
            is AuthEffect.ShowError -> Toast.makeText(
                context,
                effect.message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}