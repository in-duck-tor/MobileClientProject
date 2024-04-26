package com.ithirteeng.secondpatternsclientproject.features.common.login.presentation

import android.content.Intent
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseViewModel
import com.ithirteeng.secondpatternsclientproject.domain.theme.usecase.FetchApplicationThemeUseCase
import com.ithirteeng.secondpatternsclientproject.domain.user.model.Token
import com.ithirteeng.secondpatternsclientproject.domain.user.model.UserAuthData
import com.ithirteeng.secondpatternsclientproject.domain.user.usecase.GetUserAccountUseCase
import com.ithirteeng.secondpatternsclientproject.domain.user.usecase.LoginUseCase
import com.ithirteeng.secondpatternsclientproject.domain.user.usecase.SaveTokenLocallyUseCase
import com.ithirteeng.secondpatternsclientproject.domain.user.usecase.SaveUserLoginUseCase
import com.ithirteeng.secondpatternsclientproject.features.common.login.auth.AuthManager
import com.ithirteeng.secondpatternsclientproject.features.common.login.presentation.model.LoginEffect
import com.ithirteeng.secondpatternsclientproject.features.common.login.presentation.model.LoginEvent
import com.ithirteeng.secondpatternsclientproject.features.common.login.presentation.model.LoginState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(
    private val saveUserLoginUseCase: SaveUserLoginUseCase,
    private val saveTokenLocallyUseCase: SaveTokenLocallyUseCase,
    private val fetchApplicationThemeUseCase: FetchApplicationThemeUseCase,
    private val loginUseCase: LoginUseCase,
    private val getUserAccountUseCase: GetUserAccountUseCase,
    private val authManager: AuthManager,
) : BaseViewModel<LoginState, LoginEvent, LoginEffect>() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d(TAG, throwable.message.toString())
        addEffect(LoginEffect.ShowError(throwable.message ?: "SOME ERROR"))
    }

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
                    viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
                        val data = loginUseCase(
                            UserAuthData(
                                login = currentState.login.text,
                                password = currentState.password.text
                            )
                        )
                        saveUserLoginUseCase(data.login)
                        saveTokenLocallyUseCase(Token(data.token, data.token))
                        fetchApplicationThemeUseCase(data.login)
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

    fun getIntent(): Intent = authManager.getRequestIntent()

    private companion object {

        private const val TAG = "Login"
    }
}
