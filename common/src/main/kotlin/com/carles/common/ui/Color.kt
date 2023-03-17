@file:Suppress("MagicNumber")
package com.carles.common.ui
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

private val Blue10 = Color(0xFF000F5E)
private val Blue20 = Color(0xFF001E92)
private val Blue30 = Color(0xFF002ECC)
private val Blue40 = Color(0xFF1546F6)
private val Blue80 = Color(0xFFB8C3FF)
private val Blue90 = Color(0xFFDDE1FF)

private val DarkBlue10 = Color(0xFF00036B)
private val DarkBlue20 = Color(0xFF000BA6)
private val DarkBlue30 = Color(0xFF1026D3)
private val DarkBlue40 = Color(0xFF3648EA)
private val DarkBlue80 = Color(0xFFBBC2FF)
private val DarkBlue90 = Color(0xFFDEE0FF)

private val Yellow10 = Color(0xFF261900)
private val Yellow20 = Color(0xFF402D00)
private val Yellow30 = Color(0xFF5C4200)
private val Yellow40 = Color(0xFF7A5900)
private val Yellow80 = Color(0xFFFABD1B)
private val Yellow90 = Color(0xFFFFDE9C)

private val Red10 = Color(0xFF410001)
private val Red20 = Color(0xFF680003)
private val Red30 = Color(0xFF930006)
private val Red40 = Color(0xFFBA1B1B)
private val Red80 = Color(0xFFFFB4A9)
private val Red90 = Color(0xFFFFDAD4)

private val Grey10 = Color(0xFF191C1D)
private val Grey20 = Color(0xFF2D3132)
private val Grey80 = Color(0xFFC4C7C7)
private val Grey90 = Color(0xFFE0E3E3)
private val Grey95 = Color(0xFFEFF1F1)
private val Grey99 = Color(0xFFFBFDFD)

private val BlueGrey30 = Color(0xFF45464F)
private val BlueGrey50 = Color(0xFF767680)
private val BlueGrey60 = Color(0xFF90909A)
private val BlueGrey80 = Color(0xFFC6C5D0)
private val BlueGrey90 = Color(0xFFE2E1EC)

val DarkPalette = darkColorScheme(
    primary = Blue80,
    onPrimary = Blue20,
    primaryContainer = Blue30,
    onPrimaryContainer = Blue90,
    inversePrimary = Blue40,
    secondary = DarkBlue80,
    onSecondary = DarkBlue20,
    secondaryContainer = DarkBlue30,
    onSecondaryContainer = DarkBlue90,
    tertiary = Yellow80,
    onTertiary = Yellow20,
    tertiaryContainer = Yellow30,
    onTertiaryContainer = Yellow90,
    error = Red80,
    onError = Red20,
    errorContainer = Red30,
    onErrorContainer = Red90,
    background = Grey10,
    onBackground = Grey90,
    surface = Grey10,
    onSurface = Grey80,
    inverseSurface = Grey90,
    inverseOnSurface = Grey20,
    surfaceVariant = BlueGrey30,
    onSurfaceVariant = BlueGrey80,
    outline = BlueGrey60
)

val LightPalette = lightColorScheme(
    primary = Blue40,
    onPrimary = Color.White,
    primaryContainer = Blue90,
    onPrimaryContainer = Blue10,
    inversePrimary = Blue80,
    secondary = DarkBlue40,
    onSecondary = Color.White,
    secondaryContainer = DarkBlue90,
    onSecondaryContainer = DarkBlue10,
    tertiary = Yellow40,
    onTertiary = Color.White,
    tertiaryContainer = Yellow90,
    onTertiaryContainer = Yellow10,
    error = Red40,
    onError = Color.White,
    errorContainer = Red90,
    onErrorContainer = Red10,
    background = Grey99,
    onBackground = Grey10,
    surface = Grey99,
    onSurface = Grey10,
    inverseSurface = Grey20,
    inverseOnSurface = Grey95,
    surfaceVariant = BlueGrey90,
    onSurfaceVariant = BlueGrey30,
    outline = BlueGrey50
)