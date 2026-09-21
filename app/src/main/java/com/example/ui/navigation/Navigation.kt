package com.example.ui.navigation

sealed class Screen(val route: String, val title: String) {
    object Home : Screen("home", "Accueil")
    object Clubs : Screen("clubs", "Clubs")
    object Quiz : Screen("quiz", "Quiz & Duel")
    object Leaderboard : Screen("leaderboard", "Classement")
    object Profile : Screen("profile", "Profil")
    object Events : Screen("events", "Événements")
    object Notifications : Screen("notifications", "Notifications")
    object Search : Screen("search", "Découverte")
    object Portals : Screen("portals", "La Porte des Mondes")
    object Room : Screen("room", "Chambre Otaku")
}
