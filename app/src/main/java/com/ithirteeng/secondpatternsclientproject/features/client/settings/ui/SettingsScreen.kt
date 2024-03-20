package com.ithirteeng.secondpatternsclientproject.features.client.settings.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.ithirteeng.secondpatternsclientproject.app.MainViewModel
import com.ithirteeng.secondpatternsclientproject.domain.theme.datasource.ThemeRemoteDatasource
import com.ithirteeng.secondpatternsclientproject.domain.theme.model.Theme
import com.ithirteeng.secondpatternsclientproject.domain.theme.usecase.SetApplicationThemeUseCase
import org.koin.androidx.compose.get

@Composable
fun SettingsScreen(
    clientId: String,
    setApplicationThemeUseCase: SetApplicationThemeUseCase = get(),
    themeRemoteDatasource: ThemeRemoteDatasource = get(),
) {
    Column {
        Button(onClick = {
            setApplicationThemeUseCase(Theme.DARK)
            themeRemoteDatasource.updateTheme(login = MainViewModel.LOGIN, newTheme = Theme.DARK)
        }) {
            Text(text = "DarkTheme")
        }
        Button(onClick = {
            setApplicationThemeUseCase(Theme.LIGHT)
            themeRemoteDatasource.updateTheme(login = MainViewModel.LOGIN, newTheme = Theme.LIGHT)
        }) {
            Text(text = "LightTheme")
        }
        Button(onClick = {
            setApplicationThemeUseCase(Theme.AUTO)
            themeRemoteDatasource.updateTheme(login = MainViewModel.LOGIN, newTheme = Theme.AUTO)
        }) {
            Text(text = "AutoTheme")
        }
    }
}