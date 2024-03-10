package com.ithirteeng.secondpatternsclientproject.features.client.myloans.loaninfo.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ithirteeng.secondpatternsclientproject.common.uikit.components.LoadingComponent
import com.ithirteeng.secondpatternsclientproject.common.uikit.components.WideButton
import com.ithirteeng.secondpatternsclientproject.features.client.myloans.loaninfo.presentation.LoanInfoViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoanInfoScreen(
    loanId: String,
    viewModel: LoanInfoViewModel = koinViewModel(),
    navigateUp: () -> Unit,
) {
    LaunchedEffect(null) {
        viewModel.getAccount(loanId)
    }

    val state = viewModel.state.collectAsState().value

    if (state.isLoading) {
        LoadingComponent()
    } else {
        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            item {
                Text(
                    text = "AMOUNT: ${state.loan?.amount}",
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            item {
                Text(
                    text = "ID: ${state.loan?.id}",
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            item {
                Text(
                    text = "CREATED AT: ${state.loan?.createdAt}",
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            item {
                Text(
                    text = "AMOUNT: ${state.loan?.amount}",
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            item {
                WideButton(
                    text = "CLOSE ACCOUNT",
                    onClick = { viewModel.deleteAccount(loanId) }
                )
            }
        }
        if (state.isDeleted && state.isDeletedButtonClick) {
            navigateUp()
        }
    }
}