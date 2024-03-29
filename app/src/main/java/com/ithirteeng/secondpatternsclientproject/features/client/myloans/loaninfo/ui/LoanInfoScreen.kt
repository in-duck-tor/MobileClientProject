package com.ithirteeng.secondpatternsclientproject.features.client.myloans.loaninfo.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ithirteeng.secondpatternsclientproject.common.uikit.components.LoadingComponent
import com.ithirteeng.secondpatternsclientproject.common.uikit.components.WideButton
import com.ithirteeng.secondpatternsclientproject.features.client.myloans.loaninfo.presentation.LoanInfoViewModel
import com.ithirteeng.secondpatternsclientproject.features.client.myloans.loaninfo.presentation.model.LoanInfoEffect
import com.ithirteeng.secondpatternsclientproject.features.client.myloans.loaninfo.presentation.model.LoanInfoEvent
import com.ithirteeng.secondpatternsclientproject.features.client.myloans.loaninfo.presentation.model.LoanInfoState
import com.ithirteeng.secondpatternsclientproject.features.client.myloans.loaninfo.ui.component.LoanInfoDropdownMenu
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoanInfoScreen(
    loanId: Long,
    viewModel: LoanInfoViewModel = koinViewModel(),
) {
    val context = LocalContext.current

    LaunchedEffect(null) {
        viewModel.processEvent(LoanInfoEvent.Init(loanId = loanId))
        observeEffects(viewModel, context)
    }

    when (val state = viewModel.state.collectAsState().value) {
        is LoanInfoState.Loading -> LoadingComponent()
        is LoanInfoState.Content -> Content(
            state = state,
            eventListener = viewModel::processEvent
        )
    }
}

@Composable
private fun Content(
    state: LoanInfoState.Content,
    eventListener: (LoanInfoEvent) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        item {
            Text(
                text = "BorrowedAmount: ${state.loanInfo.borrowedAmount}",
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
        item {
            Text(
                text = "InterestRate: ${state.loanInfo.interestRate}",
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
        item {
            Text(
                text = "Planned Payment Number: ${state.loanInfo.plannedPaymentsNumber}",
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
        item {
            Text(
                text = "Номер привязанного аккаунта: ${state.loanInfo.clientAccountNumber}",
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
        item {
            Text(
                text = "Дата взятия кредита: ${state.loanInfo.borrowingDate}",
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
        item {
            Text(
                text = "Остаток: ${state.loanInfo.loanBody}",
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
        item {
            Text(
                text = "Задолженность по кредиту: ${state.loanInfo.loanDebt}",
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
        item {
            Text(
                text = "Штраф по задолженности: ${state.loanInfo.penalty}",
                modifier = Modifier.padding(vertical = 4.dp),
                color = Color.Red
            )
        }
        item {
            TextField(
                modifier = Modifier.padding(vertical = 16.dp),
                value = state.amountText,
                onValueChange = {
                    eventListener(LoanInfoEvent.Ui.AmountTextValueChange(it))
                },
                label = { Text(text = "amount") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )
        }
        item {
            LoanInfoDropdownMenu(
                eventListener = eventListener,
                state = state
            )
        }
        item {
            WideButton(
                text = "Make payment",
                onClick = { eventListener(LoanInfoEvent.Ui.MakePaymentButtonClick) })
        }
    }
}

private suspend fun observeEffects(
    viewModel: LoanInfoViewModel,
    context: Context
) {
    viewModel.effectsFlow.collect { effect ->
        when (effect) {
            is LoanInfoEffect.ShowError -> Toast.makeText(
                context,
                effect.message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}