package com.ithirteeng.secondpatternsclientproject.features.client.myloans.programinfo.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseViewModel
import com.ithirteeng.secondpatternsclientproject.domain.loans.usecase.GetLoanProgramsUseCase
import com.ithirteeng.secondpatternsclientproject.features.client.myloans.programinfo.presentation.model.ProgramInfoEffect
import com.ithirteeng.secondpatternsclientproject.features.client.myloans.programinfo.presentation.model.ProgramInfoEvent
import com.ithirteeng.secondpatternsclientproject.features.client.myloans.programinfo.presentation.model.ProgramInfoState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProgramInfoViewModel(
    private val getLoanProgramsUseCase: GetLoanProgramsUseCase,
) : BaseViewModel<ProgramInfoState, ProgramInfoEvent, ProgramInfoEffect>() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d(TAG, throwable.message.toString())
        addEffect(ProgramInfoEffect.ShowError(throwable.message ?: "SOME ERROR"))
    }

    override fun initState(): ProgramInfoState = ProgramInfoState.Loading

    override fun processEvent(event: ProgramInfoEvent) {
        when (event) {
            is ProgramInfoEvent.Init -> handleInit(event)
            is ProgramInfoEvent.DataLoaded -> handleDataLoaded(event)
            is ProgramInfoEvent.Ui.CreateLoanButtonClick -> handleCreateLoanButtonClick()
        }
    }

    private fun handleInit(event: ProgramInfoEvent.Init) {
        viewModelScope.launch(exceptionHandler + Dispatchers.IO) {
            val programs = getLoanProgramsUseCase()
            programs.findLast { it.id == event.programId }?.let { program ->
                processEvent(ProgramInfoEvent.DataLoaded(program = program))
            }

        }
    }

    private fun handleDataLoaded(event: ProgramInfoEvent.DataLoaded) {
        when (val currentState = state.value) {
            is ProgramInfoState.Loading -> updateState { ProgramInfoState.Content(event.program) }
            is ProgramInfoState.Content -> updateState { currentState.copy(program = event.program) }
        }
    }

    private fun handleCreateLoanButtonClick() {
        when (val currentState = state.value) {
            is ProgramInfoState.Loading -> Unit
            is ProgramInfoState.Content -> addEffect(
                ProgramInfoEffect.NavigateToCreateLoanScreen(
                    currentState.program.id
                )
            )
        }
    }

    private companion object {
        private const val TAG = "ProgramInfo"
    }
}
