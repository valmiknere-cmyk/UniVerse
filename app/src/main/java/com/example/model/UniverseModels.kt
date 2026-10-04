package com.example.model

data class StudentUser(
    val id: String = "user_1",
    val name: String = "Mayur",
    val email: String = "mayur.student@universe.edu",
    val headline: String = "Student • Dreamer • Doer",
    val avatarUrl: String = "",
    val enrolledCoursesCount: Int = 12,
    val badgesCount: Int = 6,
    val streakDays: Int = 5,
    val studyGoalMinutesDaily: Int = 180,
    val studyCompletedMinutesToday: Int = 125,
    val tasksCompletedToday: Int = 5,
    val tasksTotalToday: Int = 7,
    val xp: Int = 1840,
    val level: Int = 5,
    val rank: Int = 4,
    val levelTitle: String = "Cosmic Scholar",
    val nextLevelXp: Int = 2500
)

data class BadgeItem(
    val id: String,
    val title: String,
    val description: String,
    val category: String, // Streak, Mastery, Community, Quiz
    val iconName: String,
    val isUnlocked: Boolean = false,
    val unlockedDate: String? = null,
    val progressPercent: Int = 100,
    val rewardXp: Int = 100
)

data class LeaderboardStudent(
    val rank: Int,
    val name: String,
    val headline: String,
    val xp: Int,
    val streak: Int,
    val badgeTitle: String,
    val isCurrentUser: Boolean = false
)

data class StudyRoom(
    val id: String,
    val title: String,
    val subject: String,
    val activeParticipants: Int,
    val maxParticipants: Int = 25,
    val hostName: String,
    val isLive: Boolean = true,
    val goal: String,
    val durationMinutes: Int = 50,
    val timeRemainingSeconds: Int = 38 * 60,
    val chatMessages: List<RoomMessage> = emptyList(),
    val sharedNotesCount: Int = 3
)

data class RoomMessage(
    val id: String,
    val senderName: String,
    val text: String,
    val timeAgo: String,
    val isCurrentUser: Boolean = false
)

data class PeerQuestion(
    val id: String,
    val askerName: String,
    val askerRole: String,
    val subject: String,
    val title: String,
    val details: String,
    val timeAgo: String,
    val bountyXp: Int = 40,
    val upvotes: Int = 8,
    val isSolved: Boolean = false,
    val answers: List<PeerAnswer> = emptyList()
)

data class PeerAnswer(
    val id: String,
    val authorName: String,
    val authorRole: String,
    val content: String,
    val timeAgo: String,
    val upvotes: Int = 0,
    val isAccepted: Boolean = false,
    val isUpvotedByMe: Boolean = false
)

data class SharedGroupNote(
    val id: String,
    val title: String,
    val subject: String,
    val authorName: String,
    val groupName: String,
    val timeAgo: String,
    val likesCount: Int = 0,
    val downloadCount: Int = 1,
    val pagesCount: Int = 8,
    val previewText: String,
    val isLiked: Boolean = false
)

data class Course(
    val id: String,
    val title: String,
    val category: String, // School, Competitive, Medical, Engineering, Technology, etc.
    val description: String,
    val targetExamOrClass: String,
    val progressPercent: Int = 0,
    val totalLessons: Int = 24,
    val completedLessons: Int = 0,
    val isPopular: Boolean = false,
    val rating: Double = 4.9,
    val totalStudents: String = "45k+",
    val iconName: String = "book",
    val chapters: List<Chapter> = emptyList(),
    val isSaved: Boolean = false
)

data class Chapter(
    val id: String,
    val title: String,
    val durationText: String,
    val lessons: List<Lesson> = emptyList()
)

data class Lesson(
    val id: String,
    val title: String,
    val durationText: String,
    val isCompleted: Boolean = false,
    val contentSummary: String = "",
    val xpReward: Int = 50
)

data class PlannerTask(
    val id: String,
    val title: String,
    val subject: String,
    val timeSlot: String,
    val isCompleted: Boolean = false,
    val dayOfWeek: String = "Mon", // Mon, Tue, Wed, Thu, Fri, Sat, Sun
    val tag: String = "Study",
    val xpReward: Int = 25
)

data class MockTest(
    val id: String,
    val title: String,
    val category: String, // NEET, JEE, UPSC, Board, etc.
    val subjects: String,
    val totalQuestions: Int,
    val durationMinutes: Int,
    val difficulty: String = "Medium",
    val bestScore: Int? = null,
    val questions: List<TestQuestion> = emptyList(),
    val completionXp: Int = 100
)

data class TestQuestion(
    val id: Int,
    val questionText: String,
    val options: List<String>,
    val correctOptionIndex: Int,
    val explanation: String
)

data class FlashcardDeck(
    val id: String,
    val title: String,
    val category: String,
    val cards: List<Flashcard> = emptyList()
)

data class Flashcard(
    val id: String,
    val front: String,
    val back: String,
    val hint: String = "",
    val isMastered: Boolean = false
)

data class NoteResource(
    val id: String,
    val title: String,
    val subject: String,
    val author: String = "UniVerse Academic Team",
    val pagesCount: Int = 14,
    val fileSize: String = "2.4 MB",
    val isBookmarked: Boolean = false,
    val contentPreview: String = ""
)

data class CommunityPost(
    val id: String,
    val authorName: String,
    val authorRole: String,
    val timeAgo: String,
    val category: String,
    val title: String,
    val content: String,
    val likesCount: Int = 0,
    val commentsCount: Int = 0,
    val isLiked: Boolean = false,
    val comments: List<PostComment> = emptyList()
)

data class PostComment(
    val id: String,
    val authorName: String,
    val content: String,
    val timeAgo: String
)

data class CareerPath(
    val id: String,
    val title: String,
    val domain: String,
    val salaryRange: String,
    val requiredExams: String,
    val topSkills: List<String>,
    val description: String,
    val roadmapSteps: List<String>
)

data class StudentExpense(
    val id: String,
    val title: String,
    val amount: Double,
    val category: String, // Books, Food, Rent, Fees, Other
    val date: String
)

data class NotificationItem(
    val id: String,
    val title: String,
    val message: String,
    val timeAgo: String,
    val type: String, // Study, Exam, Course, Community, Reward
    val isRead: Boolean = false
)

data class ChatMessage(
    val id: String,
    val sender: String, // "user" or "ai"
    val text: String,
    val timestamp: Long = System.currentTimeMillis(),
    val actionSuggested: String? = null
)
