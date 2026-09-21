package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
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
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.HowToVote
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SportsKabaddi
import androidx.compose.material.icons.filled.Star
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.AnimeDebateItem
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.R
import com.example.data.model.QuizHistoryEntity
import com.example.data.model.QuizQuestionEntity
import com.example.data.model.UserProfileEntity
import com.example.ui.BattleState
import com.example.ui.OtakuViewModel
import com.example.ui.QuizPracticeState
import com.example.ui.components.otakuAtmosphericBackground
import com.example.ui.theme.DiffBeginner
import com.example.ui.theme.DiffExpert
import com.example.ui.theme.DiffIntermediate
import com.example.ui.theme.DiffMaster
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
fun QuizScreen(
    viewModel: OtakuViewModel,
    questions: List<QuizQuestionEntity>,
    quizState: QuizPracticeState,
    battleState: BattleState
) {
    var selectedModeTab by remember { mutableIntStateOf(if (battleState.isActive) 1 else 0) }
    val profile by viewModel.userProfile.collectAsStateWithLifecycle()
    val quizHistory by viewModel.quizHistory.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .otakuAtmosphericBackground()
    ) {
        // Mode Tabs: Quiz Incollable vs Anime Battle vs Grands Débats
        TabRow(
            selectedTabIndex = selectedModeTab,
            containerColor = OtakuDarkSurface,
            contentColor = OtakuTextPrimary,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedModeTab]),
                    color = when (selectedModeTab) {
                        0 -> OtakuSecondary
                        1 -> OtakuPrimary
                        else -> OtakuTertiary
                    }
                )
            }
        ) {
            Tab(
                selected = selectedModeTab == 0,
                onClick = { selectedModeTab = 0 },
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Psychology, contentDescription = null, modifier = Modifier.size(16.dp), tint = if (selectedModeTab == 0) OtakuSecondary else OtakuTextSecondary)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            "Quiz Incollable 🧠",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = if (selectedModeTab == 0) OtakuSecondary else OtakuTextSecondary
                        )
                    }
                },
                modifier = Modifier.testTag("tab_quiz_learn")
            )
            Tab(
                selected = selectedModeTab == 1,
                onClick = {
                    selectedModeTab = 1
                    if (!battleState.isActive) {
                        viewModel.startBattle("Alex", "🦊")
                    }
                },
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.SportsKabaddi, contentDescription = null, modifier = Modifier.size(16.dp), tint = if (selectedModeTab == 1) OtakuPrimary else OtakuTextSecondary)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            "Anime Battle ⚔️",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = if (selectedModeTab == 1) OtakuPrimary else OtakuTextSecondary
                        )
                    }
                },
                modifier = Modifier.testTag("tab_anime_battle")
            )
            Tab(
                selected = selectedModeTab == 2,
                onClick = { selectedModeTab = 2 },
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Forum, contentDescription = null, modifier = Modifier.size(16.dp), tint = if (selectedModeTab == 2) OtakuTertiary else OtakuTextSecondary)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            "Grands Débats 💬",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = if (selectedModeTab == 2) OtakuTertiary else OtakuTextSecondary
                        )
                    }
                },
                modifier = Modifier.testTag("tab_anime_debates")
            )
        }

        when (selectedModeTab) {
            0 -> {
                PedagogicalQuizView(
                    viewModel = viewModel,
                    allQuestions = questions,
                    state = quizState,
                    profile = profile,
                    quizHistory = quizHistory,
                    onSwitchToDebates = { selectedModeTab = 2 }
                )
            }
            1 -> {
                AnimeBattleView(
                    viewModel = viewModel,
                    battleState = battleState
                )
            }
            else -> {
                AnimeDebatesView(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun PedagogicalQuizView(
    viewModel: OtakuViewModel,
    allQuestions: List<QuizQuestionEntity>,
    state: QuizPracticeState,
    profile: UserProfileEntity,
    quizHistory: List<QuizHistoryEntity>,
    onSwitchToDebates: () -> Unit = {}
) {
    var showCreateQuestionDialog by remember { mutableStateOf(false) }
    var showAdminCreateDialog by remember { mutableStateOf(false) }
    var showScoreHistoryDialog by remember { mutableStateOf(false) }
    var questionToDelete by remember { mutableStateOf<QuizQuestionEntity?>(null) }

    val difficulties = listOf("Tous", "Débutant", "Intermédiaire", "Expert", "Maître")
    val popularUniverses = listOf(
        "Tous",
        "One Piece",
        "Naruto",
        "Dragon Ball",
        "Jujutsu Kaisen",
        "Attack on Titan",
        "Solo Leveling",
        "Chainsaw Man",
        "Bleach",
        "Demon Slayer",
        "Death Note",
        "Hunter x Hunter"
    )

    val filteredQuestions = remember(
        allQuestions,
        state.activeDifficulty,
        state.selectedUniverse,
        state.searchQuery,
        state.filterOnlyAdminCreated
    ) {
        allQuestions.filter { q ->
            val matchDiff = state.activeDifficulty == "Tous" || q.difficulty.equals(state.activeDifficulty, ignoreCase = true)
            val matchUniverse = state.selectedUniverse == "Tous" || q.universe.contains(state.selectedUniverse, ignoreCase = true)
            val matchSearch = state.searchQuery.isBlank() ||
                    q.question.contains(state.searchQuery, ignoreCase = true) ||
                    q.universe.contains(state.searchQuery, ignoreCase = true) ||
                    q.explanation.contains(state.searchQuery, ignoreCase = true)
            val matchAdmin = !state.filterOnlyAdminCreated || q.createdByAdmin
            matchDiff && matchUniverse && matchSearch && matchAdmin
        }
    }

    val currentQuestion = if (filteredQuestions.isEmpty()) null
    else filteredQuestions[state.currentIndex % filteredQuestions.size]

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp, bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // 1. User Score & Reputation Progress Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = OtakuDarkSurface),
                border = androidx.compose.foundation.BorderStroke(1.dp, OtakuTertiary.copy(alpha = 0.4f))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    // Header: User & Tier + Admin Toggle
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = profile.avatarEmoji, fontSize = 24.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = profile.username,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = OtakuTextPrimary
                                )
                                Text(
                                    text = profile.reputationTier,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Black,
                                    color = OtakuTertiary
                                )
                            }
                        }

                        // Admin Mode Chip
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(if (state.isAdminMode) Color(0x33FFD700) else OtakuDarkCardElevated)
                                .border(1.dp, if (state.isAdminMode) Color(0xFFFFD700) else OtakuDarkBorder, RoundedCornerShape(20.dp))
                                .clickable { viewModel.toggleAdminMode() }
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                                .testTag("btn_toggle_admin_mode")
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.AdminPanelSettings,
                                    contentDescription = null,
                                    tint = if (state.isAdminMode) Color(0xFFFFD700) else OtakuTextMuted,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = if (state.isAdminMode) "👑 Admin ACTIF" else "Mode Joueur",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (state.isAdminMode) Color(0xFFFFD700) else OtakuTextSecondary
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Reputation Points and Progress Bar
                    val nextTierInfo = when {
                        profile.reputationPoints < 500 -> Pair("Apprenti Otaku 📜", 500)
                        profile.reputationPoints < 1000 -> Pair("Érudit Otaku ⚔️", 1000)
                        profile.reputationPoints < 2000 -> Pair("Maître des Univers 🔮", 2000)
                        profile.reputationPoints < 3500 -> Pair("Sage Légendaire 👑", 3500)
                        else -> Pair("Rang Suprême Atteint 🌟", 3500)
                    }
                    val prevThreshold = when {
                        profile.reputationPoints < 500 -> 0
                        profile.reputationPoints < 1000 -> 500
                        profile.reputationPoints < 2000 -> 1000
                        profile.reputationPoints < 3500 -> 2000
                        else -> 3500
                    }
                    val progress = if (profile.reputationPoints >= 3500) 1f
                    else ((profile.reputationPoints - prevThreshold).toFloat() / (nextTierInfo.second - prevThreshold).toFloat()).coerceIn(0f, 1f)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Column {
                            Text(
                                text = "RÉPUTATION OTAKU",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Black,
                                color = OtakuTextMuted,
                                letterSpacing = 0.5.sp
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "${profile.reputationPoints}",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Black,
                                    color = OtakuTertiary
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "RP",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = OtakuSecondary
                                )
                            }
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "Prochain Palier : ${nextTierInfo.first}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = OtakuTextSecondary
                            )
                            Text(
                                text = "${profile.reputationPoints} / ${nextTierInfo.second} RP",
                                fontSize = 10.sp,
                                color = OtakuTextMuted
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = OtakuTertiary,
                        trackColor = OtakuDarkBorder
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Performance Stats
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Quiz Résolus", fontSize = 10.sp, color = OtakuTextMuted)
                            Text("${profile.quizzesSolved}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = OtakuTextPrimary)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Série Actuelle", fontSize = 10.sp, color = OtakuTextMuted)
                            Text("🔥 ${state.currentStreak}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = OtakuPrimary)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Record Série", fontSize = 10.sp, color = OtakuTextMuted)
                            Text("⭐ ${state.bestStreak}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFFD700))
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Précision", fontSize = 10.sp, color = OtakuTextMuted)
                            val accuracy = if (state.totalAnswered > 0) ((state.correctCount * 100) / state.totalAnswered) else 100
                            Text("$accuracy%", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = DiffBeginner)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Button: Open Score & Reputation History Dialog
                    OutlinedButton(
                        onClick = { showScoreHistoryDialog = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("btn_open_score_history"),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = OtakuSecondary),
                        border = androidx.compose.foundation.BorderStroke(1.dp, OtakuSecondary.copy(alpha = 0.5f))
                    ) {
                        Icon(imageVector = Icons.Default.History, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "📜 Historique des Quiz & Paliers de Réputation",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // 2. Trivia Fetcher & Synchronizer
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = OtakuDarkSurface),
                border = androidx.compose.foundation.BorderStroke(1.dp, OtakuSecondary.copy(alpha = 0.3f))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.CloudDownload,
                                contentDescription = null,
                                tint = OtakuSecondary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "Pack Trivia Anime Populaires 🌐",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = OtakuTextPrimary
                                )
                                Text(
                                    text = "Synchronise de nouvelles questions de lore shonen & seinen",
                                    fontSize = 11.sp,
                                    color = OtakuTextMuted
                                )
                            }
                        }

                        Button(
                            onClick = { viewModel.fetchPopularAnimeTrivia(if (state.selectedUniverse == "Tous") null else state.selectedUniverse) },
                            enabled = !state.isFetchingTrivia,
                            colors = ButtonDefaults.buttonColors(containerColor = OtakuSecondary),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.testTag("btn_fetch_trivia")
                        ) {
                            if (state.isFetchingTrivia) {
                                CircularProgressIndicator(modifier = Modifier.size(16.dp), color = Color.White, strokeWidth = 2.dp)
                            } else {
                                Icon(imageVector = Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Sync Trivia", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            }
                        }
                    }

                    if (state.fetchTriviaStatusMessage != null) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0x2200E676))
                                .border(1.dp, DiffBeginner.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = state.fetchTriviaStatusMessage ?: "",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = DiffBeginner
                            )
                        }
                    }
                }
            }
        }

        // 3. Anime Universes Filter Row
        item {
            Column {
                Text(
                    text = "Univers Anime Populaire :",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = OtakuTextSecondary
                )
                Spacer(modifier = Modifier.height(6.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(popularUniverses) { universe ->
                        val isSelected = state.selectedUniverse.equals(universe, ignoreCase = true)
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(if (isSelected) OtakuSecondary else OtakuDarkCard)
                                .border(1.dp, if (isSelected) OtakuSecondary else OtakuDarkBorder, RoundedCornerShape(16.dp))
                                .clickable { viewModel.setQuizUniverse(universe) }
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                                .testTag("universe_filter_$universe")
                        ) {
                            Text(
                                text = universe,
                                color = if (isSelected) Color.White else OtakuTextSecondary,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }
            }
        }

        // 4. Search and Difficulty Filters
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                // Search Input
                OutlinedTextField(
                    value = state.searchQuery,
                    onValueChange = { viewModel.setQuizSearchQuery(it) },
                    placeholder = { Text("Rechercher question, personnage, lore...", fontSize = 12.sp, color = OtakuTextMuted) },
                    leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = OtakuTextMuted, modifier = Modifier.size(18.dp)) },
                    trailingIcon = {
                        if (state.searchQuery.isNotBlank()) {
                            IconButton(onClick = { viewModel.setQuizSearchQuery("") }) {
                                Icon(imageVector = Icons.Default.Close, contentDescription = "Effacer", tint = OtakuTextMuted, modifier = Modifier.size(16.dp))
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_search_quiz"),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = OtakuTextPrimary,
                        unfocusedTextColor = OtakuTextPrimary,
                        focusedBorderColor = OtakuSecondary,
                        unfocusedBorderColor = OtakuDarkBorder,
                        focusedContainerColor = OtakuDarkSurface,
                        unfocusedContainerColor = OtakuDarkSurface
                    ),
                    singleLine = true
                )

                // Difficulties & Admin filter chip
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LazyRow(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        items(difficulties) { diff ->
                            val isSelected = state.activeDifficulty == diff
                            val diffColor = when (diff) {
                                "Débutant" -> DiffBeginner
                                "Intermédiaire" -> DiffIntermediate
                                "Expert" -> DiffExpert
                                "Maître" -> DiffMaster
                                else -> OtakuSecondary
                            }
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(if (isSelected) diffColor else OtakuDarkCard)
                                    .border(1.dp, if (isSelected) diffColor else OtakuDarkBorder, RoundedCornerShape(14.dp))
                                    .clickable { viewModel.setQuizDifficulty(diff) }
                                    .padding(horizontal = 10.dp, vertical = 5.dp)
                                    .testTag("diff_pill_$diff")
                            ) {
                                Text(
                                    text = diff,
                                    color = if (isSelected) Color.White else OtakuTextSecondary,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    // Admin filter chip
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(14.dp))
                            .background(if (state.filterOnlyAdminCreated) Color(0xFFFFD700) else OtakuDarkCard)
                            .border(1.dp, if (state.filterOnlyAdminCreated) Color(0xFFFFD700) else OtakuDarkBorder, RoundedCornerShape(14.dp))
                            .clickable { viewModel.toggleFilterOnlyAdmin() }
                            .padding(horizontal = 8.dp, vertical = 5.dp)
                            .testTag("chip_filter_admin_quiz")
                    ) {
                        Text(
                            text = "👑 Quiz Admin",
                            color = if (state.filterOnlyAdminCreated) Color.Black else OtakuTextSecondary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // 5. Admin Console Banner (Shown when admin mode is enabled)
        if (state.isAdminMode) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1736)),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFFFFD700))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.AdminPanelSettings,
                                    contentDescription = null,
                                    tint = Color(0xFFFFD700),
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Espace Administrateur des Quiz 👑",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color(0xFFFFD700)
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(Color(0xFFFFD700).copy(alpha = 0.2f))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text("Droits Certifiés", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFFD700))
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Tu peux créer des questions officielles certifiées, définir les gains d'XP et de Réputation, ou modérer les questions existantes.",
                            fontSize = 11.sp,
                            color = OtakuTextSecondary,
                            lineHeight = 16.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = { showAdminCreateDialog = true },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("btn_open_admin_create_quiz"),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD700))
                            ) {
                                Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = Color.Black, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Créer Quiz Admin", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                            }

                            OutlinedButton(
                                onClick = { showCreateQuestionDialog = true },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("btn_open_propose_quiz"),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = OtakuSecondary),
                                border = androidx.compose.foundation.BorderStroke(1.dp, OtakuSecondary)
                            ) {
                                Text("✍️ Proposer Simple", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }

        // 6. Current Question Card
        if (filteredQuestions.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = OtakuDarkSurface)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("🔍", fontSize = 32.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Aucune question trouvée pour ces critères",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = OtakuTextPrimary,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Essaye de réinitialiser la recherche ou de synchroniser les trivia anime populaires ci-dessus !",
                            fontSize = 12.sp,
                            color = OtakuTextSecondary,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(
                            onClick = {
                                viewModel.setQuizDifficulty("Tous")
                                viewModel.setQuizUniverse("Tous")
                                viewModel.setQuizSearchQuery("")
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = OtakuSecondary),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Réinitialiser les Filtres", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }
                }
            }
        } else if (currentQuestion != null) {
            item {
                val diffColor = when (currentQuestion.difficulty) {
                    "Débutant" -> DiffBeginner
                    "Intermédiaire" -> DiffIntermediate
                    "Expert" -> DiffExpert
                    "Maître" -> DiffMaster
                    else -> OtakuSecondary
                }

                val repGain = when (currentQuestion.difficulty) {
                    "Débutant" -> 25
                    "Intermédiaire" -> 50
                    "Expert" -> 85
                    "Maître" -> 120
                    else -> 35
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("card_quiz_current"),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = OtakuDarkSurface),
                    border = androidx.compose.foundation.BorderStroke(
                        width = if (currentQuestion.createdByAdmin) 1.5.dp else 1.dp,
                        color = if (currentQuestion.createdByAdmin) Color(0xFFFFD700) else OtakuDarkBorder
                    )
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        // Top Badges & Admin Delete Action
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color(0x2205D9E8))
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = currentQuestion.universe,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = OtakuSecondary
                                    )
                                }

                                Spacer(modifier = Modifier.width(6.dp))

                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(diffColor.copy(alpha = 0.2f))
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = currentQuestion.difficulty,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = diffColor
                                    )
                                }
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color(0x22FFD700))
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = "+${currentQuestion.xpReward} XP | +$repGain RP",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Black,
                                        color = Color(0xFFFFD700)
                                    )
                                }

                                if (state.isAdminMode) {
                                    Spacer(modifier = Modifier.width(4.dp))
                                    IconButton(
                                        onClick = { questionToDelete = currentQuestion },
                                        modifier = Modifier
                                            .size(28.dp)
                                            .testTag("btn_delete_quiz_${currentQuestion.id}")
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Supprimer (Admin)",
                                            tint = Color(0xFFFF5252),
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            }
                        }

                        // Admin Author Badge
                        if (currentQuestion.createdByAdmin) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(Color(0x22FFD700))
                                    .border(1.dp, Color(0x66FFD700), RoundedCornerShape(6.dp))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("👑", fontSize = 12.sp)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "Quiz Officiel certifié par ${currentQuestion.adminAuthor}",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFFFFD700)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Question index and Question text
                        val currentIndexDisplay = (state.currentIndex % filteredQuestions.size) + 1
                        Text(
                            text = "QUESTION $currentIndexDisplay / ${filteredQuestions.size}",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black,
                            color = OtakuTextMuted,
                            letterSpacing = 1.sp
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = currentQuestion.question,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = OtakuTextPrimary,
                            lineHeight = 22.sp
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        // 4 Options
                        val options = listOf(
                            currentQuestion.option1,
                            currentQuestion.option2,
                            currentQuestion.option3,
                            currentQuestion.option4
                        )

                        options.forEachIndexed { index, optionText ->
                            val isSelected = state.selectedOptionIndex == index
                            val isCorrectAnswer = currentQuestion.correctOptionIndex == index

                            val (bgColor, borderColor, textColor) = when {
                                !state.isAnswered -> {
                                    if (isSelected) Triple(Color(0x3305D9E8), OtakuSecondary, OtakuTextPrimary)
                                    else Triple(OtakuDarkCardElevated, OtakuDarkBorder, OtakuTextPrimary)
                                }
                                isCorrectAnswer -> {
                                    Triple(Color(0x3300E676), DiffBeginner, DiffBeginner)
                                }
                                isSelected -> {
                                    Triple(Color(0x33FF2A85), DiffMaster, DiffMaster)
                                }
                                else -> {
                                    Triple(OtakuDarkCardElevated, OtakuDarkBorder, OtakuTextMuted)
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(bgColor)
                                    .border(1.dp, borderColor, RoundedCornerShape(12.dp))
                                    .clickable(enabled = !state.isAnswered) {
                                        viewModel.answerQuizQuestion(index, currentQuestion)
                                    }
                                    .padding(14.dp)
                                    .testTag("quiz_option_$index")
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                                        Box(
                                            modifier = Modifier
                                                .size(24.dp)
                                                .clip(CircleShape)
                                                .background(borderColor.copy(alpha = 0.2f)),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            val letter = when (index) {
                                                0 -> "A"
                                                1 -> "B"
                                                2 -> "C"
                                                else -> "D"
                                            }
                                            Text(
                                                text = letter,
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Black,
                                                color = borderColor
                                            )
                                        }

                                        Spacer(modifier = Modifier.width(10.dp))

                                        Text(
                                            text = optionText,
                                            fontSize = 13.sp,
                                            color = textColor,
                                            fontWeight = if (isSelected || (state.isAnswered && isCorrectAnswer)) FontWeight.Bold else FontWeight.Normal
                                        )
                                    }

                                    if (state.isAnswered) {
                                        if (isCorrectAnswer) {
                                            Icon(
                                                imageVector = Icons.Default.CheckCircle,
                                                contentDescription = "Correct",
                                                tint = DiffBeginner,
                                                modifier = Modifier.size(18.dp)
                                            )
                                        } else if (isSelected) {
                                            Icon(
                                                imageVector = Icons.Default.Close,
                                                contentDescription = "Incorrect",
                                                tint = DiffMaster,
                                                modifier = Modifier.size(18.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        // Pedagogical Lore & Explanation Box on Answer
                        if (state.isAnswered) {
                            Spacer(modifier = Modifier.height(14.dp))

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(if (state.isCorrect) Color(0x1A00E676) else Color(0x1AFFB300))
                                    .border(
                                        1.dp,
                                        if (state.isCorrect) DiffBeginner.copy(alpha = 0.5f) else Color(0xFFFFB300).copy(alpha = 0.5f),
                                        RoundedCornerShape(14.dp)
                                    )
                                    .padding(14.dp)
                            ) {
                                Column {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = if (state.isCorrect) "✅ RÉPONSE EXACTE !" else "💡 APPRENTISSAGE BIENVEILLANT",
                                            fontWeight = FontWeight.Black,
                                            fontSize = 12.sp,
                                            color = if (state.isCorrect) DiffBeginner else Color(0xFFFFB300),
                                            letterSpacing = 0.5.sp
                                        )

                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(6.dp))
                                                .background(OtakuDarkSurface)
                                                .padding(horizontal = 8.dp, vertical = 3.dp)
                                        ) {
                                            Text(
                                                text = if (state.isCorrect) "+$repGain RP • +${currentQuestion.xpReward} XP" else "+10 RP (Effort)",
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = OtakuTertiary
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Text(
                                        text = "📜 Règle de Lore & Analyse Détaillée :",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = OtakuTextMuted
                                    )

                                    Spacer(modifier = Modifier.height(4.dp))

                                    Text(
                                        text = currentQuestion.explanation,
                                        fontSize = 13.sp,
                                        color = OtakuTextPrimary,
                                        lineHeight = 18.sp
                                    )

                                    Spacer(modifier = Modifier.height(10.dp))

                                    OutlinedButton(
                                        onClick = onSwitchToDebates,
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(10.dp),
                                        colors = ButtonDefaults.outlinedButtonColors(contentColor = OtakuTertiary),
                                        border = androidx.compose.foundation.BorderStroke(1.dp, OtakuTertiary.copy(alpha = 0.5f))
                                    ) {
                                        Icon(imageVector = Icons.Default.Forum, contentDescription = null, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = "Explorer les Grands Débats Anime 💬",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Button(
                                onClick = { viewModel.nextQuizQuestion(filteredQuestions.size) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("btn_next_quiz"),
                                colors = ButtonDefaults.buttonColors(containerColor = OtakuPrimary),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    text = "Question Suivante >",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }

        // 7. Session Stats Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = OtakuDarkSurface)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Réussites Session", fontSize = 11.sp, color = OtakuTextMuted)
                        Text("${state.correctCount}/${state.totalAnswered}", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = DiffBeginner)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("XP Session", fontSize = 11.sp, color = OtakuTextMuted)
                        Text("+${state.xpEarnedSession} XP", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = OtakuTertiary)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Série Actuelle", fontSize = 11.sp, color = OtakuTextMuted)
                        Text("🔥 ${state.currentStreak}", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = OtakuPrimary)
                    }
                }
            }
        }
    }

    // Propose standard user question dialog
    if (showCreateQuestionDialog) {
        CreateQuizQuestionDialog(
            onDismiss = { showCreateQuestionDialog = false },
            onSubmit = { universe, diff, quest, o1, o2, o3, o4, correct, exp ->
                viewModel.createQuizQuestion(universe, diff, quest, o1, o2, o3, o4, correct, exp)
                showCreateQuestionDialog = false
            }
        )
    }

    // Admin quiz creation dialog
    if (showAdminCreateDialog) {
        AdminCreateQuizDialog(
            adminName = profile.username,
            onDismiss = { showAdminCreateDialog = false },
            onSubmit = { universe, diff, quest, o1, o2, o3, o4, correct, exp, xp, author ->
                viewModel.createAdminQuizQuestion(
                    universe = universe,
                    difficulty = diff,
                    question = quest,
                    opt1 = o1,
                    opt2 = o2,
                    opt3 = o3,
                    opt4 = o4,
                    correctIndex = correct,
                    explanation = exp,
                    xp = xp,
                    adminAuthor = author
                )
                showAdminCreateDialog = false
            }
        )
    }

    // Score & Reputation History Dialog
    if (showScoreHistoryDialog) {
        ScoreAndReputationHistoryDialog(
            profile = profile,
            quizHistory = quizHistory,
            onDismiss = { showScoreHistoryDialog = false }
        )
    }

    // Delete question confirmation dialog
    questionToDelete?.let { q ->
        AlertDialog(
            onDismissRequest = { questionToDelete = null },
            title = {
                Text(
                    text = "Supprimer le Quiz (Admin) 🗑️",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = OtakuTextPrimary
                )
            },
            text = {
                Text(
                    text = "Voulez-vous supprimer définitivement la question #${q.id} sur « ${q.universe} » ?\n\n« ${q.question} »",
                    fontSize = 13.sp,
                    color = OtakuTextSecondary
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.deleteQuizQuestion(q.id)
                        questionToDelete = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5252)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Supprimer", color = Color.White, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { questionToDelete = null }) {
                    Text("Annuler", color = OtakuTextMuted)
                }
            },
            containerColor = OtakuDarkSurface
        )
    }
}

@Composable
fun AdminCreateQuizDialog(
    adminName: String,
    onDismiss: () -> Unit,
    onSubmit: (
        universe: String,
        diff: String,
        quest: String,
        o1: String,
        o2: String,
        o3: String,
        o4: String,
        correct: Int,
        exp: String,
        xp: Int,
        author: String
    ) -> Unit
) {
    var universe by remember { mutableStateOf("One Piece") }
    var selectedDifficulty by remember { mutableStateOf("Intermédiaire") }
    var adminAuthor by remember { mutableStateOf(if (adminName.isNotBlank()) adminName else "Admin Otaku") }
    var questionText by remember { mutableStateOf("") }
    var option1 by remember { mutableStateOf("") }
    var option2 by remember { mutableStateOf("") }
    var option3 by remember { mutableStateOf("") }
    var option4 by remember { mutableStateOf("") }
    var correctIndex by remember { mutableIntStateOf(0) }
    var explanationText by remember { mutableStateOf("") }
    var selectedXp by remember { mutableIntStateOf(50) }

    val difficulties = listOf("Débutant", "Intermédiaire", "Expert", "Maître")
    val xpOptions = listOf(35, 50, 85, 120)

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("👑", fontSize = 18.sp)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Créer un Quiz Officiel (Admin)",
                        color = Color(0xFFFFD700),
                        fontWeight = FontWeight.Black,
                        fontSize = 16.sp
                    )
                }
                Text(
                    text = "Publie une question certifiée dans la base centrale",
                    color = OtakuTextSecondary,
                    fontSize = 11.sp
                )
            }
        },
        text = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(380.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Preset Templates
                item {
                    Text("⚡ Remplissage Rapide via Modèles :", color = OtakuTextMuted, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        item {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0x33FFD700))
                                    .border(1.dp, Color(0xFFFFD700), RoundedCornerShape(8.dp))
                                    .clickable {
                                        universe = "One Piece"
                                        selectedDifficulty = "Expert"
                                        questionText = "Quel est le nom véritable du Fruit du Démon de Luffy révélé à Wano ?"
                                        option1 = "Gomu Gomu no Mi"
                                        option2 = "Hito Hito no Mi, Modèle Nika"
                                        option3 = "Mochi Mochi no Mi"
                                        option4 = "Nika Nika Paramecia"
                                        correctIndex = 1
                                        explanationText = "Le Gouvernement Mondial avait dissimulé son véritable nom mythologique Zoan : Hito Hito no Mi, Modèle Nika."
                                        selectedXp = 85
                                    }
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text("🏴‍☠️ One Piece (Nika)", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFFD700))
                            }
                        }
                        item {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0x3305D9E8))
                                    .border(1.dp, OtakuSecondary, RoundedCornerShape(8.dp))
                                    .clickable {
                                        universe = "Solo Leveling"
                                        selectedDifficulty = "Intermédiaire"
                                        questionText = "Quel titre Sung Jinwoo acquiert-il après l'épreuve du Trône ?"
                                        option1 = "Monarque des Ombres"
                                        option2 = "Roi des Bêtes"
                                        option3 = "Souverain de la Destruction"
                                        option4 = "Maître du Sang"
                                        correctIndex = 0
                                        explanationText = "Sung Jinwoo hérite du pouvoir primordial d'Ashborn et devient le Monarque des Ombres."
                                        selectedXp = 50
                                    }
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text("🗡️ Solo Leveling", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = OtakuSecondary)
                            }
                        }
                        item {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0x33FF2A85))
                                    .border(1.dp, OtakuPrimary, RoundedCornerShape(8.dp))
                                    .clickable {
                                        universe = "Dragon Ball"
                                        selectedDifficulty = "Expert"
                                        questionText = "Qui a créé les Super Dragon Balls de la dimension universelle ?"
                                        option1 = "Le Grand Prêtre"
                                        option2 = "Zalama, le Dieu Dragon"
                                        option3 = "Zeno l'Omnipotent"
                                        option4 = "Super Shenron"
                                        correctIndex = 1
                                        explanationText = "Zalama a créé les Super Dragon Balls en l'an 41 du calendrier divin, sans aucune restriction."
                                        selectedXp = 85
                                    }
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text("🐉 Dragon Ball", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = OtakuPrimary)
                            }
                        }
                    }
                }

                item {
                    OutlinedTextField(
                        value = universe,
                        onValueChange = { universe = it },
                        label = { Text("Univers / Série Anime", color = OtakuTextMuted, fontSize = 11.sp) },
                        modifier = Modifier.fillMaxWidth().testTag("input_admin_quiz_universe"),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = OtakuTextPrimary,
                            unfocusedTextColor = OtakuTextPrimary,
                            focusedBorderColor = Color(0xFFFFD700),
                            unfocusedBorderColor = OtakuDarkBorder
                        ),
                        singleLine = true
                    )
                }

                item {
                    OutlinedTextField(
                        value = adminAuthor,
                        onValueChange = { adminAuthor = it },
                        label = { Text("Nom de l'Administrateur Signataire", color = OtakuTextMuted, fontSize = 11.sp) },
                        modifier = Modifier.fillMaxWidth().testTag("input_admin_quiz_author"),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = OtakuTextPrimary,
                            unfocusedTextColor = OtakuTextPrimary,
                            focusedBorderColor = Color(0xFFFFD700),
                            unfocusedBorderColor = OtakuDarkBorder
                        ),
                        singleLine = true
                    )
                }

                item {
                    Text("Difficulté & Points :", color = OtakuTextMuted, fontSize = 11.sp)
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        items(difficulties) { diff ->
                            val isSelected = selectedDifficulty == diff
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isSelected) Color(0xFFFFD700) else OtakuDarkCardElevated)
                                    .clickable {
                                        selectedDifficulty = diff
                                        selectedXp = when (diff) {
                                            "Débutant" -> 35
                                            "Intermédiaire" -> 50
                                            "Expert" -> 85
                                            else -> 120
                                        }
                                    }
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = diff,
                                    color = if (isSelected) Color.Black else OtakuTextSecondary,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }

                item {
                    OutlinedTextField(
                        value = questionText,
                        onValueChange = { questionText = it },
                        label = { Text("Question de Lore Canon", color = OtakuTextMuted, fontSize = 11.sp) },
                        modifier = Modifier.fillMaxWidth().testTag("input_admin_quiz_question"),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = OtakuTextPrimary,
                            unfocusedTextColor = OtakuTextPrimary,
                            focusedBorderColor = Color(0xFFFFD700),
                            unfocusedBorderColor = OtakuDarkBorder
                        ),
                        maxLines = 3
                    )
                }

                item {
                    Text("4 Options (Sélectionne la réponse correcte) :", color = OtakuTextMuted, fontSize = 11.sp)
                }

                // Options with radio buttons
                listOf(
                    Pair("Choix A", option1),
                    Pair("Choix B", option2),
                    Pair("Choix C", option3),
                    Pair("Choix D", option4)
                ).forEachIndexed { index, pair ->
                    item {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = correctIndex == index,
                                onClick = { correctIndex = index },
                                colors = RadioButtonDefaults.colors(selectedColor = Color(0xFFFFD700), unselectedColor = OtakuTextMuted)
                            )
                            OutlinedTextField(
                                value = when (index) {
                                    0 -> option1
                                    1 -> option2
                                    2 -> option3
                                    else -> option4
                                },
                                onValueChange = {
                                    when (index) {
                                        0 -> option1 = it
                                        1 -> option2 = it
                                        2 -> option3 = it
                                        3 -> option4 = it
                                    }
                                },
                                placeholder = { Text(pair.first, color = OtakuTextMuted, fontSize = 11.sp) },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("input_admin_opt_$index"),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = OtakuTextPrimary,
                                    unfocusedTextColor = OtakuTextPrimary,
                                    focusedBorderColor = if (correctIndex == index) Color(0xFFFFD700) else OtakuDarkBorder,
                                    unfocusedBorderColor = OtakuDarkBorder
                                ),
                                singleLine = true
                            )
                        }
                    }
                }

                item {
                    OutlinedTextField(
                        value = explanationText,
                        onValueChange = { explanationText = it },
                        label = { Text("Analyse de Lore & Explication Pédagogique 📜", color = OtakuTextMuted, fontSize = 11.sp) },
                        modifier = Modifier.fillMaxWidth().testTag("input_admin_quiz_explanation"),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = OtakuTextPrimary,
                            unfocusedTextColor = OtakuTextPrimary,
                            focusedBorderColor = Color(0xFFFFD700),
                            unfocusedBorderColor = OtakuDarkBorder
                        ),
                        maxLines = 3
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (questionText.isNotBlank() && option1.isNotBlank() && option2.isNotBlank()) {
                        onSubmit(
                            if (universe.isBlank()) "Général" else universe,
                            selectedDifficulty,
                            questionText,
                            option1,
                            option2,
                            if (option3.isBlank()) "Option C" else option3,
                            if (option4.isBlank()) "Option D" else option4,
                            correctIndex,
                            if (explanationText.isBlank()) "Explication canonique certifiée par l'équipe administrative." else explanationText,
                            selectedXp,
                            if (adminAuthor.isBlank()) "Admin Otaku" else adminAuthor
                        )
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD700)),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.testTag("btn_submit_admin_quiz")
            ) {
                Text("Publier Officiellement 👑", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Annuler", color = OtakuTextMuted)
            }
        },
        containerColor = Color(0xFF1E1736)
    )
}

