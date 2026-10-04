package com.example.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.Course
import com.example.ui.components.FreeBadge
import com.example.ui.theme.*
import com.example.viewmodel.Screen
import com.example.viewmodel.UniverseUiState

@Composable
fun HomeScreen(
    uiState: UniverseUiState,
    onNavigate: (Screen) -> Unit,
    onSelectCourse: (Course) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("home_screen_scroll"),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        // XP Alert Banner if recently awarded
        if (uiState.recentXpAwarded != null) {
            item {
                Surface(
                    color = CosmicSuccess.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, CosmicSuccess),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "✨", fontSize = 18.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "+${uiState.recentXpAwarded} XP: ${uiState.recentXpReason}",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = CosmicSuccess
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = CosmicSuccess,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }

        // Hero Greeting Card
        item {
            HeroGreetingCard(
                userName = uiState.user.name,
                onExploreClick = { onNavigate(Screen.COURSES) }
            )
        }

        // Gamification Level & XP Card
        item {
            Spacer(modifier = Modifier.height(14.dp))
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CosmicNavyDark),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
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
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(CosmicPurplePrimary),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "L${uiState.user.level}",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color.White
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "Level ${uiState.user.level} • ${uiState.user.levelTitle}",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    text = "${uiState.user.xp} Cosmic XP Earned",
                                    fontSize = 11.sp,
                                    color = CosmicLavenderAccent
                                )
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = CosmicWarning.copy(alpha = 0.2f),
                            modifier = Modifier.clickable { onNavigate(Screen.LEADERBOARD) }
                        ) {
                            Text(
                                text = "🏆 Rank #${uiState.user.rank}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = CosmicWarning,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    val currentLevelBase = (uiState.user.level - 1) * 500
                    val progressFraction = (uiState.user.xp - currentLevelBase).toFloat() / 500f
                    LinearProgressIndicator(
                        progress = { progressFraction.coerceIn(0f, 1f) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(CircleShape),
                        color = CosmicPurplePrimary,
                        trackColor = Color.White.copy(alpha = 0.15f)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TextButton(
                            onClick = { onNavigate(Screen.LEADERBOARD) },
                            contentPadding = PaddingValues(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Icon(Icons.Default.Leaderboard, contentDescription = null, tint = CosmicLavenderAccent, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Leaderboard", fontSize = 11.sp, color = CosmicLavenderAccent)
                        }

                        TextButton(
                            onClick = { onNavigate(Screen.BADGES_DETAIL) },
                            contentPadding = PaddingValues(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Icon(Icons.Default.MilitaryTech, contentDescription = null, tint = CosmicSuccess, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("${uiState.user.badgesCount} Badges Unlocked", fontSize = 11.sp, color = CosmicSuccess)
                        }
                    }
                }
            }
        }

        // Progress Section (Circular chart & streaks)
        item {
            Spacer(modifier = Modifier.height(16.dp))
            SectionTitle(title = "Your Progress")
            Spacer(modifier = Modifier.height(8.dp))
            ProgressSummaryCard(
                progressPercent = 68,
                tasksDone = "${uiState.user.tasksCompletedToday}/${uiState.user.tasksTotalToday} tasks done",
                streakDays = "${uiState.user.streakDays} days 🔥"
            )
        }

        // Quick Actions Grid
        item {
            Spacer(modifier = Modifier.height(20.dp))
            SectionTitle(title = "Quick Actions")
            Spacer(modifier = Modifier.height(10.dp))
            QuickActionsGrid(onNavigate = onNavigate)
        }

        // Continue Learning Card
        item {
            Spacer(modifier = Modifier.height(20.dp))
            SectionTitle(title = "Continue Learning")
            Spacer(modifier = Modifier.height(10.dp))
            val topCourse = uiState.courses.firstOrNull()
            if (topCourse != null) {
                ContinueLearningCard(
                    title = "Biology - Plant Kingdom",
                    subtitle = "Chapter 1 • 75% complete",
                    progress = 0.75f,
                    onClick = { onSelectCourse(topCourse) }
                )
            }
        }

        // Recommended Courses
        item {
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Recommended For You",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "See All",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = CosmicPurplePrimary,
                    modifier = Modifier.clickable { onNavigate(Screen.COURSES) }
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(uiState.courses.take(4)) { course ->
                    RecommendedCourseCard(
                        course = course,
                        onClick = { onSelectCourse(course) }
                    )
                }
            }
        }

        // Upcoming Exams Banner
        item {
            Spacer(modifier = Modifier.height(20.dp))
            SectionTitle(title = "Upcoming Target Exams")
            Spacer(modifier = Modifier.height(10.dp))
            UpcomingExamBanner(
                title = "NEET UG 2026",
                dateText = "In 18 Days • High Competition",
                onMockClick = { onNavigate(Screen.EXAM_CENTER) }
            )
        }
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}

@Composable
private fun HeroGreetingCard(
    userName: String,
    onExploreClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = CosmicNavy),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .testTag("hero_greeting_card")
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            // Subtle cosmic background art
            Image(
                painter = painterResource(id = R.drawable.hero_student_1791093436249),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp),
                alpha = 0.35f
            )

            // Gradient overlay
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                CosmicNavy.copy(alpha = 0.95f),
                                CosmicNavy.copy(alpha = 0.5f)
                            )
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(CosmicSuccess)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "LIVE LEARNING",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = CosmicSuccess,
                        letterSpacing = 1.sp
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Good Morning,\n$userName!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    lineHeight = 30.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Small steps every day lead to big dreams.",
                    fontSize = 13.sp,
                    color = CosmicLavenderAccent
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = CosmicPurplePrimary,
                        modifier = Modifier.clickable { onExploreClick() }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = "Start Studying",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ProgressSummaryCard(
    progressPercent: Int,
    tasksDone: String,
    streakDays: String
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .testTag("progress_summary_card")
    ) {
        Row(
            modifier = Modifier
                .padding(18.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Circular progress indicator
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(72.dp)
            ) {
                CircularProgressIndicator(
                    progress = { progressPercent / 100f },
                    modifier = Modifier.fillMaxSize(),
                    color = CosmicPurplePrimary,
                    trackColor = CosmicPurplePrimary.copy(alpha = 0.15f),
                    strokeWidth = 7.dp
                )
                Text(
                    text = "$progressPercent%",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                // Today's Goal
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(CosmicCyan.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = CosmicCyan,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Today's Goal",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = tasksDone,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Study Streak
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(CosmicWarning.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocalFireDepartment,
                            contentDescription = null,
                            tint = CosmicWarning,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Study Streak",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = streakDays,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun QuickActionsGrid(
    onNavigate: (Screen) -> Unit
) {
    val actions = listOf(
        QuickActionItem("Explore\nCourses", Icons.AutoMirrored.Filled.MenuBook, CosmicPurplePrimary) { onNavigate(Screen.COURSES) },
        QuickActionItem("AI Tutor", Icons.Default.SmartToy, CosmicPurpleSecondary) { onNavigate(Screen.AI_TUTOR) },
        QuickActionItem("Study\nPlanner", Icons.Default.CalendarMonth, CosmicCyan) { onNavigate(Screen.PLANNER) },
        QuickActionItem("Mock\nTests", Icons.Default.FactCheck, CosmicSuccess) { onNavigate(Screen.EXAM_CENTER) },
        QuickActionItem("Leaderboard", Icons.Default.Leaderboard, CosmicWarning) { onNavigate(Screen.LEADERBOARD) },
        QuickActionItem("Badges", Icons.Default.MilitaryTech, CosmicPurplePrimary) { onNavigate(Screen.BADGES_DETAIL) },
        QuickActionItem("Community\n& Rooms", Icons.Default.Groups, CosmicCyan) { onNavigate(Screen.COMMUNITY) },
        QuickActionItem("Notes &\nLibrary", Icons.Default.Description, CosmicError) { onNavigate(Screen.NOTES) }
    )

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            actions.take(4).forEach { action ->
                QuickActionCard(action = action, modifier = Modifier.weight(1f))
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            actions.drop(4).take(4).forEach { action ->
                QuickActionCard(action = action, modifier = Modifier.weight(1f))
            }
        }
    }
}

data class QuickActionItem(
    val title: String,
    val icon: ImageVector,
    val color: Color,
    val onClick: () -> Unit
)

@Composable
private fun QuickActionCard(
    action: QuickActionItem,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable { action.onClick() }
            .testTag("quick_action_${action.title.replace("\n", "_").lowercase()}")
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 14.dp, horizontal = 4.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(action.color.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = action.icon,
                    contentDescription = null,
                    tint = action.color,
                    modifier = Modifier.size(22.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = action.title,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = 14.sp,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

@Composable
private fun ContinueLearningCard(
    title: String,
    subtitle: String,
    progress: Float,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable { onClick() }
            .testTag("continue_learning_card")
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(CosmicSuccess.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Eco,
                    contentDescription = null,
                    tint = CosmicSuccess,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(CircleShape),
                    color = CosmicPurplePrimary,
                    trackColor = CosmicPurplePrimary.copy(alpha = 0.15f)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(CosmicPurplePrimary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Resume",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
private fun RecommendedCourseCard(
    course: Course,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .width(220.dp)
            .clickable { onClick() }
            .testTag("recommended_course_${course.id}")
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                FreeBadge()
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = CosmicWarning,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "${course.rating}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = course.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1
            )
            Text(
                text = course.targetExamOrClass,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${course.totalLessons} Lessons",
                    fontSize = 11.sp,
                    color = CosmicPurplePrimary,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "${course.progressPercent}%",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun UpcomingExamBanner(
    title: String,
    dateText: String,
    onMockClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = CosmicNavyDark),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = dateText,
                    fontSize = 12.sp,
                    color = CosmicLavenderAccent
                )
            }
            Button(
                onClick = onMockClick,
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = CosmicPurplePrimary)
            ) {
                Text("Take Mock", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
