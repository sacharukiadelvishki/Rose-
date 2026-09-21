package com.example.data.local

import com.example.data.model.BadgeEntity
import com.example.data.model.ClubEntity
import com.example.data.model.CommentEntity
import com.example.data.model.EventEntity
import com.example.data.model.NotificationItemEntity
import com.example.data.model.PostEntity
import com.example.data.model.QuizHistoryEntity
import com.example.data.model.QuizQuestionEntity
import com.example.data.model.UserProfileEntity

object InitialData {
    val initialProfile = UserProfileEntity(
        id = 1,
        username = "Sacha",
        tag = "@sacha_otaku",
        avatarEmoji = "⚡",
        rankTitle = "Elite Otaku",
        level = 18,
        currentXp = 3450,
        maxXp = 4000,
        winsCount = 24,
        quizzesSolved = 156,
        joinedClubsCount = 3,
        bio = "Grand fan de Shonen & Seinen 🌸 « Ici, je peux être un Otaku sans avoir à me justifier ! » 🔥",
        favoriteAnimes = "One Piece, Naruto Shippuden, Jujutsu Kaisen, Steins;Gate",
        favoriteCharacters = "Roronoa Zoro, Itachi Uchiha, Satoru Gojo",
        profileFrame = "CYBER_AURA",
        bannerTheme = "NEBULA_PURPLE",
        reputationPoints = 1420,
        reputationTier = "Érudit Otaku",
        isAdmin = true
    )

    val initialClubs = listOf(
        ClubEntity(
            id = 1,
            name = "Club One Piece 🌀",
            bannerEmoji = "🏴‍☠️",
            animeUniverse = "One Piece",
            presidentName = "GolDRoger_99",
            memberCount = 142,
            level = 15,
            description = "Pour tous les futurs Seigneurs des Pirates ! Débats sur le Void Century, les Fruits du Démon et les chapitres scans chaque semaine.",
            userRole = "Modérateur",
            isJoined = true,
            tags = "Shonen, Aventure, Scans, Théories"
        ),
        ClubEntity(
            id = 2,
            name = "Club Naruto & Boruto 🍃",
            bannerEmoji = "🍥",
            animeUniverse = "Naruto",
            presidentName = "KakashiFan",
            memberCount = 127,
            level = 14,
            description = "Bienvenue à Konoha. Analyses tactiques de combats ninjas, tournois de jutsu et classement des ninjas les plus forts.",
            userRole = "Membre",
            isJoined = true,
            tags = "Ninjutsu, Shonen, Tournois"
        ),
        ClubEntity(
            id = 3,
            name = "Club Jujutsu Kaisen 🤞",
            bannerEmoji = "🔮",
            animeUniverse = "Jujutsu Kaisen",
            presidentName = "GojoSensei",
            memberCount = 118,
            level = 12,
            description = "Extensions du territoire, énergie occulte et discussions sans spoil pour les apprentis exorcistes de l'école d'exorcisme.",
            userRole = "Membre",
            isJoined = true,
            tags = "Occulte, Action, Dark Fantasy"
        ),
        ClubEntity(
            id = 4,
            name = "Club Demon Slayer (Kimetsu) ⚔️",
            bannerEmoji = "🗡️",
            animeUniverse = "Demon Slayer",
            presidentName = "TanjiroBreath",
            memberCount = 95,
            level = 9,
            description = "Pour les disciples du Souffle du Soleil et de l'Eau. Entraînements des Piliers et quiz des Lunes Démoniaques.",
            userRole = "Visiteur",
            isJoined = false,
            tags = "Kimetsu, Piliers, Animation"
        ),
        ClubEntity(
            id = 5,
            name = "Club Seinen & Chef-d'œuvre 🪐",
            bannerEmoji = "🧠",
            animeUniverse = "Seinen",
            presidentName = "SpikeSpiegel",
            memberCount = 88,
            level = 11,
            description = "Berserk, Vinland Saga, Monster, Steins;Gate, Cowboy Bebop. Analyses philosophiques et scénarios profonds.",
            userRole = "Visiteur",
            isJoined = false,
            tags = "Psychologique, Récits profonds, Seinen"
        ),
        ClubEntity(
            id = 6,
            name = "Bataillon d'Exploration (SnK) 🛡️",
            bannerEmoji = "⚔️",
            animeUniverse = "L'Attaque des Titans",
            presidentName = "ErwinSmith",
            memberCount = 135,
            level = 13,
            description = "Dévouez vos cœurs ! Analyses approfondies des mystères des Titans, discussions éthiques sur le Grand Terrassement.",
            userRole = "Visiteur",
            isJoined = false,
            tags = "SnK, Shonen, Mystère, Philosophie"
        ),
        ClubEntity(
            id = 7,
            name = "Académie Yuei (MHA) 💥",
            bannerEmoji = "⚡",
            animeUniverse = "My Hero Academia",
            presidentName = "DekuHero",
            memberCount = 92,
            level = 8,
            description = "Plus Ultra ! Débats sur les Alters, comparaisons de puissance entre les élèves de la Seconde A et la Ligue des Vilains.",
            userRole = "Visiteur",
            isJoined = false,
            tags = "Shonen, Héros, Combats"
        )
    )

