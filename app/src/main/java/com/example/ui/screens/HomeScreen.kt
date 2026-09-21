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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.MeetingRoom
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.SportsKabaddi
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.VpnKey
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.R
import com.example.data.model.CinematicSceneState
import com.example.data.model.ClubEntity
import com.example.data.model.DistrictType
import com.example.data.model.NexusDistrict
import com.example.data.model.OtakuArchetype
import com.example.data.model.PostEntity
import com.example.data.model.UserProfileEntity
import com.example.data.model.WorldSecret
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
fun HomeScreen(
    viewModel: OtakuViewModel,
    onNavigateToQuiz: () -> Unit,
    onNavigateToEvents: () -> Unit,
    onNavigateToClubs: () -> Unit = {},
    onNavigateToLeaderboard: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    onNavigateToPortals: () -> Unit = {},
    onNavigateToRoom: () -> Unit = {},
    onStartBattle: () -> Unit,
    posts: List<PostEntity>,
    categoryFilter: String
) {
    val userProfile by viewModel.userProfile.collectAsStateWithLifecycle()
    val clubs by viewModel.clubs.collectAsStateWithLifecycle()
    val districts by viewModel.districts.collectAsStateWithLifecycle()
    val skyGateProgress by viewModel.skyGateProgress.collectAsStateWithLifecycle()
    val userArchetype by viewModel.userArchetype.collectAsStateWithLifecycle()
    val userOrigin by viewModel.userOrigin.collectAsStateWithLifecycle()
    val otakuCoins by viewModel.otakuCoins.collectAsStateWithLifecycle()
    val worldSecrets by viewModel.worldSecrets.collectAsStateWithLifecycle()
    val companionDialogue by viewModel.companionDialogue.collectAsStateWithLifecycle()
    val isCompanionOpen by viewModel.isCompanionOpen.collectAsStateWithLifecycle()
    val activeCinematicScene by viewModel.activeCinematicScene.collectAsStateWithLifecycle()

    var showCreatePostDialog by remember { mutableStateOf(false) }
    var activePostForComments by remember { mutableStateOf<PostEntity?>(null) }
    var selectedDistrictForModal by remember { mutableStateOf<NexusDistrict?>(null) }
    var selectedSecretForModal by remember { mutableStateOf<WorldSecret?>(null) }
    var showCinematicDialog by remember { mutableStateOf(false) }

    val categories = listOf("Tous", "Actu", "Théorie", "Découverte", "Fan Art", "Sondage")
    val filteredPosts = if (categoryFilter == "Tous") {
        posts
    } else {
        posts.filter { it.category.equals(categoryFilter, ignoreCase = true) }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .otakuAtmosphericBackground()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 88.dp)
        ) {
            // 1. HERO BANNER : SKYLINE DE NEXUS CITY
            item {
                NexusCitySkylineHeroCard(
                    profile = userProfile,
                    archetype = userArchetype,
                    otakuCoins = otakuCoins,
                    onOpenProfile = onNavigateToProfile,
                    onStartBattle = onStartBattle,
                    onNavigateToPortals = onNavigateToPortals,
                    onNavigateToRoom = onNavigateToRoom,
                    onTriggerCinematic = {
                        viewModel.startCinematicScene("L'Éveil du Sceau Astral")
                        showCinematicDialog = true
                    }
                )
            }

            // 2. ÉVÉNEMENT MONDIAL EN DIRECT : LA PORTE CÉLESTE
            item {
                WorldEventAlertCard(
                    currentProgress = skyGateProgress,
                    maxGoal = viewModel.skyGateMaxGoal,
                    onContribute = { viewModel.contributeSkyGate(100) }
                )
            }

            // 3. LES 10 QUARTIERS DE NEXUS CITY
            item {
                NexusDistrictsOverviewSection(
                    districts = districts,
                    onSelectDistrict = { district ->
                        viewModel.selectDistrict(district.type)
                        selectedDistrictForModal = district
                    }
                )
            }

            // 4. SECRETS ET MYSTÈRES DE LA CITÉ
            item {
                WorldSecretsOverviewSection(
                    secrets = worldSecrets,
                    onInspectSecret = { secret ->
                        selectedSecretForModal = secret
                    }
                )
            }

            // 5. ACTIVITÉS RÉCENTES (FLUX DE LA COMMUNAUTÉ / JOURNAL DE NEXUS)
            item {
                // Category Filter Pills
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(categories) { cat ->
                        val isSelected = categoryFilter == cat
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(if (isSelected) OtakuPrimary else OtakuDarkCard)
                                .border(
                                    width = 1.dp,
                                    color = if (isSelected) OtakuPrimary else OtakuDarkBorder,
                                    shape = RoundedCornerShape(20.dp)
                                )
                                .clickable { viewModel.setHomeCategoryFilter(cat) }
                                .padding(horizontal = 14.dp, vertical = 8.dp)
                                .testTag("filter_pill_$cat")
                        ) {
                            Text(
                                text = cat,
                                color = if (isSelected) Color.White else OtakuTextSecondary,
                                fontSize = 13.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                            )
                        }
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 6.dp, bottom = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Flux de la Communauté 🌸",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = OtakuTextPrimary
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(OtakuPrimary.copy(alpha = 0.15f))
                                .padding(horizontal = 5.dp, vertical = 1.dp)
                        ) {
                            Text("En direct", fontSize = 9.sp, color = OtakuPrimary, fontWeight = FontWeight.Bold)
                        }
                    }
                    Text(
                        text = "${filteredPosts.size} publications",
                        fontSize = 12.sp,
                        color = OtakuTextMuted
                    )
                }
            }

            // Empty state if no posts in filtered category
            if (filteredPosts.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = OtakuDarkCard),
                        border = androidx.compose.foundation.BorderStroke(1.dp, OtakuDarkBorder)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("🌸", fontSize = 32.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Aucune publication dans '$categoryFilter'",
                                fontWeight = FontWeight.Bold,
                                color = OtakuTextPrimary,
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Sois le premier Otaku à lancer la discussion !",
                                color = OtakuTextMuted,
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Button(
                                onClick = { showCreatePostDialog = true },
                                colors = ButtonDefaults.buttonColors(containerColor = OtakuPrimary),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text("Créer une publication", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            } else {
                // Display top recent community posts
                items(filteredPosts.take(4), key = { it.id }) { post ->
                    PostItemCard(
                        post = post,
                        onToggleLike = { viewModel.toggleLikePost(post) },
                        onOpenComments = { activePostForComments = post }
                    )
                }
            }

            // 4. CLUBS POPULAIRES
            item {
                PopularClubsSection(
                    clubs = clubs,
                    onToggleJoin = { viewModel.toggleJoinClub(it) },
                    onNavigateToClubs = onNavigateToClubs
                )
            }

            // 5. QUIZ DU JOUR
            item {
                DailyQuizChallengeCard(onStartQuiz = onNavigateToQuiz)
            }

            // 6. CLASSEMENT (PODIUM SPOTLIGHT)
            item {
                HomeLeaderboardSpotlightCard(onNavigateToLeaderboard = onNavigateToLeaderboard)
            }

            // 7. RECOMMANDATIONS (HYPE ANIMES)
            item {
                SeasonalAnimeSpotlightRow()
            }

            // Remaining posts if more than 4
            if (filteredPosts.size > 4) {
                item {
                    Text(
                        text = "Plus d'activités récentes...",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = OtakuTextSecondary,
                        modifier = Modifier.padding(start = 16.dp, top = 12.dp, bottom = 4.dp)
                    )
                }
                items(filteredPosts.drop(4), key = { it.id }) { post ->
                    PostItemCard(
                        post = post,
                        onToggleLike = { viewModel.toggleLikePost(post) },
                        onOpenComments = { activePostForComments = post }
                    )
                }
            }
        }

        // Floating AI Companion Aiko button
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 76.dp, end = 16.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(Brush.horizontalGradient(listOf(OtakuPrimary, OtakuSecondary)))
                .clickable { viewModel.toggleCompanion(true) }
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .testTag("btn_companion_aiko")
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("🌸", fontSize = 16.sp)
                Spacer(modifier = Modifier.width(6.dp))
                Text("Guide Aiko", fontSize = 12.sp, fontWeight = FontWeight.Black, color = Color.White)
            }
        }

        // Floating Action Button to post
        FloatingActionButton(
            onClick = { showCreatePostDialog = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 16.dp, end = 16.dp)
                .testTag("fab_create_post"),
            containerColor = OtakuPrimary,
            contentColor = Color.White
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Nouvelle publication")
        }
    }

    if (showCreatePostDialog) {
        CreatePostDialog(
            onDismiss = { showCreatePostDialog = false },
            onSubmit = { content, category, club ->
                viewModel.createPost(content, category, club)
                showCreatePostDialog = false
            }
        )
    }

    activePostForComments?.let { post ->
        PostCommentsDialog(
            post = post,
            viewModel = viewModel,
            onDismiss = { activePostForComments = null }
        )
    }

    // Modal : District Inspection Dialog
    selectedDistrictForModal?.let { district ->
        DistrictInspectionDialog(
            district = district,
            onDismiss = { selectedDistrictForModal = null },
            onEnterDistrict = {
                selectedDistrictForModal = null
                when (district.type) {
                    DistrictType.BATTLE_ARENA -> onStartBattle()
                    DistrictType.PORTAL_GATE -> onNavigateToPortals()
                    DistrictType.PERSONAL_ROOM, DistrictType.OTAKU_MARKET -> onNavigateToRoom()
                    DistrictType.ACADEMY -> onNavigateToQuiz()
                    DistrictType.TOURNAMENT_STADIUM -> onNavigateToEvents()
                    DistrictType.COMMUNITY_PARK -> onNavigateToClubs()
                    DistrictType.ANCIENT_DISTRICT -> viewModel.discoverSecret("sec_kami_shrine")
                    DistrictType.ARCADE -> viewModel.discoverSecret("sec_arcade_code")
                    DistrictType.DOWNTOWN -> viewModel.setHomeCategoryFilter("Tous")
                }
            }
        )
    }

    // Modal : Secret Mystery Solve Dialog
    selectedSecretForModal?.let { secret ->
        SecretSolveDialog(
            secret = secret,
            onDismiss = { selectedSecretForModal = null },
            onSolve = {
                viewModel.discoverSecret(secret.id)
                selectedSecretForModal = null
            }
        )
    }

    // Modal : AI Companion Aiko Dialog
    if (isCompanionOpen) {
        AICompanionDialog(
            dialogue = companionDialogue,
            onDismiss = { viewModel.toggleCompanion(false) },
            onAskTopic = { topic -> viewModel.askCompanion(topic) }
        )
    }

    // Modal : Cinematic Anime Cutscene Dialog
    if (showCinematicDialog) {
        CinematicSceneDialog(
            onDismiss = { showCinematicDialog = false },
            onChoose = { choice ->
                viewModel.earnOtakuCoins(100, "Choix Narratif : $choice")
                viewModel.awardCustomXp(120, "Scène : $choice")
                showCinematicDialog = false
            }
        )
    }
}

