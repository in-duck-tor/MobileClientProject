package com.ithirteeng.secondpatternsclientproject.features.common.login.ui

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
import com.ithirteeng.secondpatternsclientproject.features.common.login.presentation.LoginViewModel
import com.ithirteeng.secondpatternsclientproject.features.common.login.presentation.model.LoginEffect
import com.ithirteeng.secondpatternsclientproject.features.common.login.presentation.model.LoginEvent
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = koinViewModel(),
    navigateToMainScreen: () -> Unit,
    navigateToRegistrationScreen: () -> Unit,
) {
    val context = LocalContext.current
    LaunchedEffect(null) {
        observeEffects(
            viewModel = viewModel,
            navigateToMainScreen = navigateToMainScreen,
            navigateToRegistrationScreen = navigateToRegistrationScreen,
            context = context,
        )
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(
            onClick = {
                viewModel.processEvent(LoginEvent.Ui.RegistrationButtonClick)
            }
        ) {
            Text(text = stringResource(id = (R.string.registration)))
        }
        Button(
            onClick = {
                viewModel.processEvent(LoginEvent.Ui.LoginButtonClick)
            }
        ) {
            Text(text = stringResource(id = (R.string.login)))
        }
    }

}

private suspend fun observeEffects(
    viewModel: LoginViewModel,
    navigateToMainScreen: () -> Unit,
    navigateToRegistrationScreen: () -> Unit,
    context: Context,
) {
    viewModel.effectsFlow.collect { effect ->
        when (effect) {
            is LoginEffect.NavigateToMainScreen -> navigateToMainScreen()
            is LoginEffect.NavigateToRegistrationScreen -> navigateToRegistrationScreen()
            is LoginEffect.ShowError -> Toast.makeText(
                context,
                context.getString(effect.stringResource),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}