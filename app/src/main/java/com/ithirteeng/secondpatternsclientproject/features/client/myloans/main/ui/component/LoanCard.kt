package com.ithirteeng.secondpatternsclientproject.features.client.myloans.main.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ithirteeng.secondpatternsclientproject.domain.loans.model.loan.LoanInfoShort
import java.math.BigDecimal

@Composable
fun LoanCard(
    onCardClick: () -> Unit,
    loan: LoanInfoShort,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = onCardClick,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(
                text = "BorrowedAmount: ${BigDecimal(loan.borrowedAmount).toPlainString()}",
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Text(
                text = "InterestRate: ${loan.interestRate}",
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Text(
                text = "Planned Payment Number: ${loan.plannedPaymentsNumber}",
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Text(
                text = "Остаток: ${BigDecimal(loan.loanBody).toPlainString()}",
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Text(
                text = "Задолженность по кредиту: ${BigDecimal(loan.loanDebt).toPlainString()}",
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Text(
                text = "Штраф по задолженности: ${loan.penalty}",
                modifier = Modifier.padding(vertical = 4.dp),
                color = Color.Red
            )
        }
    }
}
