package com.example.data

import com.example.model.*

object SampleData {

    val coursesList = listOf(
        Course(
            id = "neet_ug",
            title = "NEET UG",
            category = "Medical",
            description = "Complete master preparation for National Eligibility cum Entrance Test with physics, chemistry and biology deep dives.",
            targetExamOrClass = "Medical Entrance Exam",
            progressPercent = 45,
            totalLessons = 42,
            completedLessons = 19,
            isPopular = true,
            rating = 4.9,
            totalStudents = "120k+",
            iconName = "medical",
            chapters = listOf(
                Chapter(
                    id = "ch_bio_1",
                    title = "Biology: Plant Kingdom & Diversity",
                    durationText = "8 lessons • 4h 30m",
                    lessons = listOf(
                        Lesson("l_1", "Classification Systems & Algae", "35 min", true, "Study of Thallophyta, Chlorophyceae, Phaeophyceae and Rhodophyceae."),
                        Lesson("l_2", "Bryophytes & Pteridophytes", "42 min", true, "Amphibians of the plant kingdom, alternation of generation, vascular cryptogams."),
                        Lesson("l_3", "Gymnosperms & Angiosperms", "50 min", false, "Naked seeded plants, double fertilization, life cycles."),
                        Lesson("l_4", "Plant Morphology & Anatomy", "45 min", false, "Roots, stems, leaves, modifications and internal tissues.")
                    )
                ),
                Chapter(
                    id = "ch_chem_1",
                    title = "Chemistry: Chemical Bonding & Molecular Structure",
                    durationText = "6 lessons • 3h 15m",
                    lessons = listOf(
                        Lesson("l_5", "Kossel-Lewis Approach & Ionic Bonds", "30 min", true, "Lattice enthalpy, Born-Haber cycle, octet rule limitations."),
                        Lesson("l_6", "VSEPR Theory & Hybridization", "48 min", false, "Geometry prediction of NH3, H2O, SF6, PCl5, sp3d hybridization.")
                    )
                ),
                Chapter(
                    id = "ch_phy_1",
                    title = "Physics: Optics & Modern Physics",
                    durationText = "10 lessons • 5h 40m",
                    lessons = listOf(
                        Lesson("l_7", "Wave Nature of Light & Huygens Principle", "40 min", false, "Wavefronts, reflection, refraction and interference proofs."),
                        Lesson("l_8", "Photoelectric Effect & Photons", "35 min", false, "Einstein equation, work function, threshold frequency.")
                    )
                )
            )
        ),
        Course(
            id = "jee_main_adv",
            title = "JEE Main & Advanced",
            category = "Engineering",
            description = "High-octane concepts, problem solving tricks, and rigorous mock assessments for IIT aspirants.",
            targetExamOrClass = "Engineering Entrance",
            progressPercent = 60,
            totalLessons = 56,
            completedLessons = 34,
            isPopular = true,
            rating = 4.9,
            totalStudents = "95k+",
            iconName = "engineering",
            chapters = listOf(
                Chapter(
                    id = "ch_jee_m1",
                    title = "Mathematics: Calculus & Coordinate Geometry",
                    durationText = "12 lessons • 7h 20m",
                    lessons = listOf(
                        Lesson("l_m1", "Limits, Continuity & Differentiability", "45 min", true, "L'Hopital rule, epsilon-delta intuition, standard expansions."),
                        Lesson("l_m2", "Definite Integrals & Properties", "55 min", true, "King's property, Leibniz integral rule, areas under curves.")
                    )
                ),
                Chapter(
                    id = "ch_jee_p1",
                    title = "Physics: Rotational Dynamics & SHM",
                    durationText = "8 lessons • 5h 10m",
                    lessons = listOf(
                        Lesson("l_p1", "Moment of Inertia & Torque", "40 min", true, "Parallel and perpendicular axis theorems, angular momentum conservation.")
                    )
                )
            )
        ),
        Course(
            id = "class_11_12_cbse",
            title = "Class 11 – 12 (CBSE)",
            category = "School Education",
            description = "Complete NCERT aligned syllabus for Physics, Chemistry, Mathematics, and Biology with high-scoring board notes.",
            targetExamOrClass = "All subjects • Board Exam",
            progressPercent = 75,
            totalLessons = 36,
            completedLessons = 27,
            isPopular = false,
            rating = 4.8,
            totalStudents = "68k+",
            iconName = "school",
            chapters = listOf(
                Chapter(
                    id = "ch_cbse_1",
                    title = "Term Syllabus Master Blueprint",
                    durationText = "6 lessons • 3h 40m",
                    lessons = listOf(
                        Lesson("l_c1", "Core Concepts & Exemplar Problems", "35 min", true, "Step by step derivations required for 5-mark board questions.")
                    )
                )
            )
        ),
        Course(
            id = "python_programming",
            title = "Python Programming",
            category = "Technology",
            description = "From zero to building full-stack apps, data analytics scripts, and machine learning models with Python 3.",
            targetExamOrClass = "Coding & Development",
            progressPercent = 30,
            totalLessons = 28,
            completedLessons = 8,
            isPopular = false,
            rating = 4.9,
            totalStudents = "150k+",
            iconName = "code",
            chapters = listOf(
                Chapter(
                    id = "ch_py_1",
                    title = "Python Fundamentals & Data Structures",
                    durationText = "8 lessons • 4h 00m",
                    lessons = listOf(
                        Lesson("l_py1", "Variables, Data Types & Operators", "25 min", true, "Dynamic typing, integers, floats, strings, boolean logic."),
                        Lesson("l_py2", "Lists, Tuples, Dictionaries & Sets", "40 min", true, "Comprehensions, slicing, indexing, hash tables and mutability."),
                        Lesson("l_py3", "Functions, Lambdas & Decorators", "45 min", false, "Higher-order functions, args, kwargs, closures.")
                    )
                )
            )
        ),
        Course(
            id = "upsc_civil_services",
            title = "UPSC Civil Services",
            category = "Competitive Exams",
            description = "Comprehensive General Studies, Current Affairs, CSAT, and Mains answer writing mentorship.",
            targetExamOrClass = "Government Exam • IAS / IPS",
            progressPercent = 20,
            totalLessons = 64,
            completedLessons = 13,
            isPopular = true,
            rating = 4.9,
            totalStudents = "85k+",
            iconName = "governance",
            chapters = listOf(
                Chapter(
                    id = "ch_upsc_1",
                    title = "Indian Polity & Constitution",
                    durationText = "14 lessons • 9h 30m",
                    lessons = listOf(
                        Lesson("l_u1", "Preamble & Fundamental Rights (Articles 12-35)", "50 min", true, "Writs, basic structure doctrine, landmark judicial rulings.")
                    )
                )
            )
        ),
        Course(
            id = "mbbs_foundations",
            title = "MBBS",
            category = "Medical",
            description = "Anatomy, Physiology, Biochemistry, Pathology, and clinical case presentations for medical students.",
            targetExamOrClass = "Medical (Full Course)",
            progressPercent = 15,
            totalLessons = 70,
            completedLessons = 11,
            isPopular = false,
            rating = 4.9,
            totalStudents = "32k+",
            iconName = "hospital",
            chapters = listOf(
                Chapter(
                    id = "ch_mbbs_1",
                    title = "Gross Anatomy & Neuroanatomy",
                    durationText = "12 lessons • 8h 15m",
                    lessons = listOf(
                        Lesson("l_mb1", "Upper Limb Nerve Supply & Brachial Plexus", "45 min", true, "Roots, trunks, divisions, cords, and Erb's palsy.")
                    )
                )
            )
        ),
        Course(
            id = "ai_ml_foundations",
            title = "Artificial Intelligence & ML",
            category = "Technology",
            description = "Deep learning, Neural networks, LLMs, computer vision, and hands-on PyTorch implementations.",
            targetExamOrClass = "Modern Tech & Data Science",
            progressPercent = 10,
            totalLessons = 32,
            completedLessons = 3,
            isPopular = true,
            rating = 4.9,
            totalStudents = "78k+",
            iconName = "robot",
            chapters = listOf(
                Chapter(
                    id = "ch_ai_1",
                    title = "Neural Networks & Backpropagation",
                    durationText = "6 lessons • 3h 40m",
                    lessons = listOf(
                        Lesson("l_ai1", "Perceptrons, Activation Functions & Loss", "35 min", true, "ReLU, Sigmoid, Cross-Entropy Loss, Gradient Descent.")
                    )
                )
            )
        ),
        Course(
            id = "graphic_design_uiux",
            title = "Graphic Design & UI/UX",
            category = "Creative Skills",
            description = "Master visual hierarchy, typography, color theory, Figma, and design systems for web and mobile apps.",
            targetExamOrClass = "Creative & Digital Arts",
            progressPercent = 50,
            totalLessons = 22,
            completedLessons = 11,
            isPopular = false,
            rating = 4.8,
            totalStudents = "44k+",
            iconName = "palette",
            chapters = listOf(
                Chapter(
                    id = "ch_gd_1",
                    title = "Design Principles & Figma Mastery",
                    durationText = "5 lessons • 2h 50m",
                    lessons = listOf(
                        Lesson("l_gd1", "Typography & Visual Contrast", "30 min", true, "Scale, leading, kerning, and accessibility contrast standards.")
                    )
                )
            )
        ),
        Course(
            id = "cuet_exam",
            title = "CUET (UG)",
            category = "Competitive Exams",
            description = "Common University Entrance Test prep covering Language, Domain Subjects, and General Aptitude.",
            targetExamOrClass = "Central University Admissions",
            progressPercent = 25,
            totalLessons = 30,
            completedLessons = 7,
            isPopular = false,
            rating = 4.7,
            totalStudents = "52k+",
            iconName = "university",
            chapters = listOf()
        ),
        Course(
            id = "english_comm",
            title = "English Communication",
            category = "Languages",
            description = "Fluency, professional email writing, interview speaking, debate tactics, and vocabulary mastery.",
            targetExamOrClass = "Life Skills & Career",
            progressPercent = 80,
            totalLessons = 18,
            completedLessons = 14,
            isPopular = false,
            rating = 4.8,
            totalStudents = "110k+",
            iconName = "language",
            chapters = listOf()
        )
    )

