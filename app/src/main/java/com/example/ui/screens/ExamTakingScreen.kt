package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.MockTest
import com.example.ui.theme.*
import com.example.viewmodel.UniverseUiState

@Composable
fun ExamTakingScreen(
    uiState: UniverseUiState,
    onSelectAnswer: (Int, Int) -> Unit,
    onNextQuestion: () -> Unit,
    onPrevQuestion: () -> Unit,
    onSubmitExam: () -> Unit,
    onClose: () -> Unit
) {
    val test = uiState.selectedMockTest
    if (test == null || test.questions.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Sample practice questions ready for this test!")
                Spacer(modifier = Modifier.height(12.dp))
                Button(onClick = onClose) { Text("Back to Exam Center") }
            }
        }
        return
    }

    val currentQuestion = test.questions.getOrNull(uiState.activeQuestionIndex) ?: test.questions.first()
    val selectedOption = uiState.selectedAnswers[currentQuestion.id]

    val hours = uiState.examTimeRemainingSeconds / 3600
    val minutes = (uiState.examTimeRemainingSeconds % 3600) / 60
    val seconds = uiState.examTimeRemainingSeconds % 60
    val timeFormatted = "%02d:%02d:%02d".format(hours, minutes, seconds)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Top Timer & Header Bar
        Surface(
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 3.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = test.title,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Question ${uiState.activeQuestionIndex + 1} of ${test.questions.size}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(CosmicNavyDark)
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Timer,
                        contentDescription = "Timer",
                        tint = CosmicLavenderAccent,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = timeFormatted,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }

        // Question Navigator Palette
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(test.questions) { index, q ->
                val isAnswered = uiState.selectedAnswers.containsKey(q.id)
                val isCurrent = index == uiState.activeQuestionIndex
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(
                            when {
                                isCurrent -> CosmicPurplePrimary
                                isAnswered -> CosmicSuccess
                                else -> MaterialTheme.colorScheme.surfaceVariant
                            }
                        )
                        .clickable {
                            // Can click to jump to questions
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${index + 1}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isCurrent || isAnswered) Color.White else MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        // Question & Options Container
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = CosmicPurplePrimary.copy(alpha = 0.1f)
                    ) {
                        Text(
                            text = "+4 Marks • -1 Negative",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = CosmicPurplePrimary,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = currentQuestion.questionText,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 22.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Options list
                    currentQuestion.options.forEachIndexed { optIndex, optionText ->
                        val isChosen = selectedOption == optIndex
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (isChosen) CosmicPurplePrimary.copy(alpha = 0.12f) else MaterialTheme.colorScheme.background,
                            border = androidx.compose.foundation.BorderStroke(
                                1.5.dp,
                                if (isChosen) CosmicPurplePrimary else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 5.dp)
                                .clickable {
                                    onSelectAnswer(currentQuestion.id, optIndex)
                                }
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(14.dp)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clip(CircleShape)
                                        .background(
                                            if (isChosen) CosmicPurplePrimary else Color.Transparent
                                        )
                                        .then(
                                            if (!isChosen) Modifier.background(Color.Transparent) else Modifier
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = ('A' + optIndex).toString(),
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isChosen) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                Text(
                                    text = optionText,
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Bottom Actions Bar
        Surface(
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = onPrevQuestion,
                    enabled = uiState.activeQuestionIndex > 0,
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Previous")
                }

                Button(
                    onClick = onSubmitExam,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CosmicError)
                ) {
                    Text("Submit Test", fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = onNextQuestion,
                    enabled = uiState.activeQuestionIndex < test.questions.size - 1,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CosmicPurplePrimary)
                ) {
                    Text("Next")
                }
            }
        }
    }
}

@Composable
fun ExamResultScreen(
    uiState: UniverseUiState,
    onBackToCenter: () -> Unit
) {
    val test = uiState.selectedMockTest
    val total = test?.questions?.size ?: 0
    val attempted = uiState.selectedAnswers.size
    val accuracy = if (attempted > 0) (uiState.examCorrectCount * 100) / attempted else 0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(CosmicSuccess.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = CosmicSuccess,
                modifier = Modifier.size(44.dp)
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = "Test Completed!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = test?.title ?: "Mock Test",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ResultStat(label = "Total Score", value = "${uiState.examScore}", color = CosmicPurplePrimary)
                    ResultStat(label = "Accuracy", value = "$accuracy%", color = CosmicSuccess)
                    ResultStat(label = "Attempted", value = "$attempted / $total", color = CosmicCyan)
                }

                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = "✅ Correct: ${uiState.examCorrectCount}",
                        fontWeight = FontWeight.Bold,
                        color = CosmicSuccess,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "❌ Incorrect: ${uiState.examIncorrectCount}",
                        fontWeight = FontWeight.Bold,
                        color = CosmicError,
                        fontSize = 14.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Question explanations
        Text(
            text = "Question-wise Solutions & Explanations",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(10.dp))

        test?.questions?.forEachIndexed { idx, q ->
            val userPick = uiState.selectedAnswers[q.id]
            val isCorrect = userPick == q.correctOptionIndex

            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Q${idx + 1}: ${if (isCorrect) "✅ Correct" else "❌ Incorrect"}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = if (isCorrect) CosmicSuccess else CosmicError
                        )
                        Text(
                            text = "Correct Answer: Option ${('A' + q.correctOptionIndex)}",
                            fontSize = 12.sp,
                            color = CosmicPurplePrimary,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = q.questionText, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "💡 Explanation: ${q.explanation}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 16.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onBackToCenter,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = CosmicPurplePrimary),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("Back to Exam Center", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun ResultStat(label: String, value: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = color)
        Text(text = label, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}
