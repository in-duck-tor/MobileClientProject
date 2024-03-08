package com.ithirteeng.secondpatternsclientproject.features.client.myloans.main.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun MyLoansScreen(
    clientId: String
) {
    Box {
        Text(text = "MyLoans: $clientId")
    }
}