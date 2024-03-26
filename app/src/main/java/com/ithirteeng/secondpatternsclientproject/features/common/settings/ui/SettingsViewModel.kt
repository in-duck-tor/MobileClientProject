package com.ithirteeng.secondpatternsclientproject.features.common.settings.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ithirteeng.secondpatternsclientproject.app.MainViewModel
import com.ithirteeng.secondpatternsclientproject.domain.theme.model.Theme
import com.ithirteeng.secondpatternsclientproject.domain.theme.usecase.FetchApplicationThemeUseCase
import com.ithirteeng.secondpatternsclientproject.domain.theme.usecase.SetApplicationThemeUseCase
import com.ithirteeng.secondpatternsclientproject.domain.user.usecase.GetUserLoginUseCase
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val setApplicationThemeUseCase: SetApplicationThemeUseCase,
    private val fetchApplicationThemeUseCase: FetchApplicationThemeUseCase,
    private val getUserLoginUseCase: GetUserLoginUseCase
) : ViewModel() {

    fun fetchApplicationTheme(theme: Theme) {
        viewModelScope.launch {
            setApplicationThemeUseCase(theme)
            fetchApplicationThemeUseCase(getUserLoginUseCase())
        }
    }
}