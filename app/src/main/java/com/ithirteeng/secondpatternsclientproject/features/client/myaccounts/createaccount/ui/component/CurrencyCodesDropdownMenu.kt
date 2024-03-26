package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.createaccount.ui.component

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
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.createaccount.presentation.model.CreateAccountEvent
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.createaccount.presentation.model.CreateAccountState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyCodesDropdownMenu(
    modifier: Modifier = Modifier,
    eventListener: (CreateAccountEvent) -> Unit,
    state: CreateAccountState.Content,
) {
    var expanded by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = false,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = state.chosenCurrencyCode,
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
            state.currencyCodes.forEach { code ->
                DropdownMenuItem(
                    text = {
                        Text(text = code)
                    },
                    onClick = {
                        eventListener(CreateAccountEvent.Ui.ChooseCurrencyCode(code))
                        expanded = false
                    }
                )
            }
        }
    }
}
