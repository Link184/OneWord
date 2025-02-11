package com.link184.oneword.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.link184.oneword.R

private val microSoft97 = FontFamily(
    Font(R.font.ms_sans_serif_bold, FontWeight.Bold),
    Font(R.font.ms_sans_serif, FontWeight.Normal)
)

val typography = Typography(
    displayLarge = TextStyle(
        fontFamily = microSoft97,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp
    ),
    displayMedium = TextStyle(
        fontFamily = microSoft97,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        letterSpacing = 0.15.sp
    ),
    titleMedium = TextStyle(
        fontFamily = microSoft97,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = microSoft97,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = microSoft97,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    labelLarge = TextStyle(
        fontFamily = microSoft97,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        letterSpacing = 1.sp
    ),
    labelMedium = TextStyle(
        fontFamily = microSoft97,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp
    )
)

val notePadTextStyle = typography.titleMedium.copy(
    fontWeight = FontWeight.SemiBold
)