package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.SportsKabaddi
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Tv
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.components.LanguageSelectionDialog
import com.example.ui.components.SocialAccountsSection
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.BadgeEntity
import com.example.data.model.UserProfileEntity
import com.example.ui.OtakuViewModel
import com.example.ui.components.OtakuAvatarWithFrame
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
import com.example.ui.theme.OtakuTextPrimary
import com.example.ui.theme.OtakuTextSecondary

@Composable
fun ProfileScreen(
    viewModel: OtakuViewModel,
    profile: UserProfileEntity,
    badges: List<BadgeEntity>
) {
    var showEditProfileDialog by remember { mutableStateOf(false) }
    var showLanguageDialog by remember { mutableStateOf(false) }

    val socialAccounts by viewModel.socialAccounts.collectAsStateWithLifecycle()
    val currentLanguage by viewModel.currentLanguage.collectAsStateWithLifecycle()

    val bannerColors = when (profile.bannerTheme) {
        "CYBER_BLUE" -> listOf(Color(0xFFE0F2FE), Color(0xFFBAE6FD), OtakuDarkCard)
        "SOLAR_GOLD" -> listOf(Color(0xFFFEF3C7), Color(0xFFFDE68A), OtakuDarkCard)
        "SAKURA_PINK" -> listOf(Color(0xFFFCE7F3), Color(0xFFFBCFE8), OtakuDarkCard)
        else -> listOf(Color(0xFFEDE9FE), Color(0xFFDDD6FE), OtakuDarkCard) // NEBULA_PURPLE
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .otakuAtmosphericBackground(),
        contentPadding = PaddingValues(16.dp, bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Identity Hero Header with Customizable Banner and Glowing Frame
        item {
            Card(
                modifier = Modifier.fillMaxWidth().testTag("profile_identity_card"),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = OtakuDarkCard),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, OtakuDarkBorder)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Brush.verticalGradient(bannerColors))
                        .padding(18.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Avatar with Customizable Glowing Frame
                            OtakuAvatarWithFrame(
                                avatarEmoji = profile.avatarEmoji,
                                frameType = profile.profileFrame,
                                size = 68.dp
                            )

                            Spacer(modifier = Modifier.width(14.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = profile.username,
                                        fontSize = 19.sp,
                                        fontWeight = FontWeight.Black,
                                        color = OtakuTextPrimary
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(
                                                Brush.horizontalGradient(
                                                    listOf(OtakuPrimary, OtakuTertiary)
                                                )
                                            )
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            text = "Niv. ${profile.level}",
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Black,
                                            color = Color.White
                                        )
                                    }
                                }

                                Text(
                                    text = profile.tag,
                                    fontSize = 12.sp,
                                    color = OtakuTextMuted
                                )

                                Spacer(modifier = Modifier.height(3.dp))

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = profile.rankTitle,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = OtakuSecondary
                                    )
                                }
                            }

                            OutlinedButton(
                                onClick = { showEditProfileDialog = true },
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.testTag("btn_edit_profile"),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = OtakuSecondary),
                                border = androidx.compose.foundation.BorderStroke(1.dp, OtakuSecondary)
                            ) {
                                Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Style", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Bio
                        Text(
                            text = profile.bio,
                            fontSize = 12.sp,
                            color = OtakuTextSecondary,
                            lineHeight = 17.sp
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // XP Progress Bar
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Progression de Rang", fontSize = 11.sp, color = OtakuTextMuted)
                            Text(
                                "${profile.currentXp} / ${profile.maxXp} XP",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = OtakuSecondary
                            )
                        }
                        Spacer(modifier = Modifier.height(5.dp))
                        LinearProgressIndicator(
                            progress = { (profile.currentXp.toFloat() / profile.maxXp.toFloat()).coerceIn(0f, 1f) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            color = OtakuSecondary,
                            trackColor = OtakuDarkBorder
                        )
                    }
                }
            }
        }

        // Stats Grid: Victoires, Quiz, Animes, Mangas, Clubs
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    StatCard(
                        title = "Duels Gagnés",
                        value = "${profile.winsCount}",
                        icon = "🏆",
                        color = OtakuTertiary,
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        title = "Quiz Réussis",
                        value = "${profile.quizzesSolved}",
                        icon = "🧠",
                        color = OtakuSecondary,
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        title = "Clubs Otaku",
                        value = "${profile.joinedClubsCount}",
                        icon = "🏯",
                        color = OtakuPrimary,
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    StatCard(
                        title = "Animes Suivis",
                        value = "42",
                        icon = "📺",
                        color = Color(0xFF0284C7),
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        title = "Mangas Lus",
                        value = "89",
                        icon = "📖",
                        color = Color(0xFFD97706),
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        title = "Réputation",
                        value = "Top 5%",
                        icon = "⭐",
                        color = OtakuTertiary,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Favorite Animes & Characters
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = OtakuDarkCard),
                border = androidx.compose.foundation.BorderStroke(1.dp, OtakuDarkBorder)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("🌸 Univers & Héros Favoris", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = OtakuTextPrimary)
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                    Text("Anime préférés :", fontSize = 11.sp, color = OtakuTextMuted)
                    Text(profile.favoriteAnimes, fontSize = 13.sp, color = OtakuSecondary, fontWeight = FontWeight.SemiBold)

                    Spacer(modifier = Modifier.height(10.dp))

                    Text("Personnages favoris :", fontSize = 11.sp, color = OtakuTextMuted)
                    Text(profile.favoriteCharacters, fontSize = 13.sp, color = OtakuTextPrimary, fontWeight = FontWeight.SemiBold)
                }
            }
        }

        // Activity History (Dernières Activités Otaku)
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = OtakuDarkCard),
                border = androidx.compose.foundation.BorderStroke(1.dp, OtakuDarkBorder)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.History, contentDescription = null, tint = OtakuSecondary, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Historique d'Activité Otaku", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = OtakuTextPrimary)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    val activities = listOf(
                        Triple("⚔️", "Victoire en Anime Battle vs Alex", "+350 XP • Il y a 2h"),
                        Triple("🧠", "Quiz Jujutsu Kaisen maîtrisé avec succès", "+50 XP • Il y a 5h"),
                        Triple("🏯", "A rejoint le Club Mugiwara Crew", "Nouveau membre • Hier"),
                        Triple("💬", "Commentaire publié sur l'analyse de Gojo", "+10 XP • Il y a 2j"),
                        Triple("🎖️", "Badge 'Chasseur de Débats' débloqué", "Rang Or • Il y a 3j")
                    )

                    activities.forEachIndexed { index, act ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(OtakuDarkCardElevated),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(act.first, fontSize = 14.sp)
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(act.second, fontSize = 12.sp, color = OtakuTextPrimary, fontWeight = FontWeight.Medium)
                                Text(act.third, fontSize = 10.sp, color = OtakuTertiary)
                            }
                        }
                        if (index < activities.size - 1) {
                            Spacer(modifier = Modifier.height(2.dp))
                        }
                    }
                }
            }
        }

        // Language Selection Quick Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showLanguageDialog = true }
                    .testTag("btn_change_language_profile"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = OtakuDarkCard),
                border = androidx.compose.foundation.BorderStroke(1.dp, OtakuDarkBorder)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(Color(0x22BD00FF)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Language,
                                contentDescription = null,
                                tint = OtakuTertiary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Langue de l'application",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = OtakuTextPrimary
                            )
                            Text(
                                text = "${currentLanguage.flag} ${currentLanguage.nativeName} (${currentLanguage.displayName})",
                                fontSize = 11.sp,
                                color = OtakuSecondary
                            )
                        }
                    }

                    OutlinedButton(
                        onClick = { showLanguageDialog = true },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = OtakuTertiary),
                        border = androidx.compose.foundation.BorderStroke(1.dp, OtakuTertiary.copy(alpha = 0.5f))
                    ) {
                        Text("Changer", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Social Accounts & Sign-in Linkage Section (Google, Facebook, Messenger, WeChat)
        item {
            SocialAccountsSection(
                socialAccounts = socialAccounts,
                onConnect = { providerId, handle ->
                    viewModel.connectSocialAccount(providerId, handle)
                },
                onDisconnect = { providerId ->
                    viewModel.disconnectSocialAccount(providerId)
                }
            )
        }

        // Badges Section
        item {
            Text(
                text = "Collection de Badges (${badges.count { it.isUnlocked }}/${badges.size}) 🎖️",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = OtakuTextPrimary,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                badges.forEach { badge ->
                    BadgeItemRow(badge = badge)
                }
            }
        }
    }

    if (showEditProfileDialog) {
        CustomizationProfileDialog(
            profile = profile,
            onDismiss = { showEditProfileDialog = false },
            onSave = { username, avatar, rank, bio, favAnimes, favChars, frame, banner ->
                viewModel.updateProfileCustomization(
                    username = username,
                    avatarEmoji = avatar,
                    rankTitle = rank,
                    bio = bio,
                    favAnimes = favAnimes,
                    favCharacters = favChars,
                    profileFrame = frame,
                    bannerTheme = banner
                )
                showEditProfileDialog = false
            }
        )
    }

    if (showLanguageDialog) {
        LanguageSelectionDialog(
            currentLanguage = currentLanguage,
            onSelectLanguage = { newLang ->
                viewModel.setLanguage(newLang)
                showLanguageDialog = false
            },
            onDismiss = { showLanguageDialog = false }
        )
    }
}

