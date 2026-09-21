package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Event
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.EventEntity
import com.example.ui.OtakuViewModel
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
import com.example.ui.theme.OtakuTextSecondary

@Composable
fun EventsScreen(
    viewModel: OtakuViewModel,
    events: List<EventEntity>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .otakuAtmosphericBackground(),
        contentPadding = PaddingValues(16.dp, bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Tournament Banner Header
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = OtakuDarkSurface)
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFF331628),
                                    Color(0xFF221338),
                                    OtakuDarkSurface
                                )
                            )
                        )
                        .padding(16.dp)
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "🔥", fontSize = 24.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "TOURNOIS & ÉVÉNEMENTS COMMUNAUTAIRES",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Black,
                                    color = OtakuTertiary,
                                    letterSpacing = 1.sp
                                )
                                Text(
                                    text = "Qualifications • Éliminations • Demi-finales • Finale",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Inscris-toi aux tournois officiels ou aux soirées de débats de clubs pour gagner des points, des badges et du prestige.",
                            fontSize = 12.sp,
                            color = OtakuTextSecondary,
                            lineHeight = 16.sp
                        )
                    }
                }
            }
        }

        item {
            Text(
                text = "Événements à Venir 📅",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        if (events.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = OtakuDarkCard)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("📅", fontSize = 32.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Aucun événement pour le moment",
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 15.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Les tournois et soirées communautaires apparaîtront bientôt ici !",
                            color = OtakuTextMuted,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        } else {
            items(events, key = { it.id }) { event ->
                EventItemCard(
                    event = event,
                    onToggleRegister = { viewModel.toggleRegisterEvent(event) }
                )
            }
        }

        // Bracket Explanatory Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = OtakuDarkCard),
                border = androidx.compose.foundation.BorderStroke(1.dp, OtakuDarkBorder)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "🏆 Arbre du Grand Tournoi (128 Joueurs)",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = OtakuTertiary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "1. Phase 1 : 128 Participants (Duels rapides 3 rounds)\n" +
                                "2. Huitièmes & Quarts : 32 puis 16 qualifiés\n" +
                                "3. Demi-Finales : 4 meilleurs Otaku en direct\n" +
                                "4. Grande Finale : Duel 5 rounds pour le Titre de Champion !",
                        fontSize = 12.sp,
                        color = OtakuTextSecondary,
                        lineHeight = 18.sp
                    )
                }
            }
        }
    }
}

@Composable
fun EventItemCard(
    event: EventEntity,
    onToggleRegister: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("event_card_${event.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = OtakuDarkCard)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(OtakuPrimary.copy(alpha = 0.15f))
                        .border(1.dp, OtakuPrimary.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(event.type, fontSize = 10.sp, color = OtakuPrimary, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Organisé par ${event.hostClub}",
                    fontSize = 11.sp,
                    color = OtakuTextMuted
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = event.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "📅 ${event.dateDisplay}",
                fontSize = 12.sp,
                color = OtakuSecondary,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = event.description,
                fontSize = 12.sp,
                color = OtakuTextSecondary,
                lineHeight = 16.sp
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "🎁 Récompense : ${event.reward}",
                fontSize = 12.sp,
                color = OtakuTertiary,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Capacity & Register action
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Participants : ${event.participantsCount}/${event.maxParticipants}",
                        fontSize = 11.sp,
                        color = OtakuTextMuted
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    LinearProgressIndicator(
                        progress = { event.participantsCount.toFloat() / event.maxParticipants.toFloat() },
                        modifier = Modifier.fillMaxWidth(0.9f).height(6.dp).clip(RoundedCornerShape(3.dp)),
                        color = OtakuSecondary,
                        trackColor = OtakuDarkBorder
                    )
                }

                Button(
                    onClick = onToggleRegister,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (event.isRegistered) OtakuDarkCardElevated else OtakuPrimary
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.testTag("btn_register_event_${event.id}")
                ) {
                    if (event.isRegistered) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = OtakuSecondary, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Inscrit", color = OtakuSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    } else {
                        Text("S'inscrire", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