// ==========================================
// NEXUS CITY VIRTUAL WORLD UI COMPONENTS
// ==========================================

@Composable
fun NexusCitySkylineHeroCard(
    profile: UserProfileEntity,
    archetype: OtakuArchetype,
    otakuCoins: Int,
    onOpenProfile: () -> Unit,
    onStartBattle: () -> Unit,
    onNavigateToPortals: () -> Unit,
    onNavigateToRoom: () -> Unit,
    onTriggerCinematic: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .testTag("nexus_city_hero_card"),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = OtakuDarkSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, OtakuPrimary.copy(alpha = 0.4f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            // Skyline Artwork Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_nexus_city_skyline),
                    contentDescription = "Nexus City Skyline",
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
                                    Color(0xAA0D0B18),
                                    Color(0xFF17132B)
                                )
                            )
                        )
                )

                // Top Floating Status Badges
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.Black.copy(alpha = 0.75f))
                            .border(1.dp, OtakuPrimary, RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "✦ NEXUS CITY • 虚空の都",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black,
                            color = OtakuPrimary
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF10B981).copy(alpha = 0.85f))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "☀️ 34,280 En Ligne",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White
                        )
                    }
                }

                // Subtitle on image
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(14.dp)
                ) {
                    Text(
                        text = "La Cité Vivante des Otakus",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )
                    Text(
                        text = "« Ne sois plus un simple spectateur. Vis l'aventure. »",
                        fontSize = 11.sp,
                        color = OtakuSecondary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Player Identity Strip
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(OtakuDarkCard)
                        .clickable { onOpenProfile() }
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OtakuAvatarWithFrame(
                        avatarEmoji = profile.avatarEmoji,
                        frameType = profile.profileFrame,
                        size = 46.dp
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = profile.username,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Black,
                                color = OtakuTextPrimary
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(OtakuSecondary.copy(alpha = 0.15f))
                                    .padding(horizontal = 5.dp, vertical = 1.dp)
                            ) {
                                Text(
                                    text = "${archetype.icon} ${archetype.name}",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = OtakuSecondary
                                )
                            }
                        }
                        Text(
                            text = "${profile.rankTitle} • 🪙 $otakuCoins Pièces Otaku",
                            fontSize = 11.sp,
                            color = OtakuTertiary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    OutlinedButton(
                        onClick = onTriggerCinematic,
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = OtakuPrimary),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text("🎬 Scène", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Fast Travel Action Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = onStartBattle,
                        modifier = Modifier.weight(1f).testTag("home_btn_battle"),
                        colors = ButtonDefaults.buttonColors(containerColor = OtakuPrimary),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        Icon(Icons.Default.SportsKabaddi, contentDescription = null, modifier = Modifier.size(15.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Arène ⚔️", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = onNavigateToPortals,
                        modifier = Modifier.weight(1f).testTag("home_btn_portals"),
                        colors = ButtonDefaults.buttonColors(containerColor = OtakuSecondary),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(15.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Mondes 🌌", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = OtakuDarkBackground)
                    }

                    OutlinedButton(
                        onClick = onNavigateToRoom,
                        modifier = Modifier.weight(1f).testTag("home_btn_room"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = OtakuTertiary),
                        border = androidx.compose.foundation.BorderStroke(1.2.dp, OtakuTertiary),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        Icon(Icons.Default.MeetingRoom, contentDescription = null, modifier = Modifier.size(15.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Chambre 🏠", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun WorldEventAlertCard(
    currentProgress: Int,
    maxGoal: Int,
    onContribute: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .testTag("world_event_banner"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = OtakuDarkCard),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF59E0B).copy(alpha = 0.5f))
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color(0xFFF59E0B).copy(alpha = 0.2f))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text("🚨 ÉVÉNEMENT MONDIAL EN DIRECT", fontSize = 10.sp, color = Color(0xFFF59E0B), fontWeight = FontWeight.Black)
                    }
                }
                Text("Fin dans 2j 14h ⏳", fontSize = 10.sp, color = OtakuTextMuted)
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "L'Ouverture de la Porte Céleste 🌌",
                fontSize = 16.sp,
                fontWeight = FontWeight.Black,
                color = OtakuTextPrimary
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Les 6 sceaux dimensionnels réagissent. Rassemblez l'Éther cosmique pour déverrouiller la Porte !",
                fontSize = 12.sp,
                color = OtakuTextSecondary,
                lineHeight = 16.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Progress Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Progression de la communauté", fontSize = 11.sp, color = OtakuTextMuted)
                Text("$currentProgress / $maxGoal Fragments (${(currentProgress * 100) / maxGoal}%)", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFFF59E0B))
            }
            Spacer(modifier = Modifier.height(4.dp))
            LinearProgressIndicator(
                progress = { currentProgress.toFloat() / maxGoal },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = Color(0xFFF59E0B),
                trackColor = OtakuDarkBorder
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = onContribute,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF59E0B)),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Contribuer (+100 Frags • +120 XP)", fontSize = 11.sp, fontWeight = FontWeight.Black, color = Color.Black)
                }
            }
        }
    }
}

