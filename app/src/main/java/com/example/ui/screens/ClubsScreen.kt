package com.example.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Diversity3
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.ClubEntity
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
import com.example.ui.theme.OtakuTextPrimary
import com.example.ui.theme.OtakuTextSecondary

@Composable
fun ClubsScreen(
    viewModel: OtakuViewModel,
    clubs: List<ClubEntity>
) {
    var showCreateClubDialog by remember { mutableStateOf(false) }
    var selectedClubForDetail by remember { mutableStateOf<ClubEntity?>(null) }
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilterUniverse by remember { mutableStateOf("Tous") }
    var selectedSort by remember { mutableStateOf("Populaires 🔥") }

    val categories = listOf("Tous", "Mes Clubs ⭐", "Shonen", "Seinen", "One Piece", "Naruto", "Jujutsu Kaisen", "SnK", "Demon Slayer")
    val sortOptions = listOf("Populaires 🔥", "Niveau 🏆", "Nom A-Z")

    val filteredClubs = remember(clubs, searchQuery, selectedFilterUniverse, selectedSort) {
        clubs.filter { club ->
            val matchesSearch = searchQuery.isBlank() ||
                club.name.contains(searchQuery, ignoreCase = true) ||
                club.animeUniverse.contains(searchQuery, ignoreCase = true) ||
                club.tags.contains(searchQuery, ignoreCase = true) ||
                club.description.contains(searchQuery, ignoreCase = true)

            val matchesFilter = when (selectedFilterUniverse) {
                "Tous" -> true
                "Mes Clubs ⭐" -> club.isJoined
                "Shonen" -> club.tags.contains("Shonen", ignoreCase = true)
                "Seinen" -> club.tags.contains("Seinen", ignoreCase = true) || club.animeUniverse.contains("Seinen", ignoreCase = true)
                "SnK" -> club.animeUniverse.contains("Titan", ignoreCase = true) || club.tags.contains("SnK", ignoreCase = true)
                else -> club.animeUniverse.contains(selectedFilterUniverse, ignoreCase = true) ||
                    club.name.contains(selectedFilterUniverse, ignoreCase = true)
            }

            matchesSearch && matchesFilter
        }.sortedWith { a, b ->
            when (selectedSort) {
                "Populaires 🔥" -> b.memberCount.compareTo(a.memberCount)
                "Niveau 🏆" -> b.level.compareTo(a.level)
                "Nom A-Z" -> a.name.compareTo(b.name)
                else -> 0
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .otakuAtmosphericBackground()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp, bottom = 80.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                ClubsHeaderCard(
                    totalClubs = clubs.size,
                    joinedCount = clubs.count { it.isJoined }
                )
            }

            // Search Bar & Filter Controls
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    // Search text field
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("search_clubs_input"),
                        placeholder = { Text("Rechercher un club, un anime, un mot-clé...", color = OtakuTextMuted, fontSize = 13.sp) },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Search, contentDescription = "Recherche", tint = OtakuSecondary)
                        },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { searchQuery = "" }) {
                                    Icon(imageVector = Icons.Default.Close, contentDescription = "Effacer", tint = OtakuTextMuted)
                                }
                            }
                        },
                        shape = RoundedCornerShape(14.dp),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = OtakuTextPrimary,
                            unfocusedTextColor = OtakuTextPrimary,
                            focusedBorderColor = OtakuSecondary,
                            unfocusedBorderColor = OtakuDarkBorder,
                            focusedContainerColor = OtakuDarkCard,
                            unfocusedContainerColor = OtakuDarkCard
                        )
                    )

                    // Universe Filter Chips
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(horizontal = 2.dp)
                    ) {
                        items(categories) { category ->
                            val isSelected = selectedFilterUniverse == category
                            FilterChip(
                                selected = isSelected,
                                onClick = { selectedFilterUniverse = category },
                                label = {
                                    Text(
                                        text = category,
                                        fontSize = 12.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    )
                                },
                                shape = RoundedCornerShape(10.dp),
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = OtakuSecondary.copy(alpha = 0.2f),
                                    selectedLabelColor = OtakuSecondary,
                                    containerColor = OtakuDarkCard,
                                    labelColor = OtakuTextMuted
                                ),
                                border = FilterChipDefaults.filterChipBorder(
                                    enabled = true,
                                    selected = isSelected,
                                    borderColor = if (isSelected) OtakuSecondary else OtakuDarkBorder,
                                    borderWidth = 1.dp
                                )
                            )
                        }
                    }

                    // Sort Chips Row & Count
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${filteredClubs.size} ${if (filteredClubs.size <= 1) "club disponible" else "clubs disponibles"}",
                            fontSize = 12.sp,
                            color = OtakuTextMuted,
                            fontWeight = FontWeight.SemiBold
                        )

                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            sortOptions.forEach { sort ->
                                val isSelected = selectedSort == sort
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(if (isSelected) OtakuTertiary.copy(alpha = 0.2f) else OtakuDarkCardElevated)
                                        .border(
                                            1.dp,
                                            if (isSelected) OtakuTertiary else OtakuDarkBorder,
                                            RoundedCornerShape(8.dp)
                                        )
                                        .clickable { selectedSort = sort }
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = sort,
                                        fontSize = 10.sp,
                                        color = if (isSelected) OtakuTertiary else OtakuTextMuted,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    )
                                }
                            }
                        }
                    }
                }
            }

            if (filteredClubs.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = OtakuDarkCard)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("🔍", fontSize = 32.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Aucun club correspondant",
                                fontWeight = FontWeight.Bold,
                                color = OtakuTextPrimary,
                                fontSize = 15.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Essaie un autre mot-clé ou réinitialise les filtres.",
                                color = OtakuTextMuted,
                                fontSize = 12.sp
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Button(
                                onClick = {
                                    searchQuery = ""
                                    selectedFilterUniverse = "Tous"
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = OtakuSecondary)
                            ) {
                                Text("Réinitialiser les Filtres", fontWeight = FontWeight.Bold, color = Color.White)
                            }
                        }
                    }
                }
            } else {
                items(filteredClubs, key = { it.id }) { club ->
                    ClubItemCard(
                        club = club,
                        onToggleJoin = { viewModel.toggleJoinClub(club) },
                        onClickDetail = { selectedClubForDetail = club }
                    )
                }
            }
        }

        FloatingActionButton(
            onClick = { showCreateClubDialog = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 16.dp, end = 16.dp)
                .testTag("fab_create_club"),
            containerColor = OtakuSecondary,
            contentColor = Color.White
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Créer un club")
        }
    }

    if (showCreateClubDialog) {
        CreateClubDialog(
            onDismiss = { showCreateClubDialog = false },
            onCreate = { name, banner, universe, desc, tags ->
                viewModel.createClub(name, banner, universe, desc, tags)
                showCreateClubDialog = false
            }
        )
    }

    selectedClubForDetail?.let { club ->
        ClubDetailDialog(
            club = club,
            onDismiss = { selectedClubForDetail = null },
            onToggleJoin = {
                viewModel.toggleJoinClub(club)
                selectedClubForDetail = null
            }
        )
    }
}

