package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.UniVerseBottomNav
import com.example.ui.components.UniVerseTopBar
import com.example.ui.screens.*
import com.example.ui.theme.UniVerseTheme
import com.example.viewmodel.Screen
import com.example.viewmodel.UniverseViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: UniverseViewModel = viewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            UniVerseTheme(darkTheme = uiState.isDarkMode) {
                UniVerseApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun UniVerseApp(viewModel: UniverseViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Handle back button for sub-screens
    if (uiState.currentScreen != Screen.HOME && uiState.currentScreen != Screen.SPLASH && uiState.currentScreen != Screen.AUTH) {
        BackHandler {
            viewModel.goBack()
        }
    }

    val isTopLevelScreen = uiState.currentScreen in listOf(
        Screen.HOME,
        Screen.COURSES,
        Screen.PLANNER,
        Screen.COMMUNITY,
        Screen.PROFILE
    )

    val showBottomBar = uiState.currentScreen !in listOf(
        Screen.SPLASH,
        Screen.AUTH,
        Screen.EXAM_TAKING,
        Screen.EXAM_RESULT,
        Screen.STUDY_ROOM_DETAIL
    )

    val showTopBar = uiState.currentScreen !in listOf(
        Screen.SPLASH,
        Screen.AUTH,
        Screen.EXAM_TAKING,
        Screen.EXAM_RESULT,
        Screen.STUDY_ROOM_DETAIL
    )

    val topBarTitle = when (uiState.currentScreen) {
        Screen.HOME -> null
        Screen.COURSES -> "Explore Courses"
        Screen.COURSE_DETAIL -> uiState.selectedCourse?.title ?: "Course Details"
        Screen.AI_TUTOR -> "AI Tutor"
        Screen.PLANNER -> "Study Planner"
        Screen.EXAM_CENTER -> "Exam Center & Mocks"
        Screen.NOTES -> "Notes & Library"
        Screen.FLASHCARDS -> "Flashcards"
        Screen.COMMUNITY -> "Community & Study Circles"
        Screen.CAREER_HUB -> "Career Hub"
        Screen.FINANCE -> "Student Finance"
        Screen.PROFILE -> "Profile & Settings"
        Screen.SETTINGS -> "Settings"
        Screen.NOTIFICATIONS -> "Notifications"
        Screen.AI_NOTES_GEN -> "AI Notes Generator"
        Screen.AI_QUIZ_GEN -> "AI Quiz Generator"
        Screen.LEADERBOARD -> "Cosmic Leaderboard"
        Screen.BADGES_DETAIL -> "Badges & Achievements"
        else -> null
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (showTopBar) {
                UniVerseTopBar(
                    title = topBarTitle,
                    showBackButton = !isTopLevelScreen,
                    onBackClick = { viewModel.goBack() },
                    onNotificationClick = { viewModel.navigateTo(Screen.NOTIFICATIONS) },
                    onProfileClick = { viewModel.navigateTo(Screen.PROFILE) },
                    onSearchClick = { viewModel.navigateTo(Screen.COURSES) },
                    unreadCount = uiState.unreadNotificationsCount
                )
            }
        },
        bottomBar = {
            if (showBottomBar) {
                UniVerseBottomNav(
                    currentScreen = uiState.currentScreen,
                    onNavigate = { screen -> viewModel.navigateTo(screen) }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (uiState.currentScreen) {
                Screen.SPLASH -> {
                    SplashScreen(
                        onGetStarted = { viewModel.navigateTo(Screen.AUTH) }
                    )
                }

                Screen.AUTH -> {
                    AuthScreen(
                        onLoginSuccess = { viewModel.loginDemo() }
                    )
                }

                Screen.HOME -> {
                    HomeScreen(
                        uiState = uiState,
                        onNavigate = { screen -> viewModel.navigateTo(screen) },
                        onSelectCourse = { course -> viewModel.selectCourse(course) }
                    )
                }

                Screen.COURSES -> {
                    CoursesScreen(
                        uiState = uiState,
                        onCourseClick = { course -> viewModel.selectCourse(course) },
                        onCategorySelect = { cat -> viewModel.setCourseCategory(cat) },
                        onSearchChange = { q -> viewModel.setCourseSearchQuery(q) }
                    )
                }

                Screen.COURSE_DETAIL -> {
                    CourseDetailScreen(
                        course = uiState.selectedCourse,
                        onBack = { viewModel.goBack() },
                        onToggleBookmark = { courseId -> viewModel.toggleBookmarkCourse(courseId) },
                        onToggleLesson = { lessonId -> viewModel.toggleLessonCompleted(lessonId) },
                        onOpenAiHelp = { prompt ->
                            viewModel.sendChatMessage(prompt)
                            viewModel.navigateTo(Screen.AI_TUTOR)
                        }
                    )
                }

                Screen.AI_TUTOR -> {
                    AiTutorScreen(
                        uiState = uiState,
                        onSendMessage = { text -> viewModel.sendChatMessage(text) },
                        onNavigate = { screen -> viewModel.navigateTo(screen) }
                    )
                }

                Screen.PLANNER -> {
                    PlannerScreen(
                        uiState = uiState,
                        onSelectDay = { day -> viewModel.setPlannerDay(day) },
                        onToggleTask = { taskId -> viewModel.toggleTask(taskId) },
                        onDeleteTask = { taskId -> viewModel.deleteTask(taskId) },
                        onAddTask = { title, subj, time, tag -> viewModel.addTask(title, subj, time, tag) },
                        onTogglePomodoro = { viewModel.togglePomodoro() },
                        onResetPomodoro = { viewModel.resetPomodoro() }
                    )
                }

                Screen.EXAM_CENTER -> {
                    ExamCenterScreen(
                        uiState = uiState,
                        onStartTest = { test -> viewModel.startMockTest(test) }
                    )
                }

                Screen.EXAM_TAKING -> {
                    ExamTakingScreen(
                        uiState = uiState,
                        onSelectAnswer = { qId, opt -> viewModel.selectAnswer(qId, opt) },
                        onNextQuestion = { viewModel.nextQuestion() },
                        onPrevQuestion = { viewModel.prevQuestion() },
                        onSubmitExam = { viewModel.submitExam() },
                        onClose = { viewModel.goBack() }
                    )
                }

                Screen.EXAM_RESULT -> {
                    ExamResultScreen(
                        uiState = uiState,
                        onBackToCenter = { viewModel.navigateTo(Screen.EXAM_CENTER) }
                    )
                }

                Screen.NOTES -> {
                    NotesScreen(
                        uiState = uiState,
                        onToggleBookmark = { id -> viewModel.toggleBookmarkNote(id) },
                        onSearchChange = { q -> viewModel.setNotesSearchQuery(q) },
                        onSubjectSelect = { s -> viewModel.setNotesSubject(s) }
                    )
                }

                Screen.FLASHCARDS -> {
                    FlashcardsScreen(
                        uiState = uiState,
                        onSelectDeck = { deck -> viewModel.selectDeck(deck) },
                        onFlipCard = { viewModel.flipFlashcard() },
                        onNextCard = { viewModel.nextFlashcard() },
                        onPrevCard = { viewModel.prevFlashcard() },
                        onToggleMastery = { cardId -> viewModel.toggleCardMastery(cardId) }
                    )
                }

                Screen.COMMUNITY -> {
                    CommunityScreen(
                        uiState = uiState,
                        onLikePost = { id -> viewModel.likePost(id) },
                        onCreatePost = { title, content, cat -> viewModel.createPost(title, content, cat) },
                        onAddComment = { id, text -> viewModel.addCommentToPost(id, text) },
                        onJoinStudyRoom = { room -> viewModel.joinStudyRoom(room) },
                        onCreateStudyRoom = { title, subj, goal, dur -> viewModel.createStudyRoom(title, subj, goal, dur) },
                        onAskQuestion = { title, subj, details, bounty -> viewModel.askPeerQuestion(title, subj, details, bounty) },
                        onAnswerQuestion = { qId, ans -> viewModel.answerPeerQuestion(qId, ans) },
                        onUpvoteAnswer = { qId, aId -> viewModel.upvotePeerAnswer(qId, aId) },
                        onAcceptSolution = { qId, aId -> viewModel.acceptPeerSolution(qId, aId) },
                        onShareGroupNote = { title, subj, group, preview -> viewModel.shareNoteWithGroup(title, subj, group, preview) },
                        onLikeGroupNote = { noteId -> viewModel.likeSharedGroupNote(noteId) }
                    )
                }

                Screen.STUDY_ROOM_DETAIL -> {
                    StudyRoomDetailScreen(
                        room = uiState.activeStudyRoom,
                        onLeaveRoom = { viewModel.leaveStudyRoom() },
                        onSendMessage = { rId, msg -> viewModel.sendRoomMessage(rId, msg) }
                    )
                }

                Screen.LEADERBOARD -> {
                    LeaderboardScreen(
                        uiState = uiState,
                        onBack = { viewModel.goBack() }
                    )
                }

                Screen.BADGES_DETAIL -> {
                    BadgesScreen(
                        uiState = uiState,
                        onSelectBadge = { badge -> viewModel.selectBadgeForDetail(badge) }
                    )
                }

                Screen.CAREER_HUB -> {
                    CareerHubScreen(uiState = uiState)
                }

                Screen.FINANCE -> {
                    FinanceScreen(
                        uiState = uiState,
                        onAddExpense = { title, amt, cat -> viewModel.addExpense(title, amt, cat) }
                    )
                }

                Screen.PROFILE -> {
                    ProfileScreen(
                        uiState = uiState,
                        onNavigate = { screen -> viewModel.navigateTo(screen) },
                        onToggleDarkMode = { viewModel.toggleDarkMode() },
                        onLogout = { viewModel.navigateTo(Screen.AUTH) }
                    )
                }

                Screen.SETTINGS -> {
                    SettingsScreen(
                        uiState = uiState,
                        onToggleDarkMode = { viewModel.toggleDarkMode() }
                    )
                }

                Screen.NOTIFICATIONS -> {
                    NotificationsScreen(
                        uiState = uiState,
                        onMarkAllAsRead = { viewModel.markAllNotificationsAsRead() }
                    )
                }

                Screen.AI_NOTES_GEN -> {
                    AiNotesGeneratorScreen(
                        uiState = uiState,
                        onGenerate = { topic, subj, lang -> viewModel.generateAiNotes(topic, subj, lang) }
                    )
                }

                Screen.AI_QUIZ_GEN -> {
                    AiQuizGeneratorScreen(uiState = uiState)
                }
            }
        }
    }
}
