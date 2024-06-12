package com.kunalchhabra.today.ui.theme

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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.core.view.WindowCompat
import com.kunalchhabra.today.R

val ItimFontFamily = FontFamily(
    Font(R.font.itim, FontWeight.Normal)
)

// Pink Color Schemes
private val PinkDarkColorScheme = darkColorScheme(
    primary = PinkPrimaryDark,
    secondary = PinkSecondaryDark,
    tertiary = PinkSecondaryDark,
    background = BackgroundDark,
    surface = SurfaceDark,
    onPrimary = OnPrimary,
    onSecondary = OnSecondary,
    onTertiary = OnTertiaryDark,
    onBackground = OnBackgroundDark,
    onSurface = OnSurfaceDark
)

private val PinkLightColorScheme = lightColorScheme(
    primary = PinkPrimary,
    secondary = PinkSecondary,
    tertiary = PinkSecondaryLight,
    background = BackgroundLight,
    surface = SurfaceLight,
    onPrimary = OnPrimary,
    onSecondary = OnSecondary,
    onTertiary = OnTertiaryLight,
    onBackground = OnBackgroundLight,
    onSurface = OnSurfaceLight
)

// Blue Color Schemes
private val BlueDarkColorScheme = darkColorScheme(
    primary = BluePrimaryDark,
    secondary = BlueSecondaryDark,
    tertiary = BlueSecondaryDark,
    background = BackgroundDark,
    surface = SurfaceDark,
    onPrimary = OnBluePrimary,
    onSecondary = OnBlueSecondary,
    onTertiary = OnBlueTertiaryDark,
    onBackground = OnBackgroundDark,
    onSurface = OnSurfaceDark
)

private val BlueLightColorScheme = lightColorScheme(
    primary = BluePrimary,
    secondary = BlueSecondary,
    tertiary = BlueSecondaryLight,
    background = BackgroundLight,
    surface = SurfaceLight,
    onPrimary = OnBluePrimary,
    onSecondary = OnBlueSecondary,
    onTertiary = OnBlueTertiaryLight,
    onBackground = OnBackgroundLight,
    onSurface = OnSurfaceLight
)

var THEME_COLOR_CARD = PinkPrimaryCard

@Composable
fun TodayTheme(
    darkTheme: Boolean,
    dynamicColor: Boolean = false, // Disable dynamic color
    useBlueTheme: Boolean, // New parameter to toggle blue theme
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> if (useBlueTheme) BlueDarkColorScheme else PinkDarkColorScheme
        else -> if (useBlueTheme) BlueLightColorScheme else PinkLightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )

    if(useBlueTheme)
        THEME_COLOR_CARD = BluePrimaryCard
    else THEME_COLOR_CARD = PinkPrimaryCard
}