@Composable
fun StatCard(title: String, value: String, icon: String, color: Color, modifier: Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = OtakuDarkCard),
        border = androidx.compose.foundation.BorderStroke(1.dp, OtakuDarkBorder)
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(icon, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(3.dp))
            Text(value, fontSize = 15.sp, fontWeight = FontWeight.Black, color = color)
            Text(title, fontSize = 10.sp, color = OtakuTextMuted)
        }
    }
}

@Composable
fun BadgeItemRow(badge: BadgeEntity) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (badge.isUnlocked) OtakuDarkCard else Color(0xFFF8FAFC)
        ),
        border = if (badge.isUnlocked) androidx.compose.foundation.BorderStroke(1.dp, OtakuTertiary.copy(alpha = 0.5f)) else androidx.compose.foundation.BorderStroke(1.dp, OtakuDarkBorder)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(if (badge.isUnlocked) OtakuTertiary.copy(alpha = 0.15f) else Color(0xFFE2E8F0))
                    .border(
                        1.dp,
                        if (badge.isUnlocked) OtakuTertiary else OtakuDarkBorder,
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(badge.icon, fontSize = 22.sp)
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = badge.title,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (badge.isUnlocked) OtakuTextPrimary else OtakuTextMuted
                    )
                    if (badge.isUnlocked) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("• Débloqué ✨", fontSize = 10.sp, color = OtakuTertiary, fontWeight = FontWeight.Bold)
                    }
                }
                Text(
                    text = badge.description,
                    fontSize = 11.sp,
                    color = OtakuTextSecondary,
                    lineHeight = 15.sp
                )
            }
        }
    }
}

