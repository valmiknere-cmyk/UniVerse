package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import com.example.viewmodel.UniverseUiState

@Composable
fun SettingsScreen(
    uiState: UniverseUiState,
    onToggleDarkMode: () -> Unit
) {
    var studyReminderNotifications by remember { mutableStateOf(true) }
    var offlineDownloadWifiOnly by remember { mutableStateOf(true) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Appearance
        item {
            Text(
                text = "Appearance & Interface",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = CosmicPurplePrimary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column {
                    ProfileMenuItem(
                        icon = Icons.Default.DarkMode,
                        title = "Dark Cosmic Theme",
                        isToggle = true,
                        toggleState = uiState.isDarkMode,
                        onToggle = onToggleDarkMode
                    )
                }
            }
        }

        // Notifications & Reminders
        item {
            Text(
                text = "Notifications & Reminders",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = CosmicPurplePrimary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column {
                    ProfileMenuItem(
                        icon = Icons.Default.NotificationsActive,
                        title = "Daily Study Target Reminders",
                        isToggle = true,
                        toggleState = studyReminderNotifications,
                        onToggle = { studyReminderNotifications = !studyReminderNotifications }
                    )
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                    ProfileMenuItem(
                        icon = Icons.Default.Wifi,
                        title = "Download offline notes on Wi-Fi only",
                        isToggle = true,
                        toggleState = offlineDownloadWifiOnly,
                        onToggle = { offlineDownloadWifiOnly = !offlineDownloadWifiOnly }
                    )
                }
            }
        }

        // About UniVerse
        item {
            Text(
                text = "About UniVerse",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = CosmicPurplePrimary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "UniVerse — Student Super App",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Version 1.0.0 • Build 2026.10",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Tagline: \"One App. Every Student. Infinite Possibilities.\"\nBrand Mantra: \"Learn • Grow • Achieve.\"\n\nAll student-facing core learning features are completely FREE. Designed with cosmic love for students worldwide.",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 17.sp
                    )
                }
            }
        }
    }
}
