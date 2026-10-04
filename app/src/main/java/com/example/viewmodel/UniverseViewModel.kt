package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.GeminiService
import com.example.data.SampleData
import com.example.model.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class Screen {
    SPLASH,
    AUTH,
    HOME,
    COURSES,
    COURSE_DETAIL,
    AI_TUTOR,
    PLANNER,
    EXAM_CENTER,
    EXAM_TAKING,
    EXAM_RESULT,
    NOTES,
    FLASHCARDS,
    COMMUNITY,
    CAREER_HUB,
    FINANCE,
    PROFILE,
    SETTINGS,
    NOTIFICATIONS,
    AI_NOTES_GEN,
    AI_QUIZ_GEN,
    LEADERBOARD,
    STUDY_ROOM_DETAIL,
    BADGES_DETAIL
}

data class UniverseUiState(
    val currentScreen: Screen = Screen.SPLASH,
    val previousScreen: Screen = Screen.HOME,
    val isDarkMode: Boolean = false,
    val isAuthenticated: Boolean = true, // Default ready for preview/demo
    val user: StudentUser = StudentUser(),

    // Gamification
    val badges: List<BadgeItem> = SampleData.badgesList,
    val leaderboard: List<LeaderboardStudent> = SampleData.leaderboardList,
    val selectedBadgeForDetail: BadgeItem? = null,
    val recentXpAwarded: Int? = null,
    val recentXpReason: String? = null,

    // Collaborative Community & Study Rooms
    val studyRooms: List<StudyRoom> = SampleData.studyRoomsList,
    val activeStudyRoom: StudyRoom? = null,
    val peerQuestions: List<PeerQuestion> = SampleData.peerQuestionsList,
    val selectedPeerQuestion: PeerQuestion? = null,
    val sharedGroupNotes: List<SharedGroupNote> = SampleData.sharedGroupNotesList,

    // Courses
    val courses: List<Course> = SampleData.coursesList,
    val selectedCourse: Course? = null,
    val courseSearchQuery: String = "",
    val selectedCourseCategory: String = "All",

    // Planner
    val tasks: List<PlannerTask> = SampleData.plannerTasksList,
    val selectedPlannerDay: String = "Mon",
    val pomodoroMinutes: Int = 25,
    val pomodoroSecondsLeft: Int = 25 * 60,
    val isPomodoroRunning: Boolean = false,

    // Exam Center
    val mockTests: List<MockTest> = SampleData.mockTestsList,
    val selectedMockTest: MockTest? = null,
    val activeQuestionIndex: Int = 0,
    val selectedAnswers: Map<Int, Int> = emptyMap(), // questionId -> selectedOptionIndex
    val examTimeRemainingSeconds: Int = 180 * 60,
    val isExamSubmitted: Boolean = false,
    val examScore: Int = 0,
    val examCorrectCount: Int = 0,
    val examIncorrectCount: Int = 0,

    // AI Tutor
    val chatMessages: List<ChatMessage> = listOf(
        ChatMessage(
            id = "msg_init",
            sender = "ai",
            text = "Hi Mayur! 👋\nI'm your UniVerse AI Tutor. Ask me anything about your studies, exams, or homework, and I'll help you with simple explanations, examples, and step-by-step solutions."
        )
    ),
    val isAiThinking: Boolean = false,

    // AI Notes Generator
    val notesGenTopic: String = "Photosynthesis & Light Reactions",
    val notesGenSubject: String = "Biology",
    val notesGenLanguage: String = "English",
    val generatedNotesContent: String = "",
    val isGeneratingNotes: Boolean = false,

    // AI Quiz Generator
    val quizGenTopic: String = "Chemical Bonding & VSEPR Theory",
    val quizGenSubject: String = "Chemistry",
    val isGeneratingQuiz: Boolean = false,

    // Flashcards
    val flashcardDecks: List<FlashcardDeck> = SampleData.flashcardDecksList,
    val selectedDeck: FlashcardDeck? = null,
    val currentFlashcardIndex: Int = 0,
    val isFlashcardFlipped: Boolean = false,

    // Notes & Resources
    val noteResources: List<NoteResource> = SampleData.noteResourcesList,
    val notesSearchQuery: String = "",
    val selectedNotesSubject: String = "All",

    // Community
    val communityPosts: List<CommunityPost> = SampleData.communityPostsList,
    val communityCategory: String = "All",
    val communitySearchQuery: String = "",

    // Career Hub
    val careerPaths: List<CareerPath> = SampleData.careerPathsList,
    val selectedCareerPath: CareerPath? = null,

    // Student Finance
    val expenses: List<StudentExpense> = SampleData.studentExpensesList,
    val monthlyBudget: Double = 8000.0,

    // Notifications
    val notifications: List<NotificationItem> = SampleData.notificationsList,
    val unreadNotificationsCount: Int = 3
)

class UniverseViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(UniverseUiState())
    val uiState: StateFlow<UniverseUiState> = _uiState.asStateFlow()

    private var pomodoroJob: Job? = null
    private var examTimerJob: Job? = null

    // Navigation
    fun navigateTo(screen: Screen) {
        _uiState.update { current ->
            current.copy(
                previousScreen = if (current.currentScreen != Screen.SPLASH && current.currentScreen != Screen.AUTH) current.currentScreen else Screen.HOME,
                currentScreen = screen
            )
        }
    }

    fun goBack() {
        _uiState.update { current ->
            val target = when (current.currentScreen) {
                Screen.COURSE_DETAIL -> Screen.COURSES
                Screen.EXAM_TAKING, Screen.EXAM_RESULT -> Screen.EXAM_CENTER
                Screen.AI_NOTES_GEN, Screen.AI_QUIZ_GEN -> Screen.AI_TUTOR
                Screen.LEADERBOARD, Screen.BADGES_DETAIL -> Screen.PROFILE
                Screen.STUDY_ROOM_DETAIL -> Screen.COMMUNITY
                Screen.NOTIFICATIONS, Screen.SETTINGS -> current.previousScreen
                else -> Screen.HOME
            }
            current.copy(currentScreen = target)
        }
    }

    fun toggleDarkMode() {
        _uiState.update { it.copy(isDarkMode = !it.isDarkMode) }
    }

    fun loginDemo() {
        _uiState.update { it.copy(isAuthenticated = true, currentScreen = Screen.HOME) }
    }

    // Gamification: Awarding Points (XP) & Unlocking Badges
    fun awardXp(amount: Int, reason: String) {
        _uiState.update { state ->
            val newXp = state.user.xp + amount
            val newLevel = (newXp / 500) + 1
            val nextLevelThreshold = newLevel * 500
            val leveledUp = newLevel > state.user.level

            val levelTitles = listOf(
                "Novice Seeker", "Stellar Explorer", "Nebula Voyager",
                "Star Navigator", "Cosmic Scholar", "Supernova Master", "Grand Celestial"
            )
            val title = levelTitles.getOrElse(newLevel - 1) { "Grand Cosmic Titan" }

            val updatedUser = state.user.copy(
                xp = newXp,
                level = newLevel,
                levelTitle = title,
                nextLevelXp = nextLevelThreshold
            )

            // Update user in leaderboard
            val updatedLeaderboard = state.leaderboard.map { student ->
                if (student.isCurrentUser) student.copy(xp = newXp, badgeTitle = title)
                else student
            }.sortedByDescending { it.xp }.mapIndexed { index, student ->
                student.copy(rank = index + 1)
            }
            val myNewRank = updatedLeaderboard.find { it.isCurrentUser }?.rank ?: updatedUser.rank

            val newNotifications = if (leveledUp) {
                listOf(
                    NotificationItem(
                        id = "notif_lvl_${System.currentTimeMillis()}",
                        title = "🎉 Level Up: Level $newLevel!",
                        message = "Congratulations! You have been promoted to $title. Keep learning!",
                        timeAgo = "Just now",
                        type = "Reward",
                        isRead = false
                    )
                ) + state.notifications
            } else state.notifications

            state.copy(
                user = updatedUser.copy(rank = myNewRank),
                leaderboard = updatedLeaderboard,
                notifications = newNotifications,
                recentXpAwarded = amount,
                recentXpReason = reason
            )
        }
    }

    fun dismissXpBanner() {
        _uiState.update { it.copy(recentXpAwarded = null, recentXpReason = null) }
    }

    fun selectBadgeForDetail(badge: BadgeItem) {
        _uiState.update { it.copy(selectedBadgeForDetail = badge, currentScreen = Screen.BADGES_DETAIL) }
    }

    // Collaborative Study Rooms
    fun joinStudyRoom(room: StudyRoom) {
        _uiState.update {
            it.copy(
                activeStudyRoom = room.copy(activeParticipants = room.activeParticipants + 1),
                currentScreen = Screen.STUDY_ROOM_DETAIL
            )
        }
    }

    fun leaveStudyRoom() {
        _uiState.update {
            it.copy(
                activeStudyRoom = null,
                currentScreen = Screen.COMMUNITY
            )
        }
    }

    fun sendRoomMessage(roomId: String, text: String) {
        if (text.isBlank()) return
        val newMsg = RoomMessage(
            id = "rm_${System.currentTimeMillis()}",
            senderName = _uiState.value.user.name,
            text = text,
            timeAgo = "Just now",
            isCurrentUser = true
        )
        _uiState.update { state ->
            val updatedRooms = state.studyRooms.map { room ->
                if (room.id == roomId) {
                    room.copy(chatMessages = room.chatMessages + newMsg)
                } else room
            }
            state.copy(
                studyRooms = updatedRooms,
                activeStudyRoom = if (state.activeStudyRoom?.id == roomId) {
                    state.activeStudyRoom.copy(chatMessages = state.activeStudyRoom.chatMessages + newMsg)
                } else state.activeStudyRoom
            )
        }
    }

    fun createStudyRoom(title: String, subject: String, goal: String, durationMinutes: Int) {
        if (title.isBlank()) return
        val newRoom = StudyRoom(
            id = "room_${System.currentTimeMillis()}",
            title = title,
            subject = subject.ifBlank { "General" },
            activeParticipants = 1,
            maxParticipants = 30,
            hostName = _uiState.value.user.name,
            isLive = true,
            goal = goal.ifBlank { "Deep collaborative focus session" },
            durationMinutes = durationMinutes,
            timeRemainingSeconds = durationMinutes * 60,
            chatMessages = listOf(
                RoomMessage("init", "System", "Welcome to $title! Session started by ${_uiState.value.user.name}.", "Just now")
            )
        )
        _uiState.update { state ->
            state.copy(
                studyRooms = listOf(newRoom) + state.studyRooms,
                activeStudyRoom = newRoom,
                currentScreen = Screen.STUDY_ROOM_DETAIL
            )
        }
        awardXp(50, "Created a Study Room")
    }

    // Peer-to-Peer Q&A
    fun selectPeerQuestion(question: PeerQuestion) {
        _uiState.update { it.copy(selectedPeerQuestion = question) }
    }

    fun askPeerQuestion(title: String, subject: String, details: String, bountyXp: Int) {
        if (title.isBlank() || details.isBlank()) return
        val newQuestion = PeerQuestion(
            id = "pq_${System.currentTimeMillis()}",
            askerName = _uiState.value.user.name,
            askerRole = "${_uiState.value.user.levelTitle} • Class 12",
            subject = subject.ifBlank { "General Studies" },
            title = title,
            details = details,
            timeAgo = "Just now",
            bountyXp = bountyXp,
            upvotes = 1,
            isSolved = false,
            answers = emptyList()
        )
        _uiState.update { state ->
            state.copy(peerQuestions = listOf(newQuestion) + state.peerQuestions)
        }
        awardXp(20, "Asked Coursework Question")
    }

    fun answerPeerQuestion(questionId: String, answerContent: String) {
        if (answerContent.isBlank()) return
        val newAnswer = PeerAnswer(
            id = "pa_${System.currentTimeMillis()}",
            authorName = _uiState.value.user.name,
            authorRole = "${_uiState.value.user.levelTitle} • Contributor",
            content = answerContent,
            timeAgo = "Just now",
            upvotes = 0,
            isAccepted = false,
            isUpvotedByMe = false
        )
        _uiState.update { state ->
            val updated = state.peerQuestions.map { q ->
                if (q.id == questionId) q.copy(answers = q.answers + newAnswer)
                else q
            }
            state.copy(
                peerQuestions = updated,
                selectedPeerQuestion = if (state.selectedPeerQuestion?.id == questionId) {
                    state.selectedPeerQuestion.copy(answers = state.selectedPeerQuestion.answers + newAnswer)
                } else state.selectedPeerQuestion
            )
        }
        awardXp(40, "Contributed Answer to Peer")
    }

    fun upvotePeerAnswer(questionId: String, answerId: String) {
        _uiState.update { state ->
            val updatedQuestions = state.peerQuestions.map { q ->
                if (q.id == questionId) {
                    val updatedAnswers = q.answers.map { a ->
                        if (a.id == answerId) {
                            val newUpvoted = !a.isUpvotedByMe
                            val newCount = if (newUpvoted) a.upvotes + 1 else a.upvotes - 1
                            a.copy(upvotes = newCount, isUpvotedByMe = newUpvoted)
                        } else a
                    }
                    q.copy(answers = updatedAnswers)
                } else q
            }
            state.copy(
                peerQuestions = updatedQuestions,
                selectedPeerQuestion = if (state.selectedPeerQuestion?.id == questionId) {
                    val answers = state.selectedPeerQuestion.answers.map { a ->
                        if (a.id == answerId) {
                            val newUpvoted = !a.isUpvotedByMe
                            val newCount = if (newUpvoted) a.upvotes + 1 else a.upvotes - 1
                            a.copy(upvotes = newCount, isUpvotedByMe = newUpvoted)
                        } else a
                    }
                    state.selectedPeerQuestion.copy(answers = answers)
                } else state.selectedPeerQuestion
            )
        }
    }

    fun acceptPeerSolution(questionId: String, answerId: String) {
        _uiState.update { state ->
            val updated = state.peerQuestions.map { q ->
                if (q.id == questionId) {
                    val updatedAns = q.answers.map { it.copy(isAccepted = it.id == answerId) }
                    q.copy(answers = updatedAns, isSolved = true)
                } else q
            }
            state.copy(
                peerQuestions = updated,
                selectedPeerQuestion = if (state.selectedPeerQuestion?.id == questionId) {
                    val ans = state.selectedPeerQuestion.answers.map { it.copy(isAccepted = it.id == answerId) }
                    state.selectedPeerQuestion.copy(answers = ans, isSolved = true)
                } else state.selectedPeerQuestion
            )
        }
        awardXp(80, "Verified Peer Solution Accepted")
    }

    // Shared Group Notes
    fun likeSharedGroupNote(noteId: String) {
        _uiState.update { state ->
            val updated = state.sharedGroupNotes.map { note ->
                if (note.id == noteId) {
                    val newLiked = !note.isLiked
                    val newCount = if (newLiked) note.likesCount + 1 else note.likesCount - 1
                    note.copy(isLiked = newLiked, likesCount = newCount)
                } else note
            }
            state.copy(sharedGroupNotes = updated)
        }
    }

    fun shareNoteWithGroup(title: String, subject: String, groupName: String, previewText: String) {
        if (title.isBlank()) return
        val newNote = SharedGroupNote(
            id = "sgn_${System.currentTimeMillis()}",
            title = title,
            subject = subject.ifBlank { "General" },
            authorName = _uiState.value.user.name,
            groupName = groupName.ifBlank { "UniVerse General" },
            timeAgo = "Just now",
            likesCount = 1,
            downloadCount = 0,
            pagesCount = 4,
            previewText = previewText.ifBlank { "Shared student revision notes." },
            isLiked = true
        )
        _uiState.update { state ->
            state.copy(sharedGroupNotes = listOf(newNote) + state.sharedGroupNotes)
        }
        awardXp(60, "Shared Notes with Study Group")
    }

    // Courses & Lessons (with gamification points)
    fun selectCourse(course: Course) {
        _uiState.update { it.copy(selectedCourse = course, currentScreen = Screen.COURSE_DETAIL) }
    }

    fun toggleBookmarkCourse(courseId: String) {
        _uiState.update { state ->
            val updated = state.courses.map {
                if (it.id == courseId) it.copy(isSaved = !it.isSaved) else it
            }
            state.copy(
                courses = updated,
                selectedCourse = if (state.selectedCourse?.id == courseId) state.selectedCourse.copy(isSaved = !state.selectedCourse.isSaved) else state.selectedCourse
            )
        }
    }

    fun toggleLessonCompleted(lessonId: String) {
        var isNowCompleted = false
        _uiState.update { state ->
            val course = state.selectedCourse ?: return@update state
            val updatedChapters = course.chapters.map { ch ->
                ch.copy(lessons = ch.lessons.map { l ->
                    if (l.id == lessonId) {
                        isNowCompleted = !l.isCompleted
                        l.copy(isCompleted = !l.isCompleted)
                    } else l
                })
            }
            val total = updatedChapters.sumOf { it.lessons.size }
            val completed = updatedChapters.sumOf { ch -> ch.lessons.count { it.isCompleted } }
            val progress = if (total > 0) (completed * 100) / total else course.progressPercent
            val updatedCourse = course.copy(chapters = updatedChapters, progressPercent = progress, completedLessons = completed)
            state.copy(
                selectedCourse = updatedCourse,
                courses = state.courses.map { if (it.id == course.id) updatedCourse else it }
            )
        }
        if (isNowCompleted) {
            awardXp(50, "Completed Lesson")
        }
    }

    fun setCourseSearchQuery(query: String) {
        _uiState.update { it.copy(courseSearchQuery = query) }
    }

    fun setCourseCategory(category: String) {
        _uiState.update { it.copy(selectedCourseCategory = category) }
    }

    // Planner (with gamification points)
    fun setPlannerDay(day: String) {
        _uiState.update { it.copy(selectedPlannerDay = day) }
    }

    fun toggleTask(taskId: String) {
        var isNowDone = false
        _uiState.update { state ->
            val updated = state.tasks.map {
                if (it.id == taskId) {
                    isNowDone = !it.isCompleted
                    it.copy(isCompleted = !it.isCompleted)
                } else it
            }
            val completedCount = updated.count { it.isCompleted }
            state.copy(
                tasks = updated,
                user = state.user.copy(
                    tasksCompletedToday = completedCount,
                    tasksTotalToday = updated.size
                )
            )
        }
        if (isNowDone) {
            awardXp(25, "Completed Study Task")
        }
    }

    fun addTask(title: String, subject: String, timeSlot: String, tag: String) {
        if (title.isBlank()) return
        val newTask = PlannerTask(
            id = "t_${System.currentTimeMillis()}",
            title = title,
            subject = subject.ifBlank { "General" },
            timeSlot = timeSlot.ifBlank { "Flexible" },
            dayOfWeek = _uiState.value.selectedPlannerDay,
            tag = tag.ifBlank { "Study" }
        )
        _uiState.update { state ->
            val updated = state.tasks + newTask
            state.copy(
                tasks = updated,
                user = state.user.copy(tasksTotalToday = updated.size)
            )
        }
    }

    fun deleteTask(taskId: String) {
        _uiState.update { state ->
            val updated = state.tasks.filterNot { it.id == taskId }
            val completedCount = updated.count { it.isCompleted }
            state.copy(
                tasks = updated,
                user = state.user.copy(
                    tasksCompletedToday = completedCount,
                    tasksTotalToday = updated.size
                )
            )
        }
    }

    // Pomodoro Timer
    fun togglePomodoro() {
        val currentlyRunning = _uiState.value.isPomodoroRunning
        if (currentlyRunning) {
            pomodoroJob?.cancel()
            _uiState.update { it.copy(isPomodoroRunning = false) }
        } else {
            _uiState.update { it.copy(isPomodoroRunning = true) }
            pomodoroJob = viewModelScope.launch {
                while (_uiState.value.pomodoroSecondsLeft > 0) {
                    delay(1000)
                    _uiState.update { it.copy(pomodoroSecondsLeft = it.pomodoroSecondsLeft - 1) }
                }
                _uiState.update { it.copy(isPomodoroRunning = false, pomodoroSecondsLeft = 25 * 60) }
                awardXp(50, "Finished 25-Min Pomodoro Session")
            }
        }
    }

    fun resetPomodoro() {
        pomodoroJob?.cancel()
        _uiState.update { it.copy(isPomodoroRunning = false, pomodoroSecondsLeft = 25 * 60) }
    }

    // AI Tutor
    fun sendChatMessage(text: String) {
        if (text.isBlank()) return
        val userMsg = ChatMessage(id = "msg_${System.currentTimeMillis()}", sender = "user", text = text)
        _uiState.update { it.copy(chatMessages = it.chatMessages + userMsg, isAiThinking = true) }

        viewModelScope.launch {
            val responseText = GeminiService.askTutor(text)
            val aiMsg = ChatMessage(
                id = "msg_${System.currentTimeMillis()}",
                sender = "ai",
                text = responseText
            )
            _uiState.update { it.copy(chatMessages = it.chatMessages + aiMsg, isAiThinking = false) }
        }
    }

    // AI Notes Generator
    fun generateAiNotes(topic: String, subject: String, language: String) {
        _uiState.update {
            it.copy(
                notesGenTopic = topic,
                notesGenSubject = subject,
                notesGenLanguage = language,
                isGeneratingNotes = true
            )
        }
        viewModelScope.launch {
            val notes = GeminiService.generateNotes(topic, subject, language)
            _uiState.update {
                it.copy(
                    generatedNotesContent = notes,
                    isGeneratingNotes = false
                )
            }
            awardXp(30, "Generated AI Notes")
        }
    }

    // Mock Tests & CBT Exam (with gamification points)
    fun startMockTest(test: MockTest) {
        examTimerJob?.cancel()
        _uiState.update {
            it.copy(
                selectedMockTest = test,
                activeQuestionIndex = 0,
                selectedAnswers = emptyMap(),
                examTimeRemainingSeconds = test.durationMinutes * 60,
                isExamSubmitted = false,
                examScore = 0,
                examCorrectCount = 0,
                examIncorrectCount = 0,
                currentScreen = Screen.EXAM_TAKING
            )
        }
        examTimerJob = viewModelScope.launch {
            while (_uiState.value.examTimeRemainingSeconds > 0 && !_uiState.value.isExamSubmitted) {
                delay(1000)
                _uiState.update { it.copy(examTimeRemainingSeconds = it.examTimeRemainingSeconds - 1) }
            }
            if (!_uiState.value.isExamSubmitted) {
                submitExam()
            }
        }
    }

    fun selectAnswer(questionId: Int, optionIndex: Int) {
        _uiState.update {
            val updated = it.selectedAnswers.toMutableMap()
            updated[questionId] = optionIndex
            it.copy(selectedAnswers = updated)
        }
    }

    fun nextQuestion() {
        val test = _uiState.value.selectedMockTest ?: return
        val currentIdx = _uiState.value.activeQuestionIndex
        if (currentIdx < test.questions.size - 1) {
            _uiState.update { it.copy(activeQuestionIndex = currentIdx + 1) }
        }
    }

    fun prevQuestion() {
        val currentIdx = _uiState.value.activeQuestionIndex
        if (currentIdx > 0) {
            _uiState.update { it.copy(activeQuestionIndex = currentIdx - 1) }
        }
    }

    fun submitExam() {
        examTimerJob?.cancel()
        val test = _uiState.value.selectedMockTest ?: return
        val answers = _uiState.value.selectedAnswers
        var correct = 0
        var incorrect = 0

        test.questions.forEach { q ->
            val userPick = answers[q.id]
            if (userPick != null) {
                if (userPick == q.correctOptionIndex) {
                    correct++
                } else {
                    incorrect++
                }
            }
        }
        val score = (correct * 4) - incorrect

        _uiState.update {
            it.copy(
                isExamSubmitted = true,
                examScore = score.coerceAtLeast(0),
                examCorrectCount = correct,
                examIncorrectCount = incorrect,
                currentScreen = Screen.EXAM_RESULT
            )
        }
        val xpGain = 100 + (correct * 15)
        awardXp(xpGain, "Completed ${test.title}")
    }

    // Flashcards
    fun selectDeck(deck: FlashcardDeck) {
        _uiState.update {
            it.copy(
                selectedDeck = deck,
                currentFlashcardIndex = 0,
                isFlashcardFlipped = false,
                currentScreen = Screen.FLASHCARDS
            )
        }
    }

    fun flipFlashcard() {
        _uiState.update { it.copy(isFlashcardFlipped = !it.isFlashcardFlipped) }
    }

    fun nextFlashcard() {
        val deck = _uiState.value.selectedDeck ?: return
        val nextIdx = (_uiState.value.currentFlashcardIndex + 1) % deck.cards.size
        _uiState.update {
            it.copy(currentFlashcardIndex = nextIdx, isFlashcardFlipped = false)
        }
    }

    fun prevFlashcard() {
        val deck = _uiState.value.selectedDeck ?: return
        val prevIdx = if (_uiState.value.currentFlashcardIndex > 0) _uiState.value.currentFlashcardIndex - 1 else deck.cards.size - 1
        _uiState.update {
            it.copy(currentFlashcardIndex = prevIdx, isFlashcardFlipped = false)
        }
    }

    fun toggleCardMastery(cardId: String) {
        var isNowMastered = false
        _uiState.update { state ->
            val deck = state.selectedDeck ?: return@update state
            val updatedCards = deck.cards.map {
                if (it.id == cardId) {
                    isNowMastered = !it.isMastered
                    it.copy(isMastered = !it.isMastered)
                } else it
            }
            val updatedDeck = deck.copy(cards = updatedCards)
            state.copy(
                selectedDeck = updatedDeck,
                flashcardDecks = state.flashcardDecks.map { if (it.id == deck.id) updatedDeck else it }
            )
        }
        if (isNowMastered) {
            awardXp(15, "Mastered Flashcard")
        }
    }

    // Notes
    fun toggleBookmarkNote(noteId: String) {
        _uiState.update { state ->
            state.copy(
                noteResources = state.noteResources.map {
                    if (it.id == noteId) it.copy(isBookmarked = !it.isBookmarked) else it
                }
            )
        }
    }

    fun setNotesSearchQuery(query: String) {
        _uiState.update { it.copy(notesSearchQuery = query) }
    }

    fun setNotesSubject(subject: String) {
        _uiState.update { it.copy(selectedNotesSubject = subject) }
    }

    // Community Posts
    fun likePost(postId: String) {
        _uiState.update { state ->
            state.copy(
                communityPosts = state.communityPosts.map { post ->
                    if (post.id == postId) {
                        val newLiked = !post.isLiked
                        val newCount = if (newLiked) post.likesCount + 1 else post.likesCount - 1
                        post.copy(isLiked = newLiked, likesCount = newCount.coerceAtLeast(0))
                    } else post
                }
            )
        }
    }

    fun createPost(title: String, content: String, category: String) {
        if (title.isBlank() || content.isBlank()) return
        val newPost = CommunityPost(
            id = "p_${System.currentTimeMillis()}",
            authorName = _uiState.value.user.name,
            authorRole = "UniVerse Student",
            timeAgo = "Just now",
            category = category.ifBlank { "Discussion" },
            title = title,
            content = content,
            likesCount = 0,
            commentsCount = 0,
            isLiked = false
        )
        _uiState.update { it.copy(communityPosts = listOf(newPost) + it.communityPosts) }
        awardXp(30, "Shared Community Post")
    }

    fun addCommentToPost(postId: String, commentText: String) {
        if (commentText.isBlank()) return
        val newComment = PostComment(
            id = "c_${System.currentTimeMillis()}",
            authorName = _uiState.value.user.name,
            content = commentText,
            timeAgo = "Just now"
        )
        _uiState.update { state ->
            state.copy(
                communityPosts = state.communityPosts.map { post ->
                    if (post.id == postId) {
                        post.copy(
                            comments = post.comments + newComment,
                            commentsCount = post.commentsCount + 1
                        )
                    } else post
                }
            )
        }
    }

    // Finance
    fun addExpense(title: String, amount: Double, category: String) {
        if (title.isBlank() || amount <= 0) return
        val expense = StudentExpense(
            id = "exp_${System.currentTimeMillis()}",
            title = title,
            amount = amount,
            category = category,
            date = "Today"
        )
        _uiState.update { it.copy(expenses = listOf(expense) + it.expenses) }
    }

    // Notifications
    fun markAllNotificationsAsRead() {
        _uiState.update { state ->
            state.copy(
                notifications = state.notifications.map { it.copy(isRead = true) },
                unreadNotificationsCount = 0
            )
        }
    }
}