@Composable
fun ClubsHeaderCard(totalClubs: Int, joinedCount: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = OtakuDarkSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, OtakuDarkBorder)
    ) {
        Column {
            // Anime Club Showcase Banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_anime_club_showcase),
                    contentDescription = "Anime Guilds Showcase",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color(0x33000000),
                                    Color(0x99000000)
                                )
                            )
                        )
                )
                // Guild Pill on Image
                Box(
                    modifier = Modifier
                        .padding(12.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color.Black.copy(alpha = 0.7f))
                        .border(1.dp, OtakuSecondary, RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = "⛩️ GUILDES OTAKU • ギルド連合",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black,
                        color = OtakuSecondary
                    )
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Rejoins ta guilde & forge ta renommée",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    color = OtakuTextPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "« Unis par la même passion pour le manga et l'animation ! »",
                    fontSize = 12.sp,
                    color = OtakuTextSecondary
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(OtakuDarkCard)
                            .border(1.dp, OtakuDarkBorder, RoundedCornerShape(12.dp))
                            .padding(10.dp)
                    ) {
                        Column {
                            Text("Guildes Rejointes", fontSize = 11.sp, color = OtakuTextMuted)
                            Text("$joinedCount clubs", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = OtakuPrimary)
                        }
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(OtakuDarkCard)
                            .border(1.dp, OtakuDarkBorder, RoundedCornerShape(12.dp))
                            .padding(10.dp)
                    ) {
                        Column {
                            Text("Total Actifs", fontSize = 11.sp, color = OtakuTextMuted)
                            Text("$totalClubs répertoires", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = OtakuSecondary)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ClubItemCard(
    club: ClubEntity,
    onToggleJoin: () -> Unit,
    onClickDetail: () -> Unit
) {
    val guildRank = when {
        club.level >= 5 -> "SSS-RANK"
        club.level >= 3 -> "S-RANK"
        club.level >= 2 -> "A-RANK"
        else -> "B-RANK"
    }
    val rankColor = when {
        club.level >= 5 -> OtakuPrimary
        club.level >= 3 -> OtakuTertiary
        club.level >= 2 -> OtakuSecondary
        else -> Color(0xFF00E676)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClickDetail() }
            .testTag("club_card_${club.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = OtakuDarkCard),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (club.isJoined) OtakuSecondary.copy(alpha = 0.5f) else OtakuDarkBorder
        )
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Banner Emoji
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(OtakuDarkCardElevated)
                        .border(1.dp, rankColor.copy(alpha = 0.5f), RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = club.bannerEmoji, fontSize = 24.sp)
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = club.name,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = OtakuTextPrimary
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(rankColor.copy(alpha = 0.15f))
                                .border(1.dp, rankColor, RoundedCornerShape(4.dp))
                                .padding(horizontal = 4.dp, vertical = 1.dp)
                        ) {
                            Text(
                                text = guildRank,
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Black,
                                color = rankColor
                            )
                        }
                    }
                    Text(
                        text = "👑 ${club.presidentName} • Niv. ${club.level} 【ギルド】",
                        fontSize = 12.sp,
                        color = OtakuTertiary
                    )
                    Text(
                        text = "👥 ${club.memberCount} membres • ${club.animeUniverse}",
                        fontSize = 11.sp,
                        color = OtakuTextMuted
                    )
                }

                // Join Button
                if (club.isJoined) {
                    Button(
                        onClick = onToggleJoin,
                        colors = ButtonDefaults.buttonColors(containerColor = OtakuDarkCardElevated),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.testTag("btn_joined_club_${club.id}")
                    ) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = OtakuSecondary, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = club.userRole, fontSize = 11.sp, color = OtakuSecondary, fontWeight = FontWeight.Bold)
                    }
                } else {
                    Button(
                        onClick = onToggleJoin,
                        colors = ButtonDefaults.buttonColors(containerColor = OtakuPrimary),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.testTag("btn_join_club_${club.id}")
                    ) {
                        Text(text = "Rejoindre", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = club.description,
                fontSize = 12.sp,
                color = OtakuTextSecondary,
                lineHeight = 16.sp
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "🏷️ ${club.tags}",
                fontSize = 10.sp,
                color = OtakuTextMuted
            )
        }
    }
}

