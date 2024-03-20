package com.ithirteeng.secondpatternsclientproject.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ithirteeng.secondpatternsclientproject.domain.theme.datasource.ThemeRemoteDatasource
import com.ithirteeng.secondpatternsclientproject.domain.theme.model.Theme
import com.ithirteeng.secondpatternsclientproject.domain.theme.usecase.ObserveThemeUseCase
import com.ithirteeng.secondpatternsclientproject.domain.theme.usecase.SetApplicationThemeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val observeThemeUseCase: ObserveThemeUseCase,
    private val setApplicationThemeUseCase: SetApplicationThemeUseCase,
    private val themeRemoteDatasource: ThemeRemoteDatasource,
) : ViewModel() {

    val themeState = MutableStateFlow(Theme.AUTO)

    init {
        fetchTheme()
        observeTheme()
    }

    private fun observeTheme() {
        viewModelScope.launch {
            observeThemeUseCase().collect { theme ->
                themeState.update { theme }
            }
        }
    }

    private fun fetchTheme() {
        viewModelScope.launch(Dispatchers.IO) {
            val theme = themeRemoteDatasource.getTheme(LOGIN)
            if (theme == null) {
                themeRemoteDatasource.updateTheme(LOGIN, Theme.AUTO)
            } else {
                setApplicationThemeUseCase(theme)
            }
        }
    }

     companion object {

        const val LOGIN = "user_login_test"
    }
}
