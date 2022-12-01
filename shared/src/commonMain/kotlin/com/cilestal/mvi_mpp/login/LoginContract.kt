package com.cilestal.mvi_mpp.login

import com.cilestal.mvi_mpp.BaseViewModel

interface LoginViewModel : BaseViewModel<LoginState, LoginEvent, LoginEffect>

data class LoginState(
    val progress: Boolean = false,
    val emailError: String? = null,
    val passwordError: String? = null,
) {
    val enabled get() = !progress
}

sealed class LoginEvent {
    object OnInit : LoginEvent()
    object OnBackClicked : LoginEvent()
    data class OnForgotPasswordClicked(val email: String) : LoginEvent()
    data class OnNextButtonClicked(val email: String, val password: String) : LoginEvent()
    data class OnEmailChanged(val email: String) : LoginEvent()
    data class OnPasswordChanged(val password: String) : LoginEvent()
}

sealed class LoginEffect {
    data class ShowError(val error: String) : LoginEffect()
    object ClearAllData : LoginEffect()
    object Close : LoginEffect()
}