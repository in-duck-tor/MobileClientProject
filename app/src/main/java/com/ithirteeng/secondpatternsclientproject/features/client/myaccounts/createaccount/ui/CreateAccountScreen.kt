package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.createaccount.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ithirteeng.secondpatternsclientproject.common.uikit.components.LoadingComponent
import com.ithirteeng.secondpatternsclientproject.common.uikit.components.WideButton
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.createaccount.presentation.CreateAccountViewModel
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.createaccount.presentation.model.CreateAccountEffect
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.createaccount.presentation.model.CreateAccountEvent
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.createaccount.presentation.model.CreateAccountState
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreateAccountScreen(
    clientId: String,
    navigateUp: () -> Unit,
    viewModel: CreateAccountViewModel = koinViewModel(),
) {
    val context = LocalContext.current
    LaunchedEffect(null) {
        viewModel.processEvent(CreateAccountEvent.Init)
        observeEffectsFlow(viewModel, context, navigateUp)
    }

    when (val state = viewModel.state.collectAsState().value) {
        is CreateAccountState.Loading -> LoadingComponent()
        is CreateAccountState.Content -> Content(
            state = state,
            eventListener = viewModel::processEvent
        )
    }
}

@Composable
private fun Content(
    state: CreateAccountState.Content,
    eventListener: (CreateAccountEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = state.customComment,
            onValueChange = {
                eventListener(CreateAccountEvent.Ui.CommentTextChanged(it))
            },
            label = {
                Text(text = "custom comment")
            }
        )

        WideButton(
            text = "Create Account",
            onClick = {
                eventListener(CreateAccountEvent.Ui.CreateAccountButtonClick)
            }
        )
    }
}

private suspend fun observeEffectsFlow(
    viewModel: CreateAccountViewModel,
    context: Context,
    navigateUp: () -> Unit,
) {
    viewModel.effectsFlow.collect { effect ->
        when (effect) {
            is CreateAccountEffect.CloseSelf -> navigateUp()
            is CreateAccountEffect.ShowCreationToast -> Toast.makeText(
                context,
                effect.message,
                Toast.LENGTH_SHORT
            ).show()

            is CreateAccountEffect.ShowError -> Toast.makeText(
                context,
                context.getString(effect.stringResource, effect.message),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}