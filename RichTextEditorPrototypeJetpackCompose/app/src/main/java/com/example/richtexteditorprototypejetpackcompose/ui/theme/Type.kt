package com.example.richtexteditorprototypejetpackcompose.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import com.example.richtexteditorprototypejetpackcompose.R

val openSansFamily = FontFamily(
    Font(R.font.roboto_light, FontWeight.Light),
    Font(R.font.roboto_light_italic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.roboto_regular, FontWeight.Normal),
    Font(R.font.roboto_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.roboto_medium, FontWeight.Medium),
    Font(R.font.roboto_medium_italic, FontWeight.Medium, FontStyle.Italic),
    Font(R.font.roboto_bold, FontWeight.Bold),
    Font(R.font.roboto_bold_italic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.roboto_semi_bold, FontWeight.SemiBold),
    Font(R.font.roboto_semi_bold_italic, FontWeight.SemiBold, FontStyle.Italic),
    Font(R.font.roboto_extra_bold, FontWeight.ExtraBold),
    Font(R.font.roboto_extra_bold_italic, FontWeight.ExtraBold, FontStyle.Italic),
)

val Typography = Typography(
    bodyLarge = Typography().bodyLarge.copy(
        fontFamily = openSansFamily,
        fontWeight = FontWeight.Medium,
    ),
    bodyMedium = Typography().bodyMedium.copy(
        fontFamily = openSansFamily,
        fontWeight = FontWeight.Medium
    ),
    bodySmall = Typography().bodySmall.copy(
        fontFamily = openSansFamily,
        fontWeight = FontWeight.Medium,
    ),
    labelLarge = Typography().labelLarge.copy(
        fontFamily = openSansFamily,
        fontWeight = FontWeight.Medium
    ),
    labelMedium = Typography().labelMedium.copy(
        fontFamily = openSansFamily,
        fontWeight = FontWeight.Light
    ),
    labelSmall = Typography().labelSmall.copy(
        fontFamily = openSansFamily,
        fontWeight = FontWeight.Light
    ),
    titleLarge = Typography().titleLarge.copy(
        fontFamily = openSansFamily,
        fontWeight = FontWeight.ExtraBold
    ),
    titleMedium = Typography().titleMedium.copy(
        fontFamily = openSansFamily,
        fontWeight = FontWeight.Bold
    ),
    titleSmall = Typography().titleSmall.copy(
        fontFamily = openSansFamily,
        fontWeight = FontWeight.SemiBold
    )
)