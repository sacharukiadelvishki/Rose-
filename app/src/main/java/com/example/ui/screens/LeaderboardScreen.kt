package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.LeaderboardUser
import com.example.ui.OtakuViewModel
import com.example.ui.components.otakuAtmosphericBackground
import com.example.ui.theme.OtakuDarkBackground
import com.example.ui.theme.OtakuDarkBorder
import com.example.ui.theme.OtakuDarkCard
import com.example.ui.theme.OtakuDarkCardElevated
import com.example.ui.theme.OtakuDarkSurface
import com.example.ui.theme.OtakuPrimary
import com.example.ui.theme.OtakuSecondary
import com.example.ui.theme.OtakuTertiary
import com.example.ui.theme.OtakuTextMuted
import com.example.ui.theme.OtakuTextSecondary

@Composable
fun LeaderboardScreen(
    viewModel: OtakuViewModel,
    selectedTab: Int
) {
    val tabs = listOf(
        "🌍 Global",
        "🏯 Club",
        "📅 Hebdomadaire",
        "🧠 Quiz",
        "⚔️ Battle"
    )

    val leaderboardData = viewModel.getLeaderboardUsers()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .otakuAtmosphericBackground()
    ) {
        // Tab row with horizontal scroll
        ScrollableTabRow(
            selectedTabIndex = selectedTab,
            containerColor = OtakuDarkSurface,
            contentColor = Color.White,
            edgePadding = 12.dp,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                    color = OtakuTertiary
                )
            }
        ) {
            tabs.forEachIndexed { index, tabTitle ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { viewModel.setLeaderboardTab(index) },
                    text = {
                        Text(
                            text = tabTitle,
                            fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 13.sp,
                            color = if (selectedTab == index) OtakuTertiary else OtakuTextSecondary
                        )
                    },
                    modifier = Modifier.testTag("leaderboard_tab_$index")
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp, bottom = 80.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Header Explanation Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = OtakuDarkSurface)
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFF261D42),
                                        OtakuDarkSurface
                                    )
                                )
                            )
                            .padding(14.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(Color(0x22FFB800)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(imageVector = Icons.Default.EmojiEvents, contentDescription = null, tint = OtakuTertiary)
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = tabs[selectedTab],
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    text = when (selectedTab) {
                                        0 -> "Classement global basé sur l'expérience totale cumulée."
                                        1 -> "Les clubs les plus prestigieux et influents d'Otaku Hub."
                                        2 -> "Les membres les plus actifs et en forme des 7 derniers jours !"
                                        3 -> "Les esprits les plus brillants et incollables en culture manga."
                                        else -> "Les guerriers invaincus de l'Arène Anime Battle."
                                    },
                                    fontSize = 11.sp,
                                    color = OtakuTextSecondary
                                )
                            }
                        }
                    }
                }
            }

            // Top 3 Podium Cards preview
            item {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    val first = leaderboardData.getOrNull(0)
                    val second = leaderboardData.getOrNull(1)
                    val third = leaderboardData.getOrNull(2)

                    second?.let { PodiumColumn(it, 2, 110.dp, Color(0xFFC0C0C0), Modifier.weight(1f)) }
                    first?.let { PodiumColumn(it, 1, 130.dp, OtakuTertiary, Modifier.weight(1f)) }
                    third?.let { PodiumColumn(it, 3, 95.dp, Color(0xFFCD7F32), Modifier.weight(1f)) }
                }
            }

            // Leaderboard list
            items(leaderboardData, key = { it.rank.toString() + it.name }) { entry ->
                LeaderboardRowItem(entry = entry)
            }
        }
    }
}

@Composable
fun PodiumColumn(user: LeaderboardUser, rank: Int, height: androidx.compose.ui.unit.Dp, crownColor: Color, modifier: Modifier) {
    Card(
        modifier = modifier.height(height),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = OtakuDarkCardElevated),
        border = androidx.compose.foundation.BorderStroke(1.dp, crownColor.copy(alpha = 0.6f))
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(user.avatar, fontSize = 20.sp)
            Text(
                text = "#$rank ${user.name}",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                maxLines = 1
            )
            Text(
                text = user.scoreDisplay,
                fontSize = 10.sp,
                color = crownColor,
                fontWeight = FontWeight.Black
            )
        }
    }
}

@Composable
fun LeaderboardRowItem(entry: LeaderboardUser) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("leaderboard_row_${entry.rank}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (entry.isCurrentUser) Color(0xFF281C4A) else OtakuDarkCard
        ),
        border = if (entry.isCurrentUser) androidx.compose.foundation.BorderStroke(1.5.dp, OtakuPrimary) else null
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Rank Number
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(
                        when (entry.rank) {
                            1 -> OtakuTertiary.copy(alpha = 0.2f)
                            2 -> Color(0x33C0C0C0)
                            3 -> Color(0x33CD7F32)
                            else -> OtakuDarkCardElevated
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${entry.rank}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = when (entry.rank) {
                        1 -> OtakuTertiary
                        2 -> Color(0xFFE0E0E0)
                        3 -> Color(0xFFFFB74D)
                        else -> OtakuTextMuted
                    }
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Avatar Emoji
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(OtakuDarkCardElevated),
                contentAlignment = Alignment.Center
            ) {
                Text(text = entry.avatar, fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.width(12.dp))

            // User Info
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = entry.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = if (entry.isCurrentUser) OtakuPrimary else Color.White
                    )
                    if (entry.isCurrentUser) {
                        Spacer(modifier = Modifier.width(4.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(OtakuPrimary)
                                .padding(horizontal = 4.dp, vertical = 1.dp)
                        ) {
                            Text("Toi", fontSize = 9.sp, color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
                Text(
                    text = entry.subtitle,
                    fontSize = 11.sp,
                    color = OtakuTextMuted
                )
            }

            // Score Badge
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(OtakuDarkCardElevated)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = entry.scoreDisplay,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = OtakuSecondary
                )
            }
        }
    }
}
