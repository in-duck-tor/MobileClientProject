package com.ithirteeng.secondpatternsclientproject.features.client.myloans.main.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.ithirteeng.secondpatternsclientproject.domain.loans.model.program.LoanProgramResponse

@Composable
fun ProgramCard(
    onCardClick: () -> Unit,
    program: LoanProgramResponse,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        onClick = onCardClick,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(
                text = "InterestRate: ${program.interestRate}",
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Text(
                text = "Payment type: ${program.paymentType}",
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Text(
                text = "Schedule payment type: ${program.paymentScheduleType}",
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Text(
                text = "Период: ${program.periodInterval}",
                modifier = Modifier.padding(vertical = 4.dp),
                textDecoration = TextDecoration.Underline
            )
        }
    }
}