@Composable
fun NexusDistrictsOverviewSection(
    districts: List<NexusDistrict>,
    onSelectDistrict: (NexusDistrict) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "🗺️ Les 10 Lieux de Nexus City",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    color = OtakuTextPrimary
                )
                Spacer(modifier = Modifier.width(6.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(OtakuPrimary.copy(alpha = 0.15f))
                        .padding(horizontal = 5.dp, vertical = 1.dp)
                ) {
                    Text("Explorable", fontSize = 9.sp, color = OtakuPrimary, fontWeight = FontWeight.Bold)
                }
            }
            Text("Touchez pour visiter", fontSize = 11.sp, color = OtakuTextMuted)
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Horizontal scrolling row of districts
        LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            items(districts) { district ->
                Card(
                    modifier = Modifier
                        .width(170.dp)
                        .clickable { onSelectDistrict(district) }
                        .testTag("district_card_${district.type.name.lowercase()}"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = OtakuDarkCard),
                    border = androidx.compose.foundation.BorderStroke(1.dp, OtakuDarkBorder)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(district.icon, fontSize = 26.sp)
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(OtakuDarkCardElevated)
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text("👥 ${district.activePlayersCount}", fontSize = 9.sp, color = OtakuSecondary, fontWeight = FontWeight.Bold)
                            }
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(district.name, fontSize = 13.sp, fontWeight = FontWeight.Black, color = OtakuTextPrimary, maxLines = 1)
                        Text(district.japaneseName, fontSize = 10.sp, color = OtakuTextMuted, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(district.description, fontSize = 11.sp, color = OtakuTextSecondary, maxLines = 2, lineHeight = 14.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun WorldSecretsOverviewSection(
    secrets: List<WorldSecret>,
    onInspectSecret: (WorldSecret) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .testTag("world_secrets_card"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = OtakuDarkCard),
        border = androidx.compose.foundation.BorderStroke(1.dp, OtakuTertiary.copy(alpha = 0.3f))
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("🗝️", fontSize = 18.sp)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Mystères & Secrets de la Cité", fontSize = 14.sp, fontWeight = FontWeight.Black, color = OtakuTextPrimary)
                }
                Text("${secrets.count { it.isDiscovered }} / ${secrets.size} Trouvés", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = OtakuTertiary)
            }

            Spacer(modifier = Modifier.height(8.dp))

            secrets.forEach { secret ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(OtakuDarkCardElevated)
                        .clickable { onInspectSecret(secret) }
                        .padding(horizontal = 10.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(secret.title, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = OtakuTextPrimary)
                            Spacer(modifier = Modifier.width(4.dp))
                            if (secret.isDiscovered) {
                                Text("✓", fontSize = 11.sp, color = OtakuSecondary, fontWeight = FontWeight.Black)
                            }
                        }
                        Text("📍 ${secret.districtType.name} • ${secret.hint.take(42)}...", fontSize = 10.sp, color = OtakuTextMuted)
                    }
                    Text(
                        text = if (secret.isDiscovered) "Résolu" else "Décrypter →",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (secret.isDiscovered) OtakuSecondary else OtakuTertiary
                    )
                }
            }
        }
    }
}

