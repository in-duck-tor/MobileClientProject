package com.ithirteeng.secondpatternsclientproject.app

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ithirteeng.secondpatternsclientproject.domain.exchange.usecase.GetCurrencyRatesUseCase
import com.ithirteeng.secondpatternsclientproject.domain.theme.model.Theme
import com.ithirteeng.secondpatternsclientproject.domain.theme.usecase.FetchApplicationThemeUseCase
import com.ithirteeng.secondpatternsclientproject.domain.theme.usecase.ObserveApplicationThemeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val observeApplicationThemeUseCase: ObserveApplicationThemeUseCase,
    private val fetchApplicationThemeUseCase: FetchApplicationThemeUseCase,
    private val getCurrencyRatesUseCase: GetCurrencyRatesUseCase,
) : ViewModel() {

    val themeState = MutableStateFlow(Theme.AUTO)

    init {
        fetchTheme()
        observeTheme()
        getRates()
    }

    private fun getRates() {
        viewModelScope.launch(Dispatchers.IO) {
            getCurrencyRatesUseCase()
                .onSuccess {
                    Log.d(TAG, it.toString())
                }
        }
    }

    private fun observeTheme() {
        viewModelScope.launch {
            observeApplicationThemeUseCase().collect { theme ->
                themeState.update { theme }
            }
        }
    }

    private fun fetchTheme() {
        viewModelScope.launch(Dispatchers.IO) {
            fetchApplicationThemeUseCase(LOGIN)
                .onFailure {
                    Log.d(TAG, "failed to fetch theme")
                }
        }
    }

    companion object {

        private const val TAG = "MainViewModel"
        const val LOGIN = "user_login_test"
    }
}