@Composable
fun ScoreAndReputationHistoryDialog(
    profile: UserProfileEntity,
    quizHistory: List<QuizHistoryEntity>,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.MilitaryTech,
                    contentDescription = null,
                    tint = OtakuTertiary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "Score & Réputation Otaku",
                        fontWeight = FontWeight.Black,
                        fontSize = 16.sp,
                        color = OtakuTextPrimary
                    )
                    Text(
                        text = "Suivi des points, paliers et historique des réponses",
                        fontSize = 11.sp,
                        color = OtakuTextSecondary
                    )
                }
            }
        },
        text = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(440.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Reputation Summary Header
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = OtakuDarkCardElevated),
                        border = androidx.compose.foundation.BorderStroke(1.dp, OtakuTertiary.copy(alpha = 0.5f))
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(profile.avatarEmoji, fontSize = 28.sp)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column {
                                        Text(profile.username, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = OtakuTextPrimary)
                                        Text(profile.reputationTier, fontWeight = FontWeight.Black, fontSize = 12.sp, color = OtakuTertiary)
                                    }
                                }

                                Column(horizontalAlignment = Alignment.End) {
                                    Text("${profile.reputationPoints} RP", fontSize = 18.sp, fontWeight = FontWeight.Black, color = OtakuTertiary)
                                    Text("${profile.quizzesSolved} résolus", fontSize = 11.sp, color = OtakuTextMuted)
                                }
                            }
                        }
                    }
                }

                // Reputation Roadmap Section
                item {
                    Text(
                        text = "🏆 Paliers de Réputation & Rangs :",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = OtakuTextPrimary
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    val tiers = listOf(
                        Triple("🍃 Initié Anime", "0 - 499 RP", profile.reputationPoints >= 0),
                        Triple("📜 Apprenti Otaku", "500 - 999 RP", profile.reputationPoints >= 500),
                        Triple("⚔️ Érudit Otaku", "1000 - 1999 RP", profile.reputationPoints >= 1000),
                        Triple("🔮 Maître des Univers", "2000 - 3499 RP", profile.reputationPoints >= 2000),
                        Triple("👑 Sage Légendaire d'Akihabara", "3500+ RP", profile.reputationPoints >= 3500)
                    )

                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        tiers.forEach { (name, range, isUnlocked) ->
                            val isCurrent = profile.reputationTier.contains(name.substring(3).trim())
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(if (isCurrent) Color(0x33FFB300) else if (isUnlocked) OtakuDarkCardElevated else Color(0x11FFFFFF))
                                    .border(
                                        width = if (isCurrent) 1.5.dp else 1.dp,
                                        color = if (isCurrent) Color(0xFFFFB300) else if (isUnlocked) OtakuDarkBorder else Color.Transparent,
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(if (isUnlocked) "✓ " else "🔒 ", fontSize = 11.sp, color = if (isUnlocked) DiffBeginner else OtakuTextMuted)
                                        Text(name, fontSize = 12.sp, fontWeight = if (isCurrent) FontWeight.Black else FontWeight.SemiBold, color = if (isCurrent) Color(0xFFFFB300) else OtakuTextPrimary)
                                    }
                                    Text(range, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = if (isCurrent) Color(0xFFFFB300) else OtakuTextMuted)
                                }
                            }
                        }
                    }
                }

                // Chronological History Section
                item {
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "📜 Historique Récent des Quiz (${quizHistory.size}) :",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = OtakuTextPrimary
                    )
                }

                if (quizHistory.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(OtakuDarkCardElevated)
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Aucun historique pour le moment.\nRéponds à tes premières questions pour bâtir ta réputation !",
                                fontSize = 12.sp,
                                color = OtakuTextMuted,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                } else {
                    items(quizHistory) { item ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(containerColor = OtakuDarkCardElevated),
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (item.isCorrect) DiffBeginner.copy(alpha = 0.3f) else OtakuDarkBorder
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                                    Text(if (item.isCorrect) "✅" else "💡", fontSize = 16.sp)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                text = item.animeSeries,
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = OtakuSecondary
                                            )
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text(
                                                text = item.timestampStr,
                                                fontSize = 10.sp,
                                                color = OtakuTextMuted
                                            )
                                        }
                                        Text(
                                            text = item.questionSummary,
                                            fontSize = 12.sp,
                                            color = OtakuTextPrimary,
                                            maxLines = 1
                                        )
                                    }
                                }

                                Column(horizontalAlignment = Alignment.End) {
                                    Text(
                                        text = "+${item.pointsEarned} RP",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Black,
                                        color = if (item.isCorrect) DiffBeginner else OtakuTertiary
                                    )
                                    Text(
                                        text = if (item.isCorrect) "Succès" else "Effort",
                                        fontSize = 10.sp,
                                        color = OtakuTextMuted
                                    )
                                }
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = OtakuSecondary),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Fermer", color = Color.White, fontWeight = FontWeight.Bold)
            }
        },
        containerColor = OtakuDarkSurface
    )
}

