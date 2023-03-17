package com.carles.common.ui

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.carles.common.R

private val Hylia = FontFamily(
    Font(R.font.hylia_serif_regular)
)

private val Breath = FontFamily(
    Font(R.font.wild_breath_of_zelda)
)

val Typo = Typography(
    headlineLarge = TextStyle(
        fontFamily = Hylia,
        fontSize = 38.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = Hylia,
        fontSize = 24.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = Hylia,
        fontSize = 22.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Breath,
        fontSize = 20.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Breath,
        fontSize = 18.sp
    ),
    bodySmall = TextStyle(
        fontFamily = Breath,
        fontSize = 16.sp
    ),
    labelLarge = TextStyle(
        fontFamily = Hylia,
        fontSize = 20.sp
    ),
    labelMedium = TextStyle(
        fontFamily = Hylia,
        fontSize = 18.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Hylia,
        fontSize = 16.sp
    )
)