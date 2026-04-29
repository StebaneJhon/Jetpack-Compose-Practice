package com.example.dynamictheme.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.dynamictheme.ui.AppTheme

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

// --- 1. Base (Neutral) Color Schemes ---
val BaseLightColorScheme = lightColorScheme(
    primary = Neutral600,
    onPrimary = Neutral50,
    primaryContainer = Neutral700,
    onPrimaryContainer = Neutral50,
    secondaryContainer = Neutral100Transparent,
    surface = Neutral200, // mapping surfaceContainer
    onSurface = Neutral950,
    onSurfaceVariant = Neutral600
)

val BaseDarkColorScheme = darkColorScheme(
    primary = Neutral50,
    onPrimary = Neutral950,
    primaryContainer = Neutral50,
    onPrimaryContainer = Neutral950,
    secondaryContainer = Neutral800Transparent,
    surface = Neutral500, // mapping surfaceContainer
    onSurface = Neutral50,
    onSurfaceVariant = Neutral200
)

// --- 2. Red Color Schemes ---
val RedLightColorScheme = lightColorScheme(
    primary = Red700,
    onPrimary = Red50,
    primaryContainer = Red600,
    onPrimaryContainer = Red50,
    secondaryContainer = Red100Transparent,
    surface = Red200,
    onSurface = Red950,
    onSurfaceVariant = Red600
)

val RedDarkColorScheme = darkColorScheme(
    primary = Red50,
    onPrimary = Red950,
    primaryContainer = Red50,
    onPrimaryContainer = Red950,
    secondaryContainer = Red800Transparent,
    surface = Red800,
    onSurface = Red50,
    onSurfaceVariant = Red200
)

// --- 3. Blue Color Schemes ---
val BlueLightColorScheme = lightColorScheme(
    primary = Blue700,
    onPrimary = Blue50,
    primaryContainer = Blue600,
    onPrimaryContainer = Blue50,
    secondaryContainer = Blue100Transparent,
    surface = Blue200,
    onSurface = Blue950,
    onSurfaceVariant = Blue600
)

val BlueDarkColorScheme = darkColorScheme(
    primary = Blue50,
    onPrimary = Blue950,
    primaryContainer = Blue50,
    onPrimaryContainer = Blue950,
    secondaryContainer = Blue800Transparent,
    surface = Blue800,
    onSurface = Blue50,
    onSurfaceVariant = Blue200
)

// --- 4. Yellow Color Schemes ---
val YellowLightColorScheme = lightColorScheme(
    primary = Yellow700,
    onPrimary = Yellow50,
    primaryContainer = Yellow600,
    onPrimaryContainer = Yellow50,
    secondaryContainer = Yellow100Transparent,
    surface = Yellow200,
    onSurface = Yellow950,
    onSurfaceVariant = Yellow600
)

val YellowDarkColorScheme = darkColorScheme(
    primary = Yellow50,
    onPrimary = Yellow950,
    primaryContainer = Yellow50,
    onPrimaryContainer = Yellow950,
    secondaryContainer = Yellow800Transparent,
    surface = Yellow800,
    onSurface = Yellow50,
    onSurfaceVariant = Yellow200
)

@Composable
fun DynamicThemeTheme(
    appTheme: AppTheme = AppTheme.DEFAULT,
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
//    val colorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }
//
//        darkTheme -> DarkColorScheme
//        else -> LightColorScheme
//    }

    val colorScheme = when (appTheme) {
        AppTheme.RED -> if (darkTheme) RedDarkColorScheme else RedLightColorScheme
        AppTheme.BLUE -> if (darkTheme) BlueDarkColorScheme else BlueLightColorScheme
        AppTheme.YELLOW -> if (darkTheme) YellowDarkColorScheme else YellowLightColorScheme
        else -> if (darkTheme) BaseDarkColorScheme else BaseLightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
