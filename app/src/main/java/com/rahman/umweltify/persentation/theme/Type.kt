package com.rahman.umweltify.persentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    displayLarge = CustomTypography.DisplayLarge,
    displayMedium = CustomTypography.DisplayMedium,
    displaySmall = CustomTypography.DisplaySmall,
    headlineLarge = CustomTypography.HeadlineLarge,
    headlineMedium = CustomTypography.HeadlineMedium,
    headlineSmall = CustomTypography.HeadlineSmall,
    titleLarge = CustomTypography.TitleLarge,
    titleMedium = CustomTypography.TitleMedium,
    titleSmall = CustomTypography.TitleSmall,
    bodyLarge = CustomTypography.BodyLarge,
    bodyMedium = CustomTypography.BodyMedium,
    bodySmall = CustomTypography.BodySmall,
    labelLarge = CustomTypography.LabelLarge,
    labelMedium = CustomTypography.LabelMedium,
    labelSmall = CustomTypography.LabelSmall,
)

object CustomTypography {
    val BodyLarge =
        TextStyle(
            fontFamily = TypeScaleTokens.BodyLargeFont,
            fontWeight = TypeScaleTokens.BodyLargeWeight,
            fontSize = TypeScaleTokens.BodyLargeSize,
            lineHeight = TypeScaleTokens.BodyLargeLineHeight,
            letterSpacing = TypeScaleTokens.BodyLargeTracking,
        )
    val BodyMedium =
        TextStyle(
            fontFamily = TypeScaleTokens.BodyMediumFont,
            fontWeight = TypeScaleTokens.BodyMediumWeight,
            fontSize = TypeScaleTokens.BodyMediumSize,
            lineHeight = TypeScaleTokens.BodyMediumLineHeight,
            letterSpacing = TypeScaleTokens.BodyMediumTracking,
        )
    val BodySmall =
        TextStyle(
            fontFamily = TypeScaleTokens.BodySmallFont,
            fontWeight = TypeScaleTokens.BodySmallWeight,
            fontSize = TypeScaleTokens.BodySmallSize,
            lineHeight = TypeScaleTokens.BodySmallLineHeight,
            letterSpacing = TypeScaleTokens.BodySmallTracking,
        )
    val DisplayLarge =
        TextStyle(
            fontFamily = TypeScaleTokens.DisplayLargeFont,
            fontWeight = TypeScaleTokens.DisplayLargeWeight,
            fontSize = TypeScaleTokens.DisplayLargeSize,
            lineHeight = TypeScaleTokens.DisplayLargeLineHeight,
            letterSpacing = TypeScaleTokens.DisplayLargeTracking,
        )
    val DisplayMedium =
        TextStyle(
            fontFamily = TypeScaleTokens.DisplayMediumFont,
            fontWeight = TypeScaleTokens.DisplayMediumWeight,
            fontSize = TypeScaleTokens.DisplayMediumSize,
            lineHeight = TypeScaleTokens.DisplayMediumLineHeight,
            letterSpacing = TypeScaleTokens.DisplayMediumTracking,
        )
    val DisplaySmall =
        TextStyle(
            fontFamily = TypeScaleTokens.DisplaySmallFont,
            fontWeight = TypeScaleTokens.DisplaySmallWeight,
            fontSize = TypeScaleTokens.DisplaySmallSize,
            lineHeight = TypeScaleTokens.DisplaySmallLineHeight,
            letterSpacing = TypeScaleTokens.DisplaySmallTracking,
        )
    val HeadlineLarge =
        TextStyle(
            fontFamily = TypeScaleTokens.HeadlineLargeFont,
            fontWeight = TypeScaleTokens.HeadlineLargeWeight,
            fontSize = TypeScaleTokens.HeadlineLargeSize,
            lineHeight = TypeScaleTokens.HeadlineLargeLineHeight,
            letterSpacing = TypeScaleTokens.HeadlineLargeTracking,
        )
    val HeadlineMedium =
        TextStyle(
            fontFamily = TypeScaleTokens.HeadlineMediumFont,
            fontWeight = TypeScaleTokens.HeadlineMediumWeight,
            fontSize = TypeScaleTokens.HeadlineMediumSize,
            lineHeight = TypeScaleTokens.HeadlineMediumLineHeight,
            letterSpacing = TypeScaleTokens.HeadlineMediumTracking,
        )
    val HeadlineSmall =
        TextStyle(
            fontFamily = TypeScaleTokens.HeadlineSmallFont,
            fontWeight = TypeScaleTokens.HeadlineSmallWeight,
            fontSize = TypeScaleTokens.HeadlineSmallSize,
            lineHeight = TypeScaleTokens.HeadlineSmallLineHeight,
            letterSpacing = TypeScaleTokens.HeadlineSmallTracking,
        )
    val LabelLarge =
        TextStyle(
            fontFamily = TypeScaleTokens.LabelLargeFont,
            fontWeight = TypeScaleTokens.LabelLargeWeight,
            fontSize = TypeScaleTokens.LabelLargeSize,
            lineHeight = TypeScaleTokens.LabelLargeLineHeight,
            letterSpacing = TypeScaleTokens.LabelLargeTracking,
        )
    val LabelMedium =
        TextStyle(
            fontFamily = TypeScaleTokens.LabelMediumFont,
            fontWeight = TypeScaleTokens.LabelMediumWeight,
            fontSize = TypeScaleTokens.LabelMediumSize,
            lineHeight = TypeScaleTokens.LabelMediumLineHeight,
            letterSpacing = TypeScaleTokens.LabelMediumTracking,
        )
    val LabelSmall =
        TextStyle(
            fontFamily = TypeScaleTokens.LabelSmallFont,
            fontWeight = TypeScaleTokens.LabelSmallWeight,
            fontSize = TypeScaleTokens.LabelSmallSize,
            lineHeight = TypeScaleTokens.LabelSmallLineHeight,
            letterSpacing = TypeScaleTokens.LabelSmallTracking,
        )
    val TitleLarge =
        TextStyle(
            fontFamily = TypeScaleTokens.TitleLargeFont,
            fontWeight = TypeScaleTokens.TitleLargeWeight,
            fontSize = TypeScaleTokens.TitleLargeSize,
            lineHeight = TypeScaleTokens.TitleLargeLineHeight,
            letterSpacing = TypeScaleTokens.TitleLargeTracking,
        )
    val TitleMedium =
        TextStyle(
            fontFamily = TypeScaleTokens.TitleMediumFont,
            fontWeight = TypeScaleTokens.TitleMediumWeight,
            fontSize = TypeScaleTokens.TitleMediumSize,
            lineHeight = TypeScaleTokens.TitleMediumLineHeight,
            letterSpacing = TypeScaleTokens.TitleMediumTracking,
        )
    val TitleSmall =
        TextStyle(
            fontFamily = TypeScaleTokens.TitleSmallFont,
            fontWeight = TypeScaleTokens.TitleSmallWeight,
            fontSize = TypeScaleTokens.TitleSmallSize,
            lineHeight = TypeScaleTokens.TitleSmallLineHeight,
            letterSpacing = TypeScaleTokens.TitleSmallTracking,
        )
}


