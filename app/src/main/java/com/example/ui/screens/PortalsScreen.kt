package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
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
import com.example.data.model.AnimeWorldPortal
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
fun PortalsScreen(
    viewModel: OtakuViewModel,
    onNavigateToArena: () -> Unit = {}
) {
    val worldPortals by viewModel.worldPortals.collectAsStateWithLifecycle()
    val skyGateProgress by viewModel.skyGateProgress.collectAsStateWithLifecycle()
    var selectedWorldForModal by remember { mutableStateOf<AnimeWorldPortal?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .otakuAtmosphericBackground()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp, bottom = 92.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 1. HERO BANNER : LA PORTE DES MONDES
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = OtakuDarkCard),
                    border = androidx.compose.foundation.BorderStroke(1.dp, OtakuPrimary.copy(alpha = 0.3f)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                    modifier = Modifier.fillMaxWidth().testTag("the_gate_hero_card")
                ) {
                    Column {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(160.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.img_the_gate_portals),
                                contentDescription = "La Porte des Mondes",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        Brush.verticalGradient(
                                            colors = listOf(Color.Transparent, Color(0xE60A0716))
                                        )
                                    )
                            )
                            Column(
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(14.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("🌌 THE GATE", fontSize = 11.sp, color = OtakuSecondary, fontWeight = FontWeight.Black)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(OtakuPrimary)
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text("6 Mondes Actifs", fontSize = 9.sp, color = Color.White, fontWeight = FontWeight.Bold)
                                    }
                                }
                                Text(
                                    text = "La Grande Porte des Mondes",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color.White
                                )
                            }
                        }

                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = "Au cœur de Nexus City se dresse l'arche colossale menant vers 6 univers originaux inspirés des genres majeurs de l'animation japonaise. Explorez, apprenez leur histoire et récoltez leurs reliques légendaires.",
                                fontSize = 13.sp,
                                color = OtakuTextSecondary,
                                lineHeight = 18.sp
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            // Sky Gate Progress preview
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Sceau Astral Global", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = OtakuTextPrimary)
                                Text("$skyGateProgress / ${viewModel.skyGateMaxGoal} (${(skyGateProgress * 100) / viewModel.skyGateMaxGoal}%)", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = OtakuPrimary)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            LinearProgressIndicator(
                                progress = { skyGateProgress.toFloat() / viewModel.skyGateMaxGoal },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(6.dp)
                                    .clip(RoundedCornerShape(3.dp)),
                                color = OtakuPrimary,
                                trackColor = OtakuDarkBorder,
                            )
                        }
                    }
                }
            }

            // 2. LIST OF THE 6 ANIME WORLDS
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Les 6 Univers Dimensionnels 🌀",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = OtakuTextPrimary
                    )
                    Text(
                        text = "${worldPortals.count { it.isUnlocked }} / ${worldPortals.size} Débloqués",
                        fontSize = 12.sp,
                        color = OtakuTextMuted
                    )
                }
            }

            items(worldPortals) { world ->
                WorldPortalCard(
                    world = world,
                    onOpenWorldDetails = { selectedWorldForModal = world },
                    onExplore = { viewModel.exploreWorld(world.id) }
                )
            }
        }
    }

    // Modal Details for Selected Anime World
    selectedWorldForModal?.let { world ->
        AlertDialog(
            onDismissRequest = { selectedWorldForModal = null },
            containerColor = OtakuDarkCard,
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(world.icon, fontSize = 26.sp)
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(world.name, fontSize = 18.sp, fontWeight = FontWeight.Black, color = OtakuTextPrimary)
                        Text(world.title, fontSize = 12.sp, color = OtakuPrimary, fontWeight = FontWeight.Bold)
                    }
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(world.themeColor).copy(alpha = 0.15f))
                            .padding(10.dp)
                    ) {
                        Text("Genre : ${world.genre}", fontSize = 12.sp, color = Color(world.themeColor), fontWeight = FontWeight.Bold)
                    }

                    Text(world.description, fontSize = 13.sp, color = OtakuTextSecondary, lineHeight = 18.sp)

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(OtakuDarkCardElevated)
                            .padding(10.dp)
                    ) {
                        Text("📜 Lore & Légendes :", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = OtakuTextPrimary)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(world.loreSnippet, fontSize = 12.sp, color = OtakuTextSecondary, fontStyle = androidx.compose.ui.text.font.FontStyle.Italic)
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("🛡️ Gardien : ${world.guardianName}", fontSize = 12.sp, color = OtakuTextPrimary, fontWeight = FontWeight.Medium)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("🏺 Reliques trouvées :", fontSize = 12.sp, color = OtakuTextSecondary)
                        Text("${world.relicsFound} / ${world.totalRelics}", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = OtakuTertiary)
                    }

                    world.activeAnomaly?.let { anomaly ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(OtakuTertiary.copy(alpha = 0.12f))
                                .padding(8.dp)
                        ) {
                            Text("⚡ Anomalie Actuelle : $anomaly", fontSize = 11.sp, color = OtakuTertiary, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.exploreWorld(world.id)
                        selectedWorldForModal = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = OtakuPrimary)
                ) {
                    Icon(Icons.Default.Explore, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Explorer ce Monde (+150 XP)")
                }
            },
            dismissButton = {
                TextButton(onClick = { selectedWorldForModal = null }) {
                    Text("Fermer", color = OtakuTextSecondary)
                }
            }
        )
    }
}

