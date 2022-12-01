package com.cilestal.mvi_mpp

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

expect abstract class BaseViewModelImpl<State, Event, Effect>(
    coroutineScope: CoroutineScope
) : BaseViewModel<State, Event, Effect>, EventHandler<State, Event>{

    protected val currentState: State

    abstract fun createInitialState(): State
    override fun dispatchEvent(event: Event)
    protected fun dispatchEffect(effect: Effect)
    protected fun updateState(reduce: State.() -> State)

    override val stateFlow: StateFlow<State>
    protected val eventFlow: SharedFlow<Event>
    override val effectFlow: Flow<Effect>

    override fun clear()
}