@Composable
fun AnimeBattleView(
    viewModel: OtakuViewModel,
    battleState: BattleState
) {
    if (!battleState.isActive) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp, bottom = 80.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = OtakuDarkSurface),
                    border = androidx.compose.foundation.BorderStroke(1.dp, OtakuPrimary.copy(alpha = 0.5f))
                ) {
                    Column {
                        // Anime Battle Banner Image
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(160.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.img_anime_battle_banner),
                                contentDescription = "Anime Battle Clash",
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
                                                Color(0xCC0D0B18),
                                                Color(0xFF17132B)
                                            )
                                        )
                                    )
                            )
                            // Top Clash Pill
                            Box(
                                modifier = Modifier
                                    .padding(12.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(Color.Black.copy(alpha = 0.75f))
                                    .border(1.dp, OtakuPrimary, RoundedCornerShape(6.dp))
                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                            ) {
                                Text(
                                    text = "⚡ 超決戦 • TOURNAMENT DUEL",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Black,
                                    color = OtakuPrimary
                                )
                            }
                        }

                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "L'Arène Anime Battle Shonen ⚔️",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Black,
                                color = OtakuTextPrimary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "« Prouve ta suprématie d'Otaku face aux rivaux du Hub en 5 rounds sous haute tension ! »",
                                fontSize = 12.sp,
                                color = OtakuTextSecondary
                            )

                            Spacer(modifier = Modifier.height(14.dp))

                            // 5 Rounds Preview
                            Text(
                                text = "PROGRAMME DES 5 ROUNDS :",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Black,
                                color = OtakuTertiary,
                                letterSpacing = 0.5.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))

                            val roundsInfo = listOf(
                                "R1: QCM Shonen & Anime Masters",
                                "R2: Reconnaissance de Personnages",
                                "R3: Répliques Cultes & Punchlines",
                                "R4: Lore, Arcs & Univers Mangas",
                                "R5: Question Boss Final (+200 XP)"
                            )

                            roundsInfo.forEachIndexed { idx, info ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(vertical = 2.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(18.dp)
                                            .clip(CircleShape)
                                            .background(if (idx == 4) OtakuPrimary else OtakuDarkCardElevated),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text("${idx + 1}", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = if (idx == 4) Color.White else OtakuTextSecondary)
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = info,
                                        fontSize = 12.sp,
                                        color = if (idx == 4) OtakuTertiary else OtakuTextSecondary,
                                        fontWeight = if (idx == 4) FontWeight.Bold else FontWeight.Normal
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(18.dp))

                            Button(
                                onClick = { viewModel.startBattle("Alex", "🦊") },
                                colors = ButtonDefaults.buttonColors(containerColor = OtakuPrimary),
                                shape = RoundedCornerShape(14.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp)
                                    .testTag("btn_launch_battle")
                            ) {
                                Text(
                                    text = "LANCER LE COMBAT SHONEN ⚔️",
                                    fontWeight = FontWeight.Black,
                                    fontSize = 14.sp,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
        return
    }

    val currentRound = battleState.rounds.getOrNull(battleState.currentRoundIndex)

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp, bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Duel Header: SACHA ⚔️ ALEX with interactive HP combat gauges
        item {
            Card(
                modifier = Modifier.fillMaxWidth().testTag("battle_hud_card"),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = OtakuDarkSurface),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, OtakuDarkBorder)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Top Duel Title
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "SACHA",
                            fontWeight = FontWeight.Black,
                            fontSize = 15.sp,
                            color = OtakuPrimary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "⚔️",
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = battleState.opponentName.uppercase(),
                            fontWeight = FontWeight.Black,
                            fontSize = 15.sp,
                            color = OtakuSecondary
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Side-by-side HP combat gauges (Health Points)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // User Left Jauge
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(34.dp)
                                        .clip(CircleShape)
                                        .background(OtakuPrimary.copy(alpha = 0.15f))
                                        .border(2.dp, OtakuPrimary, CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("⚡", fontSize = 16.sp)
                                }
                                Spacer(modifier = Modifier.width(6.dp))
                                Column {
                                    Text("Toi", fontSize = 11.sp, color = OtakuTextMuted)
                                    Text("${battleState.userHealth} / 100 PV", fontSize = 12.sp, fontWeight = FontWeight.Black, color = if (battleState.userHealth < 30) Color(0xFFEF4444) else OtakuPrimary)
                                }
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            LinearProgressIndicator(
                                progress = { (battleState.userHealth / 100f).coerceIn(0f, 1f) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(9.dp)
                                    .clip(RoundedCornerShape(5.dp)),
                                color = if (battleState.userHealth < 30) Color(0xFFEF4444) else OtakuPrimary,
                                trackColor = OtakuDarkBorder
                            )
                        }

                        // Center Round Badge
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(OtakuDarkCardElevated)
                                    .border(1.dp, OtakuTertiary.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "R${battleState.currentRoundIndex + 1}/5",
                                    fontWeight = FontWeight.Black,
                                    fontSize = 12.sp,
                                    color = OtakuTertiary
                                )
                            }
                        }

                        // Opponent Right Jauge
                        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.End) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Column(horizontalAlignment = Alignment.End) {
                                    Text(battleState.opponentName, fontSize = 11.sp, color = OtakuTextMuted)
                                    Text("${battleState.opponentHealth} / 100 PV", fontSize = 12.sp, fontWeight = FontWeight.Black, color = if (battleState.opponentHealth < 30) Color(0xFFEF4444) else OtakuSecondary)
                                }
                                Spacer(modifier = Modifier.width(6.dp))
                                Box(
                                    modifier = Modifier
                                        .size(34.dp)
                                        .clip(CircleShape)
                                        .background(OtakuSecondary.copy(alpha = 0.15f))
                                        .border(2.dp, OtakuSecondary, CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(battleState.opponentAvatar, fontSize = 16.sp)
                                }
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            LinearProgressIndicator(
                                progress = { (battleState.opponentHealth / 100f).coerceIn(0f, 1f) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(9.dp)
                                    .clip(RoundedCornerShape(5.dp)),
                                color = if (battleState.opponentHealth < 30) Color(0xFFEF4444) else OtakuSecondary,
                                trackColor = OtakuDarkBorder
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Energy Gauge (0 - 100)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("🔮 Jauge d'Énergie Shonen :", fontSize = 11.sp, color = OtakuTextMuted)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("${battleState.userEnergy} / 100", fontSize = 11.sp, fontWeight = FontWeight.Black, color = OtakuTertiary)
                        }
                        if (battleState.isUltimateReady) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(Color(0xFFF59E0B).copy(alpha = 0.2f))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text("🔥 ULTIME PRÊT !", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFFF59E0B))
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    LinearProgressIndicator(
                        progress = { (battleState.userEnergy / 100f).coerceIn(0f, 1f) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = if (battleState.isUltimateReady) Color(0xFFF59E0B) else OtakuTertiary,
                        trackColor = OtakuDarkBorder
                    )

                    // Ultimate Move Action Trigger
                    if (battleState.isUltimateReady && !battleState.isBattleFinished) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Button(
                            onClick = { viewModel.triggerUltimateBattleMove() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("btn_trigger_ultimate"),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF59E0B)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("💥 DÉCLENCHER LE COUP ULTIME SHONEN (-45 PV RIVAL)", fontSize = 12.sp, fontWeight = FontWeight.Black, color = Color.Black)
                            }
                        }
                    }

                    // Move Execution Ticker
                    battleState.lastMoveExecuted?.let { move ->
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(OtakuDarkCardElevated)
                                .padding(8.dp)
                        ) {
                            Text(
                                text = "⚔️ Dernier Coup : $move",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = OtakuTextPrimary
                            )
                        }
                    }
                }
            }
        }

        // Round Question or Battle Finish
        if (battleState.isBattleFinished) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth().testTag("battle_result_card"),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = OtakuDarkSurface),
                    border = androidx.compose.foundation.BorderStroke(2.dp, if (battleState.winner == "USER") OtakuTertiary else OtakuPrimary)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = when (battleState.winner) {
                                "USER" -> "🏆 VICTOIRE ÉCLATANTE !"
                                "OPPONENT" -> "⚔️ DÉFAITE HONORABLE !"
                                else -> "🤝 MATCH NUL !"
                            },
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black,
                            color = if (battleState.winner == "USER") OtakuTertiary else OtakuPrimary
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Score final : ${battleState.userScore} à ${battleState.opponentScore}",
                            fontSize = 15.sp,
                            color = OtakuTextPrimary,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(OtakuDarkCard)
                                .padding(12.dp)
                        ) {
                            Text(
                                text = if (battleState.winner == "USER")
                                    "⭐ Récompenses : +350 XP de combat • +1 Victoire ajoutée à ton profil !"
                                else
                                    "⭐ Récompenses : +120 XP d'effort de guerre • Continue l'entraînement !",
                                fontSize = 13.sp,
                                color = OtakuTextPrimary,
                                textAlign = TextAlign.Center
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = { viewModel.startBattle("Alex", "🦊") },
                            colors = ButtonDefaults.buttonColors(containerColor = OtakuPrimary),
                            modifier = Modifier.fillMaxWidth().testTag("btn_rematch")
                        ) {
                            Text("Prendre une Revanche ⚔️", fontWeight = FontWeight.Bold, color = Color.White)
                        }
                    }
                }
            }
        } else if (currentRound != null) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = OtakuDarkCard),
                    border = androidx.compose.foundation.BorderStroke(1.dp, OtakuDarkBorder)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = currentRound.roundTheme,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Black,
                            color = OtakuTertiary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = currentRound.question,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = OtakuTextPrimary,
                            lineHeight = 22.sp
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        currentRound.options.forEachIndexed { index, option ->
                            val isSelected = battleState.selectedOptionIndex == index
                            val isCorrect = index == currentRound.correctIndex

                            val bgColor = when {
                                !battleState.isRoundSubmitted -> OtakuDarkCardElevated
                                isCorrect -> Color(0xFFD1FAE5)
                                isSelected -> Color(0xFFFEE2E2)
                                else -> OtakuDarkCardElevated
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(bgColor)
                                    .border(
                                        1.dp,
                                        if (battleState.isRoundSubmitted && isCorrect) DiffBeginner else OtakuDarkBorder,
                                        RoundedCornerShape(12.dp)
                                    )
                                    .clickable(enabled = !battleState.isRoundSubmitted) {
                                        viewModel.submitBattleAnswer(index)
                                    }
                                    .padding(14.dp)
                                    .testTag("battle_opt_$index")
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = option,
                                        color = OtakuTextPrimary,
                                        fontSize = 13.sp,
                                        modifier = Modifier.weight(1f)
                                    )
                                    if (battleState.isRoundSubmitted) {
                                        if (isCorrect) {
                                            Text("✅ Correct", fontSize = 11.sp, color = DiffBeginner, fontWeight = FontWeight.Bold)
                                        } else if (isSelected) {
                                            Text("❌ Mauvais", fontSize = 11.sp, color = DiffMaster, fontWeight = FontWeight.Bold)
                                        }
                                    }
                                }
                            }
                        }

                        // Explanation after round submission
                        if (battleState.isRoundSubmitted) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "💡 ${currentRound.explanation}",
                                fontSize = 12.sp,
                                color = OtakuTextSecondary,
                                lineHeight = 16.sp
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Button(
                                onClick = { viewModel.nextBattleRound() },
                                modifier = Modifier.fillMaxWidth().testTag("btn_next_battle_round"),
                                colors = ButtonDefaults.buttonColors(containerColor = OtakuPrimary),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("Round Suivant >", fontWeight = FontWeight.Bold, color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CreateQuizQuestionDialog(
    onDismiss: () -> Unit,
    onSubmit: (
        universe: String,
        difficulty: String,
        question: String,
        option1: String,
        option2: String,
        option3: String,
        option4: String,
        correctOptionIndex: Int,
        explanation: String
    ) -> Unit
) {
    var universe by remember { mutableStateOf("") }
    var selectedDifficulty by remember { mutableStateOf("Débutant") }
    var questionText by remember { mutableStateOf("") }
    var option1 by remember { mutableStateOf("") }
    var option2 by remember { mutableStateOf("") }
    var option3 by remember { mutableStateOf("") }
    var option4 by remember { mutableStateOf("") }
    var correctIndex by remember { mutableIntStateOf(0) }
    var explanationText by remember { mutableStateOf("") }

    val difficulties = listOf("Débutant", "Intermédiaire", "Expert", "Maître")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column {
                Text("✍️ Proposer une Question Otaku", color = OtakuTextPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text("Partage ton savoir bienveillant (+50 XP)", color = OtakuSecondary, fontSize = 12.sp)
            }
        },
        text = {
            LazyColumn(
                modifier = Modifier.fillMaxWidth().height(360.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    OutlinedTextField(
                        value = universe,
                        onValueChange = { universe = it },
                        label = { Text("Univers (ex: One Piece, Naruto...)", color = OtakuTextMuted, fontSize = 12.sp) },
                        modifier = Modifier.fillMaxWidth().testTag("input_quiz_universe"),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = OtakuTextPrimary,
                            unfocusedTextColor = OtakuTextPrimary,
                            focusedBorderColor = OtakuSecondary,
                            unfocusedBorderColor = OtakuDarkBorder
                        ),
                        singleLine = true
                    )
                }

                item {
                    Text("Difficulté :", color = OtakuTextMuted, fontSize = 12.sp)
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        items(difficulties) { diff ->
                            val isSelected = selectedDifficulty == diff
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isSelected) OtakuSecondary else OtakuDarkCardElevated)
                                    .clickable { selectedDifficulty = diff }
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = diff,
                                    color = if (isSelected) Color.White else OtakuTextSecondary,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }

                item {
                    OutlinedTextField(
                        value = questionText,
                        onValueChange = { questionText = it },
                        label = { Text("Ta Question Otaku", color = OtakuTextMuted, fontSize = 12.sp) },
                        modifier = Modifier.fillMaxWidth().testTag("input_quiz_question"),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = OtakuTextPrimary,
                            unfocusedTextColor = OtakuTextPrimary,
                            focusedBorderColor = OtakuSecondary,
                            unfocusedBorderColor = OtakuDarkBorder
                        ),
                        maxLines = 3
                    )
                }

                item {
                    Text("4 Choix de réponse (coche la bonne réponse) :", color = OtakuTextMuted, fontSize = 11.sp)
                }

                // Option 1
                item {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(if (correctIndex == 0) DiffBeginner else OtakuDarkCardElevated)
                                .clickable { correctIndex = 0 },
                            contentAlignment = Alignment.Center
                        ) {
                            Text("A", color = if (correctIndex == 0) Color.White else OtakuTextPrimary, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        OutlinedTextField(
                            value = option1,
                            onValueChange = { option1 = it },
                            placeholder = { Text("Choix A", color = OtakuTextMuted, fontSize = 12.sp) },
                            modifier = Modifier.weight(1f).testTag("input_quiz_opt1"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = OtakuTextPrimary,
                                unfocusedTextColor = OtakuTextPrimary,
                                focusedBorderColor = OtakuSecondary,
                                unfocusedBorderColor = OtakuDarkBorder
                            ),
                            singleLine = true
                        )
                    }
                }

                // Option 2
                item {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(if (correctIndex == 1) DiffBeginner else OtakuDarkCardElevated)
                                .clickable { correctIndex = 1 },
                            contentAlignment = Alignment.Center
                        ) {
                            Text("B", color = if (correctIndex == 1) Color.White else OtakuTextPrimary, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        OutlinedTextField(
                            value = option2,
                            onValueChange = { option2 = it },
                            placeholder = { Text("Choix B", color = OtakuTextMuted, fontSize = 12.sp) },
                            modifier = Modifier.weight(1f).testTag("input_quiz_opt2"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = OtakuTextPrimary,
                                unfocusedTextColor = OtakuTextPrimary,
                                focusedBorderColor = OtakuSecondary,
                                unfocusedBorderColor = OtakuDarkBorder
                            ),
                            singleLine = true
                        )
                    }
                }

                // Option 3
                item {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(if (correctIndex == 2) DiffBeginner else OtakuDarkCardElevated)
                                .clickable { correctIndex = 2 },
                            contentAlignment = Alignment.Center
                        ) {
                            Text("C", color = if (correctIndex == 2) Color.White else OtakuTextPrimary, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        OutlinedTextField(
                            value = option3,
                            onValueChange = { option3 = it },
                            placeholder = { Text("Choix C", color = OtakuTextMuted, fontSize = 12.sp) },
                            modifier = Modifier.weight(1f).testTag("input_quiz_opt3"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = OtakuTextPrimary,
                                unfocusedTextColor = OtakuTextPrimary,
                                focusedBorderColor = OtakuSecondary,
                                unfocusedBorderColor = OtakuDarkBorder
                            ),
                            singleLine = true
                        )
                    }
                }

                // Option 4
                item {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(if (correctIndex == 3) DiffBeginner else OtakuDarkCardElevated)
                                .clickable { correctIndex = 3 },
                            contentAlignment = Alignment.Center
                        ) {
                            Text("D", color = if (correctIndex == 3) Color.White else OtakuTextPrimary, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        OutlinedTextField(
                            value = option4,
                            onValueChange = { option4 = it },
                            placeholder = { Text("Choix D", color = OtakuTextMuted, fontSize = 12.sp) },
                            modifier = Modifier.weight(1f).testTag("input_quiz_opt4"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = OtakuTextPrimary,
                                unfocusedTextColor = OtakuTextPrimary,
                                focusedBorderColor = OtakuSecondary,
                                unfocusedBorderColor = OtakuDarkBorder
                            ),
                            singleLine = true
                        )
                    }
                }

                item {
                    OutlinedTextField(
                        value = explanationText,
                        onValueChange = { explanationText = it },
                        label = { Text("Explication pédagogique & Anecdote 💡", color = OtakuTextMuted, fontSize = 12.sp) },
                        modifier = Modifier.fillMaxWidth().testTag("input_quiz_explanation"),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = OtakuTextPrimary,
                            unfocusedTextColor = OtakuTextPrimary,
                            focusedBorderColor = OtakuSecondary,
                            unfocusedBorderColor = OtakuDarkBorder
                        ),
                        maxLines = 3
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (questionText.isNotBlank() && option1.isNotBlank() && option2.isNotBlank()) {
                        onSubmit(
                            if (universe.isBlank()) "Général" else universe,
                            selectedDifficulty,
                            questionText,
                            option1,
                            option2,
                            if (option3.isBlank()) "Choix C" else option3,
                            if (option4.isBlank()) "Choix D" else option4,
                            correctIndex,
                            if (explanationText.isBlank()) "Bravo pour cette réponse !" else explanationText
                        )
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = OtakuSecondary),
                modifier = Modifier.testTag("btn_submit_new_quiz")
            ) {
                Text("Publier la question (+50 XP)", color = Color.White, fontWeight = FontWeight.Bold)
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

@Composable
fun AnimeDebatesView(viewModel: OtakuViewModel) {
    val debates by viewModel.debates.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp, bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Banner Header
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = OtakuDarkSurface)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(Brush.radialGradient(listOf(OtakuPrimary, OtakuTertiary))),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Forum,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column {
                        Text(
                            text = "GRANDS DÉBATS & VERDICTS CANONIQUES 🔥",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Black,
                            color = OtakuTertiary,
                            letterSpacing = 0.6.sp
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Vote sur les affrontements et dilemmes mythiques, puis consulte l'arbitrage incollable des maîtres Otaku !",
                            fontSize = 12.sp,
                            color = OtakuTextSecondary,
                            lineHeight = 16.sp
                        )
                    }
                }
            }
        }

        // List of Debate Cards
        items(debates, key = { it.id }) { debate ->
            AnimeDebateCard(
                debate = debate,
                onVote = { optionIndex ->
                    viewModel.voteDebate(debate.id, optionIndex)
                }
            )
        }
    }
}

