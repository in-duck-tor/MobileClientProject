package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.createaccount.stub

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ithirteeng.secondpatternsclientproject.R
import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseViewModel
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.CreateAccount
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.stub.CreateAccountStubUseCase
import com.ithirteeng.secondpatternsclientproject.domain.user.usecase.GetLocalTokenUseCase
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.createaccount.presentation.model.CreateAccountEffect
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.createaccount.presentation.model.CreateAccountEvent
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.createaccount.presentation.model.CreateAccountState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateAccountStubViewModel(
    private val getLocalTokenUseCase: GetLocalTokenUseCase,
    private val createAccountStubUseCase: CreateAccountStubUseCase,
) : BaseViewModel<CreateAccountState, CreateAccountEvent, CreateAccountEffect>() {

    override fun initState(): CreateAccountState = CreateAccountState.Loading

    override fun processEvent(event: CreateAccountEvent) {
        when (event) {
            is CreateAccountEvent.Init -> handleInit()
            is CreateAccountEvent.Ui.CommentTextChanged -> handleCommentTextChange(event)
            is CreateAccountEvent.Ui.CreateAccountButtonClick -> handleCreateAccountButtonClick()
            else -> {}
        }
    }

    private fun handleInit() = updateState {
        when (state.value) {
            is CreateAccountState.Content -> state.value
            is CreateAccountState.Loading -> CreateAccountState.Content()
        }
    }

    private fun handleCommentTextChange(event: CreateAccountEvent.Ui.CommentTextChanged) =
        updateState {
            when (val currentState = state.value) {
                is CreateAccountState.Content -> currentState.copy(
                    customComment = event.text
                )

                else -> state.value
            }
        }

    private fun handleCreateAccountButtonClick() {
        val token = getLocalTokenUseCase()
        when (val currentState = state.value) {
            is CreateAccountState.Content -> viewModelScope.launch(Dispatchers.IO) {
                val data = CreateAccount(
                    currencyCode = currentState.chosenCurrencyCode,
                    customComment = currentState.customComment.text
                )
                createAccountStubUseCase(data, token)
                    .onSuccess {
                        addEffect(CreateAccountEffect.ShowCreationToast("AccountCreated"))
                        addEffect(CreateAccountEffect.CloseSelf)
                    }
                    .onFailure {
                        Log.e(TAG, it.message.toString())
                        addEffect(
                            CreateAccountEffect.ShowError(
                                R.string.error_with_creating_account,
                                it.message.toString()
                            )
                        )
                    }
            }

            else -> Unit
        }
    }

    private companion object {

        const val TAG = "CreateAccountViewModel"
    }
}