package com.ithirteeng.secondpatternsclientproject.features.common.auth.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseViewModel
import com.ithirteeng.secondpatternsclientproject.domain.theme.usecase.FetchApplicationThemeUseCase
import com.ithirteeng.secondpatternsclientproject.domain.user.model.Token
import com.ithirteeng.secondpatternsclientproject.domain.user.usecase.GetUserAccountUseCase
import com.ithirteeng.secondpatternsclientproject.domain.user.usecase.SaveTokenLocallyUseCase
import com.ithirteeng.secondpatternsclientproject.domain.user.usecase.SaveUserLoginUseCase
import com.ithirteeng.secondpatternsclientproject.features.common.auth.presentation.model.AuthEffect
import com.ithirteeng.secondpatternsclientproject.features.common.auth.presentation.model.AuthEvent
import com.ithirteeng.secondpatternsclientproject.features.common.auth.presentation.model.AuthState
import com.ithirteeng.secondpatternsclientproject.features.common.auth.auth.AuthManager
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authManager: AuthManager,
    private val saveTokenLocallyUseCase: SaveTokenLocallyUseCase,
    private val saveUserLoginUseCase: SaveUserLoginUseCase,
    private val fetchApplicationThemeUseCase: FetchApplicationThemeUseCase,
    private val getUserAccountUseCase: GetUserAccountUseCase,
) : BaseViewModel<AuthState, AuthEvent, AuthEffect>() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d("AUTH", throwable.message.toString())
        addEffect(AuthEffect.ShowError(throwable.message ?: "SOME ERROR"))
    }

    override fun initState(): AuthState = AuthState()

    override fun processEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.IntentResultCame -> {
                viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
                    val token = authManager.getTokenFromAuthorizationResponseIntent(event.intent)
                    Log.d("TOKEN TOKEN", token)
                    saveTokenLocallyUseCase(Token(token, token))
                    val data = getUserAccountUseCase()

                    saveUserLoginUseCase(data.login)
                    fetchApplicationThemeUseCase(data.login)
                    addEffect(AuthEffect.NavigateToMainScreen)
                }
            }
        }
    }
    fun getIntent() = authManager.getRequestIntent()
}