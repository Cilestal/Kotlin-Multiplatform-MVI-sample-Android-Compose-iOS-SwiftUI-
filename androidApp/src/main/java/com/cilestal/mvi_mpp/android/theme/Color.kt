@file:Suppress("MagicNumber")

package com.cilestal.mvi_mpp.android.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val GreenDark = Color(0xFF009e49)
val Green = Color(0xFF00BF60)
val GreenJade = Color(0xFF00C874)
val GreenLight = Color(0xFF00da76)

val DarkBg = Color(0xFF393B3F)

val Background: Color @Composable get() = if (isSystemInDarkTheme()) DarkBg else Color.White
val EditTextBackground: Color
    @Composable get() = if (isSystemInDarkTheme()) Gray70Light else Color(0xFFF7F7F7)
val EditText: Color @Composable get() = if (isSystemInDarkTheme()) Color(0xFFF2F4F7) else Gray80Light

val Pink = Color(0xfffc438c)

val Gray20Light = Color(0xFFe9ecf2)
val Gray40Light = Color(0xFFc2c8d1)
val Gray60Light = Color(0xFF7e8591)
val Gray70Light = Color(0xFF555b66)
val Gray80Light = Color(0xFF363C45)
val Gray90Light = Color(0xFF1d222b)

val Gray20: Color @Composable get() = if (isSystemInDarkTheme()) Color(0xFF555B66) else Gray20Light
val Gray40: Color @Composable get() = if (isSystemInDarkTheme()) Color(0x66FFFFFF) else Gray40Light
val Gray60: Color @Composable get() = if (isSystemInDarkTheme()) Color(0xFFD6DCE8) else Gray60Light
val Gray70: Color @Composable get() = if (isSystemInDarkTheme()) Color(0xFFD5D9E0) else Gray70Light
val Gray80: Color @Composable get() = if (isSystemInDarkTheme()) Color(0xCCFFFFFF) else Gray80Light
val Gray90: Color @Composable get() = if (isSystemInDarkTheme()) Color(0xFFF5F5F5) else Gray90Light

val LightColorsScheme = lightColors(
    primary = GreenDark,
    background = Color.White,
    error = Pink,
    surface = Color.White,
    secondary = GreenDark
)
val DarkColorsScheme = darkColors(
    primary = GreenDark,
    background = DarkBg,
    error = Pink,
    surface = DarkBg,
    secondary = GreenDark
)