// ==========================================
// DIALOGS & OVERLAYS
// ==========================================

@Composable
fun DistrictInspectionDialog(
    district: NexusDistrict,
    onDismiss: () -> Unit,
    onEnterDistrict: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = OtakuDarkCard,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(district.icon, fontSize = 28.sp)
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(district.name, fontSize = 17.sp, fontWeight = FontWeight.Black, color = OtakuTextPrimary)
                    Text("${district.japaneseName} • 👥 ${district.activePlayersCount} en ligne", fontSize = 11.sp, color = OtakuSecondary, fontWeight = FontWeight.Bold)
                }
            }
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(OtakuDarkCardElevated)
                        .padding(10.dp)
                ) {
                    Text("Activité Principale : ${district.currentActivity}", fontSize = 12.sp, color = OtakuTertiary, fontWeight = FontWeight.Bold)
                }
                Text(district.description, fontSize = 13.sp, color = OtakuTextSecondary, lineHeight = 18.sp)

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Black.copy(alpha = 0.3f))
                        .padding(8.dp)
                ) {
                    Text("📜 Spécialité : ${district.badgeTag} • ${district.subtitle}", fontSize = 11.sp, color = OtakuTextMuted, fontStyle = androidx.compose.ui.text.font.FontStyle.Italic)
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onEnterDistrict,
                colors = ButtonDefaults.buttonColors(containerColor = OtakuPrimary)
            ) {
                Text("Entrer dans ce Lieu")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Fermer", color = OtakuTextSecondary)
            }
        }
    )
}

