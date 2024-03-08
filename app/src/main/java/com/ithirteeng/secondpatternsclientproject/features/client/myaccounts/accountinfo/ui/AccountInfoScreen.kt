package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.accountinfo.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

@Composable
fun AccountInfoScreen(
    clientId: String,
    accountId: String,
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Text(text = "account: $clientId info: $accountId")
    }
}