    val plannerTasksList = listOf(
        PlannerTask("t_1", "Physics - Modern Physics", "Physics", "8:00 AM – 9:00 AM", true, "Mon", "High Priority"),
        PlannerTask("t_2", "Chemistry - Organic Chemistry", "Chemistry", "10:00 AM – 11:00 AM", true, "Mon", "Revision"),
        PlannerTask("t_3", "Biology - Plant Kingdom", "Biology", "12:00 PM – 1:00 PM", true, "Mon", "NCERT"),
        PlannerTask("t_4", "Mock Test - NEET Section A", "NEET Prep", "3:00 PM – 4:30 PM", false, "Mon", "Practice"),
        PlannerTask("t_5", "Revision - Formulae & Short Notes", "All Subjects", "6:00 PM – 7:00 PM", false, "Mon", "Quick Review"),
        PlannerTask("t_6", "Reading - General Knowledge & Current Affairs", "General Studies", "8:00 PM – 8:30 PM", false, "Mon", "Daily Feed"),
        PlannerTask("t_7", "Free Time & Meditation", "Wellness", "9:00 PM – 10:00 PM", false, "Mon", "Relax")
    )

    val mockTestsList = listOf(
        MockTest(
            id = "mock_neet_1",
            title = "NEET Mock Test",
            category = "Medical",
            subjects = "Full Syllabus • 180 Questions • 3 Hours",
            totalQuestions = 180,
            durationMinutes = 180,
            difficulty = "High Yield",
            bestScore = 645,
            questions = listOf(
                TestQuestion(
                    id = 1,
                    questionText = "Which pigment is directly responsible for light-driven electron transfer in photosystem II reaction centers?",
                    options = listOf("Chlorophyll a (P680)", "Chlorophyll b", "Beta-carotene", "Xanthophyll"),
                    correctOptionIndex = 0,
                    explanation = "Chlorophyll a molecules with absorption peak at 680 nm (P680) constitute the reaction center of Photosystem II."
                ),
                TestQuestion(
                    id = 2,
                    questionText = "What is the hybridisation of xenon in XeF4 molecule?",
                    options = listOf("sp3", "sp3d", "sp3d2", "dsp2"),
                    correctOptionIndex = 2,
                    explanation = "XeF4 has 4 bond pairs and 2 lone pairs on xenon, giving steric number 6, which corresponds to sp3d2 hybridisation (square planar geometry)."
                ),
                TestQuestion(
                    id = 3,
                    questionText = "The de Broglie wavelength of an electron accelerated through a potential difference of 100 V is approximately:",
                    options = listOf("1.227 Å", "0.123 Å", "12.27 Å", "0.012 Å"),
                    correctOptionIndex = 0,
                    explanation = "Using lambda = 12.27 / sqrt(V) Angstroms, for V = 100 V, lambda = 12.27 / 10 = 1.227 Angstroms."
                ),
                TestQuestion(
                    id = 4,
                    questionText = "Which hormone stimulates gluconeogenesis, lipolysis, and proteolysis, and inhibits cellular uptake of amino acids?",
                    options = listOf("Insulin", "Cortisol", "Thyroxine", "Calcitonin"),
                    correctOptionIndex = 1,
                    explanation = "Cortisol, the primary glucocorticoid secreted by adrenal cortex, elevates blood glucose by stimulating gluconeogenesis, lipolysis, and proteolysis."
                ),
                TestQuestion(
                    id = 5,
                    questionText = "A body of mass 2 kg moving at 4 m/s makes an elastic head-on collision with a body of mass 2 kg at rest. What is the velocity of the first body after collision?",
                    options = listOf("0 m/s", "2 m/s", "4 m/s", "-2 m/s"),
                    correctOptionIndex = 0,
                    explanation = "In an elastic collision between two equal masses, velocities are completely exchanged. Hence the first body comes to rest (0 m/s)."
                )
            )
        ),
        MockTest(
            id = "mock_jee_1",
            title = "JEE Main Mock Test",
            category = "Engineering",
            subjects = "Physics • Chemistry • Maths • 75 Questions • 3 Hours",
            totalQuestions = 75,
            durationMinutes = 180,
            difficulty = "JEE Standard",
            bestScore = 220,
            questions = listOf(
                TestQuestion(
                    id = 1,
                    questionText = "Evaluate the limit: lim(x->0) (sin(x) - x) / x^3",
                    options = listOf("-1/6", "1/6", "0", "-1/3"),
                    correctOptionIndex = 0,
                    explanation = "Using Taylor expansion sin(x) = x - x^3/6 + ..., (sin x - x)/x^3 = -1/6."
                ),
                TestQuestion(
                    id = 2,
                    questionText = "Find the angle between the curves y = x^2 and y^2 = x at (1, 1).",
                    options = listOf("tan^-1(3/4)", "tan^-1(4/3)", "pi/4", "pi/2"),
                    correctOptionIndex = 0,
                    explanation = "m1 = 2x = 2; for y^2 = x, 2y y' = 1 => m2 = 1/2. tan(theta) = |(2 - 1/2)/(1 + 2*(1/2))| = (3/2)/2 = 3/4."
                )
            )
        ),
        MockTest(
            id = "mock_upsc_1",
            title = "UPSC Prelims Mock Test",
            category = "Government",
            subjects = "GS - CSAT • 100 Questions • 2 Hours",
            totalQuestions = 100,
            durationMinutes = 120,
            difficulty = "Moderate",
            bestScore = 112,
            questions = listOf(
                TestQuestion(
                    id = 1,
                    questionText = "Under Article 21 of the Indian Constitution, the 'Right to Privacy' was declared a fundamental right in which landmark case?",
                    options = listOf("K.S. Puttaswamy v. Union of India", "Kesavananda Bharati case", "Maneka Gandhi case", "Minerva Mills case"),
                    correctOptionIndex = 0,
                    explanation = "In the Justice K.S. Puttaswamy (Retd.) v. Union of India (2017) case, a 9-judge bench unanimously upheld Right to Privacy as intrinsic to Article 21."
                )
            )
        ),
        MockTest(
            id = "mock_ssc_1",
            title = "SSC CGL Mock Test",
            category = "Government",
            subjects = "Reasoning • Quant • English • GK • 100 Questions • 1 Hour",
            totalQuestions = 100,
            durationMinutes = 60,
            difficulty = "Speed Focused",
            bestScore = 145,
            questions = listOf()
        ),
        MockTest(
            id = "mock_bank_1",
            title = "Banking Mock Test",
            category = "Commerce",
            subjects = "Reasoning • Quant • English • GA • 100 Questions • 1 Hour",
            totalQuestions = 100,
            durationMinutes = 60,
            difficulty = "Moderate",
            bestScore = 68,
            questions = listOf()
        )
    )