@Composable
fun ClubDetailDialog(
    club: ClubEntity,
    onDismiss: () -> Unit,
    onToggleJoin: () -> Unit
) {
    val universeAccent = when {
        club.animeUniverse.contains("Jujutsu", ignoreCase = true) -> Color(0xFF8B5CF6)
        club.animeUniverse.contains("Piece", ignoreCase = true) -> Color(0xFF0284C7)
        club.animeUniverse.contains("Naruto", ignoreCase = true) -> Color(0xFFD97706)
        club.animeUniverse.contains("Slayer", ignoreCase = true) -> Color(0xFFDC2626)
        else -> OtakuSecondary
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(OtakuDarkCardElevated)
                            .border(1.5.dp, universeAccent, RoundedCornerShape(14.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(club.bannerEmoji, fontSize = 26.sp)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = club.name,
                            color = OtakuTextPrimary,
                            fontWeight = FontWeight.Black,
                            fontSize = 17.sp
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Univers : ${club.animeUniverse}",
                                color = universeAccent,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "• Niv. ${club.level}",
                                color = OtakuTertiary,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        },
        text = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(380.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Lair Description
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(OtakuDarkCardElevated)
                            .border(1.dp, universeAccent.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                            .padding(12.dp)
                    ) {
                        Text(
                            text = club.description,
                            color = OtakuTextPrimary,
                            fontSize = 12.sp,
                            lineHeight = 16.sp
                        )
                    }
                }

                // Membres en ligne
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = OtakuDarkCard),
                        border = androidx.compose.foundation.BorderStroke(1.dp, OtakuDarkBorder)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Membres de la Guilde",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = OtakuTextPrimary
                                )
                                Text(
                                    text = "🟢 14 en ligne / ${club.memberCount}",
                                    fontSize = 10.sp,
                                    color = Color(0xFF059669),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                listOf("👑", "⚡", "🔮", "🗡️", "🦊", "🌸").forEach { av ->
                                    Box(
                                        modifier = Modifier
                                            .size(28.dp)
                                            .clip(CircleShape)
                                            .background(OtakuDarkCardElevated),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(av, fontSize = 14.sp)
                                    }
                                }
                            }
                        }
                    }
                }

                // Règles du Club
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = OtakuDarkCard),
                        border = androidx.compose.foundation.BorderStroke(1.dp, OtakuDarkBorder)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text("📜 Règles du Repaire", fontWeight = FontWeight.Bold, color = OtakuTextPrimary, fontSize = 12.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("1. Balise spoiler obligatoire pour les scans récents", fontSize = 11.sp, color = OtakuTextSecondary)
                            Text("2. Respect et courtoisie dans les débats de puissance", fontSize = 11.sp, color = OtakuTextSecondary)
                            Text("3. Partage de théories argumentées bienvenu", fontSize = 11.sp, color = OtakuTextSecondary)
                        }
                    }
                }

                // Section Débats & Théories
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = OtakuDarkCard),
                        border = androidx.compose.foundation.BorderStroke(1.dp, OtakuDarkBorder)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text("💬 Débats & Théories en Cours", fontWeight = FontWeight.Bold, color = universeAccent, fontSize = 12.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("• « Théorie sur le climax et la véritable nature de l'antagoniste » (34 réponses)", fontSize = 11.sp, color = OtakuTextPrimary)
                            Text("• « Analyse tactique du dernier combat de l'arc » (18 réponses)", fontSize = 11.sp, color = OtakuTextPrimary)
                        }
                    }
                }

                // Section Recommandations du club
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = OtakuDarkCard),
                        border = androidx.compose.foundation.BorderStroke(1.dp, OtakuDarkBorder)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text("🌟 Pépites Recommandées par le Club", fontWeight = FontWeight.Bold, color = OtakuTertiary, fontSize = 12.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("• Anime similaire conseillé : Chainsaw Man & Hell's Paradise", fontSize = 11.sp, color = OtakuTextSecondary)
                            Text("• Manga one-shot à découvrir absolument par l'auteur", fontSize = 11.sp, color = OtakuTextSecondary)
                        }
                    }
                }

                // Événements & Tournois
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = OtakuDarkCard),
                        border = androidx.compose.foundation.BorderStroke(1.dp, OtakuDarkBorder)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text("⚔️ Prochains Rendez-vous", fontWeight = FontWeight.Bold, color = OtakuPrimary, fontSize = 12.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("• Duel inter-clubs : Samedi 20h00 (+200 XP à gagner)", fontSize = 11.sp, color = OtakuTextSecondary)
                            Text("• Quiz en direct du club : Dimanche 18h00", fontSize = 11.sp, color = OtakuTextSecondary)
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onToggleJoin,
                colors = ButtonDefaults.buttonColors(containerColor = if (club.isJoined) Color(0xFFDC2626) else universeAccent),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = if (club.isJoined) "Quitter le Club" else "Rejoindre le Repaire (+50 XP)",
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Fermer", color = OtakuTextMuted)
            }
        },
        containerColor = OtakuDarkSurface
    )
}

