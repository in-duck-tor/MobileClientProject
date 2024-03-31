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

    companion object {

        const val LOGIN = "user_login_test"
        const val TOKEN = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MTI0ODQyMjcsImV4cCI6MTcyMjQ4NDIyNywiaXNzIjoiaW4tZHVjay10b3IiLCJjbGllbnRfaWQiOiJhbmd1bGFyX3NwYSIsInN1YiI6IjEiLCJhdXRoX3RpbWUiOjE3MTI0ODQyMjcsImlkcCI6ImxvY2FsIiwiYWNjb3VudF90eXBlIjoiY2xpZW50IiwibG9naW4iOiJndWxldnNraXkuaXZhbiIsInJvbGVzIjoiY2xpZW50IiwianRpIjoiRjJGNjg0OUVBNzYxREVEOERCMzVFOEYzQkZCMjIyRDgiLCJzaWQiOiJBQkQ0QTRFRUFFMUMxMTA2QjE5MUU1NzdBMkUwNzBCMCIsImlhdCI6MTcxMjQ4NDIyNywic2NvcGUiOlsib3BlbmlkIiwicHJvZmlsZSIsImVtYWlsIl0sImFtciI6WyJwd2QiXX0.BVgN7S8tYwb4DNY06UhR-oC0eZbT3pDsVDMWST3Y9nGFQ5JOwQtcvWay0hfmWTjl32KKkniOPsfQhTRwXbzp1yW1ksPOcS0mnP9FRVrXQHL3rqBTopXrTNoOtmJzO4RQsQJG5pi9HgSiJxP3YBTfjwDsJzlizHRhSCIFeOQPDRP0fh51bf6jc93Qs0MdmGwj2QHtwRkcJkUKgLecKCX4gc3WfKVow4iTYocuxeT6UcvgW_Y62tfbmsUwKL2nP8h69zVucZX4IuJAjByD27Vgqgx8WCtTjbYwtvfmC4V2LDsohBjGc1T8eUfYfjp04ZW8YIV_1FnEz057-vHKTjaesw"
    }
}
