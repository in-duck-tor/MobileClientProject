package com.ithirteeng.secondpatternsclientproject.common.architecture

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<State : BaseState, Event : BaseEvent, Effect : BaseEffect> : ViewModel() {

    private val _screenState by lazy { MutableStateFlow(initState()) }

    val state: StateFlow<State>
        get() = _screenState

    private val _effectsFlow by lazy { MutableSharedFlow<Effect>() }

    val effectsFlow: SharedFlow<Effect>
        get() = _effectsFlow

    protected fun updateState(update: suspend State.() -> State) {
        viewModelScope.launch {
            val newState = update.invoke(_screenState.value)
            _screenState.emit(newState)
        }
    }

    protected abstract fun initState(): State

    abstract fun processEvent(event: Event)

    protected fun addEffect(effect: Effect) {
        viewModelScope.launch {
            _effectsFlow.emit(effect)
        }
    }
}