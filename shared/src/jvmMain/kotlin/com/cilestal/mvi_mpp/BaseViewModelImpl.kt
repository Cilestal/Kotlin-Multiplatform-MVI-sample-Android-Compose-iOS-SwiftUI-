package com.cilestal.mvi_mpp

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

actual abstract class BaseViewModelImpl<State, Event, Effect> actual constructor(
    private val coroutineScope: CoroutineScope
) : BaseViewModel<State, Event, Effect>, EventHandler<State, Event> {

    // Get Current State
    protected actual val currentState: State get() = _uiState.value

    // Create Initial State of View
    actual abstract fun createInitialState(): State
    private val initialState: State by lazy { createInitialState() }

    private val _uiState: MutableStateFlow<State> = MutableStateFlow(initialState)
    actual override val stateFlow = _uiState.asStateFlow()

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()
    protected actual val eventFlow = _event.asSharedFlow()

    private val _effect: Channel<Effect> = Channel()
    actual override val effectFlow = _effect.receiveAsFlow()

    init {
        subscribeEvents()
    }

    private fun subscribeEvents() {
        coroutineScope.launch(Dispatchers.Default) {
            eventFlow.collect {
                handleEvent(it)
            }
        }
    }

    actual override fun dispatchEvent(event: Event) {
        coroutineScope.launch {
            _event.emit(event)
        }
    }

    protected actual fun updateState(reduce: State.() -> State) {
        _uiState.update {
            it.reduce()
        }
    }

    protected actual fun dispatchEffect(effect: Effect) {
        coroutineScope.launch {
            _effect.send(effect)
        }
    }

    actual override fun clear() {
        coroutineScope.cancel()
    }
}