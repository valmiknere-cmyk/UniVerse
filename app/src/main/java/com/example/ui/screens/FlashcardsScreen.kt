package com.example.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.FlashcardDeck
import com.example.ui.theme.*
import com.example.viewmodel.UniverseUiState

@Composable
fun FlashcardsScreen(
    uiState: UniverseUiState,
    onSelectDeck: (FlashcardDeck) -> Unit,
    onFlipCard: () -> Unit,
    onNextCard: () -> Unit,
    onPrevCard: () -> Unit,
    onToggleMastery: (String) -> Unit
) {
    val activeDeck = uiState.selectedDeck ?: uiState.flashcardDecks.firstOrNull()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Deck Selector
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(uiState.flashcardDecks) { deck ->
                val isSelected = activeDeck?.id == deck.id
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = if (isSelected) CosmicPurplePrimary else MaterialTheme.colorScheme.surface,
                    border = if (isSelected) null else androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)),
                    modifier = Modifier.clickable { onSelectDeck(deck) }
                ) {
                    Text(
                        text = deck.title,
                        fontSize = 13.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                    )
                }
            }
        }

        if (activeDeck == null || activeDeck.cards.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No flashcards available in this deck.")
            }
            return
        }

        val currentCard = activeDeck.cards.getOrNull(uiState.currentFlashcardIndex) ?: activeDeck.cards.first()
        val masteredCount = activeDeck.cards.count { it.isMastered }
        val totalCount = activeDeck.cards.size

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Card Counter & Mastery
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Card ${uiState.currentFlashcardIndex + 1} of $totalCount",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Mastered: $masteredCount / $totalCount",
                    fontSize = 13.sp,
                    color = CosmicSuccess,
                    fontWeight = FontWeight.Bold
                )
            }

            // Interactive Flashcard with Flip Animation
            val rotation by animateFloatAsState(
                targetValue = if (uiState.isFlashcardFlipped) 180f else 0f,
                animationSpec = tween(durationMillis = 400),
                label = "flip"
            )

            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (uiState.isFlashcardFlipped) CosmicNavy else MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .graphicsLayer {
                        rotationY = rotation
                        cameraDistance = 12f * density
                    }
                    .clickable { onFlipCard() }
                    .testTag("flashcard_container")
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (rotation <= 90f) {
                        // Front side
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = CosmicPurplePrimary.copy(alpha = 0.12f)
                            ) {
                                Text(
                                    text = "QUESTION (Tap to flip)",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = CosmicPurplePrimary,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                            Text(
                                text = currentCard.front,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                                textAlign = TextAlign.Center,
                                lineHeight = 26.sp
                            )
                        }
                    } else {
                        // Back side (mirrored back)
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.graphicsLayer { rotationY = 180f }
                        ) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = CosmicSuccess.copy(alpha = 0.2f)
                            ) {
                                Text(
                                    text = "ANSWER & REASONING",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = CosmicSuccess,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                            Text(
                                text = currentCard.back,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                lineHeight = 24.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Mastery buttons: Still Learning vs Mastered
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = {
                        onToggleMastery(currentCard.id)
                        onNextCard()
                    },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Needs Review")
                }

                Button(
                    onClick = {
                        if (!currentCard.isMastered) onToggleMastery(currentCard.id)
                        onNextCard()
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CosmicSuccess),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Mastered!")
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Navigation arrows
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = onPrevCard) { Text("← Previous") }
                TextButton(onClick = onNextCard) { Text("Next →") }
            }
        }
    }
}
