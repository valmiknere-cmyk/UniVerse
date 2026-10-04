package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BadgeItem
import com.example.ui.theme.*
import com.example.viewmodel.UniverseUiState

@Composable
fun BadgesScreen(
    uiState: UniverseUiState,
    onSelectBadge: (BadgeItem) -> Unit
) {
    var selectedCategory by remember { mutableStateOf("All") }
    val categories = listOf("All", "Streak", "Mastery", "Community", "Quiz", "Focus")

    val filteredBadges = uiState.badges.filter {
        if (selectedCategory == "All") true else it.category == selectedCategory
    }

    val unlockedCount = uiState.badges.count { it.isUnlocked }
    val totalCount = uiState.badges.size

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Top XP & Level Showcase Banner
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = CosmicNavyDark),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Level ${uiState.user.level} • ${uiState.user.levelTitle}",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                        Text(
                            text = "${uiState.user.xp} Cosmic XP Earned",
                            fontSize = 12.sp,
                            color = CosmicLavenderAccent
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = CosmicSuccess.copy(alpha = 0.2f)
                    ) {
                        Text(
                            text = "$unlockedCount / $totalCount Badges Unlocked",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = CosmicSuccess,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Progress to next level
                val currentLevelBase = (uiState.user.level - 1) * 500
                val progressInLevel = (uiState.user.xp - currentLevelBase).toFloat() / 500f
                LinearProgressIndicator(
                    progress = { progressInLevel.coerceIn(0f, 1f) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(CircleShape),
                    color = CosmicPurplePrimary,
                    trackColor = Color.White.copy(alpha = 0.15f)
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Next: Level ${uiState.user.level + 1}",
                        fontSize = 11.sp,
                        color = CosmicLavenderAccent
                    )
                    Text(
                        text = "${uiState.user.nextLevelXp - uiState.user.xp} XP to level up",
                        fontSize = 11.sp,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        // Category Filter
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories) { cat ->
                val isSelected = selectedCategory == cat
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = if (isSelected) CosmicPurplePrimary else MaterialTheme.colorScheme.surface,
                    border = if (isSelected) null else androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)),
                    modifier = Modifier.clickable { selectedCategory = cat }
                ) {
                    Text(
                        text = cat,
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }
        }

        // Badges List
        LazyColumn(
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 80.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(filteredBadges) { badge ->
                Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (badge.isUnlocked) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.surface.copy(alpha = 0.6f)
                    ),
                    border = if (badge.isUnlocked) androidx.compose.foundation.BorderStroke(1.dp, CosmicPurplePrimary.copy(alpha = 0.4f)) else null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelectBadge(badge) }
                        .testTag("badge_card_${badge.id}")
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Badge Icon
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(
                                    if (badge.isUnlocked) CosmicPurplePrimary.copy(alpha = 0.15f)
                                    else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.12f)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = getBadgeIcon(badge.iconName),
                                contentDescription = null,
                                tint = if (badge.isUnlocked) CosmicPurplePrimary else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
                                modifier = Modifier.size(26.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = badge.title,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (badge.isUnlocked) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant
                                )

                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = if (badge.isUnlocked) CosmicSuccess.copy(alpha = 0.15f) else CosmicWarning.copy(alpha = 0.15f)
                                ) {
                                    Text(
                                        text = if (badge.isUnlocked) "+${badge.rewardXp} XP" else "${badge.progressPercent}%",
                                        color = if (badge.isUnlocked) CosmicSuccess else CosmicWarning,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(3.dp))

                            Text(
                                text = badge.description,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                lineHeight = 16.sp
                            )

                            if (!badge.isUnlocked) {
                                Spacer(modifier = Modifier.height(8.dp))
                                LinearProgressIndicator(
                                    progress = { badge.progressPercent / 100f },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(5.dp)
                                        .clip(CircleShape),
                                    color = CosmicWarning,
                                    trackColor = CosmicWarning.copy(alpha = 0.2f)
                                )
                            } else if (badge.unlockedDate != null) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Unlocked: ${badge.unlockedDate}",
                                    fontSize = 10.sp,
                                    color = CosmicSuccess,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

fun getBadgeIcon(iconName: String): ImageVector {
    return when (iconName) {
        "fire" -> Icons.Default.LocalFireDepartment
        "star" -> Icons.Default.Star
        "leaf" -> Icons.Default.Eco
        "atom" -> Icons.Default.Architecture
        "flask" -> Icons.Default.Science
        "mentor" -> Icons.Default.EmojiEvents
        "timer" -> Icons.Default.Timer
        "trophy" -> Icons.Default.MilitaryTech
        else -> Icons.Default.Verified
    }
}