    val flashcardDecksList = listOf(
        FlashcardDeck(
            id = "deck_bio",
            title = "Biology: High Yield NEET Concepts",
            category = "Medical",
            cards = listOf(
                Flashcard("fc_1", "What is the site of Krebs cycle in eukaryotic cells?", "Mitochondrial Matrix (succinate dehydrogenase is in the inner mitochondrial membrane).", "Think mitochondria"),
                Flashcard("fc_2", "Name the connecting link between glycolysis and Krebs cycle.", "Acetyl CoA (formed by oxidative decarboxylation of pyruvate via Pyruvate Dehydrogenase).", "2-carbon intermediate"),
                Flashcard("fc_3", "Which plant hormone promotes fruit ripening and horizontal growth of seedlings?", "Ethylene (gaseous plant hormone).", "Gaseous hormone"),
                Flashcard("fc_4", "What type of junction prevents leaking of substances across a tissue?", "Tight junctions (zonula occludens).", "Prevents leakage"),
                Flashcard("fc_5", "State the Hardy-Weinberg equilibrium equation for two alleles.", "p^2 + 2pq + q^2 = 1 (where p + q = 1).", "Genetics algebra")
            )
        ),
        FlashcardDeck(
            id = "deck_chem",
            title = "Chemistry: Named Reactions & Reagents",
            category = "Chemistry",
            cards = listOf(
                Flashcard("fc_6", "What is Lucas Reagent and its test purpose?", "Anhydrous ZnCl2 + conc. HCl; used to differentiate between 1°, 2°, and 3° alcohols based on turbidity appearance.", "Alcohol differentiation"),
                Flashcard("fc_7", "What product is formed in Aldol Condensation of acetaldehyde?", "3-hydroxybutanal (aldol), which upon heating gives but-2-enal (crotonaldehyde).", "Beta-hydroxy aldehyde"),
                Flashcard("fc_8", "State Cannizzaro Reaction requirement.", "Aldehydes with no alpha-hydrogen atoms (e.g., formaldehyde, benzaldehyde) undergo disproportionation in conc. alkali.", "No alpha hydrogen")
            )
        ),
        FlashcardDeck(
            id = "deck_phy",
            title = "Physics: Formulae & Key Laws",
            category = "Physics",
            cards = listOf(
                Flashcard("fc_9", "State Gauss's Law in electrostatics.", "Total electric flux through any closed surface is 1/epsilon_0 times the net charge enclosed: phi = Q_enclosed / eps_0.", "Flux and enclosed charge"),
                Flashcard("fc_10", "Formula for resonant frequency of an LCR series circuit.", "f_0 = 1 / (2 * pi * sqrt(L * C)).", "Impedance is minimum")
            )
        )
    )

