package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val OtakuColorScheme = lightColorScheme(
    primary = OtakuPrimary,
    onPrimary = Color.White,
    primaryContainer = OtakuDarkCardElevated,
    onPrimaryContainer = OtakuPrimary,
    secondary = OtakuSecondary,
    onSecondary = Color.White,
    secondaryContainer = OtakuDarkCardElevated,
    onSecondaryContainer = OtakuSecondary,
    tertiary = OtakuTertiary,
    onTertiary = Color.White,
    background = OtakuDarkBackground,
    onBackground = OtakuTextPrimary,
    surface = OtakuDarkSurface,
    onSurface = OtakuTextPrimary,
    surfaceVariant = OtakuDarkCardElevated,
    onSurfaceVariant = OtakuTextSecondary,
    outline = OtakuDarkBorder
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = false, // Clean, luminous anime light aesthetic
    content: @Composable () -> Unit
) {
    val colorScheme = OtakuColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                window.statusBarColor = colorScheme.surface.toArgb()
                window.navigationBarColor = colorScheme.surface.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
                WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = true
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
