package com.ithirteeng.secondpatternsclientproject.features.common.login.presentation

import androidx.lifecycle.viewModelScope
import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseViewModel
import com.ithirteeng.secondpatternsclientproject.domain.theme.usecase.FetchApplicationThemeUseCase
import com.ithirteeng.secondpatternsclientproject.domain.user.model.Token
import com.ithirteeng.secondpatternsclientproject.domain.user.usecase.SaveTokenLocallyUseCase
import com.ithirteeng.secondpatternsclientproject.domain.user.usecase.SaveUserLoginUseCase
import com.ithirteeng.secondpatternsclientproject.features.common.login.presentation.model.LoginEffect
import com.ithirteeng.secondpatternsclientproject.features.common.login.presentation.model.LoginEvent
import com.ithirteeng.secondpatternsclientproject.features.common.login.presentation.model.LoginState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(
    private val saveUserLoginUseCase: SaveUserLoginUseCase,
    private val saveTokenLocallyUseCase: SaveTokenLocallyUseCase,
    private val fetchApplicationThemeUseCase: FetchApplicationThemeUseCase,
) : BaseViewModel<LoginState, LoginEvent, LoginEffect>() {

    override fun initState(): LoginState = LoginState.Init

    override fun processEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.Init -> handleInit()
            is LoginEvent.Ui.LoginButtonClick -> handleLoginButtonClick()
            is LoginEvent.Ui.RegistrationButtonClick -> handleRegistrationButtonClick()
            is LoginEvent.Ui.LoginFieldChange -> handleLoginFieldChange(event)
            is LoginEvent.Ui.PasswordFieldChange -> handlePasswordFieldChange(event)
        }
    }

    private fun handleInit() {
        when (state.value) {
            is LoginState.Init -> updateState {
                LoginState.Content()
            }

            else -> Unit
        }
    }

    private fun handleLoginButtonClick() {
        when (val currentState = state.value) {
            is LoginState.Content -> {
                if (currentState.login.text.isEmpty() || currentState.password.text.isEmpty()) {
                    addEffect(LoginEffect.ShowError("Fields mustn't be empty!"))
                } else {
                    //todo implement login logic
                    viewModelScope.launch(Dispatchers.IO) {
                        saveTokenLocallyUseCase(Token(TOKEN, TOKEN))
                        saveUserLoginUseCase(LOGIN)
                        fetchApplicationThemeUseCase(LOGIN)
                        addEffect(LoginEffect.NavigateToMainScreen)
                    }
                }
            }

            is LoginState.Init -> Unit
        }
    }

    private fun handleRegistrationButtonClick() {
        addEffect(LoginEffect.NavigateToRegistrationScreen)
    }

    private fun handleLoginFieldChange(event: LoginEvent.Ui.LoginFieldChange) {
        when (val currentState = state.value) {
            is LoginState.Content -> updateState {
                currentState.copy(login = event.value)
            }

            is LoginState.Init -> Unit
        }
    }

    private fun handlePasswordFieldChange(event: LoginEvent.Ui.PasswordFieldChange) {
        when (val currentState = state.value) {
            is LoginState.Content -> updateState {
                currentState.copy(password = event.value)
            }

            is LoginState.Init -> Unit
        }
    }

    private companion object {

        const val LOGIN = "user_login_test"
        const val TOKEN =
            "eyJhbGciOiJub25lIiwidHlwIjoiSldUIn0.eyJzdWIiOiIxIiwibG9naW4iOiJ0ZXN0X2JhbmtfY2xpZW50IiwiYWNjb3VudF90eXBlIjoiY2xpZW50IiwiY2xpZW50X2lkIjoiZnJvbnQiLCJuYmYiOjE3MDk5ODMwNjQsImV4cCI6MTcxNzc1OTA2NCwiaWF0IjoxNzA5OTgzMDY0LCJpc3MiOiJpbi1kdWNrLXRvciIsImF1ZCI6ImluLWR1Y2stdG9yIn0."
    }
}
