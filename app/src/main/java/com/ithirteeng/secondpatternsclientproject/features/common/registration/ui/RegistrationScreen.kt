package com.ithirteeng.secondpatternsclientproject.features.common.registration.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.ithirteeng.secondpatternsclientproject.R
import com.ithirteeng.secondpatternsclientproject.features.common.registration.presentation.RegistrationViewModel
import com.ithirteeng.secondpatternsclientproject.features.common.registration.presentation.model.RegistrationEffect
import com.ithirteeng.secondpatternsclientproject.features.common.registration.presentation.model.RegistrationEvent
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegistrationScreen(
    viewModel: RegistrationViewModel = koinViewModel(),
    navigateToLoginScreen: () -> Unit,
    navigateToMainScreen: () -> Unit,
) {
    val context = LocalContext.current

    LaunchedEffect(null) {
        observeEffects(
            viewModel = viewModel,
            navigateToLoginScreen = navigateToLoginScreen,
            navigateToMainScreen = navigateToMainScreen,
            context = context
        )
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(
            onClick = {
                viewModel.processEvent(RegistrationEvent.Ui.LoginButtonClick)
            }
        ) {
            Text(text = stringResource(id = (R.string.login)))
        }
        Button(
            onClick = {
                viewModel.processEvent(RegistrationEvent.Ui.RegisterButtonClick)
            }
        ) {
            Text(text = stringResource(id = (R.string.register)))
        }
    }

}

private suspend fun observeEffects(
    viewModel: RegistrationViewModel,
    navigateToLoginScreen: () -> Unit,
    navigateToMainScreen: () -> Unit,
    context: Context,
) {
    viewModel.effectsFlow.collect { effect ->
        when (effect) {
            is RegistrationEffect.NavigateToMainScreen -> navigateToMainScreen()
            is RegistrationEffect.NavigateToLoginScreen -> navigateToLoginScreen()
            is RegistrationEffect.ShowError -> Toast.makeText(
                context,
                context.getString(effect.stringResource),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