    val initialPosts = listOf(
        PostEntity(
            id = 1,
            authorName = "KakashiFan",
            authorAvatar = "⚡",
            authorRank = "Président Club Naruto",
            clubName = "Club Naruto & Boruto 🍃",
            content = "⚔️ Tournoi Inter-Clubs ce Samedi à 20h ! Inscriptions ouvertes dans l'onglet Événements. Préparez vos techniques !",
            category = "Actu",
            timestampStr = "Il y a 10 min",
            likesCount = 42,
            commentsCount = 14,
            isLiked = false
        ),
        PostEntity(
            id = 2,
            authorName = "Rose",
            authorAvatar = "🌸",
            authorRank = "Elite Otaku",
            clubName = "Club Jujutsu Kaisen 🤞",
            content = "🧠 Je viens de réussir le Quiz Jujutsu niveau Expert avec un perfect score (10/10) ! Qui ose me défier en Anime Battle ?",
            category = "Découverte",
            timestampStr = "Il y a 32 min",
            likesCount = 29,
            commentsCount = 8,
            isLiked = true
        ),
        PostEntity(
            id = 3,
            authorName = "GolDRoger_99",
            authorAvatar = "🏴‍☠️",
            authorRank = "Président One Piece",
            clubName = "Club One Piece 🌀",
            content = "Théorie du jour : La vraie signification du Siècle Oublié et le lien avec le chapeau de paille géant d'Imu... Venez débattre !",
            category = "Théorie",
            timestampStr = "Il y a 2h",
            likesCount = 88,
            commentsCount = 37,
            isLiked = false
        ),
        PostEntity(
            id = 4,
            authorName = "Kenpachi99",
            authorAvatar = "🔥",
            authorRank = "Otaku Vétéran",
            clubName = "Général",
            content = "🏆 Le classement hebdomadaire vient d'être mis à jour ! Sacha vient d'entrer dans le TOP 5 de la ligue Quiz !",
            category = "Actu",
            timestampStr = "Il y a 4h",
            likesCount = 56,
            commentsCount = 9,
            isLiked = false
        ),
        PostEntity(
            id = 5,
            authorName = "AikoArt",
            authorAvatar = "🎨",
            authorRank = "Fan Artiste",
            clubName = "Club Demon Slayer (Kimetsu) ⚔️",
            content = "Voici mon fan-art de Nezuko et Rengoku pour le défi de dessin hebdomadaire ! Donnez vos avis bienveillants ✨",
            category = "Fan Art",
            timestampStr = "Il y a 6h",
            likesCount = 112,
            commentsCount = 23,
            isLiked = true
        )
    )

