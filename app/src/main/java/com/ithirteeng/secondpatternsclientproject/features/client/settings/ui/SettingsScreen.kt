package com.ithirteeng.secondpatternsclientproject.features.client.settings.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun SettingsScreen(
    clientId: String
) {
    Box {
        Text(text = "Settings: $clientId")
    }
}