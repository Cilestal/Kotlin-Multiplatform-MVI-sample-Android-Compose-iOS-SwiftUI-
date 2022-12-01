package com.cilestal.mvi_mpp

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface BaseViewModel<State, Event, Effect> : EventDispatcher<Event> {
    val stateFlow: StateFlow<State>
    val effectFlow: Flow<Effect>

    fun clear()
}

interface EventHandler<State, Event> {
    suspend fun handleEvent(event: Event)
}

interface EventDispatcher<Event> {
    fun dispatchEvent(event: Event)
}