    val initialQuestions = listOf(
        // === NIVEAU DÉBUTANT (Fondations & Concepts Clés) ===
        QuizQuestionEntity(
            id = 1,
            universe = "Demon Slayer",
            difficulty = "Débutant",
            question = "Quelle condition physiologique extrême découverte par Amane Ubuyashiki est nécessaire pour réveiller la Marque du Pourfendeur de Démons ?",
            option1 = "Une perte de sang de plus de 30% lors du combat",
            option2 = "Un rythme cardiaque supérieur à 200 BPM et une température corporelle dépassant 39°C",
            option3 = "L'ingestion d'une décoction de fleurs de glycines sauvages",
            option4 = "Un état de transe hypnotique absolue sans ressentir la douleur",
            correctOptionIndex = 1,
            explanation = "Analyse Canon (Chapitre 128 / Arc Entraînement des Piliers) : Muichiro Tokito et Mitsuri Kanroji confirment les constantes vitales mesurées : un pouls excédant 200 pulsations par minute couplé à une fièvre supérieure à 39°C. C'est ce stress cellulaire extrême qui permet au corps humain d'émuler la physiologie du premier utilisateur du Souffle du Soleil, Yoriichi Tsugikuni.",
            xpReward = 30
        ),
        QuizQuestionEntity(
            id = 2,
            universe = "Death Note",
            difficulty = "Débutant",
            question = "Dans Death Note, selon la règle officielle n°1 des carnets du monde des Shinigami, quelle condition visuelle stricte est imposée lors de l'écriture du nom ?",
            option1 = "Avoir vu la cible en personne au moins une fois dans les 24 heures",
            option2 = "L'écrivain doit avoir le visage de la victime à l'esprit pendant l'inscription du nom",
            option3 = "La victime doit regarder l'écrivain directement dans les yeux au moment de la mort",
            option4 = "Le nom doit être calligraphié en caractères japonais de droite à gauche",
            correctOptionIndex = 1,
            explanation = "Règle Canon n°1 (How to Read 13) : « Cette note ne prendra effet que si l'écrivain visualise le visage de la personne dont il écrit le nom. Cela évite que les personnes partageant le même nom et prénom ne soient affectées. » Cette règle est fondamentale car elle empêche les homonymes innocents de périr.",
            xpReward = 30
        ),
        QuizQuestionEntity(
            id = 3,
            universe = "Attack on Titan",
            difficulty = "Débutant",
            question = "Combien d'années de vie reste-t-il exactement à un être humain à partir du moment où il hérite de l'un des Neuf Titans Primordiaux (La Malédiction d'Ymir) ?",
            option1 = "7 ans",
            option2 = "10 ans",
            option3 = "13 ans",
            option4 = "20 ans",
            correctOptionIndex = 2,
            explanation = "Canon (Chapitre 88) : Eren Kruger révèle la Malédiction d'Ymir : nul héritier ne peut vivre plus longtemps qu'Ymir Fritz elle-même, morte exactement 13 ans après avoir éveillé le pouvoir des Titans. À l'approche du terme, le corps du porteur commence à dépérir rapidement.",
            xpReward = 30
        ),

        // === NIVEAU INTERMÉDIAIRE (Mécaniques de Pouvoir & Arcanes) ===
        QuizQuestionEntity(
            id = 4,
            universe = "Jujutsu Kaisen",
            difficulty = "Intermédiaire",
            question = "Quel est le nom et la mécanique précise de l'Extension du Territoire (Ryōiki Tenkai) de Satoru Gojo ?",
            option1 = "Autel Démoniaque (Fukuma Mizushi) : tranche indifféremment toute matière animée ou inanimée",
            option2 = "Sphère de l'Espace Infini (Muryōkūsho) : inonde le cerveau de l'adversaire d'une infinité d'informations sensorielles",
            option3 = "Jardin des Ombres Flottantes (Kango On'ei) : clone l'utilisateur dans les ténèbres",
            option4 = "Horizon du Capricorne (Daitokkai) : contrôle la pression des abysses marines",
            correctOptionIndex = 1,
            explanation = "Analyse Conceptuelle (Chapitre 15 / Muryōkūsho) : À l'intérieur de ce domaine, la cible voit et ressent tout sans pouvoir agir, son cerveau étant saturé par un flux perpétuel d'informations infinies. Même une exposition d'une fraction de seconde plonge un humain ordinaire dans un coma neurologique de plusieurs mois.",
            xpReward = 50
        ),
        QuizQuestionEntity(
            id = 5,
            universe = "Hunter x Hunter",
            difficulty = "Intermédiaire",
            question = "Quel serment inviolable (Restriction & Serment) Kurapika s'est-il auto-imposé pour décupler la puissance de sa 'Chain Jail' (Prison de Chaînes) ?",
            option1 = "Elle ne peut être projetée que sous la lueur de la pleine lune",
            option2 = "Elle ne peut être utilisée que contre les membres de la Brigade Fantôme (sous peine de mort instantanée)",
            option3 = "Chaque seconde d'utilisation lui retire un mois de son espérance de vie naturelle",
            option4 = "Il doit impérativement garder ses pupilles écarlates actives sans cligner des yeux",
            correctOptionIndex = 1,
            explanation = "Règle de Serment Nen (Arc York Shin / Chapitre 83) : Kurapika a planté une Lame de Jugement (Judgment Chain) autour de son propre cœur. S'il capture avec Chain Jail une personne étrangère à la Brigade Fantôme, la lame transperce son muscle cardiaque immédiatement. Ce risque mortel décuple l'aura de la chaîne au point de sceller de force le Zetsu d'Uvogin.",
            xpReward = 50
        ),
        QuizQuestionEntity(
            id = 6,
            universe = "One Piece",
            difficulty = "Intermédiaire",
            question = "Selon Vegapunk et Kaido, quelle est la condition fondamentale requise pour qu'un possesseur de Fruit du Démon atteigne son 'Éveil' (Awakening) ?",
            option1 = "S'entraîner 10 ans consécutifs dans des conditions de gravité accrue",
            option2 = "Lorsque le corps et l'esprit de l'utilisateur rattrapent et s'harmonisent avec le plein potentiel de son pouvoir",
            option3 = "Consommer un second fruit du démon sans subir l'explosion cellulaire",
            option4 = "Imprégner le fruit de Haki de l'Armement avancé pendant sa maturation",
            correctOptionIndex = 1,
            explanation = "Explication de Kaido (Chapitre 1046) & Vegapunk (Chapitre 1069) : « L'Éveil survient quand l'esprit et la chair rattrapent enfin la nature intrinsèque de ton pouvoir. » Chez les Zoan, si la volonté de l'utilisateur n'est pas au niveau, l'esprit bestial du fruit le dévore (comme les monstres d'Impel Down). Luffy a synchronisé sa volonté de libérer les cœurs avec Nika.",
            xpReward = 50
        ),
        QuizQuestionEntity(
            id = 7,
            universe = "Fullmetal Alchemist",
            difficulty = "Intermédiaire",
            question = "Dans le manga canon de Hiromu Arakawa, pourquoi l'Alkahestrie de Xing a-t-elle continué de fonctionner lorsque le Père d'Amestris a scellé l'alchimie de tous les alchimistes d'État ?",
            option1 = "Parce que Mei Chang utilisait des cercles dessinés au sang d'homonculus",
            option2 = "Parce qu'elle puise son énergie dans le 'Flux du Dragon' (lignes telluriques de la Terre) et non dans les poches d'âmes du Père",
            option3 = "Parce que l'Alkahestrie ne respecte pas le principe d'équivalence universel",
            option4 = "Parce que le Père ignorait l'existence des métaux précieux d'Orient",
            correctOptionIndex = 1,
            explanation = "Révélation Majeure (Chapitre 57) : Le Père bloquait l'alchimie d'Amestris en interceptant la circulation géologique grâce à sa propre Pierre Philosophale étalée sous le sol du pays. L'Alkahestrie de Xing ne dépend aucunement du réseau d'Amestris car elle exploite le 'Ryuumyaku' (Pouls du Dragon), l'énergie vivante naturelle émise par le manteau terrestre.",
            xpReward = 50
        ),

        // === NIVEAU EXPERT (Stratégies Avancées & Règles Cachées) ===
        QuizQuestionEntity(
            id = 8,
            universe = "Naruto Shippuden",
            difficulty = "Expert",
            question = "Quelle est la composition élémentaire exacte du Jinton (Kekkei Tōta / Art de la Poussière) manipulé par Mū et Ohnoki ?",
            option1 = "Terre (Doton) + Foudre (Raiton) + Eau (Suiton)",
            option2 = "Terre (Doton) + Vent (Fūton) + Feu (Katon)",
            option3 = "Feu (Katon) + Vent (Fūton) + Foudre (Raiton)",
            option4 = "Yin (Inton) + Yang (Yōton) + Espace-Temps",
            correctOptionIndex = 1,
            explanation = "Databook IV & Chapitre 466 : Le Kekkei Tōta est une sélection génétique supérieure au Kekkei Genkai ordinaire. Mū et Ohnoki sont les seuls capables de fusionner simultanément TROIS affinités de chakra : Doton, Fūton et Katon. La technique génère une forme géométrique tridimensionnelle qui désintègre instantanément la matière cible à l'échelle moléculaire.",
            xpReward = 80
        ),
        QuizQuestionEntity(
            id = 9,
            universe = "Bleach",
            difficulty = "Expert",
            question = "Pourquoi le Bankai de Shinji Hirako, 'Sakashima Yokoshima Happōfusagari', est-il rigoureusement prohibé en présence d'alliés par la Soul Society ?",
            option1 = "Parce qu'il détruit l'âme de Shinji s'il ne tue pas 100 ennemis en 10 minutes",
            option2 = "Parce qu'il inverse la perception cognitive d'allié et d'ennemi dans une zone gigantesque sans aucune distinction",
            option3 = "Parce qu'il absorbe le Reiatsu de tous les Shinigamis à 10 kilomètres à la ronde",
            option4 = "Parce qu'il invoque un Menos Grande incontrôlable",
            correctOptionIndex = 1,
            explanation = "Canon (Bleach: Can't Fear Your Own World & TYBW Arc) : Si le Shikai de Shinji (Sakanade) inverse les repères spatiaux (haut/bas, droite/gauche), son Bankai enferme Shinji dans un cocon floral protecteur et inverse totalement l'instinct d'allégeance. Les compagnons de Shinji se mettent instantanément à s'entretuer avec férocité. C'est une arme absolue uniquement viable lorsque Shinji est encerclé en solitaire par une armée.",
            xpReward = 80
        ),
        QuizQuestionEntity(
            id = 10,
            universe = "Jujutsu Kaisen",
            difficulty = "Expert",
            question = "Pour quelle raison technique fondamentale les barrières de domaine d'Extension du Territoire ne peuvent-elles ni piéger ni verrouiller automatiquement Toji Fushiguro ou Maki Zen'in éveillée ?",
            option1 = "Leur vitesse de pointe est supérieure à la vitesse de propagation de la barrière",
            option2 = "Leur Restriction Céleste totale à 0 énergie occulte fait qu'elles sont assimilées à des objets inanimés sans signature occulte",
            option3 = "Leur sang possède un anticorps génétique annulant les domaines",
            option4 = "Ils possèdent tous deux une barrière inversée invisible héritée du clan Zen'in",
            correctOptionIndex = 1,
            explanation = "Règle Canonique (Chapitre 198) : Les barrières de territoire utilisent la trace de l'énergie occulte pour identifier une cible vivante et appliquer le sort infaillible (Sure-Hit). Toji et Maki possèdent une Restriction Céleste avec un taux absolu de 0 Énergie Occulte. Le domaine les perçoit comme un mur ou un rocher ; ils peuvent traverser la barrière sans contrainte et sont invulnérables aux attaques guidées automatiques.",
            xpReward = 80
        ),
        QuizQuestionEntity(
            id = 11,
            universe = "Attack on Titan",
            difficulty = "Expert",
            question = "Quelle est la nature du pouvoir mémoriel du Titan Assaillant (Shingeki no Kyojin) et pourquoi Grisha a-t-il pu être manipulé par Eren ?",
            option1 = "Le Titan Assaillant lit l'avenir de manière omnisciente sans filtre",
            option2 = "Chaque porteur reçoit uniquement les souvenirs futurs que ses successeurs choisissent délibérément de lui transmettre",
            option3 = "Le Titan Assaillant remonte physiquement le temps dans les Sentiers",
            option4 = "Eren utilisait le Titan Fondateur pour effacer la mémoire de Grisha en temps réel",
            correctOptionIndex = 1,
            explanation = "Révélation des Sentiers (Chapitre 121) : Grisha confie à Frieda Reiss que le Titan Assaillant peut entrevoir les souvenirs de ses futurs possesseurs. Cependant, Eren n'a transmis à Grisha que des fragments choisis (comme le massacre des Reiss) tout en lui dissimulant la mort de Carla, forçant ainsi son propre père à accomplir son dessein dans une boucle causale fermée.",
            xpReward = 80
        ),
        QuizQuestionEntity(
            id = 12,
            universe = "Hunter x Hunter",
            difficulty = "Expert",
            question = "Par quel procédé inédit Hisoka Morow a-t-il réussi à survivre à sa mort physique déclarée après son duel d'anthologie contre Chrollo Lucilfer à la Tour Céleste ?",
            option1 = "Alluka Zoldyck a exaucé son vœu de guérison à distance",
            option2 = "Il a programmé son Bungee Gum via un serment post-mortem (Nen après la mort) pour masser son cœur et ses poumons dès l'arrêt de ses signes vitaux",
            option3 = "Machi a cousu une pierre d'immortalité dans son thorax",
            option4 = "Son Nen de la Transmutation a fusionné avec le Nen de manipulation de Shalnark",
            correctOptionIndex = 1,
            explanation = "Règle du Post-Mortem Nen (Chapitre 357) : Sachant que la marée humaine d'explosifs de Chrollo allait l'asphyxier, Hisoka a ordonné à son aura : « Bungee Gum, réveille-toi après ma mort et effectue un massage cardiaque continu ». Les fortes rancœurs galvanisent le Nen après le trépas : la contraction élastique a relancé son pouls alors que Machi s'apprêtait à refermer son cercueil.",
            xpReward = 80
        ),

        // === NIVEAU MAÎTRE (Secrets Cosmiques, Causalité & Lore Incollable) ===
        QuizQuestionEntity(
            id = 13,
            universe = "Steins;Gate",
            difficulty = "Maître",
            question = "Dans Steins;Gate, quelle est la divergence numérique absolue de la Ligne d'Univers nécessaire pour atteindre le 'Steins Gate' et quelle est la stratégie pour y parvenir ?",
            option1 = "0.000000% — Effacer le SERN en détruisant Echelon depuis 1975",
            option2 = "1.048596% — Tromper son propre moi passé en lui faisant voir Kurisu inanimée dans le sang sans altérer sa survie réelle",
            option3 = "0.571024% — Laisser Mayuri mourir le 13 août pour sauver le continuum",
            option4 = "-1.000000% — Créer une boucle temporelle infinie de 3 semaines",
            correctOptionIndex = 1,
            explanation = "Convergence & Déterminisme (Épisode 23 / Visual Novel) : Le chiffre légendaire est 1.048596%. Pour briser la convergence du champ d'attraction Beta sans provoquer la Troisième Guerre Mondiale, Okabe de 2025 explique qu'il ne faut pas changer ce que le jeune Okabe a vu le 28 juillet (Kurisu dans une mare de sang déclenchant le 1er D-mail). Il a donc assommé Kurisu au Taser et utilisé son propre sang pour simuler le meurtre, trompant la causalité de l'Univers !",
            xpReward = 120
        ),
        QuizQuestionEntity(
            id = 14,
            universe = "Berserk",
            difficulty = "Maître",
            question = "Dans Berserk de Kentaro Miura, quelle est la condition spirituelle et métaphysique incontournable pour qu'un porteur de Béhérit Pourpre active l'Éclipse ?",
            option1 = "Avoir versé le sang de 1 000 ennemis sur un autel maudit",
            option2 = "Être plongé dans un abîme de désespoir total et consentir de son plein gré à sacrifier ce qu'il chérit le plus au monde",
            option3 = "Prononcer l'incantation secrète de Void en présence de la pleine lune",
            option4 = "Posséder l'Armure du Berserker et renoncer à sa raison",
            correctOptionIndex = 1,
            explanation = "Théorie de la Causalité (Tomes 12-13 / Âge d'Or) : Les God Hand ne forcent personne : le Béhérit s'éveille uniquement lorsque la volonté de son porteur s'effondre dans le néant spirituel. Pour renaître en Démon Femto, Griffith a dû formuler le choix conscient de sacrifier la Troupe du Faucon, les seuls êtres humains qui avaient compté pour lui, afin de couper son dernier fil d'humanité.",
            xpReward = 120
        ),
        QuizQuestionEntity(
            id = 15,
            universe = "Neon Genesis Evangelion",
            difficulty = "Maître",
            question = "Selon les Manuscrits secrets de la Mer Morte d'Evangelion, quelle distinction métaphysique sépare fondamentalement les Anges et l'Humanité (Lilin) ?",
            option1 = "Les Anges ont été créés par la NERV tandis que les Lilin sont d'origine extraterrestre",
            option2 = "Les Anges possèdent le Fruit de la Vie (Moteur S2), tandis que les Lilin possèdent le Fruit de la Connaissance",
            option3 = "Les Anges sont faits de matière noire et les Lilin d'énergie pure",
            option4 = "Les Anges ne possèdent aucun Champ AT protecteur",
            correctOptionIndex = 1,
            explanation = "Lore Fondamental (Episodes 24-26 & End of Evangelion) : Deux géniteurs ancestraux (First Ancestral Race) ont atterri sur Terre : Adam (Lune Blanche) et Lilith (Lune Noire). Les enfants d'Adam (les Anges) ont hérité du Fruit de la Vie conférant un corps divin immortel à énergie infinie (Moteur S2). L'humanité (enfants de Lilith) a hérité du Fruit de la Connaissance : intelligence, science, mais corps mortels isolés par leurs Champs AT.",
            xpReward = 120
        ),
        QuizQuestionEntity(
            id = 16,
            universe = "Fate Series",
            difficulty = "Maître",
            question = "Pourquoi le Noble Phantasm 'Ea : Enuma Elish' de Gilgamesh est-il classé de rang 'Anti-Monde' et non 'Anti-Forteresse' comme Excalibur ?",
            option1 = "Il génère une explosion nucléaire recouvrant toute l'atmosphère terrestre",
            option2 = "Ses segments en rotation ne tirent pas un rayon, mais déchirent la trame spatio-temporelle de la réalité pour dévoiler le Néant primordial pré-création",
            option3 = "Il ne peut blesser que les Esprits Héroïques d'alignement Chaotique Mauvais",
            option4 = "Il invoque le Saint Graal sous sa forme corrompue",
            correctOptionIndex = 1,
            explanation = "Nasuverse Lore : Ea existait avant la genèse du concept même d'épée. Lorsque Gilgamesh active la rotation opposée de ses trois cylindres, la friction spatiale arrache la texture du Monde créée par la conscience humaine et les dieux, exposant le vide sous-jacent. Cela pulvérise instantanément les Reality Marbles (comme Ionioi Hetairoi d'Iskandar).",
            xpReward = 120
        ),
        QuizQuestionEntity(
            id = 17,
            universe = "Death Note",
            difficulty = "Maître",
            question = "Dans Death Note, selon la règle officielle n°26, que se produit-il si les circonstances de mort écrites par l'auteur impliquent inévitablement la mort collatérale d'une tierce personne non inscrite ?",
            option1 = "Les deux personnes meurent instantanément par ricochet",
            option2 = "La victime spécifiée meurt d'un arrêt cardiaque sans que les circonstances mortelles collatérales ne se déclenchent",
            option3 = "Le Death Note s'enflamme et retourne dans le monde des dieux de la mort",
            option4 = "L'auteur du carnet perd 5 ans de son espérance de vie en punition",
            correctOptionIndex = 1,
            explanation = "How to Read 13 (Règle n°26) : « Même si vous écrivez que la personne provoque un attentat ou un accident qui tuerait d'autres personnes dont les noms ne sont pas écrits, ces tiers ne mourront pas. La cible mourra simplement d'une crise cardiaque pour préserver la règle de causalité nominale. » Cela garantit que chaque décès requiert impérativement un nom propre visualisé.",
            xpReward = 120
        ),
        QuizQuestionEntity(
            id = 18,
            universe = "Hunter x Hunter",
            difficulty = "Maître",
            question = "Quelle était la nature exacte du piège fatal dissimulé dans le cœur du président Isaac Netero lors de son duel final face à Meruem ?",
            option1 = "Un sceau de Zetsu absolu qui neutralise le Nen du Roi pour 30 jours",
            option2 = "La Rose Miniature (Poor Man's Rose), une ogive empoisonnée déclenchée dès l'arrêt cardiaque de Netero qui libère une toxine cellulaire hautement contagieuse",
            option3 = "L'âme du patriarche Zoldyck réincarnée en dragon de foudre",
            option4 = "Un vortex dimensionnel l'aspirant vers le Continent Caché",
            correctOptionIndex = 1,
            explanation = "Chapitre 298 (Arc Fourmis Chimères) : Netero connaissait les limites de la force martiale humaine : même le Zéro de son Hyakushiki Kannon n'a fait qu'égratigner le Roi. La Rose Miniature était couplée à son pouls. Lors de son arrêt cardiaque, la bombe nucléaire bon marché a explosé. Et bien que Youpi et Pouf aient nourri Meruem de leur chair pour réparer ses brûlures, la toxine empoisonnée contagieuse a détruit ses organes de l'intérieur.",
            xpReward = 120
        ),
        QuizQuestionEntity(
            id = 19,
            universe = "One Piece",
            difficulty = "Maître",
            question = "Quelle est la particularité biologique des Lunarias (la race de King) découverte par le Dr Vegapunk pour concevoir les Séraphins ?",
            option1 = "Ils peuvent survivre dans le vide spatial en se nourrissant uniquement de roches lunaires",
            option2 = "Tant que la flamme sur leur dos brûle, ils sont pratiquement invulnérables à toute blessure physique, mais cette flamme s'éteint lorsqu'ils passent en vitesse supersonique",
            option3 = "Ils sont immunisés au Haki des Rois mais vulnérables à l'eau de mer comme les utilisateurs de fruits",
            option4 = "Leur sang blanc produit naturellement du granit marin",
            correctOptionIndex = 1,
            explanation = "Chapitres 1035 & 1060+ (Wano & Egghead) : Zoro a décelé la mécanique en combat : avec flamme dorsale allumée = défense absolue impénétrable (même face à des taillades de Haki du Conquérant). Flamme éteinte = vitesse foudroyante mais corps tranchable. Vegapunk a réussi à extraire ce Facteur de Lignée pour l'implanter dans les Séraphins S-Hawk, S-Snake et S-Bear.",
            xpReward = 120
        ),
        QuizQuestionEntity(
            id = 20,
            universe = "Bleach",
            difficulty = "Maître",
            question = "Quelle est la faille matérielle unique qui a permis à Uryū Ishida de neutraliser temporairement le pouvoir divin 'The Almighty' d'Yhwach ?",
            option1 = "La goutte de sang originelle de Soul King versée dans le Sanrei Glove",
            option2 = "Une pointe de flèche forgée dans l'Argent Immobile (Still Silver) extrait du cœur d'une victime de l'Auswählen",
            option3 = "L'activation simultanée du Bankai d'Aizen et du Shikai d'Ichigo",
            option4 = "Le déversement des cendres de Yamamoto Genryusai",
            correctOptionIndex = 1,
            explanation = "Tome 74 (TYBW Climax) : Ryūken Ishida a autopsié le corps de sa défunte épouse Kanae pour extraire le caillot d'argent immobile généré dans le cœur des Quincies lors du vol de sang d'Yhwach (Auswählen). Tiré en plein cœur d'Yhwach, cet argent fige son sang et interrompt l'Almighty pendant une minuscule seconde, ouvrant la fenêtre permettant à Ichigo de le trancher.",
            xpReward = 120
        )
    )

