package com.cilestal.mvi_mpp.utils

import kotlinx.coroutines.CoroutineDispatcher

expect object XDispatchers {
    val Main: CoroutineDispatcher
    val Default: CoroutineDispatcher
}