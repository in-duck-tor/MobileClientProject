package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.self.stub

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.self.presentation.model.SelfTransactionState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionsDropdownStubMenu(
    eventListener: (StubTransactionEvent) -> Unit,
    state: SelfTransactionState.Content,
) {
    var expanded by remember { mutableStateOf(false) }
    var scrollState = rememberScrollState()

    ExposedDropdownMenuBox(
        expanded = false,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = state.chosenAccount.number,
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor()
        )
        ExposedDropdownMenu(
            scrollState = scrollState,
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.height(300.dp),
        ) {
            state.accounts.forEach { account ->
                DropdownMenuItem(
                    text = {
                        Text(text = "NUMBER: ${account.number} \n AMOUNT: ${account.amount}")

                    },
                    onClick = {
                        eventListener(StubTransactionEvent.Ui.WithdrawAccountChoice(account))
                        expanded = false
                    }
                )
            }
        }
    }
}