@Composable
fun SecretSolveDialog(
    secret: WorldSecret,
    onDismiss: () -> Unit,
    onSolve: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = OtakuDarkCard,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("🗝️", fontSize = 24.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(secret.title, fontSize = 16.sp, fontWeight = FontWeight.Black, color = OtakuTextPrimary)
            }
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Lieu : ${secret.districtType.name}", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = OtakuSecondary)
                Text("Indice de la Ville :", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = OtakuTextPrimary)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(OtakuDarkCardElevated)
                        .padding(10.dp)
                ) {
                    Text(secret.hint, fontSize = 12.sp, color = OtakuTextSecondary, lineHeight = 17.sp)
                }
                Text("Récompense : ${secret.loreReward}", fontSize = 11.sp, color = OtakuTertiary, fontWeight = FontWeight.Bold)
            }
        },
        confirmButton = {
            if (!secret.isDiscovered) {
                Button(
                    onClick = onSolve,
                    colors = ButtonDefaults.buttonColors(containerColor = OtakuPrimary)
                ) {
                    Text("Déchiffrer l'Énigme (+XP & Pièces)")
                }
            } else {
                TextButton(onClick = onDismiss) {
                    Text("Déjà Résolu ✓", color = OtakuSecondary)
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Fermer", color = OtakuTextSecondary)
            }
        }
    )
}

@Composable
fun AICompanionDialog(
    dialogue: com.example.data.model.CompanionDialogue,
    onDismiss: () -> Unit,
    onAskTopic: (String) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = OtakuDarkCard,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(OtakuPrimary.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("🌸", fontSize = 22.sp)
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text("Aiko (Guide de Nexus)", fontSize = 16.sp, fontWeight = FontWeight.Black, color = OtakuTextPrimary)
                    Text("« Je veille sur ton aventure ! »", fontSize = 11.sp, color = OtakuSecondary)
                }
            }
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(OtakuDarkCardElevated)
                        .padding(12.dp)
                ) {
                    Text(dialogue.message, fontSize = 13.sp, color = Color.White, lineHeight = 18.sp)
                }

                Text("Pose une question à Aiko :", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = OtakuTextMuted)

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    OutlinedButton(
                        onClick = { onAskTopic("Où aller ?") },
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 2.dp)
                    ) {
                        Text("🗺️ Où aller ?", fontSize = 10.sp)
                    }
                    OutlinedButton(
                        onClick = { onAskTopic("Conseil d'Arène") },
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 2.dp)
                    ) {
                        Text("⚔️ Arène", fontSize = 10.sp)
                    }
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    OutlinedButton(
                        onClick = { onAskTopic("Alerte Événement") },
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 2.dp)
                    ) {
                        Text("🌌 Événement", fontSize = 10.sp)
                    }
                    OutlinedButton(
                        onClick = { onAskTopic("Lore Secret") },
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 2.dp)
                    ) {
                        Text("🗝️ Lore", fontSize = 10.sp)
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Merci Aiko !", color = OtakuPrimary, fontWeight = FontWeight.Bold)
            }
        }
    )
}

@Composable
fun CinematicSceneDialog(
    onDismiss: () -> Unit,
    onChoose: (String) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = OtakuDarkCard,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("🎬", fontSize = 24.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text("L'Éveil du Sceau Astral", fontSize = 16.sp, fontWeight = FontWeight.Black, color = Color.White)
                    Text("Scène Cinématique Interactive", fontSize = 11.sp, color = OtakuSecondary)
                }
            }
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.Black.copy(alpha = 0.4f))
                        .border(1.dp, OtakuPrimary.copy(alpha = 0.3f), RoundedCornerShape(10.dp))
                        .padding(12.dp)
                ) {
                    Text(
                        text = "« Les nuages se dispersent au-dessus de la tour de Nexus. Une onde violette traverse la Porte des Mondes. Tu ressens l'afflux d'un Éther antique. Comment canalises-tu cette puissance ? »",
                        fontSize = 13.sp,
                        color = OtakuTextPrimary,
                        lineHeight = 19.sp,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                    )
                }

                Button(
                    onClick = { onChoose("Canalisation par la Force Shonen") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = OtakuPrimary),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("⚔️ Canaliser par la Force (+120 XP, +100 🪙)", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = { onChoose("Harmonisation par l'Éther Mystique") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = OtakuSecondary),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("✨ Harmoniser l'Éther (+120 XP, +100 🪙)", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = OtakuDarkBackground)
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Passer", color = OtakuTextMuted)
            }
        }
    )
}


