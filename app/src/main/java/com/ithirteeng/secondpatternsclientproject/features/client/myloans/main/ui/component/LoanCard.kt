package com.ithirteeng.secondpatternsclientproject.features.client.myloans.main.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.unit.dp
import com.ithirteeng.secondpatternsclientproject.domain.loans.model.base.Loan

@Composable
fun LoanCard(
    loan: Loan,
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
            Text(
                modifier = Modifier
                    .padding(),
                text = "Amount: ${loan.amount}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "PAYMENT: ${loan.amount}",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = loan.id,
                style = MaterialTheme.typography.bodyMedium
            )


        }
    }
}