    val initialEvents = listOf(
        EventEntity(
            id = 1,
            title = "🔥 GRAND TOURNOI OTAKU HUB",
            hostClub = "Équipe Officielle Otaku Hub",
            dateDisplay = "Samedi 20:00 • En direct",
            participantsCount = 104,
            maxParticipants = 128,
            type = "Tournoi",
            reward = "Badge Légendaire + 1500 XP + Titre Champion",
            description = "Tournoi d'Anime Battle par élimination directe ! Qualifications en 3 manches, quart de finale, demi-finale et grande finale commentée.",
            isRegistered = true
        ),
        EventEntity(
            id = 2,
            title = "🌀 Nuit des Théories Void Century",
            hostClub = "Club One Piece 🌀",
            dateDisplay = "Vendredi 21:30",
            participantsCount = 48,
            maxParticipants = 60,
            type = "Débat Théorie",
            reward = "Badge Stratège Marin + 300 XP",
            description = "Débat vocal et écrit autour des révélations d'Egghead et des mystères d'Elbaf.",
            isRegistered = false
        ),
        EventEntity(
            id = 3,
            title = "🧠 Quiz Showdown Inter-Clubs",
            hostClub = "Club Jujutsu Kaisen 🤞",
            dateDisplay = "Dimanche 18:00",
            participantsCount = 76,
            maxParticipants = 100,
            type = "Soirée Quiz",
            reward = "500 XP de club + Badge Cerveau Shonen",
            description = "Affrontement des connaissances : 30 questions de rapidité en direct entre les 5 clubs majeurs.",
            isRegistered = false
        )
    )