@Composable
fun CustomizationProfileDialog(
    profile: UserProfileEntity,
    onDismiss: () -> Unit,
    onSave: (
        username: String,
        avatarEmoji: String,
        rankTitle: String,
        bio: String,
        favAnimes: String,
        favCharacters: String,
        profileFrame: String,
        bannerTheme: String
    ) -> Unit
) {
    var username by remember { mutableStateOf(profile.username) }
    var selectedAvatar by remember { mutableStateOf(profile.avatarEmoji) }
    var selectedRank by remember { mutableStateOf(profile.rankTitle) }
    var selectedFrame by remember { mutableStateOf(profile.profileFrame) }
    var selectedBanner by remember { mutableStateOf(profile.bannerTheme) }
    var bio by remember { mutableStateOf(profile.bio) }
    var favAnimes by remember { mutableStateOf(profile.favoriteAnimes) }
    var favCharacters by remember { mutableStateOf(profile.favoriteCharacters) }

    val avatars = listOf("⚡", "🦊", "👑", "🔮", "🗡️", "🌸", "🪐", "🍃")
    val frames = listOf(
        "CYBER_AURA" to "⚡ Néon Cyber",
        "GOLD_ROYAL" to "👑 Or Royal",
        "FLAME_PILLAR" to "🔥 Flamme",
        "SHADOW_VOID" to "🔮 Ombre Void"
    )
    val banners = listOf(
        "NEBULA_PURPLE" to "Nébuleuse 🌌",
        "CYBER_BLUE" to "Cyberpunk 🏙️",
        "SOLAR_GOLD" to "Or Solaire ☀️",
        "SAKURA_PINK" to "Sakura 🌸"
    )
    val ranks = listOf(
        "Elite Otaku ⚡",
        "Hokage en Devenir 🍃",
        "Chasseur de Primes 👑",
        "Sorcier de Classe S 🔮",
        "Pilier de la Flamme 🗡️",
        "Maître Otaku ⭐"
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Personnalisation Otaku ✨", color = OtakuTextPrimary, fontWeight = FontWeight.Bold) },
        text = {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    Text("Choisis ton Avatar :", fontSize = 12.sp, color = OtakuTextMuted, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(avatars) { av ->
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(if (selectedAvatar == av) OtakuPrimary.copy(alpha = 0.15f) else OtakuDarkCardElevated)
                                    .border(
                                        2.dp,
                                        if (selectedAvatar == av) OtakuPrimary else OtakuDarkBorder,
                                        CircleShape
                                    )
                                    .clickable { selectedAvatar = av },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(av, fontSize = 20.sp)
                            }
                        }
                    }
                }

                item {
                    Text("Cadre d'Avatar :", fontSize = 12.sp, color = OtakuTextMuted, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(frames) { (frameKey, frameName) ->
                            val isSelected = selectedFrame == frameKey
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(if (isSelected) OtakuSecondary else OtakuDarkCardElevated)
                                    .border(
                                        1.5.dp,
                                        if (isSelected) OtakuSecondary else OtakuDarkBorder,
                                        RoundedCornerShape(10.dp)
                                    )
                                    .clickable { selectedFrame = frameKey }
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text(frameName, fontSize = 11.sp, color = if (isSelected) Color.White else OtakuTextSecondary, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                item {
                    Text("Thème de Bannière :", fontSize = 12.sp, color = OtakuTextMuted, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(banners) { (banKey, banName) ->
                            val isSelected = selectedBanner == banKey
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(if (isSelected) OtakuTertiary else OtakuDarkCardElevated)
                                    .border(
                                        1.5.dp,
                                        if (isSelected) OtakuTertiary else OtakuDarkBorder,
                                        RoundedCornerShape(10.dp)
                                    )
                                    .clickable { selectedBanner = banKey }
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text(banName, fontSize = 11.sp, color = if (isSelected) Color.White else OtakuTextSecondary, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                item {
                    Text("Titre Préféré :", fontSize = 12.sp, color = OtakuTextMuted, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(ranks) { rk ->
                            val isSelected = selectedRank == rk
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(if (isSelected) OtakuPrimary else OtakuDarkCardElevated)
                                    .border(
                                        1.5.dp,
                                        if (isSelected) OtakuPrimary else OtakuDarkBorder,
                                        RoundedCornerShape(10.dp)
                                    )
                                    .clickable { selectedRank = rk }
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text(rk, fontSize = 11.sp, color = if (isSelected) Color.White else OtakuTextSecondary, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                item {
                    OutlinedTextField(
                        value = username,
                        onValueChange = { username = it },
                        label = { Text("Pseudo", color = OtakuTextMuted) },
                        modifier = Modifier.fillMaxWidth().testTag("input_edit_username"),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = OtakuTextPrimary,
                            unfocusedTextColor = OtakuTextPrimary,
                            focusedBorderColor = OtakuSecondary,
                            unfocusedBorderColor = OtakuDarkBorder
                        )
                    )
                }

                item {
                    OutlinedTextField(
                        value = bio,
                        onValueChange = { bio = it },
                        label = { Text("Bio Otaku", color = OtakuTextMuted) },
                        modifier = Modifier.fillMaxWidth().height(80.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = OtakuTextPrimary,
                            unfocusedTextColor = OtakuTextPrimary,
                            focusedBorderColor = OtakuSecondary,
                            unfocusedBorderColor = OtakuDarkBorder
                        )
                    )
                }

                item {
                    OutlinedTextField(
                        value = favAnimes,
                        onValueChange = { favAnimes = it },
                        label = { Text("Anime favoris", color = OtakuTextMuted) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = OtakuTextPrimary,
                            unfocusedTextColor = OtakuTextPrimary,
                            focusedBorderColor = OtakuSecondary,
                            unfocusedBorderColor = OtakuDarkBorder
                        )
                    )
                }

                item {
                    OutlinedTextField(
                        value = favCharacters,
                        onValueChange = { favCharacters = it },
                        label = { Text("Personnages préférés", color = OtakuTextMuted) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = OtakuTextPrimary,
                            unfocusedTextColor = OtakuTextPrimary,
                            focusedBorderColor = OtakuSecondary,
                            unfocusedBorderColor = OtakuDarkBorder
                        )
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSave(
                        username,
                        selectedAvatar,
                        selectedRank,
                        bio,
                        favAnimes,
                        favCharacters,
                        selectedFrame,
                        selectedBanner
                    )
                },
                colors = ButtonDefaults.buttonColors(containerColor = OtakuPrimary),
                modifier = Modifier.testTag("btn_save_profile")
            ) {
                Text("Enregistrer ✨", fontWeight = FontWeight.Bold, color = Color.White)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Annuler", color = OtakuTextMuted)
            }
        },
        containerColor = OtakuDarkSurface
    )
}