@Composable
fun AnimeDebateCard(
    debate: AnimeDebateItem,
    onVote: (Int) -> Unit
) {
    var isVerdictExpanded by remember { mutableStateOf(debate.userVote != null) }
    val totalVotes = (debate.votesA + debate.votesB).coerceAtLeast(1)
    val percentA = ((debate.votesA.toFloat() / totalVotes) * 100).toInt()
    val percentB = 100 - percentA

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("debate_card_${debate.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = OtakuDarkSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, OtakuDarkBorder)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Universe badge & total votes
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0x22BD00FF))
                        .border(1.dp, OtakuTertiary.copy(alpha = 0.6f), RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = debate.universe.uppercase(),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black,
                        color = OtakuTertiary,
                        letterSpacing = 0.5.sp
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.HowToVote,
                        contentDescription = null,
                        tint = OtakuTextMuted,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "$totalVotes votes",
                        fontSize = 11.sp,
                        color = OtakuTextMuted,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = debate.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Black,
                color = OtakuTextPrimary,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = debate.premise,
                fontSize = 13.sp,
                color = OtakuTextSecondary,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Option A Box
            val isSelectedA = debate.userVote == 0
            val hasVoted = debate.userVote != null

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(if (isSelectedA) Color(0x3305D9E8) else OtakuDarkCardElevated)
                    .border(
                        width = if (isSelectedA) 2.dp else 1.dp,
                        color = if (isSelectedA) OtakuSecondary else OtakuDarkBorder,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable(enabled = !hasVoted) {
                        onVote(0)
                        isVerdictExpanded = true
                    }
                    .padding(12.dp)
                    .testTag("debate_${debate.id}_option_a")
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "A. ",
                                fontWeight = FontWeight.Black,
                                fontSize = 13.sp,
                                color = OtakuSecondary
                            )
                            Text(
                                text = debate.optionA,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = OtakuTextPrimary
                            )
                        }
                        if (hasVoted) {
                            Text(
                                text = "$percentA%",
                                fontWeight = FontWeight.Black,
                                fontSize = 13.sp,
                                color = OtakuSecondary
                            )
                        }
                    }

                    if (hasVoted) {
                        Spacer(modifier = Modifier.height(6.dp))
                        LinearProgressIndicator(
                            progress = { percentA / 100f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = OtakuSecondary,
                            trackColor = OtakuDarkBorder
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Option B Box
            val isSelectedB = debate.userVote == 1

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(if (isSelectedB) Color(0x33FF2A85) else OtakuDarkCardElevated)
                    .border(
                        width = if (isSelectedB) 2.dp else 1.dp,
                        color = if (isSelectedB) OtakuPrimary else OtakuDarkBorder,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable(enabled = !hasVoted) {
                        onVote(1)
                        isVerdictExpanded = true
                    }
                    .padding(12.dp)
                    .testTag("debate_${debate.id}_option_b")
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "B. ",
                                fontWeight = FontWeight.Black,
                                fontSize = 13.sp,
                                color = OtakuPrimary
                            )
                            Text(
                                text = debate.optionB,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = OtakuTextPrimary
                            )
                        }
                        if (hasVoted) {
                            Text(
                                text = "$percentB%",
                                fontWeight = FontWeight.Black,
                                fontSize = 13.sp,
                                color = OtakuPrimary
                            )
                        }
                    }

                    if (hasVoted) {
                        Spacer(modifier = Modifier.height(6.dp))
                        LinearProgressIndicator(
                            progress = { percentB / 100f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = OtakuPrimary,
                            trackColor = OtakuDarkBorder
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Toggle Verdict Button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isVerdictExpanded = !isVerdictExpanded }
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Gavel,
                        contentDescription = null,
                        tint = OtakuTertiary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (isVerdictExpanded) "Masquer l'Arbitrage Canonique" else "Consulter l'Arbitrage Incollable 📜",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = OtakuTertiary
                    )
                }

                Text(
                    text = if (isVerdictExpanded) "▲" else "▼",
                    fontSize = 11.sp,
                    color = OtakuTertiary
                )
            }

            // Expandable Canon Verdict
            AnimatedVisibility(visible = isVerdictExpanded) {
                Box(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF13111E))
                        .border(1.dp, OtakuTertiary.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                        .padding(14.dp)
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Gavel,
                                contentDescription = null,
                                tint = OtakuTertiary,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "ANALYSE DE LORE & RÈGLES DE L'UNIVERS",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Black,
                                color = OtakuTertiary,
                                letterSpacing = 0.5.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = debate.canonVerdict,
                            fontSize = 12.sp,
                            color = OtakuTextPrimary,
                            lineHeight = 18.sp
                        )
                    }
                }
            }
        }
    }
}
