package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Diversity3
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.MeetingRoom
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.SportsKabaddi
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.i18n.LocalOtakuStrings
import com.example.ui.navigation.Screen
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

data class AnimeNavItem(
    val route: String,
    val label: String,
    val japaneseKatakana: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

@Composable
fun OtakuBottomBar(
    currentRoute: String,
    onNavigateToRoute: (String) -> Unit
) {
    val strings = LocalOtakuStrings.current

    Column {
        // Top clean divider line
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(OtakuDarkBorder)
        )

        NavigationBar(
            windowInsets = WindowInsets.navigationBars,
            containerColor = OtakuDarkSurface,
            tonalElevation = 3.dp
        ) {
            val items = listOf(
                AnimeNavItem(Screen.Home.route, strings.navHome, "ネクサス", Icons.Default.Home),
                AnimeNavItem(Screen.Portals.route, strings.navPortals, "異世界", Icons.Default.AutoAwesome),
                AnimeNavItem(Screen.Quiz.route, strings.navQuiz, "バトル", Icons.Default.SportsKabaddi),
                AnimeNavItem(Screen.Room.route, strings.navRoom, "マイルーム", Icons.Default.MeetingRoom),
                AnimeNavItem(Screen.Profile.route, strings.navProfile, "マイ頁", Icons.Default.Person)
            )

            items.forEach { item ->
                val isSelected = currentRoute == item.route
                NavigationBarItem(
                    selected = isSelected,
                    onClick = { onNavigateToRoute(item.route) },
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                            modifier = Modifier.size(22.dp)
                        )
                    },
                    label = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = item.label,
                                fontSize = 10.sp,
                                fontWeight = if (isSelected) FontWeight.Black else FontWeight.Normal,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = item.japaneseKatakana,
                                fontSize = 8.sp,
                                color = if (isSelected) OtakuSecondary else OtakuTextMuted,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1
                            )
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = OtakuPrimary,
                        selectedTextColor = OtakuPrimary,
                        indicatorColor = OtakuPrimary.copy(alpha = 0.12f),
                        unselectedIconColor = OtakuTextMuted,
                        unselectedTextColor = OtakuTextSecondary
                    ),
                    modifier = Modifier.testTag("nav_item_${item.route}")
                )
            }
        }
    }
}
