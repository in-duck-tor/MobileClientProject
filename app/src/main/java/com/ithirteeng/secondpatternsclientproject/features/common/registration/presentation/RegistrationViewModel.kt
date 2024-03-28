package com.ithirteeng.secondpatternsclientproject.features.common.registration.presentation

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseViewModel
import com.ithirteeng.secondpatternsclientproject.domain.theme.usecase.FetchApplicationThemeUseCase
import com.ithirteeng.secondpatternsclientproject.domain.theme.usecase.SetApplicationThemeUseCase
import com.ithirteeng.secondpatternsclientproject.domain.user.model.Token
import com.ithirteeng.secondpatternsclientproject.domain.user.usecase.SaveTokenLocallyUseCase
import com.ithirteeng.secondpatternsclientproject.domain.user.usecase.SaveUserLoginUseCase
import com.ithirteeng.secondpatternsclientproject.features.common.login.presentation.LoginViewModel
import com.ithirteeng.secondpatternsclientproject.features.common.registration.presentation.model.RegistrationEffect
import com.ithirteeng.secondpatternsclientproject.features.common.registration.presentation.model.RegistrationEvent
import com.ithirteeng.secondpatternsclientproject.features.common.registration.presentation.model.RegistrationState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegistrationViewModel(
    private val setApplicationThemeUseCase: SetApplicationThemeUseCase,
    private val fetchApplicationThemeUseCase: FetchApplicationThemeUseCase,
    private val saveTokenLocallyUseCase: SaveTokenLocallyUseCase,
    private val saveUserLoginUseCase: SaveUserLoginUseCase,
) :
    BaseViewModel<RegistrationState, RegistrationEvent, RegistrationEffect>() {

    override fun initState(): RegistrationState = RegistrationState.Init

    override fun processEvent(event: RegistrationEvent) {
        when (event) {
            is RegistrationEvent.Init -> handleInit()
            is RegistrationEvent.Ui.RegisterButtonClick -> handleRegisterButtonClick()
            is RegistrationEvent.Ui.LoginButtonClick -> handleLoginButtonClick()
            is RegistrationEvent.Ui.LoginFieldChange -> handleLoginFieldChange(event)
            is RegistrationEvent.Ui.PasswordFieldChange -> handlePasswordFieldChange(event)
            is RegistrationEvent.Ui.RepeatPasswordFieldChange -> handleRepeatPasswordFieldChange(
                event
            )
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

    private fun handleLoginFieldChange(event: RegistrationEvent.Ui.LoginFieldChange) {
        when (val currentState = state.value) {
            is RegistrationState.Content -> updateState {
                currentState.copy(login = event.value)
            }

            is RegistrationState.Init -> Unit
        }
    }

    private fun handlePasswordFieldChange(event: RegistrationEvent.Ui.PasswordFieldChange) {
        when (val currentState = state.value) {
            is RegistrationState.Content -> updateState {
                currentState.copy(password = event.value)
            }

            is RegistrationState.Init -> Unit
        }
    }

    private fun handleRepeatPasswordFieldChange(event: RegistrationEvent.Ui.RepeatPasswordFieldChange) {
        when (val currentState = state.value) {
            is RegistrationState.Content -> updateState {
                currentState.copy(repeatPassword = event.value)
            }

            is RegistrationState.Init -> Unit
        }
    }

    private fun handleRegisterButtonClick() {
        when (val currentState = state.value) {
            is RegistrationState.Content -> {
                if (currentState.login.text.isEmpty() || currentState.password.text.isEmpty()) {
                    addEffect(RegistrationEffect.ShowError("Fields mustn't be empty!"))
                } else {
                    if (currentState.password.text != currentState.repeatPassword.text) {
                        addEffect(RegistrationEffect.ShowError("Password must be equal"))
                    } else {
                        //todo implement registration logic
                        viewModelScope.launch(Dispatchers.IO) {
                            saveTokenLocallyUseCase(
                                Token(LoginViewModel.TOKEN, LoginViewModel.TOKEN)
                            )
                            saveUserLoginUseCase(LoginViewModel.LOGIN)
                            //setApplicationThemeUseCase(Theme.AUTO)
                            fetchApplicationThemeUseCase(LoginViewModel.LOGIN)
                            //TODO: with new login fetchApplicationThemeUseCase()
                            addEffect(RegistrationEffect.NavigateToMainScreen)
                        }
                    }
                }
            }

            is RegistrationState.Init -> Unit
        }
    }

    private fun handleLoginButtonClick() {
        addEffect(RegistrationEffect.NavigateToLoginScreen)
    }
}