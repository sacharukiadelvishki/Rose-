package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.NotificationItemEntity
import com.example.ui.OtakuViewModel
import com.example.ui.theme.OtakuDarkBackground
import com.example.ui.theme.OtakuDarkCard
import com.example.ui.theme.OtakuDarkCardElevated
import com.example.ui.theme.OtakuPrimary
import com.example.ui.theme.OtakuSecondary
import com.example.ui.theme.OtakuTextMuted
import com.example.ui.theme.OtakuTextSecondary

@Composable
fun NotificationsScreen(
    viewModel: OtakuViewModel,
    notifications: List<NotificationItemEntity>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(16.dp, bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Notifications Otaku 🔔",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Button(
                    onClick = { viewModel.markNotificationsRead() },
                    colors = ButtonDefaults.buttonColors(containerColor = OtakuDarkCardElevated),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.testTag("btn_mark_all_read")
                ) {
                    Text("Tout marquer comme lu", fontSize = 11.sp, color = OtakuSecondary)
                }
            }
        }

        if (notifications.isEmpty()) {
            item {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Aucune notification pour le moment.", color = OtakuTextMuted, fontSize = 13.sp)
                }
            }
        } else {
            items(notifications, key = { it.id }) { notif ->
                NotificationRow(notification = notif)
            }
        }
    }
}

@Composable
fun NotificationRow(notification: NotificationItemEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("notif_item_${notification.id}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (!notification.isRead) Color(0xFF261D42) else OtakuDarkCard
        ),
        border = if (!notification.isRead) androidx.compose.foundation.BorderStroke(1.dp, OtakuPrimary.copy(alpha = 0.5f)) else null
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(OtakuDarkCardElevated),
                contentAlignment = Alignment.Center
            ) {
                Text(text = notification.typeIcon, fontSize = 20.sp)
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = notification.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color.White
                    )
                    Text(
                        text = notification.timestampStr,
                        fontSize = 10.sp,
                        color = OtakuTextMuted
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = notification.message,
                    fontSize = 12.sp,
                    color = OtakuTextSecondary,
                    lineHeight = 16.sp
                )
            }
        }
    }
}
