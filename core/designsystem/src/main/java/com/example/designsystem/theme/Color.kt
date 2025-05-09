package com.example.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// light mode
val primaryLight = Color(0xFF221E1F)
val primaryContainerLight = Color(0xFF221E1F)
val onPrimaryLight = Color(0xFFB3B3B3)
val secondaryLight = Color(0xFF02851A)
val onSecondaryLight = Color(0xFF3D3D3D)
val tertiaryLight = Color(0xFF006B54)
val onTertiaryLight = Color(0xFFEEEEEE)
val errorLight = Color(0xFFB3130A)
val onErrorLight = Color(0xFFFFFFFF)

// dark mode
val primaryDark = Color(0xFFE1E1E1)
val onPrimaryDark = Color(0xFF1E1E1E)
val primaryContainerDark = Color(0xFF3A3A3A)
val secondaryDark = Color(0xFF66FF99)
val onSecondaryDark = Color(0xFF00230B)
val tertiaryDark = Color(0xFF65FFD9)
val onTertiaryDark = Color(0xFF00201B)
val errorDark = Color(0xFFFFB4AB)
val onErrorDark = Color(0xFF690003)

@Immutable
data class ExtendedColors(
    val success: Color,
    val onSuccess: Color,
)

val LocalExtendedColors = staticCompositionLocalOf<ExtendedColors> {
    error("No colors provided")
}
