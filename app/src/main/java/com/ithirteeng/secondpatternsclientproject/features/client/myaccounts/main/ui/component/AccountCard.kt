package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.main.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ithirteeng.secondpatternsclientproject.R
import com.ithirteeng.secondpatternsclientproject.common.uikit.activeAccountColor
import com.ithirteeng.secondpatternsclientproject.common.uikit.inactiveAccountColor
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.main.presentation.model.AccountInfo

@Composable
fun AccountCard(
    accountInfo: AccountInfo,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        onClick = onCardClick
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
                    text = accountInfo.name,
                    style = MaterialTheme.typography.titleMedium
                )
                val color = if (accountInfo.isActive) activeAccountColor else inactiveAccountColor
                val text = if (accountInfo.isActive) {
                    stringResource(id = R.string.active)
                } else {
                    stringResource(id = R.string.inactive)
                }
                Text(
                    text = text,
                    color = color,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Text(
                text = stringResource(id = R.string.account_balance, accountInfo.balance),
                style = MaterialTheme.typography.bodyMedium
            )

        }
    }
}

@Composable
@Preview
private fun AccountCardPreview() {
    AccountCard(
        accountInfo = AccountInfo(
            name = "Namme",
            number = "234234234234234",
            isActive = true,
            balance = 12323452
        ),
        onCardClick = {}
    )

}