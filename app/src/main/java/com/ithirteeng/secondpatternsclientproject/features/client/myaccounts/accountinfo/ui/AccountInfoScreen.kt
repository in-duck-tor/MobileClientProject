package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.accountinfo.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ithirteeng.secondpatternsclientproject.common.uikit.components.LoadingComponent
import com.ithirteeng.secondpatternsclientproject.common.uikit.components.WideButton
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.AccountState
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.accountinfo.presentation.AccountInfoViewModel
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.accountinfo.presentation.model.AccountInfoEffect
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.accountinfo.presentation.model.AccountInfoEvent
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.accountinfo.presentation.model.AccountInfoState
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.accountinfo.ui.component.TransactionInfoComponent
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.main.ui.component.AccountCard
import org.koin.androidx.compose.koinViewModel

@Composable
fun AccountInfoScreen(
    accountNumber: String,
    navigateToSelfTransactionScreen: (accountNumber: String) -> Unit,
    navigateToGlobalTransactionScreen: (accountNumber: String) -> Unit,
    viewModel: AccountInfoViewModel = koinViewModel(),
) {
    val context = LocalContext.current
    LaunchedEffect(null) {
        viewModel.processEvent(AccountInfoEvent.Init(accountNumber))
        observeEffects(
            viewModel = viewModel,
            context = context,
            navigateToTransactionScreen = {
                navigateToSelfTransactionScreen(accountNumber)
            },
            navigateToGlobalTransactionScreen = {
                navigateToGlobalTransactionScreen(accountNumber)
            },
        )
    }

    when (val state = viewModel.state.collectAsState().value) {
        is AccountInfoState.Loading -> LoadingComponent()
        is AccountInfoState.Content -> MainContent(
            state = state,
            eventListener = viewModel::processEvent
        )
    }
}

@Composable
private fun MainContent(
    state: AccountInfoState.Content,
    eventListener: (AccountInfoEvent) -> Unit,
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            AccountCard(account = state.account, onCardClick = {})
        }
        item {
            ActionButtonsRow(state = state, eventListener = eventListener)
        }
        if (state.account.state == AccountState.active) {
            item {
                WideButton(
                    text = "Make transaction (SELF)",
                    onClick = { eventListener(AccountInfoEvent.Ui.MakeTransactionSelfButtonClick) },
                    modifier = Modifier.padding(16.dp)
                )
                WideButton(
                    text = "Make transaction (GLOBAL)",
                    onClick = { eventListener(AccountInfoEvent.Ui.MakeTransactionGlobalButtonClick) },
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
        items(state.transactions) { transaction ->
            TransactionInfoComponent(
                transaction = transaction,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }
}

@Composable
private fun ActionButtonsRow(
    state: AccountInfoState.Content,
    eventListener: (AccountInfoEvent) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        for (action in state.actions) {
            Button(onClick = {
                eventListener(AccountInfoEvent.Ui.ChangeAccountState(action))
            }) {
                Text(text = action.name)
            }
        }
    }
}

private suspend fun observeEffects(
    viewModel: AccountInfoViewModel,
    navigateToTransactionScreen: () -> Unit,
    navigateToGlobalTransactionScreen: () -> Unit,
    context: Context,
) {
    viewModel.effectsFlow.collect { effect ->
        when (effect) {
            is AccountInfoEffect.NavigateToSelfTransactionScreen -> navigateToTransactionScreen()
            is AccountInfoEffect.NavigateToGlobalTransactionScreen -> navigateToGlobalTransactionScreen()
            is AccountInfoEffect.ShowError -> Toast.makeText(
                context,
                context.getString(effect.stringResource) + ": " + effect.message,
                Toast.LENGTH_SHORT
            ).show()

        }

    }
}