@Composable
fun CreateClubDialog(
    onDismiss: () -> Unit,
    onCreate: (name: String, banner: String, universe: String, desc: String, tags: String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var banner by remember { mutableStateOf("⚡") }
    var universe by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var tags by remember { mutableStateOf("") }

    val emojis = listOf("⚡", "🌀", "🍃", "🔮", "🗡️", "🪐", "🔥", "🌸", "👑")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Fonder un Club Otaku 🏯", color = OtakuTextPrimary, fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Deviens Président de ton propre club et rassemble ta communauté !", color = OtakuTextSecondary, fontSize = 12.sp)

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nom du Club (ex: Club Bleach)", color = OtakuTextMuted) },
                    modifier = Modifier.fillMaxWidth().testTag("input_club_name"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = OtakuTextPrimary,
                        unfocusedTextColor = OtakuTextPrimary,
                        focusedBorderColor = OtakuSecondary,
                        unfocusedBorderColor = OtakuDarkBorder
                    )
                )

                Text("Choisis un Emblème :", color = OtakuTextPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    emojis.forEach { emo ->
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (banner == emo) OtakuSecondary else OtakuDarkCardElevated)
                                .border(1.dp, if (banner == emo) OtakuSecondary else OtakuDarkBorder, RoundedCornerShape(6.dp))
                                .clickable { banner = emo },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(emo, fontSize = 16.sp)
                        }
                    }
                }

                OutlinedTextField(
                    value = universe,
                    onValueChange = { universe = it },
                    label = { Text("Univers Anime / Manga", color = OtakuTextMuted) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = OtakuTextPrimary,
                        unfocusedTextColor = OtakuTextPrimary,
                        focusedBorderColor = OtakuSecondary,
                        unfocusedBorderColor = OtakuDarkBorder
                    )
                )

                OutlinedTextField(
                    value = desc,
                    onValueChange = { desc = it },
                    label = { Text("Description & Objectifs", color = OtakuTextMuted) },
                    modifier = Modifier.fillMaxWidth().height(80.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = OtakuTextPrimary,
                        unfocusedTextColor = OtakuTextPrimary,
                        focusedBorderColor = OtakuSecondary,
                        unfocusedBorderColor = OtakuDarkBorder
                    )
                )

                OutlinedTextField(
                    value = tags,
                    onValueChange = { tags = it },
                    label = { Text("Tags (ex: Shonen, Débats, Scans)", color = OtakuTextMuted) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = OtakuTextPrimary,
                        unfocusedTextColor = OtakuTextPrimary,
                        focusedBorderColor = OtakuSecondary,
                        unfocusedBorderColor = OtakuDarkBorder
                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (name.isNotBlank()) {
                        onCreate(name, banner, universe, desc, tags)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = OtakuSecondary),
                modifier = Modifier.testTag("btn_confirm_create_club")
            ) {
                Text("Fonder le Club (+100 XP)", color = Color.White, fontWeight = FontWeight.Bold)
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
