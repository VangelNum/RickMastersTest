package com.vangelnum.rickmasterstest.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.vangelnum.rickmasterstest.R

val circleFontFamily = FontFamily(
    Font(R.font.circle_thin, weight = FontWeight.Thin),
    Font(R.font.circle_light, weight = FontWeight.Light),
    Font(R.font.circle_extra_light, weight = FontWeight.ExtraLight),
    Font(R.font.circle_regular, weight = FontWeight.Normal),
    Font(R.font.circle_bold, weight = FontWeight.Bold),
    Font(R.font.circle_extra_bold, weight = FontWeight.ExtraBold),
)

val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = circleFontFamily,
        fontSize = 21.sp,
        fontWeight = FontWeight.W400,
        color = MainFontColor
    ),
    titleMedium = TextStyle(
        fontFamily = circleFontFamily,
        fontSize = 17.sp,
        fontWeight = FontWeight.W400,
        color = MainFontColor
    ),
    headlineMedium = TextStyle(
        fontFamily = circleFontFamily,
        fontSize = 14.sp,
        fontWeight = FontWeight.W300,
        color = HeadlineFontColor
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)