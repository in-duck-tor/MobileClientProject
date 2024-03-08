package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.main.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

@Composable
fun MyAccountsMainScreen(
    clientId: String,
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Text(text = "MyAccounts: $clientId")
    }
}