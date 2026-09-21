package com.example.ui.i18n

import androidx.compose.runtime.compositionLocalOf

data class AppStrings(
    // App & Navigation
    val appName: String,
    val navHome: String,
    val navClubs: String,
    val navQuiz: String,
    val navLeaderboard: String,
    val navProfile: String,
    val navEvents: String,
    val navNotifications: String,
    val navSearch: String,

    // Language Dialog
    val languageDialogTitle: String,
    val languageDialogSubtitle: String,
    val languageApplied: String,

    // Auth & Social Sign-In
    val authTitle: String,
    val authSubtitle: String,
    val tabSignIn: String,
    val tabSignUp: String,
    val continueWithGoogle: String,
    val continueWithFacebook: String,
    val continueWithMessenger: String,
    val continueWithWeChat: String,
    val orWithEmail: String,
    val continueAsGuest: String,
    val usernameLabel: String,
    val emailLabel: String,
    val passwordLabel: String,
    val btnSignIn: String,
    val btnSignUp: String,
    val accountConnectedSuccess: String,
    val linkedAccountsTitle: String,
    val linkedStatusConnected: String,
    val linkedStatusNotConnected: String,
    val btnConnect: String,
    val btnDisconnect: String,
    val btnSwitchAccount: String,
    val authGuestNotice: String,

    // Home Screen
    val homeHeroBadge: String,
    val homeHeroTitle: String,
    val homeHeroSubtitle: String,
    val homeHeroQuote: String,
    val btnStartBattle: String,
    val btnPracticeQuiz: String,
    val btnSharePost: String,
    val filterAll: String,
    val filterTheory: String,
    val filterDiscovery: String,
    val filterFanArt: String,
    val filterNews: String,
    val filterPoll: String,
    val postDialogTitle: String,
    val postPlaceholder: String,
    val postClubLabel: String,
    val postCategoryLabel: String,
    val btnPublish: String,
    val btnCancel: String,
    val commentsTitle: String,
    val commentPlaceholder: String,
    val btnComment: String,
    val likeCount: String,
    val commentCount: String,

    // Clubs Screen
    val clubsHeaderTitle: String,
    val clubsHeaderSubtitle: String,
    val searchClubsPlaceholder: String,
    val btnCreateClub: String,
    val clubMembers: String,
    val clubLevel: String,
    val btnJoinClub: String,
    val btnLeaveClub: String,
    val btnJoined: String,
    val createClubDialogTitle: String,
    val clubNameLabel: String,
    val clubUniverseLabel: String,
    val clubDescriptionLabel: String,
    val clubTagsLabel: String,
    val btnConfirmCreate: String,

    // Quiz & Battle Screen
    val tabPractice: String,
    val tabBattle: String,
    val tabSubmitQuestion: String,
    val diffAll: String,
    val diffBeginner: String,
    val diffIntermediate: String,
    val diffExpert: String,
    val diffMaster: String,
    val quizQuestionCounter: String,
    val btnCheckAnswer: String,
    val btnNextQuestion: String,
    val quizCorrect: String,
    val quizIncorrect: String,
    val quizExplanationHeader: String,
    val battleRoundTitle: String,
    val battleYourScore: String,
    val battleRivalScore: String,
    val battleVictory: String,
    val battleDefeat: String,
    val battleDraw: String,
    val btnContinueBattle: String,
    val btnNewBattle: String,
    val submitQuestionTitle: String,
    val questionInputLabel: String,
    val optionCorrectLabel: String,
    val optionIncorrectLabel: String,
    val explanationInputLabel: String,

    // Leaderboard Screen
    val leaderboardTitle: String,
    val tabGlobal: String,
    val tabClubs: String,
    val tabWeekly: String,
    val tabQuizMasters: String,
    val tabBattleChamps: String,
    val myRankingHeader: String,

    // Profile Screen
    val profileTitle: String,
    val btnCustomizeProfile: String,
    val statAnimesFollowed: String,
    val statMangasRead: String,
    val statBattlesWon: String,
    val statQuizzesSolved: String,
    val favoriteAnimesHeader: String,
    val favoriteCharactersHeader: String,
    val activityHistoryHeader: String,
    val badgesHeader: String,
    val selectLanguageHeader: String,
    val customizeDialogTitle: String,
    val avatarChoiceLabel: String,
    val frameChoiceLabel: String,
    val bannerChoiceLabel: String,
    val rankChoiceLabel: String,
    val bioLabel: String,
    val btnSave: String,

    // Notifications & Events
    val markAllRead: String,
    val noNotifications: String,
    val eventsHeader: String,
    val btnRegisterEvent: String,
    val btnRegisteredEvent: String,
    val searchHeader: String,
    val searchPlaceholder: String
)

val LocalOtakuStrings = compositionLocalOf<AppStrings> {
    StringsFr
}

fun getStringsForLanguage(language: AppLanguage): AppStrings {
    return when (language) {
        AppLanguage.FRENCH -> StringsFr
        AppLanguage.ENGLISH -> StringsEn
        AppLanguage.JAPANESE -> StringsJa
        AppLanguage.KOREAN -> StringsKo
        AppLanguage.CHINESE -> StringsZh
    }
}

