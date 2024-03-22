package com.ithirteeng.secondpatternsclientproject.features.client.settings.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.ithirteeng.secondpatternsclientproject.domain.theme.model.Theme
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = koinViewModel(),
) {
    Column {
        Button(onClick = {
            viewModel.fetchApplicationTheme(Theme.DARK)
        }) {
            Text(text = "DarkTheme")
        }
        Button(onClick = {
            viewModel.fetchApplicationTheme(Theme.LIGHT)
        }) {
            Text(text = "LightTheme")

        }
        Button(onClick = {
            viewModel.fetchApplicationTheme(Theme.AUTO)
        }) {
            Text(text = "AutoTheme")

        }
    }
}