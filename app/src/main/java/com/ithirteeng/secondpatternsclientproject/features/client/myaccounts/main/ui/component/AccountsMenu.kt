package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.main.ui.component

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
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.main.presentation.model.AccountsFilter
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.main.presentation.model.MyAccountsMainEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountsMenu(
    eventListener: (MyAccountsMainEvent) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val filters = listOf(AccountsFilter.ALL, AccountsFilter.ACTIVE, AccountsFilter.INACTIVE)
    var selectedText by remember { mutableStateOf(filters[0]) }

    ExposedDropdownMenuBox(
        expanded = false,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = selectedText.name,
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            filters.forEach { filter ->
                DropdownMenuItem(
                    text = { Text(text = filter.name) },
                    onClick = {
                        selectedText = filter
                        expanded = false
                        eventListener(MyAccountsMainEvent.Ui.AccountsFilterChange(filter))
                    }
                )
            }
        }
    }
}