    val noteResourcesList = listOf(
        NoteResource(
            id = "note_1",
            title = "Plant Kingdom & Diversity Quick Revision Notes",
            subject = "Biology",
            author = "Dr. Ananya Sharma (Ex-AIIMS)",
            pagesCount = 18,
            fileSize = "3.2 MB",
            isBookmarked = true,
            contentPreview = "Key highlights on algae pigments (chlorophyll a, b, c, d), fucoxanthin, phycoerythrin, pteridophyte life cycle and gymnosperm archegonia."
        ),
        NoteResource(
            id = "note_2",
            title = "Organic Chemistry Reaction Mechanism Map",
            subject = "Chemistry",
            author = "Prof. R. K. Gupta (IIT Delhi Alum)",
            pagesCount = 24,
            fileSize = "4.5 MB",
            isBookmarked = false,
            contentPreview = "Complete summary of SN1, SN2, E1, E2, electrophilic aromatic substitution, Markovnikov vs Anti-Markovnikov addition rules."
        ),
        NoteResource(
            id = "note_3",
            title = "Modern Physics & Optics Formula Cheat-sheet",
            subject = "Physics",
            author = "UniVerse Academic Team",
            pagesCount = 12,
            fileSize = "1.8 MB",
            isBookmarked = true,
            contentPreview = "Bohr model radii, energy levels, Rydberg formula, photoelectric equation, de Broglie wavelength, and lens maker equation."
        ),
        NoteResource(
            id = "note_4",
            title = "Indian Polity & Constitutional Articles Summary",
            subject = "General Studies",
            author = "IAS Mentor Team",
            pagesCount = 35,
            fileSize = "5.1 MB",
            isBookmarked = false,
            contentPreview = "Comprehensive compilation of Articles 1 to 395, Constitutional Amendments, Schedules, and Supreme Court judgments."
        ),
        NoteResource(
            id = "note_5",
            title = "Python Data Structures & Algorithm Cheat-Sheet",
            subject = "Computer Science",
            author = "UniVerse Tech Club",
            pagesCount = 15,
            fileSize = "2.1 MB",
            isBookmarked = true,
            contentPreview = "Big-O complexities, list/dict methods, bisect, collections.deque, recursion templates, and graph traversal algorithms."
        )
    )

