package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.global.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ithirteeng.secondpatternsclientproject.common.uikit.components.LoadingComponent
import com.ithirteeng.secondpatternsclientproject.common.uikit.components.WideButton
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.main.ui.component.AccountCard
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.global.presentation.GlobalTransactionViewModel
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.global.presentation.model.GlobalTransactionEffect
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.global.presentation.model.GlobalTransactionEvent
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.global.presentation.model.GlobalTransactionState
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.global.ui.component.BankDropdownMenu
import org.koin.androidx.compose.koinViewModel

@Composable
fun AccountsGlobalTransactionScreen(
    accountId: String,
    closeSelf: () -> Unit,
    viewModel: GlobalTransactionViewModel = koinViewModel(),
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = null) {
        viewModel.processEvent(GlobalTransactionEvent.Init(accountId))
        observeEffects(context, viewModel, closeSelf)
    }

    when (val state = viewModel.state.collectAsState().value) {
        is GlobalTransactionState.Loading -> LoadingComponent()
        is GlobalTransactionState.Content ->
            Content(
                state = state,
                eventListener = viewModel::processEvent
            )
    }
}

@Composable
private fun Content(
    eventListener: (GlobalTransactionEvent) -> Unit,
    state: GlobalTransactionState.Content,
) {
    Box {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            item {
                Column(modifier = Modifier.padding(vertical = 16.dp)) {
                    Text(text = "Your Account")
                    AccountCard(account = state.account, onCardClick = {})
                }
            }
            item {
                TextField(
                    modifier = Modifier.padding(vertical = 16.dp),
                    value = state.amountText,
                    onValueChange = {
                        eventListener(GlobalTransactionEvent.Ui.AmountValueChange(it))
                    },
                    label = { Text(text = "amount") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                )
            }
            item {
                TextField(
                    modifier = Modifier.padding(vertical = 16.dp),
                    value = state.accountNumber,
                    onValueChange = {
                        eventListener(GlobalTransactionEvent.Ui.AccountNumberChange(it))
                    },
                    label = { Text(text = "account number") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                )
            }

            item {
                BankDropdownMenu(state = state, eventListener = eventListener)
            }
        }

        Column(modifier = Modifier.align(Alignment.BottomCenter)) {
            WideButton(
                text = "Make Transaction",
                onClick = {
                    eventListener(GlobalTransactionEvent.Ui.MakeTransactionButtonClick)
                }
            )
        }
    }
}

private suspend fun observeEffects(
    context: Context,
    viewModel: GlobalTransactionViewModel,
    closeSelf: () -> Unit,
) {
    viewModel.effectsFlow.collect { effect ->
        when (effect) {
            is GlobalTransactionEffect.CloseSelf -> closeSelf()
            is GlobalTransactionEffect.ShowError -> Toast.makeText(
                context,
                effect.message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}