package com.example.data.model

import androidx.compose.ui.graphics.Color

enum class DistrictType {
    DOWNTOWN,
    ANCIENT_DISTRICT,
    BATTLE_ARENA,
    ACADEMY,
    OTAKU_MARKET,
    ARCADE,
    TOURNAMENT_STADIUM,
    PORTAL_GATE,
    PERSONAL_ROOM,
    COMMUNITY_PARK
}

data class NexusDistrict(
    val type: DistrictType,
    val name: String,
    val japaneseName: String,
    val icon: String,
    val subtitle: String,
    val description: String,
    val activePlayersCount: Int,
    val currentActivity: String,
    val secretsCount: Int,
    val badgeTag: String
)

enum class OtakuArchetype(
    val title: String,
    val icon: String,
    val role: String,
    val bonusDescription: String,
    val recommendedDistrict: DistrictType
) {
    FIGHTER("Guerrier Shonen", "⚔️", "Fighter", "+25% Dégâts en Arène des Champions", DistrictType.BATTLE_ARENA),
    STRATEGIST("Stratège Seinen", "🧠", "Strategist", "+30% XP sur les quiz tactiques & débats", DistrictType.ACADEMY),
    EXPLORER("Explorateur Astral", "🔮", "Explorer", "Détecte les secrets cachés des 6 Mondes", DistrictType.PORTAL_GATE),
    CREATOR("Artiste Créateur", "🎨", "Creator", "Bonus de notoriété pour les fanarts & théories", DistrictType.COMMUNITY_PARK),
    COMPETITOR("Champion d'Élite", "🏆", "Competitor", "+50% points dans les tournois mondiaux", DistrictType.TOURNAMENT_STADIUM),
    COLLECTOR("Grand Archiviste", "📚", "Collector", "Débloque des trophées et reliques rares", DistrictType.PERSONAL_ROOM),
    SOCIAL("Pilier de Guilde", "🤝", "Social", "Bonus de lien d'amitié & boost de club", DistrictType.DOWNTOWN)
}

data class AnimeWorldPortal(
    val id: String,
    val name: String,
    val title: String,
    val genre: String,
    val icon: String,
    val themeColor: Long,
    val description: String,
    val loreSnippet: String,
    val guardianName: String,
    val isUnlocked: Boolean,
    val completionPercent: Int,
    val relicsFound: Int,
    val totalRelics: Int,
    val activeAnomaly: String? = null
)

enum class RoomItemCategory {
    POSTER,
    FIGURE,
    MANGA,
    CONSOLE,
    TROPHY,
    NEON
}

data class OtakuRoomItem(
    val id: String,
    val category: RoomItemCategory,
    val name: String,
    val icon: String,
    val animeReference: String,
    val isPlaced: Boolean,
    val isUnlocked: Boolean,
    val price: Int = 0
)

data class NexusMarketItem(
    val id: String,
    val name: String,
    val category: String, // "Aura", "Titre", "Déco Chambre", "Effet"
    val icon: String,
    val description: String,
    val priceCoins: Int,
    val isPurchased: Boolean,
    val isEquipped: Boolean = false
)

data class WorldSecret(
    val id: String,
    val districtType: DistrictType,
    val title: String,
    val hint: String,
    val loreReward: String,
    val xpReward: Int,
    val isDiscovered: Boolean
)

data class ChronicleMilestone(
    val id: String,
    val title: String,
    val dateDisplay: String,
    val category: String,
    val icon: String,
    val narrativeSnippet: String
)

data class CompanionDialogue(
    val id: String,
    val speaker: String = "Aiko",
    val avatar: String = "🌸",
    val title: String,
    val message: String,
    val suggestionRoute: DistrictType? = null,
    val actionLabel: String? = null
)

data class CinematicSceneState(
    val title: String,
    val chapter: String,
    val speakerName: String,
    val speakerTitle: String,
    val speakerAvatar: String,
    val dialogueText: String,
    val choiceA: String,
    val choiceB: String,
    val consequenceA: String,
    val consequenceB: String
)

