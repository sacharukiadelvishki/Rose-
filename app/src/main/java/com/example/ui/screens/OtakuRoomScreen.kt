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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocalMall
import androidx.compose.material.icons.filled.MeetingRoom
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.NexusMarketItem
import com.example.data.model.OtakuRoomItem
import com.example.data.model.RoomItemCategory
import com.example.ui.OtakuViewModel
import com.example.ui.components.otakuAtmosphericBackground
import com.example.ui.theme.OtakuDarkBorder
import com.example.ui.theme.OtakuDarkCard
import com.example.ui.theme.OtakuDarkCardElevated
import com.example.ui.theme.OtakuPrimary
import com.example.ui.theme.OtakuSecondary
import com.example.ui.theme.OtakuTertiary
import com.example.ui.theme.OtakuTextMuted
import com.example.ui.theme.OtakuTextPrimary
import com.example.ui.theme.OtakuTextSecondary

@Composable
fun OtakuRoomScreen(viewModel: OtakuViewModel) {
    val roomItems by viewModel.roomItems.collectAsStateWithLifecycle()
    val marketItems by viewModel.marketItems.collectAsStateWithLifecycle()
    val otakuCoins by viewModel.otakuCoins.collectAsStateWithLifecycle()
    val userProfile by viewModel.userProfile.collectAsStateWithLifecycle()

    var selectedTab by remember { mutableIntStateOf(0) } // 0: Ma Chambre Otaku, 1: Le Marché Cosmique
    var categoryFilter by remember { mutableStateOf<RoomItemCategory?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .otakuAtmosphericBackground()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Tab Switcher : Ma Chambre vs Marché Cosmique
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = OtakuDarkCard,
                contentColor = OtakuPrimary,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = OtakuPrimary
                    )
                }
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.MeetingRoom, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Ma Chambre Otaku 🏠", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                    }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.LocalMall, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Marché Cosmique 🛍️", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                    }
                )
            }

            // Top Status Bar: Balance of Coins & Items Placed
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(OtakuDarkCardElevated)
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(OtakuTertiary.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("🪙", fontSize = 14.sp)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text("$otakuCoins Pièces Otaku", fontSize = 13.sp, fontWeight = FontWeight.Black, color = OtakuTextPrimary)
                        Text("Gagnées en arène & quêtes", fontSize = 10.sp, color = OtakuTextMuted)
                    }
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(OtakuPrimary.copy(alpha = 0.12f))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        "${roomItems.count { it.isPlaced }} Objets Installés",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = OtakuPrimary
                    )
                }
            }

            if (selectedTab == 0) {
                // TAB 1: MA CHAMBRE PERSONNELLE
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp, bottom = 92.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // ROOM VISUAL STAGE PREVIEW
                    item {
                        Card(
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(containerColor = OtakuDarkCard),
                            border = androidx.compose.foundation.BorderStroke(1.dp, OtakuPrimary.copy(alpha = 0.3f)),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                            modifier = Modifier.fillMaxWidth().testTag("room_visual_stage")
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        Brush.verticalGradient(
                                            colors = listOf(
                                                Color(0xFF1E1B4B), // Deep indigo chamber
                                                Color(0xFF0F172A)
                                            )
                                        )
                                    )
                                    .padding(16.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text("SANCTUAIRE DE SACHA 🌟", fontSize = 12.sp, fontWeight = FontWeight.Black, color = OtakuSecondary)
                                        Text("Chambre Personnelle Niveau 3", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                    }
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(Color(0xFFEC4899).copy(alpha = 0.25f))
                                            .padding(horizontal = 8.dp, vertical = 4.dp)
                                    ) {
                                        Text("✨ Ambiance Néon", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFFF472B6))
                                    }
                                }

                                Spacer(modifier = Modifier.height(14.dp))

                                // Wall Posters Row
                                Text("MURS : POSTERS COLLECTORS", fontSize = 10.sp, color = OtakuSecondary, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.height(6.dp))
                                val placedPosters = roomItems.filter { it.category == RoomItemCategory.POSTER && it.isPlaced }
                                if (placedPosters.isEmpty()) {
                                    Text("Aucun poster accroché au mur", fontSize = 12.sp, color = Color.White.copy(alpha = 0.5f))
                                } else {
                                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                        placedPosters.forEach { p ->
                                            Box(
                                                modifier = Modifier
                                                    .clip(RoundedCornerShape(8.dp))
                                                    .background(Color.White.copy(alpha = 0.1f))
                                                    .border(1.dp, Color.White.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                                            ) {
                                                Row(verticalAlignment = Alignment.CenterVertically) {
                                                    Text(p.icon, fontSize = 16.sp)
                                                    Spacer(modifier = Modifier.width(6.dp))
                                                    Text(p.animeReference, fontSize = 11.sp, color = Color.White, fontWeight = FontWeight.Bold)
                                                }
                                            }
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(14.dp))

                                // Vitrine Figurines & Trophées
                                Text("ÉTAGÈRE DE FIGURINES & TROPHÉES", fontSize = 10.sp, color = OtakuTertiary, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.height(6.dp))
                                val placedDecor = roomItems.filter { (it.category == RoomItemCategory.FIGURE || it.category == RoomItemCategory.TROPHY) && it.isPlaced }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(Color.Black.copy(alpha = 0.3f))
                                        .padding(10.dp),
                                    horizontalArrangement = Arrangement.SpaceAround,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    placedDecor.take(4).forEach { item ->
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            Text(item.icon, fontSize = 24.sp)
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(item.name.take(12), fontSize = 9.sp, color = Color.White.copy(alpha = 0.8f))
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                // Gaming & Manga corner
                                val activeConsole = roomItems.find { it.category == RoomItemCategory.CONSOLE && it.isPlaced }
                                val activeNeon = roomItems.find { it.category == RoomItemCategory.NEON && it.isPlaced }
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("🎮 Console : ${activeConsole?.name ?: "Aucune"}", fontSize = 11.sp, color = Color.White.copy(alpha = 0.7f))
                                    Text("💡 Néon : ${activeNeon?.name ?: "Éteint"}", fontSize = 11.sp, color = Color(0xFFF472B6))
                                }
                            }
                        }
                    }

                    // Category Filter Pills
                    item {
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            item {
                                FilterChip(
                                    label = "Tous",
                                    isSelected = categoryFilter == null,
                                    onClick = { categoryFilter = null }
                                )
                            }
                            RoomItemCategory.values().forEach { cat ->
                                item {
                                    FilterChip(
                                        label = when (cat) {
                                            RoomItemCategory.POSTER -> "Posters 🖼️"
                                            RoomItemCategory.FIGURE -> "Figurines 🧸"
                                            RoomItemCategory.MANGA -> "Mangas 📚"
                                            RoomItemCategory.CONSOLE -> "Consoles 🎮"
                                            RoomItemCategory.TROPHY -> "Trophées 🏆"
                                            RoomItemCategory.NEON -> "Néons 💡"
                                        },
                                        isSelected = categoryFilter == cat,
                                        onClick = { categoryFilter = cat }
                                    )
                                }
                            }
                        }
                    }

                    // Room Item Cards
                    val displayedItems = roomItems.filter { categoryFilter == null || it.category == categoryFilter }
                    items(displayedItems) { item ->
                        RoomItemCard(
                            item = item,
                            userCoins = otakuCoins,
                            onToggle = { viewModel.toggleRoomItem(item.id) }
                        )
                    }
                }
            } else {
                // TAB 2: MARCHÉ COSMIQUE
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp, bottom = 92.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    item {
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = OtakuDarkCard),
                            border = androidx.compose.foundation.BorderStroke(1.dp, OtakuTertiary.copy(alpha = 0.3f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("🛍️", fontSize = 32.sp)
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text("Marché Cosmique de Nexus", fontSize = 16.sp, fontWeight = FontWeight.Black, color = OtakuTextPrimary)
                                    Text("Dépensez vos Pièces Otaku gagnées au combat pour personnaliser votre identité et votre chambre !", fontSize = 12.sp, color = OtakuTextSecondary)
                                }
                            }
                        }
                    }

                    items(marketItems) { item ->
                        MarketItemRow(
                            item = item,
                            userCoins = otakuCoins,
                            onBuyOrEquip = { viewModel.buyMarketItem(item.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FilterChip(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(if (isSelected) OtakuPrimary else OtakuDarkCard)
            .border(1.dp, if (isSelected) OtakuPrimary else OtakuDarkBorder, RoundedCornerShape(20.dp))
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = if (isSelected) Color.White else OtakuTextSecondary
        )
    }
}

@Composable
fun RoomItemCard(
    item: OtakuRoomItem,
    userCoins: Int,
    onToggle: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = OtakuDarkCard),
        border = androidx.compose.foundation.BorderStroke(1.dp, if (item.isPlaced) OtakuPrimary else OtakuDarkBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth().testTag("room_item_${item.id}")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(OtakuDarkCardElevated),
                    contentAlignment = Alignment.Center
                ) {
                    Text(item.icon, fontSize = 22.sp)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(item.name, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = OtakuTextPrimary)
                    Text("Univers : ${item.animeReference}", fontSize = 11.sp, color = OtakuTextMuted)
                }
            }

            if (!item.isUnlocked) {
                Button(
                    onClick = onToggle,
                    colors = ButtonDefaults.buttonColors(containerColor = OtakuTertiary),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Débloquer (${item.price} 🪙)", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            } else {
                Button(
                    onClick = onToggle,
                    colors = ButtonDefaults.buttonColors(containerColor = if (item.isPlaced) OtakuPrimary else OtakuDarkCardElevated),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        if (item.isPlaced) "Installé ✓" else "Installer",
                        color = if (item.isPlaced) Color.White else OtakuTextSecondary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun MarketItemRow(
    item: NexusMarketItem,
    userCoins: Int,
    onBuyOrEquip: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = OtakuDarkCard),
        border = androidx.compose.foundation.BorderStroke(1.dp, if (item.isEquipped) OtakuPrimary else OtakuDarkBorder),
        modifier = Modifier.fillMaxWidth().testTag("market_item_${item.id}")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(CircleShape)
                        .background(OtakuDarkCardElevated),
                    contentAlignment = Alignment.Center
                ) {
                    Text(item.icon, fontSize = 24.sp)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(item.name, fontSize = 14.sp, fontWeight = FontWeight.Black, color = OtakuTextPrimary)
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(OtakuSecondary.copy(alpha = 0.15f))
                                .padding(horizontal = 4.dp, vertical = 1.dp)
                        ) {
                            Text(item.category, fontSize = 9.sp, color = OtakuSecondary, fontWeight = FontWeight.Bold)
                        }
                    }
                    Text(item.description, fontSize = 12.sp, color = OtakuTextSecondary, lineHeight = 16.sp)
                }
            }

            Spacer(modifier = Modifier.width(10.dp))

            if (!item.isPurchased) {
                Button(
                    onClick = onBuyOrEquip,
                    colors = ButtonDefaults.buttonColors(containerColor = OtakuTertiary),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("${item.priceCoins} 🪙", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            } else {
                OutlinedButton(
                    onClick = onBuyOrEquip,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = if (item.isEquipped) OtakuPrimary else OtakuTextSecondary
                    )
                ) {
                    Text(if (item.isEquipped) "Actif ✓" else "Équiper", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
