package com.ithirteeng.secondpatternsclientproject.features.common.settings.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ithirteeng.secondpatternsclientproject.domain.theme.model.Theme
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = koinViewModel(),
    navigateToLoginScreen: () -> Unit,
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
        Button(
            modifier = Modifier.padding(20.dp),
            onClick = {
                viewModel.wipeUserData()
                navigateToLoginScreen()
            }) {
            Text(text = "WipeData")

        }
    }
}