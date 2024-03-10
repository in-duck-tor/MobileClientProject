package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.ui

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
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.presentation.TransactionViewModel
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.presentation.model.TransactionEffect
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.presentation.model.TransactionEvent
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.presentation.model.TransactionState
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.ui.component.TransactionsDropdownMenu
import org.koin.androidx.compose.koinViewModel

@Composable
fun AccountsTransactionScreen(
    accountId: String,
    navigateUp: () -> Unit,
    viewModel: TransactionViewModel = koinViewModel(),
) {
    val context = LocalContext.current
    LaunchedEffect(null) {
        viewModel.processEvent(TransactionEvent.Init(accountId))
        observeEffectsFlow(viewModel, context, navigateUp)
    }

    when (val state = viewModel.state.collectAsState().value) {
        is TransactionState.Loading -> LoadingComponent()
        is TransactionState.Content -> Content(
            state = state,
            eventListener = viewModel::processEvent
        )
    }
}

@Composable
private fun Content(
    state: TransactionState.Content,
    eventListener: (TransactionEvent) -> Unit,
) {
    Box {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            item {
                Column(modifier = Modifier.padding(vertical = 16.dp)) {
                    Text(text = "DEPOSIT ACCOUNT")
                    AccountCard(account = state.defaultAccount, onCardClick = {})
                }
            }
            item {
                TextField(
                    modifier = Modifier.padding(vertical = 16.dp),
                    value = state.amountText,
                    onValueChange = {
                        eventListener(TransactionEvent.Ui.AmountValueChange(it))
                    },
                    label = { Text(text = "amount") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                )
            }
            item {
                TransactionsDropdownMenu(
                    state = state,
                    eventListener = eventListener,
                )
            }
        }

        WideButton(
            modifier = Modifier.align(Alignment.BottomCenter),
            text = "DEPOSIT",
            onClick = {
                eventListener(TransactionEvent.Ui.DepositButtonClick)
            }
        )

        WideButton(
            text = "WITHDRAW",
            onClick = {
                eventListener(TransactionEvent.Ui.WithdrawButtonClick)
            }
        )
    }
}


private suspend fun observeEffectsFlow(
    viewModel: TransactionViewModel,
    context: Context,
    navigateUp: () -> Unit,
) {
    viewModel.effectsFlow.collect { effect ->
        when (effect) {
            is TransactionEffect.CloseSelf -> navigateUp()
            is TransactionEffect.ShowCreationToast -> Toast.makeText(
                context,
                effect.message,
                Toast.LENGTH_SHORT
            ).show()

            is TransactionEffect.ShowError -> Toast.makeText(
                context,
                effect.message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}