    val communityPostsList = listOf(
        CommunityPost(
            id = "post_1",
            authorName = "Aarav Patel",
            authorRole = "NEET UG Aspirant • Class 12",
            timeAgo = "2h ago",
            category = "Exam Tips",
            title = "How I improved my Biology score from 280 to 350+ using NCERT word-by-word!",
            content = "Hey everyone! Don't skip the summary paragraphs and scientist biographies in NCERT. In my last 3 mock tests, at least 4 questions directly came from diagrams and diagram captions. Highlight everyday and make flashcards!",
            likesCount = 42,
            commentsCount = 14,
            isLiked = false,
            comments = listOf(
                PostComment("c_1", "Priya Verma", "So true! Diagram labeling questions are free marks if you review them daily.", "1h ago"),
                PostComment("c_2", "Rohan Joshi", "Do you also practice previous 10 year questions?", "45m ago")
            )
        ),
        CommunityPost(
            id = "post_2",
            authorName = "Sneha Roy",
            authorRole = "B.Tech Computer Science • 2nd Year",
            timeAgo = "5h ago",
            category = "Study Groups",
            title = "Starting a 30-Day Python & DSA Study Sprint - Who's in?",
            content = "We are solving 2 LeetCode problems daily and building one mini project every weekend on UniVerse. Drop a comment if you want to join our study group!",
            likesCount = 89,
            commentsCount = 28,
            isLiked = true,
            comments = listOf(
                PostComment("c_3", "Aditya K", "I'm in! Let's do Tree and Graph algorithms this week.", "3h ago")
            )
        ),
        CommunityPost(
            id = "post_3",
            authorName = "Vikram Malhotra",
            authorRole = "UPSC Aspirant",
            timeAgo = "1d ago",
            category = "Motivation",
            title = "Consistency beats intensity every single time.",
            content = "Even on days when you feel exhausted, studying with focus for just 45 minutes maintains momentum. Small daily compounding creates immense transformation in 6 months.",
            likesCount = 135,
            commentsCount = 19,
            isLiked = false,
            comments = emptyList()
        )
    )

