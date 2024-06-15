package com.example.emailapp.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// Define custom colors
private val RedPrimary = Color(0xFFD32F2F)
private val RedSecondary = Color(0xFFC2185B)
private val BlackOnSurface = Color(0xFF000000)
private val WhiteOnPrimary = Color(0xFFFFFFFF)

private val DarkColorScheme = darkColorScheme(
    primary = RedPrimary,
    secondary = RedSecondary,
    onPrimary = WhiteOnPrimary,
    onSecondary = WhiteOnPrimary,
    onSurface = WhiteOnPrimary
)

private val LightColorScheme = lightColorScheme(
    primary = RedPrimary,
    secondary = RedSecondary,
    onPrimary = WhiteOnPrimary,
    onSecondary = WhiteOnPrimary,
    onSurface = BlackOnSurface

    // You can override other colors if needed
    // background = Color(0xFFFFFBFE),
    // surface = Color(0xFFFFFBFE),
    // onBackground = Color(0xFF1C1B1F),
    // onSurface = Color(0xFF1C1B1F),
)

@Composable
fun EmailAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
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
