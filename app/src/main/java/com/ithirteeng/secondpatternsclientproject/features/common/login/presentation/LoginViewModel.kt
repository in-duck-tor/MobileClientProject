package com.ithirteeng.secondpatternsclientproject.features.common.login.presentation

import androidx.compose.ui.text.input.TextFieldValue
import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseViewModel
import com.ithirteeng.secondpatternsclientproject.features.common.login.presentation.model.LoginEffect
import com.ithirteeng.secondpatternsclientproject.features.common.login.presentation.model.LoginEvent
import com.ithirteeng.secondpatternsclientproject.features.common.login.presentation.model.LoginState

class LoginViewModel : BaseViewModel<LoginState, LoginEvent, LoginEffect>() {

    override fun initState(): LoginState = LoginState.Init

    override fun processEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.Init -> handleInit()
            is LoginEvent.Ui.LoginButtonClick -> handleLoginButtonClick()
            is LoginEvent.Ui.RegistrationButtonClick -> handleRegistrationButtonClick()
            else -> { /* todo: implement */ }
        }
    }

    private fun handleInit() {
        when (state.value) {
            is LoginState.Init -> updateState {
                LoginState.Content(
                    login = TextFieldValue(),
                    password = TextFieldValue(),
                )
            }
            else -> Unit
        }
    }

    private fun handleLoginButtonClick() {
        addEffect(LoginEffect.NavigateToMainScreen)
    }

    private fun handleRegistrationButtonClick() {
        addEffect(LoginEffect.NavigateToRegistrationScreen)
    }
}
