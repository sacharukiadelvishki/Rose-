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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ClubEntity
import com.example.data.model.EventEntity
import com.example.data.model.PostEntity
import com.example.ui.OtakuViewModel
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
fun SearchScreen(
    viewModel: OtakuViewModel,
    searchQuery: String,
    clubs: List<ClubEntity>,
    posts: List<PostEntity>,
    events: List<EventEntity>
) {
    var searchFilter by remember { mutableStateOf("Tous") }
    val filters = listOf("Tous", "Anime & Clubs", "Publications", "Événements")

    val q = searchQuery.trim().lowercase()

    val matchedClubs = clubs.filter {
        q.isEmpty() || it.name.lowercase().contains(q) || it.animeUniverse.lowercase().contains(q) || it.tags.lowercase().contains(q)
    }

    val matchedPosts = posts.filter {
        q.isEmpty() || it.content.lowercase().contains(q) || it.authorName.lowercase().contains(q) || it.clubName.lowercase().contains(q)
    }

    val matchedEvents = events.filter {
        q.isEmpty() || it.title.lowercase().contains(q) || it.hostClub.lowercase().contains(q) || it.description.lowercase().contains(q)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Search bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { viewModel.setSearchQuery(it) },
            placeholder = { Text("Rechercher un anime, club, otaku, tournoi...", color = OtakuTextMuted, fontSize = 13.sp) },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = OtakuSecondary)
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { viewModel.setSearchQuery("") }) {
                        Icon(imageVector = Icons.Default.Clear, contentDescription = "Effacer", tint = OtakuTextMuted)
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .testTag("input_search"),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = OtakuDarkCard,
                unfocusedContainerColor = OtakuDarkCard,
                focusedTextColor = OtakuTextPrimary,
                unfocusedTextColor = OtakuTextPrimary,
                focusedBorderColor = OtakuSecondary,
                unfocusedBorderColor = OtakuDarkBorder
            ),
            singleLine = true
        )

        // Filter Pills
        LazyRow(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filters) { f ->
                val isSelected = searchFilter == f
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(14.dp))
                        .background(if (isSelected) OtakuSecondary else OtakuDarkCard)
                        .border(1.dp, if (isSelected) OtakuSecondary else OtakuDarkBorder, RoundedCornerShape(14.dp))
                        .clickable { searchFilter = f }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = f,
                        color = if (isSelected) Color.White else OtakuTextSecondary,
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp, bottom = 80.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Clubs results
            if (searchFilter == "Tous" || searchFilter == "Anime & Clubs") {
                if (matchedClubs.isNotEmpty()) {
                    item {
                        Text("🏯 Clubs & Univers (${matchedClubs.size})", fontWeight = FontWeight.Bold, color = OtakuSecondary, fontSize = 14.sp)
                    }
                    items(matchedClubs) { club ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = OtakuDarkCard),
                            border = androidx.compose.foundation.BorderStroke(1.dp, OtakuDarkBorder)
                        ) {
                            Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                                Text(club.bannerEmoji, fontSize = 22.sp)
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(club.name, fontWeight = FontWeight.Bold, color = OtakuTextPrimary, fontSize = 14.sp)
                                    Text("Univers : ${club.animeUniverse} • ${club.memberCount} membres", fontSize = 11.sp, color = OtakuTextMuted)
                                }
                            }
                        }
                    }
                }
            }

            // Events results
            if (searchFilter == "Tous" || searchFilter == "Événements") {
                if (matchedEvents.isNotEmpty()) {
                    item {
                        Text("📅 Tournois & Événements (${matchedEvents.size})", fontWeight = FontWeight.Bold, color = OtakuTertiary, fontSize = 14.sp)
                    }
                    items(matchedEvents) { ev ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = OtakuDarkCard),
                            border = androidx.compose.foundation.BorderStroke(1.dp, OtakuDarkBorder)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(ev.title, fontWeight = FontWeight.Bold, color = OtakuTextPrimary, fontSize = 14.sp)
                                Text("📅 ${ev.dateDisplay} • ${ev.hostClub}", fontSize = 11.sp, color = OtakuSecondary)
                            }
                        }
                    }
                }
            }

            // Posts results
            if (searchFilter == "Tous" || searchFilter == "Publications") {
                if (matchedPosts.isNotEmpty()) {
                    item {
                        Text("💬 Publications (${matchedPosts.size})", fontWeight = FontWeight.Bold, color = OtakuPrimary, fontSize = 14.sp)
                    }
                    items(matchedPosts) { p ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = OtakuDarkCard),
                            border = androidx.compose.foundation.BorderStroke(1.dp, OtakuDarkBorder)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(p.authorAvatar, fontSize = 16.sp)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(p.authorName, fontWeight = FontWeight.Bold, color = OtakuTextPrimary, fontSize = 13.sp)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("• ${p.category}", fontSize = 11.sp, color = OtakuTextMuted)
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(p.content, color = OtakuTextSecondary, fontSize = 12.sp, maxLines = 2)
                            }
                        }
                    }
                }
            }

            // Empty state if no results
            val totalResults = when (searchFilter) {
                "Anime & Clubs" -> matchedClubs.size
                "Publications" -> matchedPosts.size
                "Événements" -> matchedEvents.size
                else -> matchedClubs.size + matchedPosts.size + matchedEvents.size
            }

            if (totalResults == 0) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = OtakuDarkCard),
                        border = androidx.compose.foundation.BorderStroke(1.dp, OtakuDarkBorder)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("🔍", fontSize = 36.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = if (searchQuery.isBlank()) "Commence à taper pour chercher" else "Aucun résultat pour « $searchQuery »",
                                fontWeight = FontWeight.Bold,
                                color = OtakuTextPrimary,
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Essaie avec d'autres mots-clés : One Piece, Shonen, Débat...",
                                color = OtakuTextMuted,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
