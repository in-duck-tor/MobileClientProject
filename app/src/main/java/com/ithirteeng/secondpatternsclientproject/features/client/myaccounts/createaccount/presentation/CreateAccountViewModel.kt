package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.createaccount.presentation

import androidx.lifecycle.viewModelScope
import com.ithirteeng.secondpatternsclientproject.R
import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseViewModel
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.CreateAccount
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.account.CreateAccountUseCase
import com.ithirteeng.secondpatternsclientproject.domain.user.usecase.GetLocalTokenUseCase
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.createaccount.presentation.model.CreateAccountEffect
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.createaccount.presentation.model.CreateAccountEvent
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.createaccount.presentation.model.CreateAccountState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateAccountViewModel(
    private val getLocalTokenUseCase: GetLocalTokenUseCase,
    private val createAccountUseCase: CreateAccountUseCase,
) : BaseViewModel<CreateAccountState, CreateAccountEvent, CreateAccountEffect>() {

    override fun initState(): CreateAccountState = CreateAccountState.Loading

    override fun processEvent(event: CreateAccountEvent) {
        when (event) {
            is CreateAccountEvent.Init -> handleInit()
            is CreateAccountEvent.Ui.CommentTextChanged -> handleCommentTextChange(event)
            is CreateAccountEvent.Ui.CreateAccountButtonClick -> handleCreateAccountButtonClick()
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
                    currencyCode = currentState.currencyCode,
                    customComment = currentState.customComment.text
                )
                createAccountUseCase(data, token)
                    .onSuccess {
                        addEffect(CreateAccountEffect.ShowCreationToast("AccountCreated"))
                        addEffect(CreateAccountEffect.CloseSelf)
                    }
                    .onFailure {
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
}