    val careerPathsList = listOf(
        CareerPath(
            id = "car_swe",
            title = "Software Engineer & AI Developer",
            domain = "Technology & Computer Science",
            salaryRange = "₹8 LPA – ₹45+ LPA",
            requiredExams = "JEE Main, B.Tech CSE, Gate CSE, Self-taught / Bootcamps",
            topSkills = listOf("Data Structures", "Python/Kotlin", "Web Architecture", "System Design", "Cloud & Git"),
            description = "Design, build, and deploy software applications, mobile platforms, and AI-powered systems that power global infrastructure.",
            roadmapSteps = listOf(
                "Learn programming fundamentals (Python / Java / Kotlin)",
                "Master Data Structures and Algorithms (Arrays, Trees, Graphs)",
                "Build full-stack applications and contribute to Open Source",
                "Learn Cloud (GCP/AWS), System Design, and DevOps principles",
                "Interview preparation and competitive coding"
            )
        ),
        CareerPath(
            id = "car_doctor",
            title = "Medical Doctor / Surgeon",
            domain = "Healthcare & Medicine",
            salaryRange = "₹10 LPA – ₹50+ LPA",
            requiredExams = "NEET UG, MBBS, NEET PG / NEXT",
            topSkills = listOf("Clinical Diagnostics", "Patient Care", "Anatomy & Physiology", "Emergency Medicine"),
            description = "Diagnose, treat, and heal patients, conduct biomedical research, and contribute to healthcare systems.",
            roadmapSteps = listOf(
                "Clear NEET UG with top percentile",
                "Complete 5.5 years MBBS including rotational internship",
                "Qualify NEET PG / NEXT for specialization (MD/MS)",
                "Fellowship or super-specialization (DM/MCh)"
            )
        ),
        CareerPath(
            id = "car_ias",
            title = "Civil Servant (IAS / IPS / IFS)",
            domain = "Public Administration & Governance",
            salaryRange = "₹56,100/mo starting + Government perks & immense impact",
            requiredExams = "UPSC Civil Services Examination (Prelims, Mains, Interview)",
            topSkills = listOf("Policy Analysis", "Leadership", "General Studies", "Public Ethics", "Crisis Management"),
            description = "Lead district administration, formulate national policies, and serve public welfare at state and national levels.",
            roadmapSteps = listOf(
                "Complete any recognized Bachelor's Degree",
                "Thorough preparation of GS Papers I-IV, CSAT and Optional Subject",
                "Clear UPSC Prelims and develop mains answer writing speed",
                "Personality test (Interview) and Lal Bahadur Shastri National Academy training"
            )
        )
    )

    val studentExpensesList = listOf(
        StudentExpense("e_1", "NCERT Exam Preparation Books", 1250.0, "Books", "Oct 02"),
        StudentExpense("e_2", "Hostel / Mess Monthly Charge", 4500.0, "Food", "Oct 01"),
        StudentExpense("e_3", "Metro / Bus Travel Card", 600.0, "Travel", "Sep 28"),
        StudentExpense("e_4", "Stationery, Notebooks & Highlighters", 350.0, "Books", "Sep 25"),
        StudentExpense("e_5", "Group Study Coffee & Snacks", 280.0, "Food", "Sep 22")
    )

    val notificationsList = listOf(
        NotificationItem("n_1", "Study Goal Alert ⏰", "You are only 55 minutes away from reaching today's 3-hour focus target!", "25m ago", "Study", false),
        NotificationItem("n_2", "NEET Mock Test Live 🚀", "Full Syllabus Mock Test #4 is now active. Test your ranking among 10,000+ peers.", "2h ago", "Exam", false),
        NotificationItem("n_3", "Course Update 📚", "Chapter 2 in 'Physics - Modern Physics' has new practice formula sheets added.", "1d ago", "Course", true),
        NotificationItem("n_4", "Community Reply 💬", "Aarav replied to your comment in Study Groups.", "2d ago", "Community", true),
        NotificationItem("n_5", "Level Up! 🌟", "You reached Level 5 Cosmic Scholar! +200 Cosmic Credits awarded.", "1d ago", "Reward", false)
    )