    val initialBadges = listOf(
        BadgeEntity(
            id = "b1",
            title = "Premier tournoi",
            icon = "🏆",
            description = "A remporté une victoire mémorable en tournoi officiel.",
            isUnlocked = true,
            unlockedDate = "Il y a 3 jours"
        ),
        BadgeEntity(
            id = "b2",
            title = "100 Quiz Réussis",
            icon = "🧠",
            description = "A validé plus de 100 quiz anime avec mention honorable.",
            isUnlocked = true,
            unlockedDate = "La semaine dernière"
        ),
        BadgeEntity(
            id = "b3",
            title = "Membre de Club",
            icon = "🏯",
            description = "Membre engagé et respecté au sein de 3 clubs actifs.",
            isUnlocked = true,
            unlockedDate = "Il y a 2 semaines"
        ),
        BadgeEntity(
            id = "b4",
            title = "10 Victoires d'affilée",
            icon = "⚔️",
            description = "A enchaîné 10 victoires consécutives en Anime Battle sans flancher.",
            isUnlocked = false
        ),
        BadgeEntity(
            id = "b5",
            title = "Citoyen International",
            icon = "🌍",
            description = "A participé à des discussions ou tournois multi-communautés.",
            isUnlocked = false
        ),
        BadgeEntity(
            id = "b6",
            title = "Maître Otaku",
            icon = "👑",
            description = "Atteindre le niveau 20 et le rang Légende de la communauté.",
            isUnlocked = false
        )
    )

