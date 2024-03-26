package com.ithirteeng.secondpatternsclientproject.common.uikit

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.ithirteeng.secondpatternsclientproject.domain.theme.model.Theme

private val lightColorsScheme = lightColorScheme(

    primary = themeLightPrimary,
    onPrimary = themeLightOnPrimary,
    primaryContainer = themeLightPrimaryContainer,
    onPrimaryContainer = themeLightOnPrimaryContainer,
    secondary = themeLightSecondary,
    onSecondary = themeLightOnSecondary,
    secondaryContainer = themeLightSecondaryContainer,
    onSecondaryContainer = themeLightOnSecondaryContainer,
    tertiary = themeLightTertiary,
    onTertiary = themeLightOnTertiary,
    tertiaryContainer = themeLightTertiaryContainer,
    onTertiaryContainer = themeLightOnTertiaryContainer,
    error = themeLightError,
    errorContainer = themeLightErrorContainer,
    onError = themeLightOnError,
    onErrorContainer = themeLightOnErrorContainer,
    background = themeLightBackground,
    onBackground = themeLightOnBackground,
    surface = themeLightSurface,
    onSurface = themeLightOnSurface,
    surfaceVariant = themeLightSurfaceVariant,
    onSurfaceVariant = themeLightOnSurfaceVariant,
    outline = themeLightOutline,
    inverseOnSurface = themeLightInverseOnSurface,
    inverseSurface = themeLightInverseSurface,
    inversePrimary = themeLightInversePrimary,
)
private val darkColorsScheme = darkColorScheme(
    primary = themeDarkPrimary,
    onPrimary = themeDarkOnPrimary,
    primaryContainer = themeDarkPrimaryContainer,
    onPrimaryContainer = themeDarkOnPrimaryContainer,
    secondary = themeDarkSecondary,
    onSecondary = themeDarkOnSecondary,
    secondaryContainer = themeDarkSecondaryContainer,
    onSecondaryContainer = themeDarkOnSecondaryContainer,
    tertiary = themeDarkTertiary,
    onTertiary = themeDarkOnTertiary,
    tertiaryContainer = themeDarkTertiaryContainer,
    onTertiaryContainer = themeDarkOnTertiaryContainer,
    error = themeDarkError,
    errorContainer = themeDarkErrorContainer,
    onError = themeDarkOnError,
    onErrorContainer = themeDarkOnErrorContainer,
    background = themeDarkBackground,
    onBackground = themeDarkOnBackground,
    surface = themeDarkSurface,
    onSurface = themeDarkOnSurface,
    surfaceVariant = themeDarkSurfaceVariant,
    onSurfaceVariant = themeDarkOnSurfaceVariant,
    outline = themeDarkOutline,
    inverseOnSurface = themeDarkInverseOnSurface,
    inverseSurface = themeDarkInverseSurface,
    inversePrimary = themeDarkInversePrimary,
)

@Composable
fun AppTheme(
    theme: Theme,
    content: @Composable () -> Unit,
) {
    val isSystemInDarkTheme = when (theme) {
        Theme.LIGHT -> false
        Theme.DARK -> true
        Theme.AUTO -> isSystemInDarkTheme()
    }

    val colors = if (supportsDynamic()) {
        val context = LocalContext.current
        if (isSystemInDarkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(
            context
        )
    } else {
        if (isSystemInDarkTheme) darkColorsScheme else lightColorsScheme
    }
    //todo add typography
    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}

fun supportsDynamic(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S