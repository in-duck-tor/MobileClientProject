package com.ithirteeng.secondpatternsclientproject.features.common.registration.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.ithirteeng.secondpatternsclientproject.R
import com.ithirteeng.secondpatternsclientproject.common.uikit.components.LoadingComponent
import com.ithirteeng.secondpatternsclientproject.features.common.registration.presentation.RegistrationViewModel
import com.ithirteeng.secondpatternsclientproject.features.common.registration.presentation.model.RegistrationEffect
import com.ithirteeng.secondpatternsclientproject.features.common.registration.presentation.model.RegistrationEvent
import com.ithirteeng.secondpatternsclientproject.features.common.registration.presentation.model.RegistrationState
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegistrationScreen(
    viewModel: RegistrationViewModel = koinViewModel(),
    navigateToLoginScreen: () -> Unit,
    navigateToMainScreen: (isClient: Boolean) -> Unit,
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
    viewModel.processEvent(RegistrationEvent.Init)

    when (val state = viewModel.state.collectAsState().value) {
        is RegistrationState.Content -> {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TextField(
                    value = state.login,
                    onValueChange = {
                        viewModel.processEvent(RegistrationEvent.Ui.LoginFieldChange(it))
                    },
                    label = {
                        Text(text = "Login")
                    }
                )

                var passwordVisible by remember { mutableStateOf(false) }
                TextField(
                    value = state.password,
                    onValueChange = {
                        viewModel.processEvent(RegistrationEvent.Ui.PasswordFieldChange(it))
                    },
                    label = {
                        Text(text = "Password")
                    },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image = if (passwordVisible) Icons.Filled.Lock else Icons.Filled.Warning
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = image, null)
                        }
                    }
                )
                TextField(
                    value = state.repeatPassword,
                    onValueChange = {
                        viewModel.processEvent(RegistrationEvent.Ui.RepeatPasswordFieldChange(it))
                    },
                    label = {
                        Text(text = "Repeat Password")
                    },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image = if (passwordVisible) Icons.Filled.Lock else Icons.Filled.Warning
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = image, null)
                        }
                    }
                )

                Row {
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
        }

        is RegistrationState.Init -> LoadingComponent()
    }
}

private suspend fun observeEffects(
    viewModel: RegistrationViewModel,
    navigateToLoginScreen: () -> Unit,
    navigateToMainScreen: (isClient: Boolean) -> Unit,
    context: Context,
) {
    viewModel.effectsFlow.collect { effect ->
        when (effect) {
            is RegistrationEffect.NavigateToMainScreen -> navigateToMainScreen(true)
            is RegistrationEffect.NavigateToLoginScreen -> navigateToLoginScreen()
            is RegistrationEffect.ShowError -> Toast.makeText(
                context,
                effect.message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
