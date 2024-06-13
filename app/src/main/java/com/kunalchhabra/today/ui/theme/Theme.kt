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

// Yellow Color Schemes
private val YellowDarkColorScheme = darkColorScheme(
    primary = YellowPrimaryDark,
    secondary = YellowSecondaryDark,
    tertiary = YellowSecondaryDark,
    background = BackgroundDark,
    surface = SurfaceDark,
    onPrimary = OnYellowPrimary,
    onSecondary = OnYellowSecondary,
    onTertiary = OnYellowTertiaryDark,
    onBackground = OnBackgroundDark,
    onSurface = OnSurfaceDark
)

private val YellowLightColorScheme = lightColorScheme(
    primary = YellowPrimary,
    secondary = YellowSecondary,
    tertiary = YellowSecondaryLight,
    background = BackgroundLight,
    surface = SurfaceLight,
    onPrimary = OnYellowPrimary,
    onSecondary = OnYellowSecondary,
    onTertiary = OnYellowTertiaryLight,
    onBackground = OnBackgroundLight,
    onSurface = OnSurfaceLight
)

// Purple Color Schemes
private val PurpleDarkColorScheme = darkColorScheme(
    primary = PurplePrimaryDark,
    secondary = PurpleSecondaryDark,
    tertiary = PurpleSecondaryDark,
    background = BackgroundDark,
    surface = SurfaceDark,
    onPrimary = OnPurplePrimary,
    onSecondary = OnPurpleSecondary,
    onTertiary = OnPurpleTertiaryDark,
    onBackground = OnBackgroundDark,
    onSurface = OnSurfaceDark
)

private val PurpleLightColorScheme = lightColorScheme(
    primary = PurplePrimary,
    secondary = PurpleSecondary,
    tertiary = PurpleSecondaryLight,
    background = BackgroundLight,
    surface = SurfaceLight,
    onPrimary = OnPurplePrimary,
    onSecondary = OnPurpleSecondary,
    onTertiary = OnPurpleTertiaryLight,
    onBackground = OnBackgroundLight,
    onSurface = OnSurfaceLight
)

// Green Color Schemes
private val GreenDarkColorScheme = darkColorScheme(
    primary = GreenPrimaryDark,
    secondary = GreenSecondaryDark,
    tertiary = GreenSecondaryDark,
    background = BackgroundDark,
    surface = SurfaceDark,
    onPrimary = OnGreenPrimary,
    onSecondary = OnGreenSecondary,
    onTertiary = OnGreenTertiaryDark,
    onBackground = OnBackgroundDark,
    onSurface = OnSurfaceDark
)

private val GreenLightColorScheme = lightColorScheme(
    primary = GreenPrimary,
    secondary = GreenSecondary,
    tertiary = GreenSecondaryLight,
    background = BackgroundLight,
    surface = SurfaceLight,
    onPrimary = OnGreenPrimary,
    onSecondary = OnGreenSecondary,
    onTertiary = OnGreenTertiaryLight,
    onBackground = OnBackgroundLight,
    onSurface = OnSurfaceLight
)

var THEME_COLOR_CARD = PinkPrimaryCard

@Composable
fun TodayTheme(
    darkTheme: Boolean,
    dynamicColor: Boolean = false, // Disable dynamic color
    themeColor: String = "Pink", // New parameter to toggle themes
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> when (themeColor) {
            "Blue" -> BlueDarkColorScheme
            "Yellow" -> YellowDarkColorScheme
            "Purple" -> PurpleDarkColorScheme
            "Green" -> GreenDarkColorScheme
            else -> PinkDarkColorScheme
        }
        else -> when (themeColor) {
            "Blue" -> BlueLightColorScheme
            "Yellow" -> YellowLightColorScheme
            "Purple" -> PurpleLightColorScheme
            "Green" -> GreenLightColorScheme
            else -> PinkLightColorScheme
        }
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

    THEME_COLOR_CARD = when (themeColor) {
        "Blue" -> BluePrimaryCard
        "Yellow" -> YellowPrimaryCard
        "Purple" -> PurplePrimaryCard
        "Green" -> GreenPrimaryCard
        else -> PinkPrimaryCard
    }
}
