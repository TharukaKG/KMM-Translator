package com.tharuka.traslator_kmm.android.core.presentation

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.tharuka.traslator_kmm.core.presentation.UiLanguage

@Composable
fun SmallLanguageIcon(
    language: UiLanguage,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = language.drawableRes,
        contentDescription = language.language.langName,
        modifier = modifier.size(25.dp)
    )
}