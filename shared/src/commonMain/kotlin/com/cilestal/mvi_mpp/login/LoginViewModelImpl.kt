package com.cilestal.mvi_mpp.login

import com.cilestal.mvi_mpp.BaseViewModelImpl
import com.cilestal.mvi_mpp.utils.DataValidator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class LoginViewModelImpl(
    // use DI
    private val validator: DataValidator = DataValidator(),
    coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)
) : BaseViewModelImpl<LoginState, LoginEvent, LoginEffect>(coroutineScope),
    LoginViewModel,
    CoroutineScope by coroutineScope {

    override fun createInitialState() = LoginState()

    override suspend fun handleEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnInit -> onInit()
            is LoginEvent.OnEmailChanged -> updateState { copy(emailError = null) }
            is LoginEvent.OnPasswordChanged -> updateState { copy(passwordError = null) }
            is LoginEvent.OnForgotPasswordClicked -> onForgotPasswordClicked(event.email)
            LoginEvent.OnBackClicked -> dispatchEffect(LoginEffect.Close)
            is LoginEvent.OnNextButtonClicked -> onLoginClicked(event.email, event.password)
        }
    }

    private fun onInit() {
        // send analytics, etc.
    }

    private suspend fun onForgotPasswordClicked(email: String) {
        updateState { copy(progress = true) }

        val error = validator.validateEmail(email)

        if (error == null) {
            forgotPassword(email)
        } else {
            updateState {
                copy(progress = false, emailError = error)
            }
        }
    }

    private suspend fun forgotPassword(email: String) {
        try {
            // forgot password
        } catch (thr: Throwable) {
            dispatchEffect(LoginEffect.ShowError("error msg"))
        } finally {
            updateState { copy(progress = false) }
        }
    }

    private suspend fun onLoginClicked(email: String, password: String) {
        updateState { copy(progress = true, emailError = null, passwordError = null) }

        validator.validateEmail(email)?.let {
            updateState { copy(emailError = it, progress = false) }
            return
        }
        validator.validatePassword(password)?.let {
            updateState { copy(passwordError = it, progress = false) }
            return
        }

        login(email, password)
    }

    private suspend fun login(email: String, password: String) {
        try {
            // login logic
            dispatchEffect(LoginEffect.ClearAllData)
            // navigate next
        } catch (thr: Throwable) {
            updateState { copy(passwordError = "error msg") }
        } finally {
            updateState { copy(progress = false) }
        }
    }
}