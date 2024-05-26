package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.main.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ithirteeng.secondpatternsclientproject.R
import com.ithirteeng.secondpatternsclientproject.common.uikit.activeAccountColor
import com.ithirteeng.secondpatternsclientproject.common.uikit.closedAccountColor
import com.ithirteeng.secondpatternsclientproject.common.uikit.frozenAccountColor
import com.ithirteeng.secondpatternsclientproject.common.uikit.hiddenColor
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.Account
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.AccountState

@Composable
fun AccountCard(
    account: Account,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier,
    onVisibilityChangeButtonClick: () -> Unit = {},
) {
    val strokeColor = if (account.isHidden) hiddenColor else Color.Transparent
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        onClick = onCardClick,
        border = BorderStroke(width = 2.dp, color = strokeColor)
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier
                        .padding(),
                    text = account.number,
                    style = MaterialTheme.typography.titleMedium
                )
                val color = when (account.state) {
                    AccountState.frozen -> frozenAccountColor
                    AccountState.closed -> closedAccountColor
                    AccountState.active -> activeAccountColor
                }
                Text(
                    text = account.state.name,
                    color = color,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Text(
                text = stringResource(id = R.string.account_balance, account.amount.toInt()),
                style = MaterialTheme.typography.bodyMedium
            )
            account.customComment?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Text(
                text = "Code: ${account.currencyCode}",
                style = MaterialTheme.typography.bodyMedium
            )
            val text = if (account.isHidden) "make visibile" else "make hidden"
            Button(
                onClick = onVisibilityChangeButtonClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(text = text)
            }
        }
    }
}