    val initialNotifications = listOf(
        NotificationItemEntity(
            id = 1,
            title = "⚔️ Défi Anime Battle !",
            message = "Alex t'a défié en duel rapide 5 rounds ! Montre tes connaissances.",
            typeIcon = "⚔️",
            timestampStr = "Il y a 15 min",
            isRead = false
        ),
        NotificationItemEntity(
            id = 2,
            title = "🏆 Grand Tournoi Otaku Hub",
            message = "Ton inscription au tournoi de Samedi est confirmée ! Phase 1 à 20:00.",
            typeIcon = "🏆",
            timestampStr = "Il y a 2h",
            isRead = false
        ),
        NotificationItemEntity(
            id = 3,
            title = "🏯 Club One Piece",
            message = "GolDRoger_99 a publié une nouvelle théorie : 'Le Siècle Oublié et Imu'.",
            typeIcon = "🌀",
            timestampStr = "Hier",
            isRead = true
        ),
        NotificationItemEntity(
            id = 4,
            title = "🎖️ Nouveau Badge Débloqué !",
            message = "Félicitations ! Tu as débloqué le badge « 100 Quiz Réussis » 🧠.",
            typeIcon = "🎖️",
            timestampStr = "Il y a 3 jours",
            isRead = true
        )
    )

    val initialComments = listOf(
        CommentEntity(
            id = 1,
            postId = 1,
            authorName = "Rose",
            authorAvatar = "🌸",
            content = "Je serai présente avec les techniques de Jujutsu ! Que le meilleur club gagne 🔥",
            timestampStr = "Il y a 8 min"
        ),
        CommentEntity(
            id = 2,
            postId = 1,
            authorName = "GolDRoger_99",
            authorAvatar = "🏴‍☠️",
            content = "L'équipage du chapeau de paille est prêt pour ce tournoi !",
            timestampStr = "Il y a 5 min"
        ),
        CommentEntity(
            id = 3,
            postId = 2,
            authorName = "Sacha",
            authorAvatar = "⚡",
            content = "Défi accepté ! On se retrouve dans l'arène Anime Battle ⚔️",
            timestampStr = "Il y a 20 min"
        ),
        CommentEntity(
            id = 4,
            postId = 3,
            authorName = "Kenpachi99",
            authorAvatar = "🔥",
            content = "Cette théorie tient la route, surtout avec le trésor de Marie Joie !",
            timestampStr = "Il y a 1h"
        ),
        CommentEntity(
            id = 5,
            postId = 5,
            authorName = "Rose",
            authorAvatar = "🌸",
            content = "Les détails des flammes de Rengoku sont magnifiques, bravo !",
            timestampStr = "Il y a 4h"
        )
    )

