package com.ithirteeng.secondpatternsclientproject.features.client.myloans.createloan.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import com.ithirteeng.secondpatternsclientproject.common.uikit.components.WideButton
import com.ithirteeng.secondpatternsclientproject.features.client.myloans.createloan.presentation.CreateLoanViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreateLoanScreen(
    viewModel: CreateLoanViewModel = koinViewModel(),
    navigateUp: () -> Unit,
) {
    if (viewModel.isMade.collectAsState().value) {
        navigateUp()
    }
    var amount by remember { mutableStateOf("0.0") }
    Column {
        TextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("loan amount") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
        WideButton(
            text = "create loan",
            onClick = {
                viewModel.createLoan(amount = amount.toDouble())
            }
        )
    }
}