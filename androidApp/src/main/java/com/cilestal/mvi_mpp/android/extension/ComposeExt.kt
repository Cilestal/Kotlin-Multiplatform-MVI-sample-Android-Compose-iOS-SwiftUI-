package com.cilestal.mvi_mpp.android.extension

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@SuppressLint("ComposableNaming")
@Composable
fun <T> Flow<T>.collectAsEffect(
    context: CoroutineContext = EmptyCoroutineContext,
    block: (T) -> Unit
) {
    val flow = this
    LaunchedEffect(key1 = Unit) {
        flow.onEach(block).flowOn(context).launchIn(this)
    }
}

@Composable
fun SingleLaunchedEffect(block: () -> Unit) {
    LaunchedEffect(key1 = Unit) {
        block()
    }
}