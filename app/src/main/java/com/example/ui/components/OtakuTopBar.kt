package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.OtakuDarkBackground
import com.example.ui.theme.OtakuDarkBorder
import com.example.ui.theme.OtakuDarkCardElevated
import com.example.ui.theme.OtakuDarkSurface
import com.example.ui.theme.OtakuPrimary
import com.example.ui.theme.OtakuSecondary
import com.example.ui.theme.OtakuTertiary
import com.example.ui.theme.OtakuTextMuted
import com.example.ui.theme.OtakuTextPrimary
import com.example.ui.theme.OtakuTextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtakuTopBar(
    title: String,
    canNavigateBack: Boolean,
    unreadNotificationsCount: Int,
    currentLanguageFlag: String = "🇫🇷",
    onOpenLanguage: () -> Unit = {},
    onNavigateBack: () -> Unit,
    onOpenSearch: () -> Unit,
    onOpenEvents: () -> Unit,
    onOpenNotifications: () -> Unit
) {
    val japaneseSubtitle = when (title) {
        "Otaku Hub" -> "オタクハブ • 公式ポータル"
        "Clubs" -> "ギルド • コミュニティ"
        "Quiz & Duel" -> "クイズ & アニメバトル"
        "Classement" -> "天下一順位表 • ランキング"
        "Mon Profil" -> "ハンター証 • マイページ"
        "Événements" -> "公式大会 • イベント"
        "Notifications" -> "新着通知 • お知らせ"
        "Découverte" -> "アニメ検索 • エクスプローラ"
        else -> "アニメハブ"
    }

    Column {
        TopAppBar(
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (!canNavigateBack) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(CircleShape)
                                .border(1.5.dp, OtakuPrimary, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_otaku_logo),
                                contentDescription = "Logo",
                                modifier = Modifier.size(34.dp),
                                contentScale = ContentScale.Crop
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                    }

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = title,
                                fontWeight = FontWeight.Black,
                                fontSize = 18.sp,
                                color = OtakuTextPrimary,
                                letterSpacing = 0.5.sp
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(
                                        Brush.horizontalGradient(
                                            listOf(OtakuPrimary, OtakuSecondary)
                                        )
                                    )
                                    .padding(horizontal = 5.dp, vertical = 1.5.dp)
                            ) {
                                Text(
                                    text = "S-RANK",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color.White
                                )
                            }
                        }
                        Text(
                            text = japaneseSubtitle,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium,
                            color = OtakuSecondary,
                            letterSpacing = 0.8.sp
                        )
                    }
                }
            },
            navigationIcon = {
                if (canNavigateBack) {
                    IconButton(
                        onClick = onNavigateBack,
                        modifier = Modifier.testTag("top_bar_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Retour",
                            tint = OtakuTextPrimary
                        )
                    }
                }
            },
            actions = {
                IconButton(
                    onClick = onOpenLanguage,
                    modifier = Modifier.testTag("top_bar_language_button")
                ) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(Color(0x22BD00FF)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = currentLanguageFlag, fontSize = 16.sp)
                    }
                }
                IconButton(
                    onClick = onOpenSearch,
                    modifier = Modifier.testTag("top_bar_search_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Rechercher",
                        tint = OtakuPrimary
                    )
                }
                IconButton(
                    onClick = onOpenEvents,
                    modifier = Modifier.testTag("top_bar_events_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Event,
                        contentDescription = "Événements",
                        tint = OtakuTertiary
                    )
                }
                IconButton(
                    onClick = onOpenNotifications,
                    modifier = Modifier.testTag("top_bar_notifications_button")
                ) {
                    Box {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Notifications",
                            tint = OtakuTextPrimary
                        )
                        if (unreadNotificationsCount > 0) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .align(Alignment.TopEnd)
                                    .offset(x = 1.dp, y = (-1).dp)
                                    .clip(CircleShape)
                                    .background(OtakuPrimary)
                                    .border(1.dp, Color.White, CircleShape)
                            )
                        }
                    }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = OtakuDarkSurface,
                titleContentColor = OtakuTextPrimary
            )
        )

        // Anime Light Accent Divider
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(OtakuDarkBorder)
        )
    }
}
