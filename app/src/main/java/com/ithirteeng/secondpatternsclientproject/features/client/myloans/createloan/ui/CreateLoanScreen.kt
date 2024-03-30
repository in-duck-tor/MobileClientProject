package com.ithirteeng.secondpatternsclientproject.features.client.myloans.createloan.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ithirteeng.secondpatternsclientproject.common.uikit.components.LoadingComponent
import com.ithirteeng.secondpatternsclientproject.common.uikit.components.WideButton
import com.ithirteeng.secondpatternsclientproject.features.client.myloans.createloan.presentation.CreateLoanViewModel
import com.ithirteeng.secondpatternsclientproject.features.client.myloans.createloan.presentation.model.CreateLoanEffect
import com.ithirteeng.secondpatternsclientproject.features.client.myloans.createloan.presentation.model.CreateLoanEvent
import com.ithirteeng.secondpatternsclientproject.features.client.myloans.createloan.presentation.model.CreateLoanState
import com.ithirteeng.secondpatternsclientproject.features.client.myloans.createloan.ui.components.CreateLoanDropdownMenu
import com.ithirteeng.secondpatternsclientproject.features.client.myloans.main.ui.component.ProgramCard
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreateLoanScreen(
    programId: Long,
    viewModel: CreateLoanViewModel = koinViewModel(),
    navigateUp: () -> Unit,
) {
    val context = LocalContext.current

    LaunchedEffect(null) {
        viewModel.processEvent(CreateLoanEvent.Init(programId))
        observeEffects(context, viewModel, navigateUp)
    }

    when (val state = viewModel.state.collectAsState().value) {
        is CreateLoanState.Loading -> LoadingComponent()
        is CreateLoanState.Content -> Content(
            state = state,
            eventListener = viewModel::processEvent
        )
    }
}

@Composable
private fun Content(
    state: CreateLoanState.Content,
    eventListener: (CreateLoanEvent) -> Unit,
) {
    Column {
        ProgramCard(
            onCardClick = { },
            program = state.program
        )
        TextField(
            modifier = Modifier.padding(vertical = 16.dp),
            value = state.amountText,
            onValueChange = {
                eventListener(CreateLoanEvent.Ui.AmountTextValueChange(it))
            },
            label = { Text(text = "amount") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )

        TextField(
            modifier = Modifier.padding(vertical = 16.dp),
            value = state.timeText,
            onValueChange = {
                eventListener(CreateLoanEvent.Ui.TimeTextValueChange(it))
            },
            label = { Text(text = "time in months") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )

        CreateLoanDropdownMenu(eventListener = eventListener, state = state)

        WideButton(
            text = "SubmitLoan",
            onClick = { eventListener(CreateLoanEvent.Ui.CreateLoanButtonClick) }
        )
    }
}

private suspend fun observeEffects(
    context: Context,
    viewModel: CreateLoanViewModel,
    closeSelf: () -> Unit,
) {
    viewModel.effectsFlow.collect { effect ->
        when (effect) {
            is CreateLoanEffect.CloseSelf -> closeSelf()
            is CreateLoanEffect.ShowError -> Toast.makeText(
                context,
                effect.message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}