package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.self.stub

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
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
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.self.presentation.model.SelfTransactionEffect
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.self.presentation.model.SelfTransactionState
import org.koin.androidx.compose.koinViewModel

@Composable
fun AccountsTransactionStubScreen(
    accountId: String,
    navigateUp: () -> Unit,
    viewModel: TransactionStubViewModel = koinViewModel(),
) {
    val context = LocalContext.current
    LaunchedEffect(null) {
        viewModel.processEvent(StubTransactionEvent.Init(accountId))
        observeEffectsFlow(viewModel, context, navigateUp)
    }

    when (val state = viewModel.state.collectAsState().value) {
        is SelfTransactionState.Loading -> LoadingComponent()
        is SelfTransactionState.Content -> Content(
            state = state,
            eventListener = viewModel::processEvent
        )
    }
}

@Composable
private fun Content(
    state: SelfTransactionState.Content,
    eventListener: (StubTransactionEvent) -> Unit,
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
                        eventListener(StubTransactionEvent.Ui.AmountValueChange(it))
                    },
                    label = { Text(text = "amount") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                )
            }
            item {
                TransactionsDropdownStubMenu(
                    state = state,
                    eventListener = eventListener,
                )
            }
        }

        Column(modifier = Modifier
            .align(Alignment.BottomCenter)
            .wrapContentHeight()) {
            WideButton(
                text = "DEPOSIT SINGLE",
                onClick = {
                    eventListener(StubTransactionEvent.Ui.DepositButtonClick)
                }
            )

            WideButton(
                text = "WITHDRAW SINGLE",
                onClick = {
                    eventListener(StubTransactionEvent.Ui.WithdrawButtonClick)
                }
            )

            WideButton(
                text = "TRANSFER",
                onClick = {
                    eventListener(StubTransactionEvent.Ui.TransferButtonClick)
                }
            )
        }

    }
}


private suspend fun observeEffectsFlow(
    viewModel: TransactionStubViewModel,
    context: Context,
    navigateUp: () -> Unit,
) {
    viewModel.effectsFlow.collect { effect ->
        when (effect) {
            is SelfTransactionEffect.CloseSelf -> navigateUp()
            is SelfTransactionEffect.ShowCreationToast -> Toast.makeText(
                context,
                effect.message,
                Toast.LENGTH_SHORT
            ).show()

            is SelfTransactionEffect.ShowError -> Toast.makeText(
                context,
                effect.message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}

