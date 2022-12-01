package com.cilestal.mvi_mpp.android.login

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.cilestal.mvi_mpp.android.extension.SingleLaunchedEffect
import com.cilestal.mvi_mpp.android.extension.collectAsEffect
import com.cilestal.mvi_mpp.android.theme.defaultMargin
import com.cilestal.mvi_mpp.android.views.RoundedTextField
import com.cilestal.mvi_mpp.android.views.ToolbarSimple
import com.cilestal.mvi_mpp.di.TmpInjector
import com.cilestal.mvi_mpp.login.LoginEffect
import com.cilestal.mvi_mpp.login.LoginEvent
import com.cilestal.mvi_mpp.login.LoginState
import com.cilestal.mvi_mpp.login.LoginViewModel

class FieldState {
    var email: String by mutableStateOf("")
    var password: String by mutableStateOf("")
}

@Composable
fun LoginView(vm: LoginViewModel = TmpInjector.vmInstance) {
    val state by vm.stateFlow.collectAsState()
    val fieldState = remember { FieldState() }

    SingleLaunchedEffect {
        vm.dispatchEvent(LoginEvent.OnInit)
    }
    EffectsHandler(vm, fieldState)

    BackHandler {
        vm.dispatchEvent(LoginEvent.OnBackClicked)
    }
    Content(vm, state, fieldState)
}

@Composable
private fun Content(vm: LoginViewModel, state: LoginState, fieldState: FieldState) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
        topBar = {
            ToolbarSimple(
                enabled = state.enabled,
                onClick = {
                    vm.dispatchEvent(LoginEvent.OnBackClicked)
                },
                title = "LoginView"
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            InputFields(vm, state, fieldState)
            LoginButton(vm, state, fieldState)
        }
        if (state.progress) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = Color.Transparent,
                color = Color.Green
            )
        }
    }
}

@Composable
private fun LoginButton(vm: LoginViewModel, state: LoginState, fieldState: FieldState) {
    val focusManager = LocalFocusManager.current

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {

        Button(
            modifier = Modifier.padding(defaultMargin),
            enabled = !state.progress,
            onClick = {
                focusManager.clearFocus()
                vm.dispatchEvent(LoginEvent.OnNextButtonClicked(fieldState.email, fieldState.password))
            }
        ) {
            Image(
                imageVector = Icons.Outlined.ArrowForward,
                colorFilter = ColorFilter.tint(Color.White),
                contentDescription = null
            )
        }
    }
}

@Composable
private fun EffectsHandler(vm: LoginViewModel, fieldState: FieldState) {
    vm.effectFlow.collectAsEffect {
        when (it) {
            LoginEffect.ClearAllData -> {
                fieldState.email = ""
                fieldState.password = ""
            }
            LoginEffect.Close -> {
                // todo navigate back
            }
            is LoginEffect.ShowError -> {
                //todo show error dialog
            }
        }
    }
}

@Composable
private fun InputFields(vm: LoginViewModel, state: LoginState, fieldState: FieldState) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = defaultMargin, start = defaultMargin, end = defaultMargin
            )
    ) {
        LoginField(vm, state, fieldState)
        PasswordField(vm, state, fieldState)
    }
}

@Composable
private fun LoginField(vm: LoginViewModel, state: LoginState, fieldState: FieldState) {
    Text(
        "Email",
        fontSize = 15.sp,
        color = Color.Gray,
        style = MaterialTheme.typography.body2
    )
    RoundedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = defaultMargin),
        value = fieldState.email,
        placeholder = "Email",
        onValueChange = {
            fieldState.email = it
            vm.dispatchEvent(LoginEvent.OnEmailChanged(it))
        },
        keyboardOptions = KeyboardOptions(
            autoCorrect = false,
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        errorMsg = state.emailError
    )
}

@Composable
private fun PasswordField(vm: LoginViewModel, state: LoginState, fieldState: FieldState) {
    val focusManager = LocalFocusManager.current

    Text(
        "Password",
        modifier = Modifier.padding(top = defaultMargin),
        fontSize = 15.sp,
        color = Color.Gray,
        style = MaterialTheme.typography.body2
    )
    RoundedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = defaultMargin),
        value = fieldState.password,
        placeholder = "Password",
        onValueChange = {
            fieldState.password = it
            vm.dispatchEvent(LoginEvent.OnPasswordChanged(it))
        },
        keyboardActions = KeyboardActions(onDone = {
            focusManager.clearFocus()
            vm.dispatchEvent(LoginEvent.OnNextButtonClicked(fieldState.email, fieldState.password))
        }),
        keyboardOptions = KeyboardOptions(
            autoCorrect = false,
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        errorMsg = state.passwordError
    )
}

@Composable
@Preview
fun LoginViewPreview() {
    LoginView()
}