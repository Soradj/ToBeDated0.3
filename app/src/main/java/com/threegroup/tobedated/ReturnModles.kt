package com.threegroup.tobedated

data class AgeRange(
    var min: Int = 0,
    var max: Int = 0
)



val pronounOptions = listOf("He/Him", "She/Her", "They/Them", "Ask me")
val seekingOptions = listOf("Male", "Female", "Everyone")
val sexOptions = listOf("Male", "Female", "Other")
val genderOptions = listOf("Cis-Gender", "Transgender", "Non-binary")
val ethnicityOptions = listOf("Black/African Descent", "East Asian", "Hispanic/Latino", "Middle Eastern", "Native American", "Pacific Islander", "South Asian", "Southeast Asian", "White/Caucasian", "Other", "Ask me",)
val starOptions = listOf("Aries", "Taurus", "Gemini", "Cancer", "Leo", "Virgo", "Libra", "Scorpio", "Sagittarius", "Capricorn", "Aquarius", "Pisces", "Ask me",)
val sexOrientationOptions = listOf("Androsexual", "Asexual", "Bisexual", "Demisexuality", "Gynesexual", "Heterosexual", "Pansexual", "Queer", "Questioning", "Ask Me")
val childrenOptions = listOf("Don't have", "Have children", "Ask me")
val familyOptions = listOf("Don't want", "Want children", "Open to children", "Not sure yet", "Ask me")
val educationOptions = listOf("High School", "Undergrad", "Postgrad", "Ask me")
val religionOptions =  listOf("Agnostic", "Atheist", "Buddhist", "Catholic", "Christian", "Hindu", "Jewish", "Muslim", "Sikh", "Spiritual", "Other", "Ask me")
val politicsOptions = listOf("Liberal", "Moderate", "Conservative", "Not Political", "Other", "Ask me")
val relationshipOptions = listOf("Monogamy", "Non-monogamy", "Finding it", "Other", "Ask me")
val intentionsOptions = listOf("Life Partner", "Long-term", "Long open to short", "Short open to long", "Short-term", "Figuring it out","Ask me")
val drinkOptions = listOf("Yes", "Sometimes", "No", "Ask me")
val smokeOptions = listOf("Yes", "Sometimes", "No", "Ask me")
val weedOptions = listOf("Yes", "Sometimes", "No", "Ask me")
val meetUpOptions = listOf("Right Away", "Talk first", "Take it slow", "Ask me")
val mbtiOption = listOf(  "INTJ", "INTP", "ENTJ", "ENTP", "ENFP", "ENFJ", "INFP", "INFJ", "ESFJ", "ESTJ", "ISFJ", "ISTJ", "ISTP", "ISFP", "ESTP", "ESFP", "Not Taken")

//Causal options
val leaningOptions = listOf("Dominant", "Submissive", "Switch", "Vanilla", "Ask me")
val lookingForOptions = listOf("ONS", "FWB", "Casual", "Ask me")
val experienceOptions = listOf("Novice", "Intermediate", "Experienced", "Ask me")
//sexOrientationOptions
//ethnicityOptions
//genderOptions
//sexOptions
//seekingOptions
//pronounOptions
//meetUpOptions
//intentionsOptions
val locationOptions = listOf("Host", "Travel", "Both", "Ask me")
val commOptions = listOf("Direct and explicit", "Flirty and playful", "Open and honest", "Ask me")
val sexHealthOptions = listOf("STI-Free", "Regularly Tested", "On Birth Control", "Vasectomy/Tubal Ligation", "Ask me",)
val afterCareOptions = listOf("Cuddling", "Talking", "Snacking", "Quick Departure", "Ask me",)

//Dating prompt questions
val tabs = listOf("Insights and Reflections", "Passions and Interests", "Curiosities and Imaginations")
val insightsANDReflections = listOf(
    "What's one misconception people often have about you?",
    "If you could go back and give your younger self one piece of advice, what would it be?",
    "What's a skill you've always wanted to develop but haven't had the chance to yet?",
    "Describe a moment that significantly changed your perspective on life.",
    "What's a topic you could talk about for hours without getting bored?",
    "What's the most important lesson you've learned from a past relationship?",
    "If you could rewrite one moment from your past, what would it be and why?",
    "What's something you're currently struggling with, and how do you cope with it?",
    "Describe a time when you had to step out of your comfort zone and how it impacted you.",
    "What's something you wish more people understood about you?"
)
val passionsANDInterests = listOf("What's something you're deeply passionate about, but not many people know?",
    "Describe a project or hobby you've been meaning to start but haven't yet.",
    "If you could attend any event, past or present, what would it be?",
    "What's a cause or movement you strongly support, and why?",
    "Describe a book, movie, or piece of art that profoundly influenced you.",
    "What's a skill or talent you admire in others and wish you had?",
    "If you could spend a day with someone who inspires you, who would it be and why?",
    "What's a goal you're currently working towards, and what steps are you taking to achieve it?",
    "Describe a recent accomplishment you're proud of, big or small.",
    "If you could make one positive change in the world, what would it be and why?"
)
val curiositiesANDImaginations = listOf(
    "If you could witness any historical event, what would it be and why?",
    "Describe a place you've never been to but have always wanted to visit, and what draws you to it.",
    "What's a skill or hobby you've been curious to try but haven't yet?",
    "If you could live in any era other than the present, which one would it be and why?",
    "Describe a fictional world from a book or movie that you wish you could visit.",
    "If you could master any language overnight, which one would you choose and why?",
    "What's a technology or scientific advancement you're excited about for the future?",
    "If you could have dinner with any fictional character, who would it be and what would you talk about?",
    "Describe a dream you've had that left a lasting impression on you.",
    "If you could have a conversation with your future self, what would you ask?"
)

