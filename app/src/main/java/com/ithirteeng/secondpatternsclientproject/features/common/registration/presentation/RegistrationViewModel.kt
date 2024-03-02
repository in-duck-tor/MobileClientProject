package com.ithirteeng.secondpatternsclientproject.features.common.registration.presentation

import androidx.compose.ui.text.input.TextFieldValue
import com.ithirteeng.secondpatternsclientproject.R
import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseViewModel
import com.ithirteeng.secondpatternsclientproject.features.common.registration.presentation.model.RegistrationEffect
import com.ithirteeng.secondpatternsclientproject.features.common.registration.presentation.model.RegistrationEvent
import com.ithirteeng.secondpatternsclientproject.features.common.registration.presentation.model.RegistrationState

class RegistrationViewModel :
    BaseViewModel<RegistrationState, RegistrationEvent, RegistrationEffect>() {

    override fun initState(): RegistrationState = RegistrationState.Init

    override fun processEvent(event: RegistrationEvent) {
        when (event) {
            is RegistrationEvent.Init -> handleInit()
            is RegistrationEvent.Ui.RegisterButtonClick -> handleRegisterButtonClick()
            is RegistrationEvent.Ui.LoginButtonClick -> handleLoginButtonClick()
            else -> { /*todo: implement*/ }
        }
    }

    private fun handleInit() {
        when (state.value) {
            is RegistrationState.Init -> updateState {
                RegistrationState.Content(
                    login = TextFieldValue(),
                    email = TextFieldValue(),
                    password = TextFieldValue(),
                    repeatPassword = TextFieldValue(),
                )
            }
            else -> Unit
        }
    }

    private fun handleRegisterButtonClick() {
        addEffect(RegistrationEffect.ShowError(R.string.not_implemeted_error))
    }

    private fun handleLoginButtonClick() {
        addEffect(RegistrationEffect.NavigateToLoginScreen)
    }
}