package com.ithirteeng.secondpatternsclientproject.features.client.myloans.main.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ithirteeng.secondpatternsclientproject.common.uikit.components.LoadingComponent
import com.ithirteeng.secondpatternsclientproject.features.client.myloans.main.presentation.MyLoansMainViewModel
import com.ithirteeng.secondpatternsclientproject.features.client.myloans.main.presentation.model.MyLoansMainEffect
import com.ithirteeng.secondpatternsclientproject.features.client.myloans.main.presentation.model.MyLoansMainEvent
import com.ithirteeng.secondpatternsclientproject.features.client.myloans.main.presentation.model.MyLoansMainState
import com.ithirteeng.secondpatternsclientproject.features.client.myloans.main.ui.component.LoanCard
import com.ithirteeng.secondpatternsclientproject.features.client.myloans.main.ui.component.ProgramCard
import org.koin.androidx.compose.koinViewModel

@Composable
fun MyLoansMainScreen(
    viewModel: MyLoansMainViewModel = koinViewModel(),
    navigateToProgramInfoScreen: (programId: Long) -> Unit,
    navigateToLoanInfoScreen: (loanId: Long) -> Unit,
) {
    val context = LocalContext.current
    LaunchedEffect(null) {
        viewModel.processEvent(MyLoansMainEvent.Init)
        observeEffects(
            context = context,
            viewModel = viewModel,
            navigateToProgramInfoScreen = navigateToProgramInfoScreen,
            navigateToLoanInfoScreen = navigateToLoanInfoScreen,
        )
    }

    when (val state = viewModel.state.collectAsState().value) {
        is MyLoansMainState.Loading -> LoadingComponent()
        is MyLoansMainState.Content -> Content(
            state = state,
            eventListener = viewModel::processEvent
        )
    }
}

@Composable
private fun Content(
    state: MyLoansMainState.Content,
    eventListener: (MyLoansMainEvent) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp),
    ) {
        item {
            Text(text = "Loan Programs: ", style = MaterialTheme.typography.headlineMedium)
        }
        items(state.programs) { program ->
            ProgramCard(
                program = program,
                onCardClick = {
                    eventListener(MyLoansMainEvent.Ui.LoanProgramClick(programId = program.id))
                },
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }

        item {
            Text(text = "MyLoans: ", style = MaterialTheme.typography.headlineMedium)
        }
        items(state.loans) { loan ->
            LoanCard(
                loan = loan,
                onCardClick = {
                    eventListener(MyLoansMainEvent.Ui.LoanClick(loan.id))
                },
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}

private suspend fun observeEffects(
    context: Context,
    viewModel: MyLoansMainViewModel,
    navigateToProgramInfoScreen: (programId: Long) -> Unit,
    navigateToLoanInfoScreen: (loanId: Long) -> Unit,
) {

    viewModel.effectsFlow.collect { effect ->
        when (effect) {
            is MyLoansMainEffect.NavigateToLoanInfoScreen -> navigateToLoanInfoScreen(effect.loanId)
            is MyLoansMainEffect.NavigateToProgramInfoScreen -> navigateToProgramInfoScreen(effect.programId)
            is MyLoansMainEffect.ShowError -> Toast.makeText(
                context,
                effect.message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
