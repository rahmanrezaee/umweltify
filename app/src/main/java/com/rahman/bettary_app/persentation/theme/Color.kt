package com.rahman.bettary_app.persentation.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val Teal200 = Color(0xFF03DAC5)
val backgroundColor = Color(0xF0f7f7f7)
val backgroundColorDark = Color(0xF0272525)


val DarkColorPalette = darkColorScheme(
    primary = Teal200,
    primaryContainer = Teal200,
    secondary = Teal200,
    background = backgroundColorDark,
    secondaryContainer = Color.Black,
    onPrimaryContainer  = Color.White,
    scrim = Color.LightGray
)

val LightColorPalette = lightColorScheme(
    primary = Teal200,
    primaryContainer = Teal200,
    secondary = Teal200,
    background = backgroundColor,
    surface = backgroundColor,
    secondaryContainer = Color.White,
    onPrimaryContainer  = Color.Black,
    scrim = Color.Gray
//inversePrimary: Color = ColorDarkTokens.InversePrimary,
//secondary: Color = ColorDarkTokens.Secondary,
//secondaryContainer: Color = ColorDarkTokens.SecondaryContainer,
//onSecondaryContainer: Color = ColorDarkTokens.OnSecondaryContainer,
//tertiary: Color = ColorDarkTokens.Tertiary,
//onTertiary: Color = ColorDarkTokens.OnTertiary,
//tertiaryContainer: Color = ColorDarkTokens.TertiaryContainer,
//onTertiaryContainer: Color = ColorDarkTokens.OnTertiaryContainer,
//background: Color = ColorDarkTokens.Background,
//onBackground: Color = ColorDarkTokens.OnBackground,
//surface: Color = ColorDarkTokens.Surface,
//onSurface: Color = ColorDarkTokens.OnSurface,
//surfaceVariant: Color = ColorDarkTokens.SurfaceVariant,
//onSurfaceVariant: Color = ColorDarkTokens.OnSurfaceVariant,
//surfaceTint: Color = primary,
//inverseSurface: Color = ColorDarkTokens.InverseSurface,
//inverseOnSurface: Color = ColorDarkTokens.InverseOnSurface,
//error: Color = ColorDarkTokens.Error,
//onError: Color = ColorDarkTokens.OnError,
//errorContainer: Color = ColorDarkTokens.ErrorContainer,
//onErrorContainer: Color = ColorDarkTokens.OnErrorContainer,
//outline: Color = ColorDarkTokens.Outline,
//outlineVariant: Color = ColorDarkTokens.OutlineVariant,

)