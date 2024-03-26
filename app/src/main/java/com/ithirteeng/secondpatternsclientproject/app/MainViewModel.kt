package com.ithirteeng.secondpatternsclientproject.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ithirteeng.secondpatternsclientproject.domain.theme.model.Theme
import com.ithirteeng.secondpatternsclientproject.domain.theme.usecase.ObserveApplicationThemeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val observeApplicationThemeUseCase: ObserveApplicationThemeUseCase,
) : ViewModel() {

    val themeState = MutableStateFlow(Theme.AUTO)

    init {
        observeTheme()
    }

    private fun observeTheme() {
        viewModelScope.launch {
            observeApplicationThemeUseCase().collect { theme ->
                themeState.update { theme }
            }
        }
    }
}
