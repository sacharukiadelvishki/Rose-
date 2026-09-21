package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.LinkOff
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.SocialAccountInfo
import com.example.ui.i18n.LocalOtakuStrings
import com.example.ui.theme.DiffBeginner
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
fun SocialAccountsSection(
    socialAccounts: List<SocialAccountInfo>,
    onConnect: (providerId: String, username: String) -> Unit,
    onDisconnect: (providerId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val strings = LocalOtakuStrings.current
    var selectedAccountForDialog by remember { mutableStateOf<SocialAccountInfo?>(null) }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = OtakuDarkCard),
        border = BorderStroke(1.dp, OtakuDarkBorder)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(Color(0x2205D9E8)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Security,
                            contentDescription = null,
                            tint = OtakuSecondary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = strings.linkedAccountsTitle,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = OtakuTextPrimary
                        )
                        Text(
                            text = "Google • Facebook • Messenger • WeChat",
                            fontSize = 11.sp,
                            color = OtakuTextMuted
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0x22BD00FF))
                        .padding(horizontal = 7.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = "+150 XP",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black,
                        color = OtakuTertiary
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Social Accounts list
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                socialAccounts.forEach { account ->
                    SocialAccountRow(
                        account = account,
                        onConnectClick = { selectedAccountForDialog = account },
                        onDisconnectClick = { onDisconnect(account.providerId) }
                    )
                }
            }
        }
    }

    selectedAccountForDialog?.let { targetAccount ->
        SocialConnectDialog(
            account = targetAccount,
            onDismiss = { selectedAccountForDialog = null },
            onConfirm = { handle ->
                onConnect(targetAccount.providerId, handle)
                selectedAccountForDialog = null
            }
        )
    }
}

@Composable
fun SocialAccountRow(
    account: SocialAccountInfo,
    onConnectClick: () -> Unit,
    onDisconnectClick: () -> Unit
) {
    val strings = LocalOtakuStrings.current
    val (brandColor, brandBadge, defaultHandle) = when (account.providerId) {
        "google" -> Triple(Color(0xFFEA4335), "G", "@google_otaku")
        "facebook" -> Triple(Color(0xFF1877F2), "f", "@facebook_user")
        "messenger" -> Triple(Color(0xFF00B2FF), "⚡", "@messenger_chat")
        "wechat" -> Triple(Color(0xFF07C160), "微", "@wechat_id")
        else -> Triple(OtakuSecondary, "★", "@user")
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(OtakuDarkCardElevated)
            .border(
                width = 1.dp,
                color = if (account.isConnected) brandColor.copy(alpha = 0.5f) else OtakuDarkBorder,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(12.dp)
            .testTag("social_row_${account.providerId}")
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                // Brand Avatar Badge
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(brandColor),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = brandBadge,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Black
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = account.providerName,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = OtakuTextPrimary
                        )
                        if (account.isConnected) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "Connecté",
                                tint = DiffBeginner,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }

                    Text(
                        text = if (account.isConnected) {
                            account.connectedUsername ?: defaultHandle
                        } else {
                            strings.linkedStatusNotConnected
                        },
                        fontSize = 11.sp,
                        color = if (account.isConnected) DiffBeginner else OtakuTextMuted
                    )
                }
            }

            // Action Button
            if (account.isConnected) {
                OutlinedButton(
                    onClick = onDisconnectClick,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFEF4444)),
                    border = BorderStroke(1.dp, Color(0xFFEF4444).copy(alpha = 0.4f)),
                    modifier = Modifier.testTag("btn_disconnect_${account.providerId}")
                ) {
                    Icon(imageVector = Icons.Default.LinkOff, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = strings.btnDisconnect, fontSize = 11.sp)
                }
            } else {
                Button(
                    onClick = onConnectClick,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = brandColor),
                    modifier = Modifier.testTag("btn_connect_${account.providerId}")
                ) {
                    Icon(imageVector = Icons.Default.Link, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = strings.btnConnect, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun SocialConnectDialog(
    account: SocialAccountInfo,
    onDismiss: () -> Unit,
    onConfirm: (username: String) -> Unit
) {
    val strings = LocalOtakuStrings.current
    var usernameInput by remember {
        mutableStateOf(
            when (account.providerId) {
                "google" -> "otaku.master@gmail.com"
                "facebook" -> "otaku_fan_official"
                "messenger" -> "messenger.otaku.99"
                "wechat" -> "wxid_otaku2026"
                else -> "user_identifier"
            }
        )
    }

    val (brandColor, brandTitle) = when (account.providerId) {
        "google" -> Color(0xFFEA4335) to strings.continueWithGoogle
        "facebook" -> Color(0xFF1877F2) to strings.continueWithFacebook
        "messenger" -> Color(0xFF00B2FF) to strings.continueWithMessenger
        "wechat" -> Color(0xFF07C160) to strings.continueWithWeChat
        else -> OtakuPrimary to "Connexion Sociale"
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(brandColor),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = account.iconEmoji,
                        fontSize = 14.sp
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = brandTitle,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = OtakuTextPrimary
                )
            }
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "Lier votre profil ${account.providerName} pour sauvegarder votre progression de rang S, vos badges et synchroniser vos duels.",
                    fontSize = 12.sp,
                    color = OtakuTextSecondary,
                    lineHeight = 16.sp
                )

                OutlinedTextField(
                    value = usernameInput,
                    onValueChange = { usernameInput = it },
                    label = { Text("Identifiant / Email ${account.providerName}", color = OtakuTextMuted, fontSize = 12.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = OtakuTextPrimary,
                        unfocusedTextColor = OtakuTextPrimary,
                        focusedBorderColor = brandColor,
                        unfocusedBorderColor = OtakuDarkBorder
                    ),
                    singleLine = true
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0x22BD00FF))
                        .padding(8.dp)
                ) {
                    Text(
                        text = "🎁 Récompense immédiate : +150 XP ajoutés à votre compte Otaku Hub !",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = OtakuTertiary
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(usernameInput) },
                colors = ButtonDefaults.buttonColors(containerColor = brandColor),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.testTag("btn_confirm_connect_${account.providerId}")
            ) {
                Text(text = "Autoriser & Lier (+150 XP)", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = strings.btnCancel, color = OtakuTextMuted)
            }
        },
        containerColor = OtakuDarkSurface,
        shape = RoundedCornerShape(18.dp)
    )
}
