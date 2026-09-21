package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.InitialData
import com.example.data.model.BadgeEntity
import com.example.data.model.ClubEntity
import com.example.data.model.CommentEntity
import com.example.data.model.EventEntity
import com.example.data.model.LeaderboardUser
import com.example.data.model.NotificationItemEntity
import com.example.data.model.PostEntity
import com.example.data.model.QuizQuestionEntity
import com.example.data.model.*
import com.example.data.repository.OtakuRepository
import com.example.ui.i18n.AppLanguage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class SocialAccountInfo(
    val providerId: String,
    val providerName: String,
    val iconEmoji: String,
    val isConnected: Boolean,
    val connectedUsername: String? = null
)

data class AnimeDebateItem(
    val id: Int,
    val universe: String,
    val title: String,
    val premise: String,
    val optionA: String,
    val votesA: Int,
    val optionB: String,
    val votesB: Int,
    val canonVerdict: String,
    val userVote: Int? = null // 0 for Option A, 1 for Option B
)

data class XpToastEvent(
    val amount: Int,
    val label: String,
    val timestamp: Long = System.currentTimeMillis()
)

data class LevelUpDialogEvent(
    val newLevel: Int,
    val newRank: String
)

data class BattleRound(
    val roundNumber: Int,
    val roundTheme: String, // "Connaissances", "Personnages", "Univers", "Rapidité", "Question Spéciale"
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
)

data class BattleState(
    val isActive: Boolean = false,
    val opponentName: String = "Alex",
    val opponentAvatar: String = "🦊",
    val opponentRank: String = "Rival Shonen",
    val currentRoundIndex: Int = 0,
    val rounds: List<BattleRound> = emptyList(),
    val userScore: Int = 0,
    val opponentScore: Int = 0,
    val selectedOptionIndex: Int? = null,
    val opponentSelectedOptionIndex: Int? = null,
    val isRoundSubmitted: Boolean = false,
    val isBattleFinished: Boolean = false,
    val winner: String? = null, // "USER", "OPPONENT", "DRAW"
    val userHealth: Int = 100,
    val opponentHealth: Int = 100,
    val userEnergy: Int = 35, // 0..100
    val lastMoveExecuted: String? = null,
    val isUltimateReady: Boolean = false
)


data class QuizPracticeState(
    val activeDifficulty: String = "Tous",
    val selectedUniverse: String = "Tous",
    val searchQuery: String = "",
    val currentIndex: Int = 0,
    val selectedOptionIndex: Int? = null,
    val isAnswered: Boolean = false,
    val isCorrect: Boolean = false,
    val correctCount: Int = 0,
    val totalAnswered: Int = 0,
    val xpEarnedSession: Int = 0,
    val currentStreak: Int = 0,
    val bestStreak: Int = 0,
    val isFetchingTrivia: Boolean = false,
    val fetchTriviaStatusMessage: String? = null,
    val isAdminMode: Boolean = true,
    val filterOnlyAdminCreated: Boolean = false
)

class OtakuViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: OtakuRepository

    val userProfile: StateFlow<UserProfileEntity>
    val clubs: StateFlow<List<ClubEntity>>
    val posts: StateFlow<List<PostEntity>>
    val questions: StateFlow<List<QuizQuestionEntity>>
    val quizHistory: StateFlow<List<QuizHistoryEntity>>
    val events: StateFlow<List<EventEntity>>
    val badges: StateFlow<List<BadgeEntity>>
    val notifications: StateFlow<List<NotificationItemEntity>>

    // Search query & filter
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _homeCategoryFilter = MutableStateFlow("Tous")
    val homeCategoryFilter = _homeCategoryFilter.asStateFlow()

    private val _leaderboardTabIndex = MutableStateFlow(0)
    val leaderboardTabIndex = _leaderboardTabIndex.asStateFlow()

    // Interactive Quiz practice state
    private val _quizState = MutableStateFlow(QuizPracticeState())
    val quizState = _quizState.asStateFlow()

    // Anime Battle State
    private val _battleState = MutableStateFlow(BattleState())
    val battleState = _battleState.asStateFlow()

    // Current App Language
    private val _currentLanguage = MutableStateFlow(AppLanguage.FRENCH)
    val currentLanguage = _currentLanguage.asStateFlow()

    // Social Accounts Connection State (Google, Facebook, Messenger, WeChat)
    private val _socialAccounts = MutableStateFlow(
        listOf(
            SocialAccountInfo("google", "Google Account", "🔴", false, null),
            SocialAccountInfo("facebook", "Facebook", "🔵", false, null),
            SocialAccountInfo("messenger", "Messenger", "💬", false, null),
            SocialAccountInfo("wechat", "WeChat", "🟢", false, null)
        )
    )
    val socialAccounts = _socialAccounts.asStateFlow()

    // Community Anime Debates & Lore Deep Dive
    private val _debates = MutableStateFlow(
        listOf(
            AnimeDebateItem(
                id = 1,
                universe = "Jujutsu Kaisen",
                title = "Gojo vs Sukuna (Sans les Dix Ombres / Mahoraga)",
                premise = "Satoru Gojo aurait-il triomphé de Ryomen Sukuna si ce dernier n'avait pas possédé le corps de Megumi Fushiguro et l'adaptation de Mahoraga pour percer l'Infini ?",
                optionA = "Victoire de Gojo (L'Infini impénétrable sans modèle de coupe spatiale)",
                votesA = 742,
                optionB = "Victoire de Sukuna (Forme Heian originelle à 4 bras & Amplification continue)",
                votesB = 698,
                canonVerdict = "Verdict de l'Arbitre Otaku ⚖️ : Sukuna a lui-même reconnu que l'adaptation de Mahoraga lui a servi de modèle pour concevoir la 'Coupe qui fend le Monde' (World Splitting Dismantle). Sans cette clé géométrique, il ne pouvait franchir l'Infini en combat direct hors barrière. Cependant, dans sa véritable forme Heian à 4 bras et 2 bouches, sa supériorité au corps-à-corps lors des 3 minutes de collision de domaines aurait été redoutable. Débat technique au sommet, avec un léger ascendant pour Gojo !"
            ),
            AnimeDebateItem(
                id = 2,
                universe = "Naruto Shippuden",
                title = "Itachi Uchiha vs Pain (Nagato à 100% de santé)",
                premise = "Un Itachi Uchiha au sommet de sa santé sans maladie pulmonaire pouvait-il terrasser en solitaire les Six Chemins de Pain ?",
                optionA = "Victoire d'Itachi (Épée de Totsuka + Miroir de Yata + Génie tactique)",
                votesA = 580,
                optionB = "Victoire de Pain (Vision partagée 360°, Gakidô absorbeur & Chibaku Tensei)",
                votesB = 812,
                canonVerdict = "Verdict de l'Arbitre Otaku ⚖️ : Nagato possède un contre canonique absolu : le Rinnegan immunise contre les illusions visuelles comme le Tsukuyomi, et la Voie Gakidô absorbe immédiatement l'Amaterasu. Lors de la 4e Guerre Ninja, Itachi a dû coordonner son Magatama avec le Rasenshuriken de Naruto et la Bijuu Dama de Killer Bee pour détruire le cœur du Chibaku Tensei. Seul, son Susanoo n'aurait pas pu échapper à l'écrasement gravitationnel."
            ),
            AnimeDebateItem(
                id = 3,
                universe = "Attack on Titan",
                title = "Eren Jaeger : Y avait-il une autre issue que le Grand Terrassement ?",
                premise = "Face à la déclaration de guerre globale de Willy Tybur et à la haine séculaire du monde, le Grand Terrassement était-il l'unique moyen d'assurer la survie de Paradis ?",
                optionA = "Aucune alternative réaliste (Déterminisme des Sentiers & haine irréconciliable)",
                votesA = 915,
                optionB = "Le plan des 50 ans d'Armin (Dissuasion partielle & progrès technologique)",
                votesB = 430,
                canonVerdict = "Verdict de l'Arbitre Otaku ⚖️ : Eren était contraint par la boucle causale du Titan Assaillant. De plus, le plan des 50 ans exigeait de transformer Historia et ses enfants en bétail reproducteur de Titans, ce qu'Eren refusait catégoriquement. Avec l'essor imminent de l'aviation militaire mondiale qui allait rendre les Titans obsolètes, Eren a préféré arracher la liberté du monde extérieur pour protéger ses proches jusqu'à leur dernier souffle."
            ),
            AnimeDebateItem(
                id = 4,
                universe = "One Piece",
                title = "Kaido : Le Haki surpasse-t-il réellement tous les Fruits du Démon ?",
                premise = "La célèbre maxime de Kaido 'Seul le Haki transcende tout' est-elle absolue face aux pouvoirs d'éveil ultimes comme Nika ou les Séraphins ?",
                optionA = "Oui, le Haki est le plafond absolu (Roger régnait sans fruit, Law annule les fruits)",
                votesA = 1040,
                optionB = "Non, l'Éveil des Fruits altère directement les lois physiques de l'univers",
                votesB = 320,
                canonVerdict = "Verdict de l'Arbitre Otaku ⚖️ : Gol D. Roger a conquis le monde sans le moindre pouvoir de fruit du démon. Sur Onigashima et Egghead, Trafalgar Law démontre qu'une puissante concentration de Haki dissipe instantanément les altérations corporelles causées par des Paramecia. Cependant, c'est la combinaison synergique du Haki des Rois infusé ET de l'éveil du Zoan Divin Nika (Bajrang Gun) qui a terrassé Kaido. Le Haki est le roi, mais l'éveil est l'accélérateur ultime !"
            ),
            AnimeDebateItem(
                id = 5,
                universe = "Hunter x Hunter",
                title = "Meruem vs Netero : L'humanité est-elle le véritable monstre ?",
                premise = "L'emploi de la bombe atomique toxique (Rose Miniature) par Isaac Netero prouve-t-il que la malice humaine est le prédateur suprême ?",
                optionA = "Oui, l'infinie cruauté industrielle de l'humanité dépasse l'ordre naturel",
                votesA = 870,
                optionB = "Non, c'était un acte de survie sacrificiel pour sauver notre espèce",
                votesB = 410,
                canonVerdict = "Verdict de l'Arbitre Otaku ⚖️ : Les derniers mots de Netero résument le génie thématique de Yoshihiro Togashi : 'Tu ne sais rien de l'infinie malice de l'espèce humaine.' Alors que Meruem s'ouvrait à la poésie et à l'amour grâce à Komugi, les humains ont usé d'une arme bactériologique et radioactive à bas coût pour exterminer un être qu'aucun art martial n'avait pu dompter. L'humanité est consacrée comme le véritable monstre indétrônable."
            )
        )
    )
    val debates = _debates.asStateFlow()

    // --- NEXUS CITY : THE ANIME WORLD STATE ---
    private val _selectedDistrict = MutableStateFlow<DistrictType?>(null)
    val selectedDistrict = _selectedDistrict.asStateFlow()

    private val _otakuCoins = MutableStateFlow(1650)
    val otakuCoins = _otakuCoins.asStateFlow()

    private val _userArchetype = MutableStateFlow(OtakuArchetype.FIGHTER)
    val userArchetype = _userArchetype.asStateFlow()

    private val _userOrigin = MutableStateFlow("Cité Flottante d'Astralis")
    val userOrigin = _userOrigin.asStateFlow()

    private val _combatStyle = MutableStateFlow("Lame Céleste & Déduction Tactique")
    val combatStyle = _combatStyle.asStateFlow()

    private val _userTraits = MutableStateFlow(listOf("Protecteur des Guildes", "Chasseur d'Énigmes", "Fervent du Shonen"))
    val userTraits = _userTraits.asStateFlow()

    private val _districts = MutableStateFlow(
        listOf(
            NexusDistrict(
                type = DistrictType.DOWNTOWN,
                name = "Centre-Ville de Nexus",
                japaneseName = "都心",
                icon = "🏙️",
                subtitle = "Le Cœur Palpitant de la Cité",
                description = "Rencontres d'Otakus en temps réel, rumeurs de couloirs, panneau d'affichage des guildes et tableau des défis quotidiens.",
                activePlayersCount = 3420,
                currentActivity = "Échange de Théories & Rumeurs",
                secretsCount = 2,
                badgeTag = "Vibrant"
            ),
            NexusDistrict(
                type = DistrictType.ANCIENT_DISTRICT,
                name = "Quartier Ancien",
                japaneseName = "古都",
                icon = "🏯",
                subtitle = "Sanctuaires & Esprits du Passé",
                description = "Temples traditionnels, lanternes d'ombre, énigmes de yokai et parchemins ancestraux recelant l'origine de l'animation japonaise.",
                activePlayersCount = 1890,
                currentActivity = "Méditation & Lore Ancestral",
                secretsCount = 3,
                badgeTag = "Mystique"
            ),
            NexusDistrict(
                type = DistrictType.BATTLE_ARENA,
                name = "Arène des Champions",
                japaneseName = "闘技場",
                icon = "⚔️",
                subtitle = "Où le Savoir Devient Puissance",
                description = "Combats tactiques animés où chaque bonne réponse débloque des techniques élémentaires dévastatrices contre d'autres guerriers.",
                activePlayersCount = 4210,
                currentActivity = "Duels d'Attaque en Direct",
                secretsCount = 1,
                badgeTag = "Compétitif"
            ),
            NexusDistrict(
                type = DistrictType.ACADEMY,
                name = "Académie Otaku",
                japaneseName = "学院",
                icon = "🎓",
                subtitle = "Haut Lieu de la Connaissance",
                description = "Masterclasses de mangakas célèbres, codex encyclopédique, examens de maîtrise de lore et diplômes de rang.",
                activePlayersCount = 2150,
                currentActivity = "Examens de Maîtrise de Lore",
                secretsCount = 2,
                badgeTag = "Intellectuel"
            ),
            NexusDistrict(
                type = DistrictType.OTAKU_MARKET,
                name = "Marché Cosmique",
                japaneseName = "市場",
                icon = "🛍️",
                subtitle = "Bazar des Collectionneurs",
                description = "Échoppes de cosmétiques rares, auras d'énergie, figurines de collection pour votre chambre et titres de légende.",
                activePlayersCount = 2980,
                currentActivity = "Enchères Rares & Déco",
                secretsCount = 1,
                badgeTag = "Commerce"
            ),
            NexusDistrict(
                type = DistrictType.ARCADE,
                name = "Salle d'Arcade Neo-Akiba",
                japaneseName = "遊技場",
                icon = "🎮",
                subtitle = "Réflexes & Combos Holographiques",
                description = "Bornes d'arcade rétro, quiz éclair à réaction rapide, reconnaissances d'openings et classements de score survoltés.",
                activePlayersCount = 1740,
                currentActivity = "Speed Blitz Challenge",
                secretsCount = 2,
                badgeTag = "Frenzy"
            ),
            NexusDistrict(
                type = DistrictType.TOURNAMENT_STADIUM,
                name = "Stade du Tournoi Mondial",
                japaneseName = "競技場",
                icon = "🏆",
                subtitle = "La Scène Suprême de la Gloire",
                description = "L'arène monumentale des tournois saisonniers, des ligues d'élite et des grandes confrontations inter-guildes.",
                activePlayersCount = 5120,
                currentActivity = "Ligue des Champions Active",
                secretsCount = 1,
                badgeTag = "Prestige"
            ),
            NexusDistrict(
                type = DistrictType.PORTAL_GATE,
                name = "La Grande Porte des Mondes",
                japaneseName = "大門",
                icon = "🌌",
                subtitle = "Passerelle Inter-Dimensionnelle",
                description = "L'arche monumentale surplombant les nuages, ouvrant les vortex vers les 6 univers originaux inspirés des genres de l'anime.",
                activePlayersCount = 6890,
                currentActivity = "Porte Céleste Active",
                secretsCount = 4,
                badgeTag = "Épique"
            ),
            NexusDistrict(
                type = DistrictType.PERSONAL_ROOM,
                name = "Chambre Personnelle",
                japaneseName = "マイルーム",
                icon = "🏠",
                subtitle = "Votre Sanctuaire Otaku",
                description = "Votre espace 100% personnalisable avec posters collectors, bibliothèque de mangas, console de jeu, vitrine de figurines et néons.",
                activePlayersCount = 1,
                currentActivity = "Personnalisation & Repos",
                secretsCount = 1,
                badgeTag = "Intime"
            ),
            NexusDistrict(
                type = DistrictType.COMMUNITY_PARK,
                name = "Parc Astral",
                japaneseName = "星空公園",
                icon = "🌳",
                subtitle = "Rassemblement sous les Cerisiers",
                description = "Lacs scintillants aux reflets violets, cerisiers cristallins flottants, feux de camp de guildes et discussions chaleureuses.",
                activePlayersCount = 2310,
                currentActivity = "Feu de Camp Communautaire",
                secretsCount = 2,
                badgeTag = "Convivial"
            )
        )
    )
    val districts = _districts.asStateFlow()

    // 6 ORIGINAL ANIME WORLDS
    private val _worldPortals = MutableStateFlow(
        listOf(
            AnimeWorldPortal(
                id = "valoria",
                name = "Valoria",
                title = "Le Monde des Guerriers",
                genre = "Shonen / Arts Martiaux / Épique",
                icon = "⚔️",
                themeColor = 0xFFDC2626,
                description = "Un monde de citadelles célestes où le ki, le mana martial et la maîtrise du sabre façonnent le destin des empires.",
                loreSnippet = "Les 8 Sanctuaires Martiaux de Valoria gardent les Épées Saintes légendaires. Seuls ceux qui triomphent des épreuves du Dojo Céleste peuvent contempler le Trône d'Acier.",
                guardianName = "Kenji le Maître des Cent Lames",
                isUnlocked = true,
                completionPercent = 70,
                relicsFound = 4,
                totalRelics = 6,
                activeAnomaly = "Tempête de Ki Rougeoyant"
            ),
            AnimeWorldPortal(
                id = "reikyo",
                name = "Reikyo",
                title = "Le Monde des Esprits",
                genre = "Surnaturel / Yokai / Mystique",
                icon = "🌸",
                themeColor = 0xFF9333EA,
                description = "Forêts éternelles de fleurs de cerisier habitées par des kami, des renards célestes à neuf queues et des gardiens spectraux.",
                loreSnippet = "Sous le Grand Torii de Cristal dort l'esprit d'Amaterasu. Le voile entre le monde des vivants et des esprits s'amincit chaque nuit, révélant des chemins secrets.",
                guardianName = "Kagura la Grande Prêtresse",
                isUnlocked = true,
                completionPercent = 50,
                relicsFound = 3,
                totalRelics = 5,
                activeAnomaly = "Pluie de Pétales Flottants"
            ),
            AnimeWorldPortal(
                id = "neo_kyoto",
                name = "Neo-Kyoto 2099",
                title = "Le Monde de Demain",
                genre = "Cyberpunk / Mecha / Sci-Fi",
                icon = "🚀",
                themeColor = 0xFF0284C7,
                description = "Une mégapole verticale sous néons violets et pluies acides, où androïdes renégats et pilotes de méchas s'affrontent.",
                loreSnippet = "Le Super-Ordinateur 'Yggdrasil-01' contrôle la matrice neuronale de la ville. Les pilotes de méchas cherchent à libérer le noyau d'énergie cosmique.",
                guardianName = "Capitaine Ren du Mecha Unit-01",
                isUnlocked = true,
                completionPercent = 60,
                relicsFound = 3,
                totalRelics = 6,
                activeAnomaly = "Pulsation Quantique Électromagnétique"
            ),
            AnimeWorldPortal(
                id = "aetheria",
                name = "Aetheria",
                title = "Le Monde de l'Exploration",
                genre = "Aventure / Îles Célestes / Pirates",
                icon = "🏴‍☠️",
                themeColor = 0xFFD97706,
                description = "Des archipels d'îles volantes voguant sur une mer de nuages dorés, peuplée de baleines stellaires et d'équipages de corsaires libres.",
                loreSnippet = "Le Trésor de l'Abysse Stellaire attend l'équipage qui saura déchiffrer la carte céleste et affronter le Léviathan des Nuages.",
                guardianName = "Capitaine Drake le Corsaire Céleste",
                isUnlocked = true,
                completionPercent = 45,
                relicsFound = 2,
                totalRelics = 7,
                activeAnomaly = "Courants Aériens Ascendants"
            ),
            AnimeWorldPortal(
                id = "seishun",
                name = "Seishun High",
                title = "Le Monde de l'Académie",
                genre = "Tranche de Vie / Compétition / Amitié",
                icon = "🏫",
                themeColor = 0xFF10B981,
                description = "Une prestigieuse académie d'élite où les clubs d'arts martiaux, de stratégie et de musique s'affrontent dans le Grand Festival Inter-Écoles.",
                loreSnippet = "La Bannière Dorée de l'Académie n'a pas été décrochée depuis trois ans. Les clubs forment des alliances secrètes à la veille du festival.",
                guardianName = "Présidente Aoi du Conseil des Élèves",
                isUnlocked = true,
                completionPercent = 85,
                relicsFound = 5,
                totalRelics = 6,
                activeAnomaly = "Effervescence du Festival Annuel"
            ),
            AnimeWorldPortal(
                id = "umbra",
                name = "Umbra Abyss",
                title = "Le Monde des Ombres",
                genre = "Dark Fantasy / Psychologique / Seinen",
                icon = "🌑",
                themeColor = 0xFF475569,
                description = "Un labyrinthe de brume noire et de ruines gothiques où des malédictions millénaires éprouvent l'âme et la volonté des voyageurs.",
                loreSnippet = "Dans les cryptes du Sanctuaire Déchu résonne la mélodie de l'Archange Oublié. Chaque décision morale modifie la structure des donjons.",
                guardianName = "Le Rôdeur Silencieux",
                isUnlocked = true,
                completionPercent = 30,
                relicsFound = 1,
                totalRelics = 5,
                activeAnomaly = "Brume d'Illusion Épaissie"
            )
        )
    )
    val worldPortals = _worldPortals.asStateFlow()

    // OTAKU ROOM CUSTOMIZATION
    private val _roomItems = MutableStateFlow(
        listOf(
            OtakuRoomItem("poster_1", RoomItemCategory.POSTER, "Poster One Piece Gear 5", "🏴‍☠️", "One Piece", isPlaced = true, isUnlocked = true),
            OtakuRoomItem("poster_2", RoomItemCategory.POSTER, "Poster Solo Leveling Arise", "🗡️", "Solo Leveling", isPlaced = true, isUnlocked = true),
            OtakuRoomItem("poster_3", RoomItemCategory.POSTER, "Poster Jujutsu Kaisen Gojo Satoru", "👁️", "Jujutsu Kaisen", isPlaced = false, isUnlocked = true),
            OtakuRoomItem("poster_4", RoomItemCategory.POSTER, "Poster Steins;Gate Divergence Meter", "⏳", "Steins;Gate", isPlaced = false, isUnlocked = false, price = 250),
            OtakuRoomItem("fig_1", RoomItemCategory.FIGURE, "Figurine Zoro Roronoa Enma 1/6", "⚔️", "One Piece", isPlaced = true, isUnlocked = true),
            OtakuRoomItem("fig_2", RoomItemCategory.FIGURE, "Nendoroid Anya Forger Waku Waku", "🥜", "Spy x Family", isPlaced = true, isUnlocked = true),
            OtakuRoomItem("fig_3", RoomItemCategory.FIGURE, "Statue Sukuna Trône des Crânes", "💀", "Jujutsu Kaisen", isPlaced = false, isUnlocked = true),
            OtakuRoomItem("fig_4", RoomItemCategory.FIGURE, "Figurine Nezuko Kamado Chibi", "🎀", "Demon Slayer", isPlaced = false, isUnlocked = false, price = 350),
            OtakuRoomItem("console_1", RoomItemCategory.CONSOLE, "Console OtakuStation 5 Pro", "🎮", "Next-Gen Gaming", isPlaced = true, isUnlocked = true),
            OtakuRoomItem("console_2", RoomItemCategory.CONSOLE, "Borne d'Arcade Rétro Shonen Mini", "🕹️", "Retro Gaming", isPlaced = false, isUnlocked = true),
            OtakuRoomItem("manga_1", RoomItemCategory.MANGA, "Bibliothèque 500 tomes Shonen Manga", "📚", "Collection Complète", isPlaced = true, isUnlocked = true),
            OtakuRoomItem("manga_2", RoomItemCategory.MANGA, "Étagère Artbooks & Tirages d'Auteur", "📖", "Éditions Limitées", isPlaced = false, isUnlocked = true),
            OtakuRoomItem("trophy_1", RoomItemCategory.TROPHY, "Coupe du Champion d'Arène", "🏆", "Arène Nexus", isPlaced = true, isUnlocked = true),
            OtakuRoomItem("trophy_2", RoomItemCategory.TROPHY, "Médaille d'Or du Grand Maître Quiz", "🥇", "Académie", isPlaced = true, isUnlocked = true),
            OtakuRoomItem("neon_1", RoomItemCategory.NEON, "Néon Luminescent 'WAIFU CLUB'", "💖", "Éclairage d'Ambiance", isPlaced = true, isUnlocked = true),
            OtakuRoomItem("neon_2", RoomItemCategory.NEON, "Néon Cyberpunk Bleu 'BAN-KAI'", "⚡", "Éclairage d'Ambiance", isPlaced = false, isUnlocked = true)
        )
    )
    val roomItems = _roomItems.asStateFlow()

    // OTAKU MARKET COSMETICS & GEAR
    private val _marketItems = MutableStateFlow(
        listOf(
            NexusMarketItem("aura_paths", "Aura Cosmique des Sentiers", "Aura", "🌌", "Une lueur stellaire violette et dorée flottant autour de votre avatar.", 400, isPurchased = false),
            NexusMarketItem("aura_dragon", "Aura Flamme du Dragon Noir", "Aura", "🔥", "Des étincelles d'énergie écarlate pulsant en continu.", 500, isPurchased = false),
            NexusMarketItem("title_nexus_guard", "Titre : Gardien de Nexus City", "Titre", "👑", "Titre honorifique officiel accordé aux protecteurs de la cité.", 300, isPurchased = true, isEquipped = true),
            NexusMarketItem("title_world_conqueror", "Titre : Conquérant des 6 Mondes", "Titre", "🌀", "Démontre votre bravoure à travers les portails dimensionnels.", 600, isPurchased = false),
            NexusMarketItem("neon_sukuna", "Néon 'SUKUNA DOMAIN' pour Chambre", "Déco Chambre", "⛩️", "Enseigne murale lumineuse rouge sang pour votre chambre.", 350, isPurchased = false),
            NexusMarketItem("sword_nichirin", "Épée Nichirin Décorative", "Déco Chambre", "🗡️", "Magnifique réplique d'épée forgée sur socle en chêne.", 450, isPurchased = false),
            NexusMarketItem("frame_holo", "Cadre Avatar Holographique", "Effet", "✨", "Effet de réfraction prismatique sur votre photo de profil.", 250, isPurchased = true, isEquipped = true)
        )
    )
    val marketItems = _marketItems.asStateFlow()

    // HIDDEN SECRETS & MYSTERIES
    private val _worldSecrets = MutableStateFlow(
        listOf(
            WorldSecret("secret_ancient", DistrictType.ANCIENT_DISTRICT, "La Clé des Anciens Kami", "Une dalle gravée de runes réagit à votre présence près du sanctuaire sud...", "Vous découvrez le journal d'un moine évoquant la première ouverture de la Porte Céleste il y a mille ans !", 100, isDiscovered = false),
            WorldSecret("secret_arcade", DistrictType.ARCADE, "Le Code Secret Konami de la Borne 77", "Une borne rétro clignote d'une façon étrange en rythme...", "En décodant la fréquence secrète, vous débloquez un mini-jeu secret et 150 pièces cosmiques !", 80, isDiscovered = false),
            WorldSecret("secret_park", DistrictType.COMMUNITY_PARK, "Le Messager Nocturne à la Cape Étoilée", "Une silhouette mystérieuse attend sous le cerisier millénaire au crépuscule...", "L'inconnu vous remet une boussole stellaire pointant vers une faille secrète dans Umbra Abyss.", 120, isDiscovered = false)
        )
    )
    val worldSecrets = _worldSecrets.asStateFlow()

    // WORLD EVENT : SKY GATE HAS OPENED
    private val _skyGateProgress = MutableStateFlow(16420)
    val skyGateProgress = _skyGateProgress.asStateFlow()
    val skyGateMaxGoal = 20000

    // CHRONICLE / "YOUR STORY" TIMELINE
    private val _chronicleMilestones = MutableStateFlow(
        listOf(
            ChronicleMilestone("m1", "Arrivée à Nexus City", "Il y a 14 jours", "Origine", "🚪", "Vous avez franchi la frontière d'Astralis comme simple voyageur, guidé par les lueurs de la cité suspendue."),
            ChronicleMilestone("m2", "Premier Duel Remporté en Arène", "Il y a 10 jours", "Combat", "⚔️", "Votre connaissance affûtée de One Piece et Jujutsu Kaisen vous a permis de terrasser votre premier rival dans l'Arène des Champions."),
            ChronicleMilestone("m3", "Intégration d'une Guilde d'Élite", "Il y a 6 jours", "Social", "🏰", "Reconnu pour votre dévouement, vous avez rejoint la prestigieuse guilde des Otakus Célestes."),
            ChronicleMilestone("m4", "Exploration du Monde de Valoria", "Il y a 3 jours", "Portail", "🌀", "Vous avez bravé la première tempête martiale et déterré la Relique sacrée de l'Épée Sainte."),
            ChronicleMilestone("m5", "Découverte d'un Secret de la Cité", "Hier", "Mystère", "🧩", "Vous avez percé l'énigme du Sanctuaire Ancien et obtenu la bénédiction des Gardiens.")
        )
    )
    val chronicleMilestones = _chronicleMilestones.asStateFlow()

    // AI COMPANION AIKO GUIDE
    private val _companionDialogue = MutableStateFlow(
        CompanionDialogue(
            id = "intro",
            speaker = "Aiko",
            avatar = "🌸",
            title = "Votre Compagne Spirituelle",
            message = "Bienvenue à Nexus City ! La Porte Céleste est actuellement instable et l'Arène des Champions est en ébullition. Quel quartier souhaites-tu explorer en premier ?",
            suggestionRoute = DistrictType.BATTLE_ARENA,
            actionLabel = "Visiter l'Arène"
        )
    )
    val companionDialogue = _companionDialogue.asStateFlow()

    private val _isCompanionOpen = MutableStateFlow(false)
    val isCompanionOpen = _isCompanionOpen.asStateFlow()

    // CINEMATIC STORY SCENE STATE
    private val _activeCinematicScene = MutableStateFlow<CinematicSceneState?>(null)
    val activeCinematicScene = _activeCinematicScene.asStateFlow()


    fun setLanguage(language: AppLanguage) {
        _currentLanguage.value = language
    }

    fun connectSocialAccount(providerId: String, username: String) {
        val currentList = _socialAccounts.value
        _socialAccounts.value = currentList.map {
            if (it.providerId == providerId) {
                it.copy(isConnected = true, connectedUsername = username.ifBlank { "@otaku_user" })
            } else it
        }

        // Grant XP reward for account binding (+150 XP)
        viewModelScope.launch {
            val result = repository.addXp(150)
            val providerName = currentList.find { it.providerId == providerId }?.providerName ?: "Réseau"
            _xpToastEvent.value = XpToastEvent(
                amount = 150,
                label = "+150 XP • Compte $providerName lié avec succès ! 🌐"
            )
            if (result.didLevelUp) {
                _levelUpEvent.value = LevelUpDialogEvent(result.newLevel, result.newRank)
            }
        }
    }

    fun disconnectSocialAccount(providerId: String) {
        val currentList = _socialAccounts.value
        _socialAccounts.value = currentList.map {
            if (it.providerId == providerId) {
                it.copy(isConnected = false, connectedUsername = null)
            } else it
        }
    }

    fun voteDebate(debateId: Int, selectedOption: Int) {
        val currentList = _debates.value
        _debates.value = currentList.map { item ->
            if (item.id == debateId && item.userVote == null) {
                val newVotesA = if (selectedOption == 0) item.votesA + 1 else item.votesA
                val newVotesB = if (selectedOption == 1) item.votesB + 1 else item.votesB
                item.copy(userVote = selectedOption, votesA = newVotesA, votesB = newVotesB)
            } else item
        }

        // Reward for participating in the intellectual debate (+20 XP)
        viewModelScope.launch {
            val result = repository.addXp(20)
            _xpToastEvent.value = XpToastEvent(
                amount = 20,
                label = "+20 XP • Vote enregistré dans le Débat Otaku ! 🗳️"
            )
            if (result.didLevelUp) {
                _levelUpEvent.value = LevelUpDialogEvent(result.newLevel, result.newRank)
            }
        }
    }

    // Selected club for detail modal
    private val _selectedClub = MutableStateFlow<ClubEntity?>(null)
    val selectedClub = _selectedClub.asStateFlow()

    // Satisfying XP progression toasts and Level Up celebrations
    private val _xpToastEvent = MutableStateFlow<XpToastEvent?>(null)
    val xpToastEvent = _xpToastEvent.asStateFlow()

    private val _levelUpEvent = MutableStateFlow<LevelUpDialogEvent?>(null)
    val levelUpEvent = _levelUpEvent.asStateFlow()

    fun dismissXpToast() {
        _xpToastEvent.value = null
    }

    fun dismissLevelUpDialog() {
        _levelUpEvent.value = null
    }

    init {
        val dao = AppDatabase.getInstance(application).otakuDao()
        repository = OtakuRepository(dao)

        userProfile = repository.userProfile
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = InitialData.initialProfile
            )

        clubs = repository.allClubs
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = InitialData.initialClubs
            )

        posts = repository.allPosts
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = InitialData.initialPosts
            )

        questions = repository.allQuestions
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = InitialData.initialQuestions
            )

        quizHistory = repository.allQuizHistory
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = InitialData.initialQuizHistory
            )

        events = repository.allEvents
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = InitialData.initialEvents
            )

        badges = repository.allBadges
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = InitialData.initialBadges
            )

        notifications = repository.allNotifications
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = InitialData.initialNotifications
            )

        viewModelScope.launch {
            repository.ensureDataSeeded()
        }
    }

    fun setHomeCategoryFilter(category: String) {
        _homeCategoryFilter.value = category
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setLeaderboardTab(index: Int) {
        _leaderboardTabIndex.value = index
    }

    fun selectClub(club: ClubEntity?) {
        _selectedClub.value = club
    }

    fun toggleLikePost(post: PostEntity) {
        viewModelScope.launch {
            repository.toggleLikePost(post)
        }
    }

    fun createPost(content: String, category: String, clubName: String) {
        if (content.isBlank()) return
        viewModelScope.launch {
            repository.addPost(
                content = content,
                category = category,
                clubName = clubName,
                authorName = userProfile.value.username
            )
            val result = repository.addXp(10)
            _xpToastEvent.value = XpToastEvent(10, "+10 XP • Publication partagée 💬")
            if (result.didLevelUp) {
                _levelUpEvent.value = LevelUpDialogEvent(result.newLevel, result.newRank)
            }
        }
    }

    fun getCommentsForPost(postId: Int): Flow<List<CommentEntity>> {
        return repository.getCommentsForPost(postId)
    }

    fun addComment(postId: Int, content: String) {
        if (content.isBlank()) return
        viewModelScope.launch {
            repository.addComment(
                postId = postId,
                content = content,
                authorName = userProfile.value.username,
                authorAvatar = userProfile.value.avatarEmoji
            )
        }
    }

    fun createQuizQuestion(
        universe: String,
        difficulty: String,
        question: String,
        opt1: String,
        opt2: String,
        opt3: String,
        opt4: String,
        correctIndex: Int,
        explanation: String
    ) {
        if (question.isBlank() || opt1.isBlank() || opt2.isBlank() || opt3.isBlank() || opt4.isBlank()) return
        viewModelScope.launch {
            repository.addQuizQuestion(
                universe = universe,
                difficulty = difficulty,
                question = question,
                opt1 = opt1,
                opt2 = opt2,
                opt3 = opt3,
                opt4 = opt4,
                correctIndex = correctIndex,
                explanation = explanation
            )
        }
    }

    fun toggleJoinClub(club: ClubEntity) {
        viewModelScope.launch {
            repository.toggleJoinClub(club)
        }
    }

    fun createClub(name: String, banner: String, universe: String, description: String, tags: String) {
        if (name.isBlank()) return
        viewModelScope.launch {
            repository.createClub(name, banner, universe, description, tags)
            val result = repository.addXp(100)
            _xpToastEvent.value = XpToastEvent(100, "+100 XP • Club Otaku fondé ! 🏯")
            if (result.didLevelUp) {
                _levelUpEvent.value = LevelUpDialogEvent(result.newLevel, result.newRank)
            }
        }
    }

    fun toggleRegisterEvent(event: EventEntity) {
        viewModelScope.launch {
            repository.toggleRegisterEvent(event)
        }
    }

    fun markNotificationsRead() {
        viewModelScope.launch {
            repository.markNotificationsRead()
        }
    }

    fun updateProfile(username: String, bio: String, favAnimes: String, favCharacters: String) {
        viewModelScope.launch {
            repository.updateProfile(username, bio, favAnimes, favCharacters)
        }
    }

    fun updateProfileCustomization(
        username: String,
        avatarEmoji: String,
        rankTitle: String,
        bio: String,
        favAnimes: String,
        favCharacters: String,
        profileFrame: String,
        bannerTheme: String
    ) {
        viewModelScope.launch {
            repository.updateCustomization(
                username = username,
                avatarEmoji = avatarEmoji,
                rankTitle = rankTitle,
                bio = bio,
                favAnimes = favAnimes,
                favCharacters = favCharacters,
                profileFrame = profileFrame,
                bannerTheme = bannerTheme
            )
        }
    }

    fun getLeaderboardUsers(): List<LeaderboardUser> {
        return repository.getLeaderboardForTab(_leaderboardTabIndex.value, userProfile.value)
    }

    // --- QUIZ SYSTEM (Pedagogical, Score & Reputation Tracking, Administrator Management) ---

    fun setQuizDifficulty(difficulty: String) {
        _quizState.value = _quizState.value.copy(
            activeDifficulty = difficulty,
            currentIndex = 0,
            selectedOptionIndex = null,
            isAnswered = false
        )
    }

    fun setQuizUniverse(universe: String) {
        _quizState.value = _quizState.value.copy(
            selectedUniverse = universe,
            currentIndex = 0,
            selectedOptionIndex = null,
            isAnswered = false
        )
    }

    fun setQuizSearchQuery(query: String) {
        _quizState.value = _quizState.value.copy(
            searchQuery = query,
            currentIndex = 0,
            selectedOptionIndex = null,
            isAnswered = false
        )
    }

    fun toggleAdminMode() {
        _quizState.value = _quizState.value.copy(
            isAdminMode = !_quizState.value.isAdminMode
        )
    }

    fun toggleFilterOnlyAdmin() {
        _quizState.value = _quizState.value.copy(
            filterOnlyAdminCreated = !_quizState.value.filterOnlyAdminCreated,
            currentIndex = 0,
            selectedOptionIndex = null,
            isAnswered = false
        )
    }

    fun fetchPopularAnimeTrivia(series: String? = null) {
        if (_quizState.value.isFetchingTrivia) return
        viewModelScope.launch {
            _quizState.value = _quizState.value.copy(
                isFetchingTrivia = true,
                fetchTriviaStatusMessage = "📡 Connexion aux archives de trivia Otaku..."
            )
            kotlinx.coroutines.delay(750)
            val addedCount = repository.fetchAnimeTriviaQuestions(series)
            val msg = if (addedCount > 0) {
                "✨ $addedCount nouvelles questions trivia importées dans la bibliothèque !"
            } else {
                "⚡ Toutes les questions de cette série sont déjà synchronisées !"
            }
            _quizState.value = _quizState.value.copy(
                isFetchingTrivia = false,
                fetchTriviaStatusMessage = msg
            )
            _xpToastEvent.value = XpToastEvent(
                amount = 25,
                label = "+25 XP • Trivia Anime synchronisées 🌐"
            )
            kotlinx.coroutines.delay(3500)
            _quizState.value = _quizState.value.copy(fetchTriviaStatusMessage = null)
        }
    }

    fun answerQuizQuestion(selectedIndex: Int, currentQuestion: QuizQuestionEntity) {
        if (_quizState.value.isAnswered) return
        val isCorrect = selectedIndex == currentQuestion.correctOptionIndex
        val xpGain = if (isCorrect) currentQuestion.xpReward else 10
        val repGain = if (isCorrect) {
            when (currentQuestion.difficulty) {
                "Débutant" -> 25
                "Intermédiaire" -> 50
                "Expert" -> 85
                "Maître" -> 120
                else -> 35
            }
        } else 10

        val newStreak = if (isCorrect) _quizState.value.currentStreak + 1 else 0
        val newBestStreak = maxOf(_quizState.value.bestStreak, newStreak)

        _quizState.value = _quizState.value.copy(
            selectedOptionIndex = selectedIndex,
            isAnswered = true,
            isCorrect = isCorrect,
            correctCount = if (isCorrect) _quizState.value.correctCount + 1 else _quizState.value.correctCount,
            totalAnswered = _quizState.value.totalAnswered + 1,
            xpEarnedSession = _quizState.value.xpEarnedSession + xpGain,
            currentStreak = newStreak,
            bestStreak = newBestStreak
        )

        viewModelScope.launch {
            val result = repository.recordQuizScoreAndReputation(
                animeSeries = currentQuestion.universe,
                questionSummary = currentQuestion.question,
                isCorrect = isCorrect,
                xpReward = xpGain,
                reputationReward = repGain
            )
            val toastLabel = if (isCorrect) {
                "+$xpGain XP | +$repGain Réputation • Bonne réponse ! 🌟"
            } else {
                "+$xpGain XP | +10 Réputation • Apprentissage de Lore 💡"
            }
            _xpToastEvent.value = XpToastEvent(
                amount = xpGain,
                label = toastLabel
            )
            if (result.didLevelUp) {
                _levelUpEvent.value = LevelUpDialogEvent(result.newLevel, result.newRank)
            }
        }
    }

    fun createAdminQuizQuestion(
        universe: String,
        difficulty: String,
        question: String,
        opt1: String,
        opt2: String,
        opt3: String,
        opt4: String,
        correctIndex: Int,
        explanation: String,
        xp: Int = 50,
        adminAuthor: String = "Admin Otaku"
    ) {
        if (question.isBlank() || opt1.isBlank() || opt2.isBlank() || opt3.isBlank() || opt4.isBlank()) return
        viewModelScope.launch {
            repository.addAdminQuizQuestion(
                universe = universe,
                difficulty = difficulty,
                question = question,
                opt1 = opt1,
                opt2 = opt2,
                opt3 = opt3,
                opt4 = opt4,
                correctIndex = correctIndex,
                explanation = explanation,
                xp = xp,
                adminAuthor = adminAuthor
            )
            _xpToastEvent.value = XpToastEvent(
                amount = 60,
                label = "+60 XP • Quiz Administrateur publié officiellement ! 👑"
            )
        }
    }

    fun deleteQuizQuestion(questionId: Int) {
        viewModelScope.launch {
            repository.deleteQuizQuestion(questionId)
            _xpToastEvent.value = XpToastEvent(
                amount = 0,
                label = "🗑️ Question supprimée par l'administrateur"
            )
        }
    }

    fun nextQuizQuestion(totalQuestionsCount: Int) {
        val nextIdx = (_quizState.value.currentIndex + 1) % totalQuestionsCount.coerceAtLeast(1)
        _quizState.value = _quizState.value.copy(
            currentIndex = nextIdx,
            selectedOptionIndex = null,
            isAnswered = false,
            isCorrect = false
        )
    }

    // --- ANIME BATTLE DUEL (5 Rounds as envisioned) ---

    fun startBattle(opponentName: String = "Alex", opponentAvatar: String = "🦊") {
        val rounds = listOf(
            BattleRound(
                roundNumber = 1,
                roundTheme = "Round 1 — Connaissances 🧠",
                question = "Quel est le nom de l'épée que Mihawk porte sur son dos dans One Piece ?",
                options = listOf("Yoru (Lame Noire)", "Enma", "Wado Ichimonji", "Murakumogiri"),
                correctIndex = 0,
                explanation = "C'est Kokuto Yoru, l'une des 12 lames de premier rang (Saijo O Wazamono) au monde !"
            ),
            BattleRound(
                roundNumber = 2,
                roundTheme = "Round 2 — Personnages 👤",
                question = "Quel personnage de Naruto possède le surnom 'L'Éclair Jaune de Konoha' ?",
                options = listOf("Kakashi Hatake", "Minato Namikaze", "Tobirama Senju", "Sasuke Uchiha"),
                correctIndex = 1,
                explanation = "Minato Namikaze, le 4ème Hokage et père de Naruto, grâce à sa technique du Dieu du Tonnerre Volant (Hiraishin) !"
            ),
            BattleRound(
                roundNumber = 3,
                roundTheme = "Round 3 — Univers 🌌",
                question = "Dans Jujutsu Kaisen, quelle est la restriction divine de Toji Fushiguro ?",
                options = listOf("Aucune énergie occulte mais physique surhumain", "Cécité totale", "Impossibilité d'utiliser des armes", "Perte de mémoire permanente"),
                correctIndex = 0,
                explanation = "Toji n'a strictement aucune énergie occulte (zéro), mais en échange possède des sens et une force physique hors du commun !"
            ),
            BattleRound(
                roundNumber = 4,
                roundTheme = "Round 4 — Rapidité ⚡",
                question = "Dans Demon Slayer, quelle est la première forme du Souffle de l'Eau maîtrisée par Tanjiro ?",
                options = listOf("Entaille de l'Eau Calme", "L'Entaille de la Surface de l'Eau (Ichi no kata)", "Roue d'Eau", "Goutte de Rosée"),
                correctIndex = 1,
                explanation = "Ichi no kata: Minamo Giri (L'Entaille de la Surface de l'Eau) est la toute première forme exécutée !"
            ),
            BattleRound(
                roundNumber = 5,
                roundTheme = "Round 5 — Question Spéciale 🔥",
                question = "Dans Hunter x Hunter, quel est le type de Nen de Gon Freecss ?",
                options = listOf("Transformation", "Renforcement (Enhancement)", "Matérialisation", "Manipulation"),
                correctIndex = 1,
                explanation = "Gon est un Enhancer (Renforcement), ce qui donne toute sa puissance dévastatrice à son Jajanken !"
            )
        )

        _battleState.value = BattleState(
            isActive = true,
            opponentName = opponentName,
            opponentAvatar = opponentAvatar,
            opponentRank = "Rival Shonen",
            currentRoundIndex = 0,
            rounds = rounds,
            userScore = 0,
            opponentScore = 0,
            selectedOptionIndex = null,
            opponentSelectedOptionIndex = null,
            isRoundSubmitted = false,
            isBattleFinished = false,
            winner = null
        )
    }

    fun submitBattleAnswer(selectedOption: Int) {
        val current = _battleState.value
        if (!current.isActive || current.isRoundSubmitted || current.isBattleFinished) return

        val currentRound = current.rounds.getOrNull(current.currentRoundIndex) ?: return
        val userCorrect = selectedOption == currentRound.correctIndex

        // Opponent simulated answer (68% accuracy to make it competitive!)
        val opponentCorrect = (1..100).random() <= 68
        val opponentChoice = if (opponentCorrect) {
            currentRound.correctIndex
        } else {
            (0..3).filter { it != currentRound.correctIndex }.random()
        }

        val newUserScore = current.userScore + if (userCorrect) 100 else 20
        val newOpponentScore = current.opponentScore + if (opponentCorrect) 100 else 20

        val damageToOpponent = if (userCorrect) 25 else 5
        val damageToUser = if (opponentCorrect) 20 else 5

        val newOpponentHealth = maxOf(0, current.opponentHealth - damageToOpponent)
        val newUserHealth = maxOf(0, current.userHealth - damageToUser)

        val newEnergy = if (userCorrect) minOf(100, current.userEnergy + 35) else current.userEnergy
        val isUltReady = newEnergy >= 100

        val moveName = if (userCorrect) {
            when (_userArchetype.value) {
                OtakuArchetype.FIGHTER -> "⚔️ Frappe de Lame Céleste (-$damageToOpponent PV)"
                OtakuArchetype.STRATEGIST -> "🧠 Percée Analytique & Contre-Attaque (-$damageToOpponent PV)"
                OtakuArchetype.EXPLORER -> "🔮 Pulsation Astrale Dimensionnelle (-$damageToOpponent PV)"
                else -> "⚡ Décharge de Savoir Animé (-$damageToOpponent PV)"
            }
        } else {
            "🛡️ Réponse incorrecte — Garde brisée !"
        }

        _battleState.value = current.copy(
            selectedOptionIndex = selectedOption,
            opponentSelectedOptionIndex = opponentChoice,
            isRoundSubmitted = true,
            userScore = newUserScore,
            opponentScore = newOpponentScore,
            userHealth = newUserHealth,
            opponentHealth = newOpponentHealth,
            userEnergy = newEnergy,
            lastMoveExecuted = moveName,
            isUltimateReady = isUltReady
        )
    }

    fun triggerUltimateBattleMove() {
        val current = _battleState.value
        if (!current.isUltimateReady || current.isBattleFinished) return

        val ultDamage = 45
        val newOpponentHealth = maxOf(0, current.opponentHealth - ultDamage)
        val ultName = when (_userArchetype.value) {
            OtakuArchetype.FIGHTER -> "🔥 TECHNIQUE SECRÈTE : ÉVEIL DU ROI GUERRIER (-$ultDamage PV)"
            OtakuArchetype.STRATEGIST -> "🌌 EXPANSION DU TERRITOIRE : SAVOIR ABSOLU (-$ultDamage PV)"
            OtakuArchetype.EXPLORER -> "🌀 VORTEX INTER-DIMENSIONNEL DE LA PORTE (-$ultDamage PV)"
            else -> "⚡ EXPLOSION COSMIQUE DU SCELLÉ D'OR (-$ultDamage PV)"
        }

        _battleState.value = current.copy(
            opponentHealth = newOpponentHealth,
            userEnergy = 0,
            isUltimateReady = false,
            lastMoveExecuted = ultName,
            userScore = current.userScore + 150
        )

        _xpToastEvent.value = XpToastEvent(
            amount = 75,
            label = "💥 Coup Ultime Exécuté avec Succès !"
        )
    }

    // --- NEXUS WORLD ACTIONS ---
    fun selectDistrict(district: DistrictType?) {
        _selectedDistrict.value = district
    }

    fun setArchetype(archetype: OtakuArchetype) {
        _userArchetype.value = archetype
        _xpToastEvent.value = XpToastEvent(
            amount = 50,
            label = "Archétype actif : ${archetype.title} ${archetype.icon}"
        )
        val milestoneId = "archetype_${archetype.name}"
        if (_chronicleMilestones.value.none { it.id == milestoneId }) {
            _chronicleMilestones.value = listOf(
                ChronicleMilestone(
                    id = milestoneId,
                    title = "Voie de l'Otaku : ${archetype.title}",
                    dateDisplay = "Aujourd'hui",
                    category = "Identité",
                    icon = archetype.icon,
                    narrativeSnippet = "Vous embrassez pleinement l'archétype de ${archetype.title}, bénéficiant de l'affinité : ${archetype.bonusDescription}."
                )
            ) + _chronicleMilestones.value
        }
    }

    fun updateCharacterProfile(origin: String, style: String) {
        _userOrigin.value = origin.ifBlank { _userOrigin.value }
        _combatStyle.value = style.ifBlank { _combatStyle.value }
        _xpToastEvent.value = XpToastEvent(
            amount = 30,
            label = "Identité de voyageur mise à jour ✨"
        )
    }

    fun toggleRoomItem(itemId: String) {
        _roomItems.value = _roomItems.value.map { item ->
            if (item.id == itemId) {
                if (!item.isUnlocked) {
                    if (_otakuCoins.value >= item.price) {
                        _otakuCoins.value -= item.price
                        _xpToastEvent.value = XpToastEvent(amount = 25, label = "Objet débloqué : ${item.name} 🎁")
                        item.copy(isUnlocked = true, isPlaced = true)
                    } else {
                        _xpToastEvent.value = XpToastEvent(amount = 0, label = "Pièces Otaku insuffisantes (${item.price} requis) ⚠️")
                        item
                    }
                } else {
                    item.copy(isPlaced = !item.isPlaced)
                }
            } else item
        }
    }

    fun buyMarketItem(itemId: String) {
        val item = _marketItems.value.find { it.id == itemId } ?: return
        if (item.isPurchased) {
            _marketItems.value = _marketItems.value.map {
                if (it.id == itemId) it.copy(isEquipped = !it.isEquipped)
                else if (it.category == item.category && it.category == "Aura") it.copy(isEquipped = false)
                else it
            }
            return
        }
        if (_otakuCoins.value >= item.priceCoins) {
            _otakuCoins.value -= item.priceCoins
            _marketItems.value = _marketItems.value.map {
                if (it.id == itemId) it.copy(isPurchased = true, isEquipped = true) else it
            }
            viewModelScope.launch {
                val result = repository.addXp(100)
                _xpToastEvent.value = XpToastEvent(
                    amount = 100,
                    label = "+100 XP • Cosmétique acquis : ${item.name} 🛍️"
                )
                if (result.didLevelUp) {
                    _levelUpEvent.value = LevelUpDialogEvent(result.newLevel, result.newRank)
                }
            }
        } else {
            _xpToastEvent.value = XpToastEvent(
                amount = 0,
                label = "Pièces Otaku insuffisantes (${item.priceCoins} requises) 🪙"
            )
        }
    }

    fun discoverSecret(secretId: String) {
        val secret = _worldSecrets.value.find { it.id == secretId } ?: return
        if (secret.isDiscovered) return

        _worldSecrets.value = _worldSecrets.value.map {
            if (it.id == secretId) it.copy(isDiscovered = true) else it
        }
        _otakuCoins.value += 150

        viewModelScope.launch {
            val result = repository.addXp(secret.xpReward)
            _xpToastEvent.value = XpToastEvent(
                amount = secret.xpReward,
                label = "+${secret.xpReward} XP • Secret Découvert : ${secret.title} 🗝️"
            )
            if (result.didLevelUp) {
                _levelUpEvent.value = LevelUpDialogEvent(result.newLevel, result.newRank)
            }
        }

        _chronicleMilestones.value = listOf(
            ChronicleMilestone(
                id = "sec_${System.currentTimeMillis()}",
                title = "Secret Élucidé : ${secret.title}",
                dateDisplay = "Aujourd'hui",
                category = "Mystère",
                icon = "🗝️",
                narrativeSnippet = secret.loreReward
            )
        ) + _chronicleMilestones.value
    }

    fun contributeSkyGate(amount: Int = 100) {
        val currentProgress = _skyGateProgress.value
        val newProgress = minOf(skyGateMaxGoal, currentProgress + amount)
        _skyGateProgress.value = newProgress
        _otakuCoins.value += 80

        viewModelScope.launch {
            val result = repository.addXp(120)
            _xpToastEvent.value = XpToastEvent(
                amount = 120,
                label = "+120 XP • Contribution à la Porte Céleste (+${amount} Fragments) 🌌"
            )
            if (result.didLevelUp) {
                _levelUpEvent.value = LevelUpDialogEvent(result.newLevel, result.newRank)
            }
        }
    }

    fun exploreWorld(worldId: String) {
        _worldPortals.value = _worldPortals.value.map { world ->
            if (world.id == worldId) {
                val newCompletion = minOf(100, world.completionPercent + 15)
                val newRelics = if (newCompletion >= 80 && world.relicsFound < world.totalRelics) world.relicsFound + 1 else world.relicsFound
                world.copy(completionPercent = newCompletion, relicsFound = newRelics)
            } else world
        }

        viewModelScope.launch {
            val result = repository.addXp(150)
            _otakuCoins.value += 100
            _xpToastEvent.value = XpToastEvent(
                amount = 150,
                label = "+150 XP • Exploration avancée dans le Portail ! 🌀"
            )
            if (result.didLevelUp) {
                _levelUpEvent.value = LevelUpDialogEvent(result.newLevel, result.newRank)
            }
        }
    }

    fun toggleCompanion(open: Boolean? = null) {
        _isCompanionOpen.value = open ?: !_isCompanionOpen.value
    }

    fun askCompanion(promptKey: String) {
        val response = when (promptKey) {
            "explore" -> CompanionDialogue(
                id = "explore",
                speaker = "Aiko",
                avatar = "🌸",
                title = "Conseil d'Exploration",
                message = "Une rumeur circule : des runes cachées réagissent près du Quartier Ancien au crépuscule. Va y jeter un œil, un secret ancestral s'y terre !",
                suggestionRoute = DistrictType.ANCIENT_DISTRICT,
                actionLabel = "Aller au Quartier Ancien"
            )
            "battle" -> CompanionDialogue(
                id = "battle",
                speaker = "Aiko",
                avatar = "⚔️",
                title = "Conseil Stratégique d'Arène",
                message = "Dans l'Arène des Champions, chaque bonne réponse charge 35% de ta jauge d'énergie. Une fois à 100%, déclenche ton Coup Ultime pour infliger 45 PV instantanés !",
                suggestionRoute = DistrictType.BATTLE_ARENA,
                actionLabel = "Combattre en Arène"
            )
            "event" -> CompanionDialogue(
                id = "event",
                speaker = "Aiko",
                avatar = "🌌",
                title = "Alerte Événement Mondial",
                message = "La Porte Céleste a atteint plus de 80% d'activation grâce aux Otakus de toute la ville ! En apportant des fragments d'éther, tu gagnes des pièces et de l'XP précieux.",
                suggestionRoute = DistrictType.PORTAL_GATE,
                actionLabel = "Voir La Porte"
            )
            else -> CompanionDialogue(
                id = "lore",
                speaker = "Aiko",
                avatar = "📖",
                title = "Archive de Nexus",
                message = "Nexus City a été bâtie à la convergence des 6 genres de l'anime. Ton histoire ici ne s'arrête jamais : chaque duel, chaque théorie et chaque club renforce l'harmonie du monde !",
                suggestionRoute = DistrictType.DOWNTOWN,
                actionLabel = "Visiter le Centre"
            )
        }
        _companionDialogue.value = response
        _isCompanionOpen.value = true
    }

    fun startCinematicScene(scene: CinematicSceneState) {
        _activeCinematicScene.value = scene
    }

    fun startCinematicScene(title: String) {
        _activeCinematicScene.value = CinematicSceneState(
            title = title,
            chapter = "Prologue • La Convergence",
            speakerName = "Aiko",
            speakerTitle = "Gardienne Astrale de Nexus",
            speakerAvatar = "🌸",
            dialogueText = "« Les nuages se dispersent au-dessus de la tour de Nexus. Une onde violette traverse la Porte des Mondes. Tu ressens l'afflux d'un Éther antique. Comment canalises-tu cette puissance ? »",
            choiceA = "Canaliser par la Force Shonen",
            choiceB = "Harmoniser l'Éther Mystique",
            consequenceA = "Ton affinité combative s'éveille avec ferveur.",
            consequenceB = "L'harmonie mystique résonne avec ton âme."
        )
    }

    fun earnOtakuCoins(amount: Int, reason: String? = null) {
        _otakuCoins.value += amount
    }

    fun awardCustomXp(amount: Int, reason: String? = null) {
        viewModelScope.launch {
            val result = repository.addXp(amount)
            _xpToastEvent.value = XpToastEvent(
                amount = amount,
                label = "+$amount XP • ${reason ?: "Exploit héroïque"}"
            )
            if (result.didLevelUp) {
                _levelUpEvent.value = LevelUpDialogEvent(result.newLevel, result.newRank)
            }
        }
    }

    fun dismissCinematicScene() {
        _activeCinematicScene.value = null
    }

    fun nextBattleRound() {
        val current = _battleState.value
        val nextIndex = current.currentRoundIndex + 1

        if (nextIndex >= current.rounds.size) {
            // Battle finished!
            val won = current.userScore >= current.opponentScore
            val winner = when {
                current.userScore > current.opponentScore -> "USER"
                current.userScore < current.opponentScore -> "OPPONENT"
                else -> "DRAW"
            }
            val xpGain = if (won) 350 else 120

            _battleState.value = current.copy(
                isBattleFinished = true,
                winner = winner
            )

            viewModelScope.launch {
                val result = repository.recordBattleResult(won, xpGain)
                _xpToastEvent.value = XpToastEvent(
                    amount = xpGain,
                    label = if (won) "+$xpGain XP • Victoire Écrasante ! ⚔️" else "+$xpGain XP • Honneur du Combat ! 🛡️"
                )
                if (result.didLevelUp) {
                    _levelUpEvent.value = LevelUpDialogEvent(result.newLevel, result.newRank)
                }
            }
        } else {
            _battleState.value = current.copy(
                currentRoundIndex = nextIndex,
                selectedOptionIndex = null,
                opponentSelectedOptionIndex = null,
                isRoundSubmitted = false
            )
        }
    }

    fun closeBattle() {
        _battleState.value = BattleState()
    }
}
