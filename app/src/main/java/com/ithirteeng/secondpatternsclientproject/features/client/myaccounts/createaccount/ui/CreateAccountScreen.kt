package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.createaccount.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

@Composable
fun CreateAccountScreen(
    clientId: String,
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Text(text = "create account screen: $clientId")
    }
}