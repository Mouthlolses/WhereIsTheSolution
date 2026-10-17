package com.whereisthesolution.whereisihesolutionapp.font

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import whereisthesolution.app.shared.generated.resources.Inter_Bold
import whereisthesolution.app.shared.generated.resources.Inter_Medium
import whereisthesolution.app.shared.generated.resources.Inter_Regular
import whereisthesolution.app.shared.generated.resources.Inter_SemiBold
import whereisthesolution.app.shared.generated.resources.Res


@Composable
fun interFontFamily(): FontFamily = FontFamily(
    Font(Res.font.Inter_Regular, FontWeight.Normal),
    Font(Res.font.Inter_Medium, FontWeight.Medium),
    Font(Res.font.Inter_SemiBold, FontWeight.SemiBold),
    Font(Res.font.Inter_Bold, FontWeight.Bold),
)