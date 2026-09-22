package com.example.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.R
import com.example.ui.components.LanguageSelectionDialog
import com.example.ui.components.LevelUpCelebrationDialog
import com.example.ui.components.OtakuBottomBar
import com.example.ui.components.OtakuTopBar
import com.example.ui.components.XpFloatingToast
import com.example.ui.i18n.LocalOtakuStrings
import com.example.ui.i18n.getStringsForLanguage
import com.example.ui.navigation.Screen
import com.example.ui.screens.ClubsScreen
import com.example.ui.screens.EventsScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LeaderboardScreen
import com.example.ui.screens.NotificationsScreen
import com.example.ui.screens.OtakuRoomScreen
import com.example.ui.screens.PortalsScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.QuizScreen
import com.example.ui.screens.SearchScreen

@Composable
fun MainAppScaffold(viewModel: OtakuViewModel) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: Screen.Home.route

    val userProfile by viewModel.userProfile.collectAsStateWithLifecycle()
    val clubs by viewModel.clubs.collectAsStateWithLifecycle()
    val posts by viewModel.posts.collectAsStateWithLifecycle()
    val questions by viewModel.questions.collectAsStateWithLifecycle()
    val events by viewModel.events.collectAsStateWithLifecycle()
    val badges by viewModel.badges.collectAsStateWithLifecycle()
    val notifications by viewModel.notifications.collectAsStateWithLifecycle()

    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val categoryFilter by viewModel.homeCategoryFilter.collectAsStateWithLifecycle()
    val leaderboardTab by viewModel.leaderboardTabIndex.collectAsStateWithLifecycle()
    val quizState by viewModel.quizState.collectAsStateWithLifecycle()
    val battleState by viewModel.battleState.collectAsStateWithLifecycle()

    val xpToastEvent by viewModel.xpToastEvent.collectAsStateWithLifecycle()
    val levelUpEvent by viewModel.levelUpEvent.collectAsStateWithLifecycle()
    val currentLanguage by viewModel.currentLanguage.collectAsStateWithLifecycle()
    val strings = getStringsForLanguage(currentLanguage)
    var showLanguageDialog by remember { mutableStateOf(false) }

    val unreadNotificationsCount = notifications.count { !it.isRead }

    val isTopLevelRoute = currentRoute in listOf(
        Screen.Home.route,
        Screen.Portals.route,
        Screen.Quiz.route,
        Screen.Room.route,
        Screen.Profile.route
    )

    val topBarTitle = when (currentRoute) {
        Screen.Home.route -> strings.navHome
        Screen.Portals.route -> strings.navPortals
        Screen.Room.route -> strings.navRoom
        Screen.Clubs.route -> strings.navClubs
        Screen.Quiz.route -> strings.navQuiz
        Screen.Leaderboard.route -> strings.navLeaderboard
        Screen.Profile.route -> strings.navProfile
        Screen.Events.route -> strings.navEvents
        Screen.Notifications.route -> strings.navNotifications
        Screen.Search.route -> strings.navSearch
        else -> strings.appName
    }

    CompositionLocalProvider(LocalOtakuStrings provides strings) {
        Box(modifier = Modifier.fillMaxSize()) {
            // App Wallpaper Background (Glowing lavender ribbon & crystal sakura petals)
            Image(
                painter = painterResource(id = R.drawable.img_app_background),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Atmospheric Overlay to ensure high contrast, readability & deep anime aesthetic
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xBF0A0716), // 75% dark violet
                                Color(0xD90D0B18), // 85% dark violet
                                Color(0xEE090714)  // 93% dark violet
                            )
                        )
                    )
            )

            Scaffold(
                containerColor = Color.Transparent,
                topBar = {
                OtakuTopBar(
                    title = topBarTitle,
                    canNavigateBack = !isTopLevelRoute,
                    unreadNotificationsCount = unreadNotificationsCount,
                    currentLanguageFlag = currentLanguage.flag,
                    currentRoute = currentRoute,
                    onOpenLanguage = { showLanguageDialog = true },
                    onNavigateBack = { navController.popBackStack() },
                    onOpenSearch = { navController.navigate(Screen.Search.route) },
                    onOpenEvents = { navController.navigate(Screen.Events.route) },
                    onOpenNotifications = { navController.navigate(Screen.Notifications.route) }
                )
            },
        bottomBar = {
            if (isTopLevelRoute) {
                OtakuBottomBar(
                    currentRoute = currentRoute,
                    onNavigateToRoute = { route ->
                        if (route != currentRoute) {
                            navController.navigate(route) {
                                popUpTo(Screen.Home.route) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route
            ) {
                composable(Screen.Home.route) {
                    HomeScreen(
                        viewModel = viewModel,
                        onNavigateToQuiz = {
                            navController.navigate(Screen.Quiz.route) {
                                popUpTo(Screen.Home.route) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        onNavigateToEvents = { navController.navigate(Screen.Events.route) },
                        onNavigateToClubs = {
                            navController.navigate(Screen.Clubs.route) {
                                popUpTo(Screen.Home.route) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        onNavigateToLeaderboard = {
                            navController.navigate(Screen.Leaderboard.route) {
                                popUpTo(Screen.Home.route) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        onNavigateToProfile = {
                            navController.navigate(Screen.Profile.route) {
                                popUpTo(Screen.Home.route) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        onStartBattle = {
                            viewModel.startBattle("Alex", "🦊")
                            navController.navigate(Screen.Quiz.route) {
                                popUpTo(Screen.Home.route) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        onNavigateToPortals = {
                            navController.navigate(Screen.Portals.route) {
                                popUpTo(Screen.Home.route) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        onNavigateToRoom = {
                            navController.navigate(Screen.Room.route) {
                                popUpTo(Screen.Home.route) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        posts = posts,
                        categoryFilter = categoryFilter
                    )
                }

                composable(Screen.Portals.route) {
                    PortalsScreen(
                        viewModel = viewModel,
                        onNavigateToArena = {
                            viewModel.startBattle("Alex", "🦊")
                            navController.navigate(Screen.Quiz.route)
                        }
                    )
                }

                composable(Screen.Room.route) {
                    OtakuRoomScreen(viewModel = viewModel)
                }

                composable(Screen.Clubs.route) {
                    ClubsScreen(
                        viewModel = viewModel,
                        clubs = clubs
                    )
                }

                composable(Screen.Quiz.route) {
                    QuizScreen(
                        viewModel = viewModel,
                        questions = questions,
                        quizState = quizState,
                        battleState = battleState
                    )
                }

                composable(Screen.Leaderboard.route) {
                    LeaderboardScreen(
                        viewModel = viewModel,
                        selectedTab = leaderboardTab
                    )
                }

                composable(Screen.Profile.route) {
                    ProfileScreen(
                        viewModel = viewModel,
                        profile = userProfile,
                        badges = badges
                    )
                }

                composable(Screen.Events.route) {
                    EventsScreen(
                        viewModel = viewModel,
                        events = events
                    )
                }

                composable(Screen.Notifications.route) {
                    NotificationsScreen(
                        viewModel = viewModel,
                        notifications = notifications
                    )
                }

                composable(Screen.Search.route) {
                    SearchScreen(
                        viewModel = viewModel,
                        searchQuery = searchQuery,
                        clubs = clubs,
                        posts = posts,
                        events = events
                    )
                }
            }

            // Floating satisfaction XP feedback
            XpFloatingToast(
                event = xpToastEvent,
                onDismiss = { viewModel.dismissXpToast() },
                modifier = Modifier.align(Alignment.TopCenter)
            )

            // Epic Shonen Level Up Celebration
            LevelUpCelebrationDialog(
                event = levelUpEvent,
                onDismiss = { viewModel.dismissLevelUpDialog() }
            )
        }
    }
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
}
