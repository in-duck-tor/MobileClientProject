package com.ithirteeng.secondpatternsclientproject.features.client.myloans.programinfo.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ithirteeng.secondpatternsclientproject.common.uikit.components.LoadingComponent
import com.ithirteeng.secondpatternsclientproject.common.uikit.components.WideButton
import com.ithirteeng.secondpatternsclientproject.features.client.myloans.programinfo.presentation.ProgramInfoViewModel
import com.ithirteeng.secondpatternsclientproject.features.client.myloans.programinfo.presentation.model.ProgramInfoEffect
import com.ithirteeng.secondpatternsclientproject.features.client.myloans.programinfo.presentation.model.ProgramInfoEvent
import com.ithirteeng.secondpatternsclientproject.features.client.myloans.programinfo.presentation.model.ProgramInfoState
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProgramInfoScreen(
    programId: Int,
    navigateToCreateLoanScreen: (programId: Int) -> Unit,
    viewModel: ProgramInfoViewModel = koinViewModel(),
) {
    val context = LocalContext.current

    LaunchedEffect(null) {
        viewModel.processEvent(ProgramInfoEvent.Init(programId))
        observeEffects(context, viewModel, navigateToCreateLoanScreen)
    }

    when (val state = viewModel.state.collectAsState().value) {
        is ProgramInfoState.Loading -> LoadingComponent()
        is ProgramInfoState.Content -> Content(
            state = state,
            eventListener = viewModel::processEvent
        )
    }
}

@Composable
private fun Content(
    state: ProgramInfoState.Content,
    eventListener: (ProgramInfoEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Процентная ставка: ${state.program.interestRate}",
            modifier = Modifier.padding(vertical = 4.dp),
            color = Color.Red
        )
        Text(
            text = "PaymentType: ${state.program.paymentType}",
            modifier = Modifier.padding(vertical = 4.dp),
        )
        Text(
            text = "Платежный график: ${state.program.paymentScheduleType}",
            modifier = Modifier.padding(vertical = 4.dp),
        )
        Text(
            text = "Длительность расчетного периода: ${state.program.periodInterval}",
            modifier = Modifier.padding(vertical = 4.dp),
            color = Color.Red
        )
        WideButton(
            text = "Create Loan",
            onClick = { eventListener(ProgramInfoEvent.Ui.CreateLoanButtonClick) }
        )
    }
}

private suspend fun observeEffects(
    context: Context,
    viewModel: ProgramInfoViewModel,
    navigateToCreateLoanScreen: (Int) -> Unit,
) {
    viewModel.effectsFlow.collect { effect ->
        when (effect) {
            is ProgramInfoEffect.NavigateToCreateLoanScreen -> navigateToCreateLoanScreen(effect.programId)
            is ProgramInfoEffect.ShowError -> Toast.makeText(
                context,
                effect.message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}