object TypeScaleTokens {
    val BodyLargeFont = TypefaceCustomWeight.Plain
    val BodyLargeLineHeight = 24.0.sp
    val BodyLargeSize = 16.sp
    val BodyLargeTracking = 0.5.sp
    val BodyLargeWeight = TypefaceCustomWeight.WeightRegular
    val BodyMediumFont = TypefaceCustomWeight.Plain
    val BodyMediumLineHeight = 20.0.sp
    val BodyMediumSize = 14.sp
    val BodyMediumTracking = 0.2.sp
    val BodyMediumWeight = TypefaceCustomWeight.WeightRegular
    val BodySmallFont = TypefaceCustomWeight.Plain
    val BodySmallLineHeight = 16.0.sp
    val BodySmallSize = 12.sp
    val BodySmallTracking = 0.4.sp
    val BodySmallWeight = TypefaceCustomWeight.WeightRegular
    val DisplayLargeFont = TypefaceCustomWeight.Brand
    val DisplayLargeLineHeight = 64.0.sp
    val DisplayLargeSize = 57.sp
    val DisplayLargeTracking = -0.2.sp
    val DisplayLargeWeight = TypefaceCustomWeight.WeightRegular
    val DisplayMediumFont = TypefaceCustomWeight.Brand
    val DisplayMediumLineHeight = 52.0.sp
    val DisplayMediumSize = 45.sp
    val DisplayMediumTracking = 0.0.sp
    val DisplayMediumWeight = TypefaceCustomWeight.WeightRegular
    val DisplaySmallFont = TypefaceCustomWeight.Brand
    val DisplaySmallLineHeight = 44.0.sp
    val DisplaySmallSize = 36.sp
    val DisplaySmallTracking = 0.0.sp
    val DisplaySmallWeight = TypefaceCustomWeight.WeightRegular
    val HeadlineLargeFont = TypefaceCustomWeight.Brand
    val HeadlineLargeLineHeight = 40.0.sp
    val HeadlineLargeSize = 32.sp
    val HeadlineLargeTracking = 0.0.sp
    val HeadlineLargeWeight = TypefaceCustomWeight.WeightRegular
    val HeadlineMediumFont = TypefaceCustomWeight.Brand
    val HeadlineMediumLineHeight = 36.0.sp
    val HeadlineMediumSize = 28.sp
    val HeadlineMediumTracking = 0.0.sp
    val HeadlineMediumWeight = TypefaceCustomWeight.WeightRegular
    val HeadlineSmallFont = TypefaceCustomWeight.Brand
    val HeadlineSmallLineHeight = 32.0.sp
    val HeadlineSmallSize = 24.sp
    val HeadlineSmallTracking = 0.0.sp
    val HeadlineSmallWeight = TypefaceCustomWeight.WeightRegular
    val LabelLargeFont = TypefaceCustomWeight.Plain
    val LabelLargeLineHeight = 20.0.sp
    val LabelLargeSize = 14.sp
    val LabelLargeTracking = 0.1.sp
    val LabelLargeWeight = TypefaceCustomWeight.WeightMedium
    val LabelMediumFont = TypefaceCustomWeight.Plain
    val LabelMediumLineHeight = 16.0.sp
    val LabelMediumSize = 12.sp
    val LabelMediumTracking = 0.5.sp
    val LabelMediumWeight = TypefaceCustomWeight.WeightMedium
    val LabelSmallFont = TypefaceCustomWeight.Plain
    val LabelSmallLineHeight = 16.0.sp
    val LabelSmallSize = 11.sp
    val LabelSmallTracking = 0.5.sp
    val LabelSmallWeight = TypefaceCustomWeight.WeightMedium
    val TitleLargeFont = TypefaceCustomWeight.Brand
    val TitleLargeLineHeight = 28.0.sp
    val TitleLargeSize = 22.sp
    val TitleLargeTracking = 0.0.sp
    val TitleLargeWeight = TypefaceCustomWeight.WeightRegular
    val TitleMediumFont = TypefaceCustomWeight.Plain
    val TitleMediumLineHeight = 24.0.sp
    val TitleMediumSize = 16.sp
    val TitleMediumTracking = 0.2.sp
    val TitleMediumWeight = TypefaceCustomWeight.WeightMedium
    val TitleSmallFont = TypefaceCustomWeight.Plain
    val TitleSmallLineHeight = 20.0.sp
    val TitleSmallSize = 14.sp
    val TitleSmallTracking = 0.1.sp
    val TitleSmallWeight = TypefaceCustomWeight.WeightMedium
}


object TypefaceCustomWeight {
    val Brand = font
    val Plain = font
    val WeightBold = FontWeight.Bold
    val WeightMedium = FontWeight.Medium
    val WeightRegular = FontWeight.Normal
}