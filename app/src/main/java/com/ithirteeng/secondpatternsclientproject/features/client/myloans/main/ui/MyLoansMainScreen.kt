package com.ithirteeng.secondpatternsclientproject.features.client.myloans.main.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ithirteeng.secondpatternsclientproject.common.uikit.components.LoadingComponent
import com.ithirteeng.secondpatternsclientproject.common.uikit.components.WideButton
import com.ithirteeng.secondpatternsclientproject.features.client.myloans.main.stub.LoansMainStubViewModel
import com.ithirteeng.secondpatternsclientproject.features.client.myloans.main.ui.component.LoanCard
import org.koin.androidx.compose.koinViewModel

@Composable
fun MyLoansMainScreen(
    viewModel: LoansMainStubViewModel = koinViewModel(),
    navigateToCreateLoanScreen: () -> Unit,
    navigateToLoanInfoScreen: (loanId: String) -> Unit,
) {
    LaunchedEffect(null) {
        viewModel.observeLoans()
    }

    val state = viewModel.state.collectAsState().value
    if (state.isLoading) {
        LoadingComponent()
    } else {
        Box {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                items(state.loans) { loan ->
                    LoanCard(
                        loan = loan,
                        onCardClick = { navigateToLoanInfoScreen(loan.id) },
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }

            WideButton(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                text = "CREATE LOAN",
                onClick = { navigateToCreateLoanScreen() }
            )
        }
    }


}