    val badgesList = listOf(
        BadgeItem(
            id = "badge_streak_3",
            title = "Cosmic Spark",
            description = "Maintain an uninterrupted 3-day study streak",
            category = "Streak",
            iconName = "fire",
            isUnlocked = true,
            unlockedDate = "Oct 01, 2026",
            progressPercent = 100,
            rewardXp = 100
        ),
        BadgeItem(
            id = "badge_streak_7",
            title = "Supernova Orbit",
            description = "Reach a 7-day consistent study streak",
            category = "Streak",
            iconName = "star",
            isUnlocked = false,
            unlockedDate = null,
            progressPercent = 71, // 5 / 7
            rewardXp = 250
        ),
        BadgeItem(
            id = "badge_bio_prodigy",
            title = "Biology Prodigy",
            description = "Complete Plant Kingdom, Cell Biology & score 85%+ in Bio Quiz",
            category = "Mastery",
            iconName = "leaf",
            isUnlocked = true,
            unlockedDate = "Sep 28, 2026",
            progressPercent = 100,
            rewardXp = 200
        ),
        BadgeItem(
            id = "badge_quantum_ace",
            title = "Quantum Explorer",
            description = "Solve 50 Modern Physics problems with 80%+ accuracy",
            category = "Mastery",
            iconName = "atom",
            isUnlocked = true,
            unlockedDate = "Sep 25, 2026",
            progressPercent = 100,
            rewardXp = 200
        ),
        BadgeItem(
            id = "badge_alchemist",
            title = "Formula Alchemist",
            description = "Master all 25 high-yield Organic Chemistry flashcards",
            category = "Mastery",
            iconName = "flask",
            isUnlocked = true,
            unlockedDate = "Sep 20, 2026",
            progressPercent = 100,
            rewardXp = 150
        ),
        BadgeItem(
            id = "badge_peer_mentor",
            title = "Community Champion",
            description = "Have 3 of your answers accepted as verified solutions in Peer Q&A",
            category = "Community",
            iconName = "mentor",
            isUnlocked = true,
            unlockedDate = "Oct 02, 2026",
            progressPercent = 100,
            rewardXp = 300
        ),
        BadgeItem(
            id = "badge_zen_focus",
            title = "Zen Focus Master",
            description = "Complete 10 Pomodoro deep study sessions without pausing",
            category = "Focus",
            iconName = "timer",
            isUnlocked = false,
            unlockedDate = null,
            progressPercent = 60, // 6/10
            rewardXp = 180
        ),
        BadgeItem(
            id = "badge_mock_titan",
            title = "Mock Exam Titan",
            description = "Complete 5 full-length Computer Based Mock Tests",
            category = "Quiz",
            iconName = "trophy",
            isUnlocked = false,
            unlockedDate = null,
            progressPercent = 40, // 2/5
            rewardXp = 400
        )
    )

    val leaderboardList = listOf(
        LeaderboardStudent(1, "Aarav Sharma", "NEET UG AIR Target #1 • Delhi", 3420, 14, "Grand Cosmic Master", false),
        LeaderboardStudent(2, "Priya Nair", "JEE Advanced IITB Aspirant • Kerala", 3150, 12, "Supernova Scholar", false),
        LeaderboardStudent(3, "Rohan Varma", "Class 12 CBSE Topper • Mumbai", 2890, 9, "Nebula Voyager", false),
        LeaderboardStudent(4, "Mayur", "Student • Dreamer • Doer", 1840, 5, "Cosmic Scholar", true),
        LeaderboardStudent(5, "Sneha Roy", "B.Tech CSE & Data Science • Kolkata", 1720, 6, "Star Navigator", false),
        LeaderboardStudent(6, "Aditya Joshi", "UPSC IAS Preparation • Pune", 1580, 4, "Stellar Seeker", false),
        LeaderboardStudent(7, "Meera Desai", "Medical Foundations • Ahmedabad", 1430, 7, "Orbit Explorer", false),
        LeaderboardStudent(8, "Karthik Raja", "Competitive Maths Wizard • Chennai", 1290, 3, "Cosmic Spark", false)
    )

    val studyRoomsList = listOf(
        StudyRoom(
            id = "room_1",
            title = "NEET 2026 Biology Sprint 🌿",
            subject = "Biology",
            activeParticipants = 18,
            maxParticipants = 30,
            hostName = "Dr. Ananya Sharma",
            isLive = true,
            goal = "Solve 45 Plant Kingdom NCERT word-by-word questions together",
            durationMinutes = 50,
            timeRemainingSeconds = 34 * 60,
            chatMessages = listOf(
                RoomMessage("m1", "Dr. Ananya", "Welcome everyone! Keep your NCERT Open to page 32.", "5m ago"),
                RoomMessage("m2", "Priya V", "Ready! Did anyone review red algae pigments?", "3m ago"),
                RoomMessage("m3", "Mayur", "Yes, phycoerythrin gives red color and absorbs blue-green light!", "1m ago", true)
            ),
            sharedNotesCount = 4
        ),
        StudyRoom(
            id = "room_2",
            title = "JEE Advanced Calculus Marathon 📐",
            subject = "Mathematics",
            activeParticipants = 9,
            maxParticipants = 20,
            hostName = "Rahul Verma (IITD)",
            isLive = true,
            goal = "Master Leibniz Rule for definite integrals & tricky limits",
            durationMinutes = 60,
            timeRemainingSeconds = 48 * 60,
            chatMessages = listOf(
                RoomMessage("m4", "Rahul Verma", "Starting problem #3 on Leibniz integral differentiation now!", "4m ago"),
                RoomMessage("m5", "Aditya", "On it! Differentiating the upper limit first.", "2m ago")
            ),
            sharedNotesCount = 2
        ),
        StudyRoom(
            id = "room_3",
            title = "Late Night Silent Pomodoro 🌙",
            subject = "All Subjects",
            activeParticipants = 27,
            maxParticipants = 50,
            hostName = "UniVerse Study Bot",
            isLive = true,
            goal = "Pure silent focus session (50m study / 10m break). Camera/mic off.",
            durationMinutes = 50,
            timeRemainingSeconds = 22 * 60,
            chatMessages = listOf(
                RoomMessage("m6", "System", "Focus phase active. 27 students currently in deep work.", "20m ago")
            ),
            sharedNotesCount = 6
        )
    )

