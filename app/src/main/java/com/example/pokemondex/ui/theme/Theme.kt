package com.example.pokemondex.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = WarmOrange,
    onPrimary = Color.White,
    secondary = PaleTeal,
    onSecondary = TextDark,
    tertiary = SoftCherry,
    onTertiary = Color.White,
    background = PaleYellow,
    onBackground = TextDark,
    surface = SurfaceWarm,
    onSurface = TextDark,
    surfaceVariant = WarmBeige,
    onSurfaceVariant = TextDark
)

private val DarkColorScheme = darkColorScheme(
    primary = WarmOrange,
    onPrimary = Color.White,
    secondary = PaleTeal,
    onSecondary = TextDark,
    tertiary = SoftCherry,
    onTertiary = Color.White,
    background = Color(0xFF2C251E),
    onBackground = PaleYellow,
    surface = Color(0xFF382F27),
    onSurface = PaleYellow,
    surfaceVariant = Color(0xFF4A3E34),
    onSurfaceVariant = PaleYellow
)

@Composable
fun PokemonDexTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Set default to false to apply custom Pokemon palette
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
