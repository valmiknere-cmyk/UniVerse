package com.example.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = CosmicPurplePrimary,
    onPrimary = CosmicTextLight,
    primaryContainer = CosmicSurfaceCardDark,
    onPrimaryContainer = CosmicLavenderAccent,
    secondary = CosmicPurpleSecondary,
    onSecondary = CosmicTextLight,
    secondaryContainer = CosmicSurfaceDark,
    onSecondaryContainer = CosmicLavenderAccent,
    tertiary = CosmicLavenderAccent,
    background = CosmicNavy,
    onBackground = CosmicTextLight,
    surface = CosmicSurfaceDark,
    onSurface = CosmicTextLight,
    surfaceVariant = CosmicSurfaceCardDark,
    onSurfaceVariant = CosmicTextMutedLight,
    error = CosmicError,
    outline = CosmicSurfaceCardDark
)

private val LightColorScheme = lightColorScheme(
    primary = CosmicPurplePrimary,
    onPrimary = CosmicTextLight,
    primaryContainer = CosmicLavenderAccent.copy(alpha = 0.25f),
    onPrimaryContainer = CosmicPurplePrimary,
    secondary = CosmicPurpleSecondary,
    onSecondary = CosmicTextLight,
    secondaryContainer = CosmicCardLight,
    onSecondaryContainer = CosmicTextDark,
    tertiary = CosmicLavenderAccent,
    background = CosmicBackgroundLight,
    onBackground = CosmicTextDark,
    surface = CosmicSurfaceLight,
    onSurface = CosmicTextDark,
    surfaceVariant = CosmicCardLight,
    onSurfaceVariant = CosmicTextMuted,
    error = CosmicError,
    outline = CosmicLavenderAccent.copy(alpha = 0.4f)
)

@Composable
fun UniVerseTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Keep brand cosmic colors consistent
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

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = if (darkTheme) CosmicNavy.toArgb() else CosmicBackgroundLight.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
