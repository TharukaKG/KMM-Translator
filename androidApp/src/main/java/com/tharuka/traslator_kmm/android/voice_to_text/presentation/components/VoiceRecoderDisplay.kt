package com.tharuka.traslator_kmm.android.voice_to_text.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tharuka.traslator_kmm.android.translate.presentation.components.gradientSurface
import kotlin.random.Random


@Composable
fun VoiceRecorderDisplay(
    powerRatio: List<Float>,
    modifier: Modifier = Modifier
) {
    val primary = MaterialTheme.colors.primary
    Box(
        modifier = modifier
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(20.dp)
            )
            .clip(
                shape = RoundedCornerShape(20.dp)
            )
            .gradientSurface()
            .padding(
                horizontal = 32.dp,
                vertical = 8.dp
            )
            .drawBehind {
                val powerRatioWidth = 3.dp.toPx()
                val powerRatioCount = ((size.width / powerRatioWidth) / 2).toInt()

                clipRect(
                    left = 0f,
                    top = 0f,
                    right = size.width,
                    bottom = size.height
                ) {
                    powerRatio
                        .takeLast(powerRatioCount)
                        .reversed()
                        .forEachIndexed { index, ratio ->
                            val yTopStart = center.y - (size.height / 2f) * ratio
                            drawRoundRect(
                                color = primary,
                                topLeft = Offset(
                                    x = size.width - index * 2 * powerRatioWidth,
                                    y = yTopStart
                                ),
                                size = Size(
                                    width = powerRatioWidth,
                                    height = (center.y - yTopStart) * 2f
                                ),
                                cornerRadius = CornerRadius(100f)
                            )
                        }
                }

            }
    )
}

@Preview
@Composable
fun VoiceRecorderDisplay() {
    VoiceRecorderDisplay(
        powerRatio = (0..100).map { Random.nextFloat() },
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    )
}