    val initialQuizHistory = listOf(
        QuizHistoryEntity(
            id = 1,
            animeSeries = "Demon Slayer",
            questionSummary = "Éveil de la Marque du Pourfendeur (BPM > 200 & Fièvre > 39°C)",
            isCorrect = true,
            pointsEarned = 30,
            timestampStr = "Hier à 18:42"
        ),
        QuizHistoryEntity(
            id = 2,
            animeSeries = "Death Note",
            questionSummary = "Règle n°1 visuelle du Shinigami (Visage en tête)",
            isCorrect = true,
            pointsEarned = 30,
            timestampStr = "Hier à 19:15"
        ),
        QuizHistoryEntity(
            id = 3,
            animeSeries = "Attack on Titan",
            questionSummary = "Malédiction d'Ymir (Durée de vie de 13 ans)",
            isCorrect = true,
            pointsEarned = 30,
            timestampStr = "Il y a 2 jours"
        ),
        QuizHistoryEntity(
            id = 4,
            animeSeries = "Jujutsu Kaisen",
            questionSummary = "Extension du Territoire Muryōkūsho de Gojo",
            isCorrect = true,
            pointsEarned = 50,
            timestampStr = "Il y a 3 jours"
        )
    )

    val popularTriviaPool = listOf(
        QuizQuestionEntity(
            universe = "One Piece",
            difficulty = "Intermédiaire",
            question = "Quel est le véritable nom révélé du Fruit du Démon de Monkey D. Luffy (Gomu Gomu no Mi) par le Gorosei ?",
            option1 = "Hito Hito no Mi, Modèle : Nika",
            option2 = "Gura Gura no Mi, Modèle : Soleil",
            option3 = "Mochi Mochi no Mi, Modèle : Divin",
            option4 = "Taiyo Taiyo no Mi, Modèle : Guerrier Libérateur",
            correctOptionIndex = 0,
            explanation = "Canon Révélation (Chapitre 1044) : Le Gouvernement Mondial a rebaptisé le Fruit Zoan Mythique 'Hito Hito no Mi, Modèle : Nika' en 'Gomu Gomu no Mi' pour effacer le nom du Dieu du Soleil Nika des archives historiques.",
            xpReward = 50,
            createdByAdmin = true,
            adminAuthor = "Admin Curateur Shonen"
        ),
        QuizQuestionEntity(
            universe = "Solo Leveling",
            difficulty = "Débutant",
            question = "Quel titre légendaire Sung Jinwoo hérite-t-il après avoir terminé l'épreuve de changement de classe dans le donjon de la quête de pénalité ?",
            option1 = "Le Monarque des Ombres (Shadow Monarch)",
            option2 = "L'Assassin de Sang",
            option3 = "Le Chasseur National Suprême",
            option4 = "Le Souverain des Épées",
            correctOptionIndex = 0,
            explanation = "Canon Solo Leveling : Jinwoo débloque la classe Nécromancien qui évolue immédiatement en Monarque des Ombres grâce à sa résistance et ses points d'intelligence accumulés.",
            xpReward = 35,
            createdByAdmin = true,
            adminAuthor = "Admin Manhwa Hub"
        ),
        QuizQuestionEntity(
            universe = "Dragon Ball",
            difficulty = "Expert",
            question = "Lors du Tournoi du Pouvoir dans Dragon Ball Super, quelle est la condition mentale absolue nécessaire pour maîtriser l'Ultra Instinct Parfait (Migatte no Gokui) ?",
            option1 = "Laisser son cœur déborder d'une fureur pure",
            option2 = "Le calme émotionnel absolu et laisser son corps réagir sans l'intermédiaire de la pensée",
            option3 = "Canaliser toute son énergie divine dans un seul point du plexus solaire",
            option4 = "Synchroniser son souffle avec le ki de ses ancêtres Saiyans",
            correctOptionIndex = 1,
            explanation = "Explication Whis : L'Ultra Instinct exige une séparation totale du conscient et de l'action motrice. Toute émotion négative ou pensée consciente ralentit la transmission nerveuse et brise l'état divin.",
            xpReward = 85,
            createdByAdmin = true,
            adminAuthor = "Admin Curateur Shonen"
        ),
        QuizQuestionEntity(
            universe = "Chainsaw Man",
            difficulty = "Intermédiaire",
            question = "Pourquoi Denji est-il capable de blesser mortellement le Démon Éternité dans l'hôtel lors de la première mission de la 4e division ?",
            option1 = "Parce qu'il boit le sang du Démon en continu pour se régénérer sans arrêt pendant 3 jours",
            option2 = "Grâce à une lame spéciale en argent forgée par le Kishibe",
            option3 = "Parce que Makima intervient en secret pour contrôler l'espace-temps",
            option4 = "Le Démon Éternité a une phobie congénitale du bruit des tronçonneuses",
            correctOptionIndex = 0,
            explanation = "Canon Chainsaw Man (Arc Démon Éternité) : Denji crée une boucle perpétuelle : scier la chair génère du sang qu'il boit aussitôt pour relancer ses tronçonneuses, acculant le Démon à implorer la mort par pure agonie.",
            xpReward = 50,
            createdByAdmin = true,
            adminAuthor = "Admin Conseil Otaku"
        ),
        QuizQuestionEntity(
            universe = "Bleach",
            difficulty = "Expert",
            question = "Quelle est la capacité suprême de l'épée Kyoka Suigetsu de Sosuke Aizen ?",
            option1 = "L'Hypnose Totale (Kanzen Saimin) contrôlant les cinq sens de quiconque a vu son Shikai se libérer",
            option2 = "La réplication infinie d'illusions tangibles par onde spirituelle",
            option3 = "Le ralentissement du temps subjectif d'un facteur 1000",
            option4 = "L'absorption irréversible du Reishi ambiant dans un rayon de 5 km",
            correctOptionIndex = 0,
            explanation = "Canon Bleach : Kanzen Saimin contrôle absolument les 5 sens : l'odorat, la vue, le toucher, le goût et l'ouïe. Une fois la libération vue une seule fois, la victime est piégée à vie sans s'en rendre compte.",
            xpReward = 85,
            createdByAdmin = true,
            adminAuthor = "Admin Conseil Otaku"
        ),
        QuizQuestionEntity(
            universe = "Hunter x Hunter",
            difficulty = "Maître",
            question = "Quelle restriction mortelle (Serment & Contrat Nen) Kurapika s'est-il imposée pour utiliser sa technique 'Chain Jail' ?",
            option1 = "S'il utilise cette chaîne contre quelqu'un d'autre qu'un membre de la Brigade Fantôme, la Lame du Jugement percera son propre cœur",
            option2 = "Chaque seconde d'utilisation raccourcit son espérance de vie d'une heure",
            option3 = "Il perd l'usage de ses yeux rouges à chaque activation",
            option4 = "Il ne peut plus jamais manifester de Nen de Renforcement",
            correctOptionIndex = 0,
            explanation = "Canon HxH (Arc York Shin) : En pointant la Lame du Jugement contre son propre cœur avec cette règle absolue, Kurapika a décuplé la puissance de sa chaîne au point de contraindre Uvoguine en état de Zetsu forcé absolu.",
            xpReward = 120,
            createdByAdmin = true,
            adminAuthor = "Admin Conseil Otaku"
        )
    )
}