//Casual prompt questions
val tabsCasual = listOf("Preferences and Desires", "Limits and Boundaries", "Expectations and Communication")
val preferencesAndDesires = listOf(
    "Do you prefer to take charge and lead in the bedroom, or do you enjoy letting your partner take control?",
    "Are you open to experimenting with new fantasies and kinks, or do you prefer to stick to familiar activities?",
    "How important is physical attraction to you in a sexual encounter?",
    "Do you enjoy engaging in foreplay and building anticipation, or do you prefer to get straight to the main event?",
    "Are you comfortable discussing your sexual boundaries and preferences with your partner beforehand?",
    "Do you prefer a slow and sensual pace during sex, or do you enjoy a more intense and passionate experience?",
    "How important is mutual pleasure and satisfaction to you in a sexual encounter?",
    "Are you interested in exploring different locations or settings for sexual activities, or do you prefer privacy and familiarity?",
    "Do you have any specific fantasies or role-playing scenarios you'd like to explore with a partner?",
    "How important is communication and feedback during sex to ensure both partners' needs are met?",
)
val limitsANDBoundaries = listOf(
"What are your hard limits or non-negotiable boundaries when it comes to sexual activities?",
"Are you comfortable discussing and respecting your partner's boundaries during a sexual encounter?",
"How important is consent and mutual agreement before engaging in any sexual activity?",
"Do you prefer to establish safe words or signals to communicate discomfort or the need to stop during sex?",
"Are you open to discussing your sexual health status and practices with potential partners?",
"How do you handle situations where a partner wants to try something you're not comfortable with?",
"Are you comfortable using protection and practicing safe sex with new partners?",
"Do you have any specific preferences or requirements regarding sexual hygiene and cleanliness?",
"How do you navigate discussions about safer sex practices and STI testing with potential partners?",
"Are you open to discussing past sexual experiences or history with new partners?",
)
val expectationsANDCommunication = listOf(
"How do you prefer to communicate your sexual desires and needs with a partner?",
"Are you comfortable discussing fantasies or desires that may be considered unconventional or taboo?",
"How do you handle conversations about sexual performance and satisfaction with a partner?",
"Are you open to giving and receiving feedback during sex to enhance the experience for both partners?",
"How important is open and honest communication about sexual preferences and expectations before meeting up?",
"Are you comfortable discussing any concerns or anxieties you may have about a sexual encounter with your partner?",
"How do you navigate discussions about sexual history and experiences with potential partners?",
"Are you open to discussing boundaries and expectations regarding the nature of the sexual encounter (e.g., one-time hookup vs. ongoing arrangement)?",
"How do you handle situations where there's a mismatch in sexual desires or expectations between you and your partner?",
"How do you prioritize mutual respect and consideration for your partner's feelings and boundaries during a sexual encounter?",
)


//val genderList = listOf("Cis-Gender", "Transgender", "Non-binary", "Doesn't Matter")
//val zodiacList = listOf("Aries", "Taurus", "Gemini", "Cancer",  "Leo", "Virgo", "Libra",  "Scorpio",  "Sagittarius",  "Capricorn", "Aquarius", "Pisces", "Doesn't Matter")
//val sexualOriList = listOf("Androsexual", "Asexual", "Bisexual", "Demisexuality", "Gynesexual", "HeteroSexual", "Pansexual", "Queer", "Questioning", "Doesn't Matter")
//val mbtiList = listOf(  "INTJ", "INTP", "ENTJ", "ENTP", "ENFP", "ENFJ", "INFP", "INFJ", "ESFJ", "ESTJ", "ISFJ", "ISTJ", "ISTP", "ISFP", "ESTP", "ESFP", "Not Taken")
//val childrenList = listOf("Don't have", "Have children", "Doesn't Matter")
//val familyPlansList = listOf("Don't want", "Want children", "Open to children", "Not sure yet", "Doesn't Matter")
//val educationList = listOf("High School", "Undergrad", "Postgrad", "Doesn't Matter")
//val religionList = listOf("Agnostic", "Atheist", "Buddhist", "Catholic", "Christian", "Hindu", "Jewish", "Muslim", "Sikh", "Spiritual", "Other", "Doesn't Matter")
//val politicalViewsList = listOf("Liberal", "Moderate", "Conservative", "Not Political", "Doesn't Matter")
//val relationshipTypeList = listOf("Monogamy", "Non-monogamy", "Finding it", "Doesn't Matter")
//val intentionsList = listOf("Life Partner", "Long-term", "Long open to short", "Short open to long", "Short-term", "Doesn't Matter")
//val drinkList = listOf("Yes", "Sometimes", "No", "Doesn't Matter")
//val smokeList = listOf("Yes", "Sometimes", "No", "Doesn't Matter")
//val weedList = listOf("Yes", "Sometimes", "No", "Doesn't Matter")
//val meetUpList = listOf("Right Away", "Talk first", "Take it slow", "Doesn't Matter")


