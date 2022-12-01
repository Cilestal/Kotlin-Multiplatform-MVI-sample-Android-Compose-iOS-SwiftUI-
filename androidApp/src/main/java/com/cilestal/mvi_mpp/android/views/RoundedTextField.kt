package com.cilestal.mvi_mpp.android.views

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cilestal.mvi_mpp.android.R
import com.cilestal.mvi_mpp.android.theme.EditText
import com.cilestal.mvi_mpp.android.theme.EditTextBackground
import com.cilestal.mvi_mpp.android.theme.Green
import com.cilestal.mvi_mpp.android.theme.defaultMargin
import com.cilestal.mvi_mpp.android.theme.defaultSmallMargin

@SuppressLint("ComposableLambdaParameterNaming")
@Composable
fun RoundedTextField(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String = "",
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    trailingIcon: @Composable (() -> Unit)? = null,
    errorMsg: String? = null
) {
    val passwordVisibility = remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(
            modifier = modifier,
            value = value,
            shape = RoundedCornerShape(24.dp),
            singleLine = true,
            placeholder = {
                Text(text = placeholder, fontSize = 15.sp, fontWeight = FontWeight.Medium)
            },
            maxLines = 1,
            onValueChange = {
                if (!it.contains("[\n\r]".toRegex())) {
                    onValueChange(it)
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = EditTextBackground,
                errorBorderColor = MaterialTheme.colors.error,
                disabledBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            ),
            isError = !errorMsg.isNullOrBlank(),
            textStyle = LocalTextStyle.current.copy(
                fontWeight = FontWeight.Medium,
                color = EditText,
                fontSize = 15.sp
            ),
            trailingIcon = trailingIcon ?: trailingIcon(keyboardOptions, passwordVisibility),
            visualTransformation = if (showText(keyboardOptions, passwordVisibility)) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions
        )
        if (!errorMsg.isNullOrBlank()) {
            Text(
                text = errorMsg,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = defaultMargin, top = defaultSmallMargin)
            )
        }
    }
}

@Composable
private fun showText(
    keyboardOptions: KeyboardOptions,
    passwordVisibility: MutableState<Boolean>
) = keyboardOptions.keyboardType != KeyboardType.Password || passwordVisibility.value

private fun trailingIcon(
    keyboardOptions: KeyboardOptions,
    passwordVisibility: MutableState<Boolean>
): @Composable (() -> Unit)? {
    if (keyboardOptions.keyboardType == KeyboardType.Password) {
        return {
            val imageRes = if (passwordVisibility.value) {
                R.drawable.ic_password_visible
            } else {
                R.drawable.ic_password_hidden
            }

            IconButton(
                modifier = Modifier.size(24.dp),
                onClick = {
                    passwordVisibility.value = !passwordVisibility.value
                }) {
                Icon(
                    painter = painterResource(id = imageRes),
                    tint = Green,
                    contentDescription = "Password toggle button"
                )
            }
        }
    }

    return null
}

@Composable
@Preview
fun RoundedTextFieldPreview() {
    var text by remember { mutableStateOf("") }

    RoundedTextField(
        value = text,
        onValueChange = {
            text = it
        }
    )
}