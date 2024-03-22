package com.ithirteeng.secondpatternsclientproject.features.client.settings.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.ithirteeng.secondpatternsclientproject.app.MainViewModel
import com.ithirteeng.secondpatternsclientproject.domain.theme.model.Theme
import com.ithirteeng.secondpatternsclientproject.domain.theme.usecase.FetchApplicationThemeUseCase
import com.ithirteeng.secondpatternsclientproject.domain.theme.usecase.SetApplicationThemeUseCase
import org.koin.androidx.compose.get

@Composable
fun SettingsScreen(
    setApplicationThemeUseCase: SetApplicationThemeUseCase = get(),
    fetchApplicationThemeUseCase: FetchApplicationThemeUseCase = get(),
) {
    var needToBeFetched by remember {
        mutableStateOf(0)
    }

    LaunchedEffect(needToBeFetched) {
        if (needToBeFetched == 1) {
            fetchApplicationThemeUseCase(MainViewModel.LOGIN)
            needToBeFetched = 0
        }
    }

    Column {
        Button(onClick = {
            setApplicationThemeUseCase(Theme.DARK)
            needToBeFetched = 1
        }) {
            Text(text = "DarkTheme")
        }
        Button(onClick = {
            setApplicationThemeUseCase(Theme.LIGHT)
            needToBeFetched = 1
        }) {
            Text(text = "LightTheme")

        }
        Button(onClick = {
            setApplicationThemeUseCase(Theme.AUTO)
            needToBeFetched = 1
        }) {
            Text(text = "AutoTheme")

        }
    }
}