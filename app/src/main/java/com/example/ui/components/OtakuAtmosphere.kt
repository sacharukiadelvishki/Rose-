package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.OtakuDarkBackground
import com.example.ui.theme.OtakuDarkBorder
import com.example.ui.theme.OtakuPrimary
import com.example.ui.theme.OtakuSecondary
import com.example.ui.theme.OtakuTertiary

/**
 * Atmospheric background modifier for Otaku Hub screens.
 * Implements subtle, luminous anime sky gradients with celestial azure & golden auras.
 */
fun Modifier.otakuAtmosphericBackground(): Modifier = this.then(
    Modifier
        .drawBehind {
            // Top-right soft sapphire / sky aura
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        OtakuPrimary.copy(alpha = 0.07f),
                        Color.Transparent
                    ),
                    center = Offset(size.width * 0.85f, size.height * 0.12f),
                    radius = size.width * 0.7f
                ),
                radius = size.width * 0.7f,
                center = Offset(size.width * 0.85f, size.height * 0.12f)
            )

            // Bottom-left subtle celestial turquoise aura
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        OtakuSecondary.copy(alpha = 0.06f),
                        Color.Transparent
                    ),
                    center = Offset(size.width * 0.15f, size.height * 0.75f),
                    radius = size.width * 0.6f
                ),
                radius = size.width * 0.6f,
                center = Offset(size.width * 0.15f, size.height * 0.75f)
            )

            // Delicate sparkle accents in subtle primary hue
            val dots = listOf(
                Offset(size.width * 0.15f, size.height * 0.08f),
                Offset(size.width * 0.82f, size.height * 0.28f),
                Offset(size.width * 0.35f, size.height * 0.45f),
                Offset(size.width * 0.90f, size.height * 0.62f),
                Offset(size.width * 0.22f, size.height * 0.88f)
            )
            for (dot in dots) {
                drawCircle(
                    color = OtakuPrimary.copy(alpha = 0.12f),
                    radius = 2.dp.toPx(),
                    center = dot
                )
            }
        }
)

/**
 * Clean styled border for cards and interactive components
 */
fun Modifier.otakuNeonBorder(
    color: Color = OtakuDarkBorder,
    width: Dp = 1.dp,
    shape: Shape = RoundedCornerShape(16.dp)
): Modifier = this.then(
    Modifier.border(width, color, shape)
)

/**
 * Custom Otaku Avatar with styled profile frames
 */
@Composable
fun OtakuAvatarWithFrame(
    avatarEmoji: String,
    frameType: String,
    size: Dp = 56.dp,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "frameGlow")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.7f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAlpha"
    )

    val (borderColor, borderWidth, frameLabel) = when (frameType) {
        "FLAME_PILLAR" -> Triple(Color(0xFFEA580C), 2.5.dp, "🔥")
        "GOLD_ROYAL" -> Triple(OtakuTertiary, 2.5.dp, "👑")
        "SHADOW_VOID" -> Triple(Color(0xFF7C3AED), 2.5.dp, "🔮")
        else -> Triple(OtakuSecondary, 2.dp, "⚡") // CYBER_AURA
    }

    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        // Outer aura halo
        Box(
            modifier = Modifier
                .size(size)
                .clip(CircleShape)
                .border(
                    width = borderWidth,
                    color = borderColor.copy(alpha = pulseAlpha),
                    shape = CircleShape
                )
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            borderColor.copy(alpha = 0.18f),
                            Color(0xFFF1F5F9)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = avatarEmoji,
                fontSize = (size.value * 0.48f).sp
            )
        }
    }
}