    val peerQuestionsList = listOf(
        PeerQuestion(
            id = "pq_1",
            askerName = "Aniket Deshmukh",
            askerRole = "NEET Aspirant • Class 11",
            subject = "Biology",
            title = "Why does RuBisCO act as an oxygenase in C3 plants under high O2?",
            details = "I understand that RuBisCO has an active site that binds both CO2 and O2, but what is the exact competitive factor in hot tropical conditions that triggers photorespiration?",
            timeAgo = "1h ago",
            bountyXp = 50,
            upvotes = 12,
            isSolved = true,
            answers = listOf(
                PeerAnswer(
                    id = "pa_1",
                    authorName = "Mayur",
                    authorRole = "Cosmic Scholar • Top Contributor",
                    content = "RuBisCO has much higher affinity for CO2 at normal temps (~25°C). But at high temperatures and intense light, stomata partially close to prevent water loss (transpiration). Internal CO2 drops while O2 from photolysis accumulates! RuBisCO then oxygenates RuBP into 1 molecule of 3-PGA and 1 molecule of 2-phosphoglycolate (photorespiration), consuming ATP without producing sugar.",
                    timeAgo = "40m ago",
                    upvotes = 9,
                    isAccepted = true,
                    isUpvotedByMe = false
                ),
                PeerAnswer(
                    id = "pa_2",
                    authorName = "Dr. Ananya",
                    authorRole = "Faculty Mentor",
                    content = "Spot on Mayur! That's precisely why C4 plants evolved the Kranz anatomy and PEP carboxylase (which has zero oxygenase affinity) to concentrate CO2 around RuBisCO in bundle sheath cells.",
                    timeAgo = "25m ago",
                    upvotes = 5,
                    isAccepted = false,
                    isUpvotedByMe = true
                )
            )
        ),
        PeerQuestion(
            id = "pq_2",
            askerName = "Sarah Thomas",
            askerRole = "JEE Main Aspirant",
            subject = "Physics",
            title = "Intuitive shortcut for Moment of Inertia of rotated laminar shapes?",
            details = "I always confuse when to apply Parallel Axis Theorem vs Perpendicular Axis Theorem on triangular and rectangular laminas.",
            timeAgo = "3h ago",
            bountyXp = 40,
            upvotes = 8,
            isSolved = false,
            answers = listOf(
                PeerAnswer(
                    id = "pa_3",
                    authorName = "Rahul IIT",
                    authorRole = "JEE Advanced Ranker",
                    content = "Golden Rule: Perpendicular Axis Theorem (Iz = Ix + Iy) works ONLY for 2D planar (flat) bodies where z is perpendicular to the plane! Parallel Axis Theorem (I = Icm + Md^2) works for ANY shape (1D, 2D, 3D), but one axis MUST pass strictly through the center of mass!",
                    timeAgo = "2h ago",
                    upvotes = 7,
                    isAccepted = false,
                    isUpvotedByMe = true
                )
            )
        ),
        PeerQuestion(
            id = "pq_3",
            askerName = "Vikram Malhotra",
            askerRole = "UPSC Aspirant",
            subject = "Chemistry",
            title = "How to differentiate between Aldol and Cannizzaro conditions instantly?",
            details = "Looking for a 5-second mnemonic rule to not mix them up in organic MCQs.",
            timeAgo = "5h ago",
            bountyXp = 35,
            upvotes = 6,
            isSolved = false,
            answers = emptyList()
        )
    )

    val sharedGroupNotesList = listOf(
        SharedGroupNote(
            id = "sgn_1",
            title = "NCERT Biology High-Yield Summary Tables",
            subject = "Biology",
            authorName = "Sneha Roy",
            groupName = "NEET Warriors 2026",
            timeAgo = "Yesterday",
            likesCount = 48,
            downloadCount = 312,
            pagesCount = 8,
            previewText = "Tabulated differences of Algae classes, Gymnosperms vs Angiosperms, and secondary growth diagrams.",
            isLiked = true
        ),
        SharedGroupNote(
            id = "sgn_2",
            title = "Organic Chemistry Named Reactions Quick Cheat Map",
            subject = "Chemistry",
            authorName = "Mayur",
            groupName = "IIT & Medical Chem Hub",
            timeAgo = "2d ago",
            likesCount = 39,
            downloadCount = 245,
            pagesCount = 6,
            previewText = "All 22 essential named reactions with reagents, intermediates, and stereochemical outcomes.",
            isLiked = false
        ),
        SharedGroupNote(
            id = "sgn_3",
            title = "JEE Mechanics & Rotational Dynamics Hand-derivations",
            subject = "Physics",
            authorName = "Aditya Joshi",
            groupName = "Physics Olympiad Circle",
            timeAgo = "3d ago",
            likesCount = 62,
            downloadCount = 420,
            pagesCount = 14,
            previewText = "Torque, angular momentum conservation, rolling without slipping on inclined plane with step derivations.",
            isLiked = true
        )
    )
}
