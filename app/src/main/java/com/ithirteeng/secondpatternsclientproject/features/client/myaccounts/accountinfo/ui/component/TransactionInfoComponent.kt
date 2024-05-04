package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.accountinfo.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.transaction.Transaction

@Composable
fun TransactionInfoComponent(
    transaction: Transaction,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(text = transaction.id)
        Text(text = "NAME: ${transaction.type.name}")
        Text(text = "AMOUNT: ${transaction.depositOn?.amount ?: transaction.withdrawFrom?.amount}")
        Text(text = "CODE: ${transaction.depositOn?.currencyCode ?: transaction.withdrawFrom?.currencyCode}")
        Text(text = "ON: ${transaction.depositOn?.accountNumber}")
        Text(text = "FROM: ${transaction.withdrawFrom?.accountNumber}")
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.primary
        )
    }
}