package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.main.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ithirteeng.secondpatternsclientproject.R
import com.ithirteeng.secondpatternsclientproject.common.uikit.components.LoadingComponent
import com.ithirteeng.secondpatternsclientproject.common.uikit.components.WideButton
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.main.presentation.MyAccountsMainViewModel
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.main.presentation.model.MyAccountsMainEffect
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.main.presentation.model.MyAccountsMainEvent
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.main.presentation.model.MyAccountsMainState
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.main.ui.component.AccountCard
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.main.ui.component.AccountsMenu
import org.koin.androidx.compose.koinViewModel

@Composable
fun MyAccountsMainScreen(
    clientId: String,
    navigateToAccountInfoScreen: (clientId: String, accountId: String) -> Unit,
    navigateToCreateAccountScreen: (clientId: String) -> Unit,
    viewModel: MyAccountsMainViewModel = koinViewModel(),
) {
    val context = LocalContext.current

    LaunchedEffect(null) {
        viewModel.processEvent(MyAccountsMainEvent.Init(clientId))
        observeEffects(
            viewModel = viewModel,
            navigateToAccountInfoScreen = navigateToAccountInfoScreen,
            navigateToCreateAccountScreen = navigateToCreateAccountScreen,
            context = context,
        )
    }

    when (val state = viewModel.state.collectAsState().value) {
        is MyAccountsMainState.Loading -> LoadingComponent()
        is MyAccountsMainState.Content -> MainContent(
            state = state,
            eventListener = viewModel::processEvent
        )
    }
}

@Composable
private fun MainContent(
    state: MyAccountsMainState.Content,
    eventListener: (MyAccountsMainEvent) -> Unit,
) {
    Box {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 16.dp),
                    text = stringResource(id = R.string.accounts),
                    style = MaterialTheme.typography.headlineLarge
                )
                AccountsMenu(eventListener = eventListener)
            }

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .padding(start = 16.dp, end = 16.dp),
                contentPadding = PaddingValues(bottom = 64.dp)
            ) {
                items(state.accounts) { account ->
                    AccountCard(
                        account = account,
                        onCardClick = {
                            eventListener(MyAccountsMainEvent.Ui.AccountClick(account.number))
                        },
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                    )
                }
            }
        }
        WideButton(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomCenter),
            text = stringResource(id = R.string.create_account),
            onClick = { eventListener(MyAccountsMainEvent.Ui.CreateAccountButtonClick) }
        )
    }
}

suspend fun observeEffects(
    viewModel: MyAccountsMainViewModel,
    navigateToAccountInfoScreen: (clientId: String, accountId: String) -> Unit,
    navigateToCreateAccountScreen: (clientId: String) -> Unit,
    context: Context,
) {
    viewModel.effectsFlow.collect { effect ->
        when (effect) {
            is MyAccountsMainEffect.NavigateToAccountInfoScreen -> navigateToAccountInfoScreen(
                effect.clientId,
                effect.accountId,
            )

            is MyAccountsMainEffect.NavigateToCreateAccountScreen -> navigateToCreateAccountScreen(
                effect.clientId
            )

            is MyAccountsMainEffect.ShowError -> Toast.makeText(
                context,
                context.getString(effect.stringResource),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
