package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.LevelUpDialogEvent
import com.example.ui.XpToastEvent
import com.example.ui.theme.OtakuDarkBorder
import com.example.ui.theme.OtakuDarkCard
import com.example.ui.theme.OtakuDarkSurface
import com.example.ui.theme.OtakuPrimary
import com.example.ui.theme.OtakuSecondary
import com.example.ui.theme.OtakuTertiary
import com.example.ui.theme.OtakuTextPrimary
import com.example.ui.theme.OtakuTextSecondary
import kotlinx.coroutines.delay

/**
 * Satisfying, non-intrusive floating XP popup banner
 */
@Composable
fun XpFloatingToast(
    event: XpToastEvent?,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(event?.timestamp) {
        if (event != null) {
            delay(2800)
            onDismiss()
        }
    }

    AnimatedVisibility(
        visible = event != null,
        enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut(),
        modifier = modifier
    ) {
        if (event != null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier
                        .clip(RoundedCornerShape(24.dp))
                        .border(
                            width = 1.5.dp,
                            brush = Brush.horizontalGradient(
                                listOf(OtakuPrimary, OtakuTertiary, OtakuSecondary)
                            ),
                            shape = RoundedCornerShape(24.dp)
                        )
                        .clickable { onDismiss() }
                        .testTag("xp_floating_toast"),
                    colors = CardDefaults.cardColors(containerColor = OtakuDarkCard),
                    shape = RoundedCornerShape(24.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 18.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(
                                    Brush.radialGradient(
                                        listOf(OtakuTertiary, OtakuPrimary)
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Text(
                            text = event.label,
                            color = OtakuTextPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}

/**
 * Shonen Anime Style Level Up Celebration Dialog
 */
@Composable
fun LevelUpCelebrationDialog(
    event: LevelUpDialogEvent?,
    onDismiss: () -> Unit
) {
    if (event == null) return

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = OtakuPrimary),
                modifier = Modifier.fillMaxWidth().testTag("btn_close_level_up"),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Continuer l'Aventure ! 🔥", fontWeight = FontWeight.Black, fontSize = 15.sp, color = Color.White)
            }
        },
        title = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "🎉 NIVEAU SUPÉRIEUR !",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    color = OtakuTertiary,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Nouvel Éveil Otaku Débloqué",
                    fontSize = 12.sp,
                    color = OtakuSecondary,
                    fontWeight = FontWeight.SemiBold
                )
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    OtakuTertiary.copy(alpha = 0.25f),
                                    OtakuDarkCard
                                )
                            )
                        )
                        .border(3.dp, OtakuTertiary, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "LVL",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Black,
                            color = OtakuTertiary
                        )
                        Text(
                            text = "${event.newLevel}",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Black,
                            color = OtakuTextPrimary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Nouveau Titre :",
                    fontSize = 12.sp,
                    color = OtakuTextSecondary
                )
                Text(
                    text = event.newRank,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Black,
                    color = OtakuSecondary,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(10.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(OtakuDarkCard)
                        .border(1.dp, OtakuDarkBorder, RoundedCornerShape(12.dp))
                        .padding(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = OtakuTertiary,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Stats de profil et réputation augmentées !",
                            fontSize = 12.sp,
                            color = OtakuTextPrimary,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        },
        containerColor = OtakuDarkCard,
        shape = RoundedCornerShape(22.dp)
    )
}
