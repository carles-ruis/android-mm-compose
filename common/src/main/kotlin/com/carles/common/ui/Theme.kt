package com.carles.common.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun GreenTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (!useDarkTheme) {
        LightPalette
    } else {
        DarkPalette
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typo,
        shapes = Shapes,
        content = content,
    )
}