@Composable
fun PopularClubsSection(
    clubs: List<ClubEntity>,
    onToggleJoin: (ClubEntity) -> Unit,
    onNavigateToClubs: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "🏯 Clubs Populaires du Sanctuaire",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Black,
                    color = OtakuTextPrimary
                )
                Spacer(modifier = Modifier.width(6.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(OtakuSecondary.copy(alpha = 0.15f))
                        .padding(horizontal = 5.dp, vertical = 1.dp)
                ) {
                    Text("Guildes", fontSize = 9.sp, color = OtakuSecondary, fontWeight = FontWeight.Bold)
                }
            }
            TextButton(onClick = onNavigateToClubs) {
                Text("Explorer tout >", fontSize = 11.sp, color = OtakuSecondary, fontWeight = FontWeight.Bold)
            }
        }

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(clubs.take(5), key = { it.id }) { club ->
                Card(
                    modifier = Modifier
                        .width(200.dp)
                        .clickable { onNavigateToClubs() }
                        .testTag("home_popular_club_${club.id}"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = OtakuDarkCard),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (club.isJoined) OtakuSecondary.copy(alpha = 0.5f) else OtakuDarkBorder
                    )
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(club.bannerEmoji, fontSize = 24.sp)
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(OtakuTertiary.copy(alpha = 0.15f))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = if (club.level >= 10) "SSS-RANK" else "S-RANK",
                                    fontSize = 8.sp,
                                    fontWeight = FontWeight.Black,
                                    color = OtakuTertiary
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = club.name,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = OtakuTextPrimary,
                            maxLines = 1
                        )
                        Text(
                            text = "${club.animeUniverse} • 👥 ${club.memberCount} membres",
                            fontSize = 10.sp,
                            color = OtakuTextMuted
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Button(
                            onClick = { onToggleJoin(club) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(32.dp)
                                .testTag("home_club_join_${club.id}"),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (club.isJoined) OtakuDarkCardElevated else OtakuPrimary
                            ),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp)
                        ) {
                            Text(
                                text = if (club.isJoined) "Membre ✓" else "Rejoindre +50XP",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (club.isJoined) OtakuSecondary else Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DailyQuizChallengeCard(onStartQuiz: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable { onStartQuiz() }
            .testTag("home_daily_quiz_card"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = OtakuDarkCard),
        border = androidx.compose.foundation.BorderStroke(1.dp, OtakuSecondary.copy(alpha = 0.4f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(OtakuSecondary.copy(alpha = 0.15f))
                    .border(1.dp, OtakuSecondary, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text("⚡", fontSize = 24.sp)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(OtakuTertiary.copy(alpha = 0.2f))
                            .padding(horizontal = 5.dp, vertical = 1.dp)
                    ) {
                        Text("DÉFI DU JOUR", fontSize = 9.sp, fontWeight = FontWeight.Black, color = OtakuTertiary)
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("+150 XP BONUS", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = OtakuSecondary)
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Quiz Shonen Spécial 5 Questions",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = OtakuTextPrimary
                )
                Text(
                    text = "Valide ton rang quotidien et grimpe au classement !",
                    fontSize = 11.sp,
                    color = OtakuTextMuted
                )
            }
            Button(
                onClick = onStartQuiz,
                colors = ButtonDefaults.buttonColors(containerColor = OtakuSecondary),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                modifier = Modifier.testTag("btn_start_daily_quiz")
            ) {
                Text("Jouer 🧠", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}

@Composable
fun HomeLeaderboardSpotlightCard(onNavigateToLeaderboard: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable { onNavigateToLeaderboard() }
            .testTag("home_leaderboard_spotlight_card"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = OtakuDarkSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, OtakuTertiary.copy(alpha = 0.4f))
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("🏆 Podium des Champions Otaku", fontSize = 14.sp, fontWeight = FontWeight.Black, color = OtakuTextPrimary)
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(OtakuTertiary.copy(alpha = 0.2f))
                            .padding(horizontal = 5.dp, vertical = 1.dp)
                    ) {
                        Text("Hebdo", fontSize = 9.sp, color = OtakuTertiary, fontWeight = FontWeight.Bold)
                    }
                }
                TextButton(onClick = onNavigateToLeaderboard) {
                    Text("Voir tout >", fontSize = 11.sp, color = OtakuTertiary, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Podium 3 Columns (2nd, 1st, 3rd)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {
                // 2nd Place (Sacha / User)
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("🥈", fontSize = 20.sp)
                    Text("Sacha [Vous]", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = OtakuSecondary)
                    Text("3 450 XP", fontSize = 10.sp, color = OtakuTextMuted)
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(
                        modifier = Modifier
                            .width(60.dp)
                            .height(38.dp)
                            .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                            .background(OtakuDarkCardElevated)
                            .border(1.dp, OtakuSecondary.copy(alpha = 0.3f), RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                    )
                }

                // 1st Place (Alex)
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("👑", fontSize = 16.sp)
                    Text("🥇", fontSize = 24.sp)
                    Text("Alex", fontSize = 12.sp, fontWeight = FontWeight.Black, color = OtakuTertiary)
                    Text("4 200 XP", fontSize = 10.sp, color = OtakuTertiary, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(
                        modifier = Modifier
                            .width(70.dp)
                            .height(52.dp)
                            .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                            .background(OtakuTertiary.copy(alpha = 0.2f))
                            .border(1.dp, OtakuTertiary, RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                    )
                }

                // 3rd Place (Kenji)
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("🥉", fontSize = 20.sp)
                    Text("Kenji", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = OtakuTextPrimary)
                    Text("3 100 XP", fontSize = 10.sp, color = OtakuTextMuted)
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(
                        modifier = Modifier
                            .width(60.dp)
                            .height(28.dp)
                            .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                            .background(OtakuDarkCardElevated)
                            .border(1.dp, OtakuDarkBorder, RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                    )
                }
            }
        }
    }
}

data class AnimeSpotlight(
    val title: String,
    val japanese: String,
    val tag: String,
    val emoji: String,
    val episodes: String
)

@Composable
fun SeasonalAnimeSpotlightRow() {
    val trendingAnime = listOf(
        AnimeSpotlight("Solo Leveling II", "俺だけレベルアップな件", "Tendance #1", "⚡", "Ep. 9 Dispo"),
        AnimeSpotlight("One Piece (Egghead)", "ワンピース", "Masterpiece", "🏴‍☠️", "Ep. 1120"),
        AnimeSpotlight("Jujutsu Kaisen", "呪術廻戦", "Dark Action", "🤞", "Saison 3 Annoncée"),
        AnimeSpotlight("Frieren", "葬送のフリーレン", "Chef-d'œuvre", "🌸", "Top 1 MAL"),
        AnimeSpotlight("Bleach TYBW", "BLEACH 千年血戦篇", "Hype Épique", "⚔️", "Cour 3")
    )

    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "🔥 Hype Anime de la Saison",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Black,
                    color = OtakuTextPrimary
                )
                Spacer(modifier = Modifier.width(6.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(OtakuPrimary.copy(alpha = 0.15f))
                        .padding(horizontal = 5.dp, vertical = 1.dp)
                ) {
                    Text("今季", fontSize = 10.sp, color = OtakuPrimary, fontWeight = FontWeight.Bold)
                }
            }
            Text(
                text = "Actu Simulcast",
                fontSize = 11.sp,
                color = OtakuSecondary,
                fontWeight = FontWeight.SemiBold
            )
        }

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(trendingAnime) { anime ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(14.dp))
                        .background(OtakuDarkCard)
                        .border(1.dp, OtakuDarkBorder, RoundedCornerShape(14.dp))
                        .padding(horizontal = 12.dp, vertical = 10.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = anime.emoji, fontSize = 22.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = anime.title,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = OtakuTextPrimary
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(3.dp))
                                        .background(OtakuTertiary.copy(alpha = 0.15f))
                                        .padding(horizontal = 4.dp, vertical = 1.dp)
                                ) {
                                    Text(
                                        text = anime.tag,
                                        fontSize = 8.sp,
                                        color = OtakuTertiary,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                            Text(
                                text = "${anime.japanese} • ${anime.episodes}",
                                fontSize = 10.sp,
                                color = OtakuTextMuted
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TournamentCalloutCard(onNavigateToEvents: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clickable { onNavigateToEvents() }
            .testTag("home_tournament_card"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = OtakuDarkCardElevated),
        border = androidx.compose.foundation.BorderStroke(1.dp, OtakuDarkBorder)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(OtakuTertiary.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "🏆", fontSize = 22.sp)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "ÉVÉNEMENT EN VUE",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = OtakuTertiary
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(OtakuPrimary)
                            .padding(horizontal = 4.dp, vertical = 1.dp)
                    ) {
                        Text("Samedi", fontSize = 9.sp, color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
                Text(
                    text = "Grand Tournoi Otaku Hub (128 Joueurs)",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = OtakuTextPrimary
                )
                Text(
                    text = "Phase éliminatoire directe • Récompenses légendaires",
                    fontSize = 11.sp,
                    color = OtakuTextSecondary
                )
            }
            TextButton(onClick = onNavigateToEvents) {
                Text("Voir >", color = OtakuSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun PostItemCard(
    post: PostEntity,
    onToggleLike: () -> Unit,
    onOpenComments: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .testTag("post_card_${post.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = OtakuDarkCard),
        border = androidx.compose.foundation.BorderStroke(1.dp, OtakuDarkBorder)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Header: Avatar, Name, Rank, Club Pill
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(OtakuDarkCardElevated),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = post.authorAvatar, fontSize = 18.sp)
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = post.authorName,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = OtakuTextPrimary
                    )
                    Text(
                        text = "${post.authorRank} • ${post.timestampStr}",
                        fontSize = 11.sp,
                        color = OtakuTextMuted
                    )
                }

                // Category pill
                val (badgeColor, japaneseCat) = when (post.category) {
                    "Théorie" -> Pair(OtakuSecondary, "考察")
                    "Actu" -> Pair(OtakuTertiary, "速報")
                    "Fan Art" -> Pair(OtakuPrimary, "神絵")
                    "Sondage" -> Pair(Color(0xFF059669), "投票")
                    else -> Pair(Color(0xFF7C3AED), "話題")
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(badgeColor.copy(alpha = 0.12f))
                        .border(1.dp, badgeColor.copy(alpha = 0.4f), RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = "【$japaneseCat】 ${post.category}",
                        fontSize = 10.sp,
                        color = badgeColor,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Club banner tag if specified
            if (post.clubName.isNotBlank() && post.clubName != "Général") {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "🏯 ${post.clubName}",
                    fontSize = 11.sp,
                    color = OtakuSecondary,
                    fontWeight = FontWeight.SemiBold
                )
            }

            // Content
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = post.content,
                fontSize = 13.sp,
                color = OtakuTextPrimary,
                lineHeight = 18.sp
            )

            // Actions row: Likes, Comments, Share
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Like Button
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { onToggleLike() }
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .testTag("like_post_${post.id}")
                ) {
                    Icon(
                        imageVector = if (post.isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "J'aime",
                        tint = if (post.isLiked) OtakuPrimary else OtakuTextMuted,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${post.likesCount}",
                        fontSize = 12.sp,
                        color = if (post.isLiked) OtakuPrimary else OtakuTextSecondary,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Comments Button
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { onOpenComments() }
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .testTag("comment_post_${post.id}")
                ) {
                    Icon(
                        imageVector = Icons.Default.ChatBubbleOutline,
                        contentDescription = "Commentaires",
                        tint = OtakuTextMuted,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${post.commentsCount} coms",
                        fontSize = 12.sp,
                        color = OtakuTextSecondary,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
fun PostCommentsDialog(
    post: PostEntity,
    viewModel: OtakuViewModel,
    onDismiss: () -> Unit
) {
    val comments by viewModel.getCommentsForPost(post.id).collectAsStateWithLifecycle(initialValue = emptyList())
    var newCommentText by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("💬 Commentaires", color = OtakuTextPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("(${comments.size})", color = OtakuSecondary, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }
                Text(
                    text = "Post de ${post.authorName}",
                    color = OtakuTextMuted,
                    fontSize = 11.sp,
                    maxLines = 1
                )
            }
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth().height(320.dp)) {
                // List of comments
                LazyColumn(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (comments.isEmpty()) {
                        item {
                            Box(
                                modifier = Modifier.fillMaxWidth().padding(top = 28.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "Aucun commentaire pour l'instant.\nSois le premier Otaku à réagir ! 💬",
                                    color = OtakuTextMuted,
                                    fontSize = 12.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    } else {
                        items(comments, key = { it.id }) { comment ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp),
                                colors = CardDefaults.cardColors(containerColor = OtakuDarkCardElevated),
                                border = androidx.compose.foundation.BorderStroke(1.dp, OtakuDarkBorder)
                            ) {
                                Row(
                                    modifier = Modifier.padding(10.dp),
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(28.dp)
                                            .clip(CircleShape)
                                            .background(OtakuDarkBackground),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(comment.authorAvatar, fontSize = 14.sp)
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                comment.authorName,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 12.sp,
                                                color = OtakuTextPrimary
                                            )
                                            Text(comment.timestampStr, fontSize = 10.sp, color = OtakuTextMuted)
                                        }
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            comment.content,
                                            color = OtakuTextPrimary,
                                            fontSize = 12.sp,
                                            lineHeight = 16.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Input row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = newCommentText,
                        onValueChange = { newCommentText = it },
                        placeholder = { Text("Écris un commentaire...", color = OtakuTextMuted, fontSize = 12.sp) },
                        modifier = Modifier.weight(1f).testTag("input_comment_content"),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = OtakuTextPrimary,
                            unfocusedTextColor = OtakuTextPrimary,
                            focusedBorderColor = OtakuSecondary,
                            unfocusedBorderColor = OtakuDarkBorder
                        ),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    IconButton(
                        onClick = {
                            if (newCommentText.isNotBlank()) {
                                viewModel.addComment(post.id, newCommentText)
                                newCommentText = ""
                            }
                        },
                        modifier = Modifier.testTag("btn_send_comment")
                    ) {
                        Icon(imageVector = Icons.Default.Send, contentDescription = "Envoyer", tint = OtakuSecondary)
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Fermer", color = OtakuTextMuted)
            }
        },
        containerColor = OtakuDarkSurface
    )
}

@Composable
fun CreatePostDialog(
    onDismiss: () -> Unit,
    onSubmit: (content: String, category: String, club: String) -> Unit
) {
    var content by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Discussion") }
    var selectedClub by remember { mutableStateOf("Club One Piece 🌀") }

    val categories = listOf("Discussion", "Théorie", "Découverte", "Fan Art", "Actu", "Sondage")
    val clubs = listOf("Général", "Club One Piece 🌀", "Club Naruto & Boruto 🍃", "Club Jujutsu Kaisen 🤞", "Club Demon Slayer (Kimetsu) ⚔️")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Nouvelle Publication Otaku 🌸", color = OtakuTextPrimary, fontWeight = FontWeight.Bold) },
        text = {
            Column {
                Text("Partage une théorie, une découverte ou lance une discussion :", color = OtakuTextSecondary, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    placeholder = { Text("Écris ton message...", color = OtakuTextMuted) },
                    modifier = Modifier.fillMaxWidth().height(120.dp).testTag("input_post_content"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = OtakuTextPrimary,
                        unfocusedTextColor = OtakuTextPrimary,
                        focusedBorderColor = OtakuPrimary,
                        unfocusedBorderColor = OtakuDarkBorder
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))
                Text("Catégorie :", color = OtakuTextPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(categories) { cat ->
                        val isSelected = selectedCategory == cat
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (isSelected) OtakuPrimary else OtakuDarkCard)
                                .clickable { selectedCategory = cat }
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(cat, fontSize = 11.sp, color = if (isSelected) Color.White else OtakuTextSecondary)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                Text("Publier dans le Club :", color = OtakuTextPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(clubs) { clb ->
                        val isSelected = selectedClub == clb
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (isSelected) OtakuSecondary else OtakuDarkCard)
                                .clickable { selectedClub = clb }
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(clb, fontSize = 11.sp, color = if (isSelected) Color.White else OtakuTextSecondary, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal)
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (content.isNotBlank()) {
                        onSubmit(content, selectedCategory, selectedClub)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = OtakuPrimary),
                modifier = Modifier.testTag("btn_submit_post")
            ) {
                Text("Publier", fontWeight = FontWeight.Bold)
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
