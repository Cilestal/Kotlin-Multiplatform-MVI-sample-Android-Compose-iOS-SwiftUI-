@file:Suppress("UNCHECKED_CAST")

package com.cilestal.mvi_mpp.android.extension

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cilestal.mvi_mpp.BaseViewModel

inline fun <T : BaseViewModel<*, *, *>> vmFactory(crossinline f: () -> BaseViewModel<*, *, *>): ViewModelProvider.Factory {
    return object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return f() as T
        }
    }
}