@Composable
fun WorldPortalCard(
    world: AnimeWorldPortal,
    onOpenWorldDetails: () -> Unit,
    onExplore: () -> Unit
) {
    val themeAccent = Color(world.themeColor)

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = OtakuDarkCard),
        border = androidx.compose.foundation.BorderStroke(1.dp, OtakuDarkBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onOpenWorldDetails() }
            .testTag("world_card_${world.id}")
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
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(themeAccent.copy(alpha = 0.15f))
                            .border(1.dp, themeAccent.copy(alpha = 0.4f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(world.icon, fontSize = 22.sp)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(world.name, fontSize = 16.sp, fontWeight = FontWeight.Black, color = OtakuTextPrimary)
                        Text(world.title, fontSize = 12.sp, color = themeAccent, fontWeight = FontWeight.Bold)
                    }
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (world.isUnlocked) OtakuPrimary.copy(alpha = 0.12f) else OtakuDarkCardElevated)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            if (world.isUnlocked) Icons.Default.LockOpen else Icons.Default.Lock,
                            contentDescription = null,
                            modifier = Modifier.size(12.dp),
                            tint = if (world.isUnlocked) OtakuPrimary else OtakuTextMuted
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            if (world.isUnlocked) "Ouvert" else "Scellé",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (world.isUnlocked) OtakuPrimary else OtakuTextMuted
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            Text(world.description, fontSize = 13.sp, color = OtakuTextSecondary, lineHeight = 18.sp)

            Spacer(modifier = Modifier.height(12.dp))

            // Completion Progress & Relics
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Exploration : ${world.completionPercent}%", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = OtakuTextPrimary)
                Text("🏺 Reliques : ${world.relicsFound}/${world.totalRelics}", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = OtakuTertiary)
            }
            Spacer(modifier = Modifier.height(4.dp))
            LinearProgressIndicator(
                progress = { world.completionPercent / 100f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = themeAccent,
                trackColor = OtakuDarkBorder,
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                OutlinedButton(
                    onClick = onOpenWorldDetails,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = OtakuTextPrimary)
                ) {
                    Text("Examiner le Lore", fontSize = 12.sp)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = onExplore,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = themeAccent)
                ) {
                    Icon(Icons.Default.Explore, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Explorer (+150 XP)", fontSize = 12.sp)
                }
            }
        }
    }
}
