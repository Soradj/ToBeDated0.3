package com.threegroup.tobedated.shareclasses

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import com.google.firebase.storage.FirebaseStorage
import com.threegroup.tobedated.R
import com.threegroup.tobedated.shareclasses.models.UserModel
import com.threegroup.tobedated.shareclasses.models.UserSearchPreferenceModel
import com.threegroup.tobedated.shareclasses.models.childrenOptions
import com.threegroup.tobedated.shareclasses.models.curiositiesANDImaginations
import com.threegroup.tobedated.shareclasses.models.drinkOptions
import com.threegroup.tobedated.shareclasses.models.educationOptions
import com.threegroup.tobedated.shareclasses.models.ethnicityOptions
import com.threegroup.tobedated.shareclasses.models.familyOptions
import com.threegroup.tobedated.shareclasses.models.genderOptions
import com.threegroup.tobedated.shareclasses.models.insightsANDReflections
import com.threegroup.tobedated.shareclasses.models.intentionsOptions
import com.threegroup.tobedated.shareclasses.models.mbtiList
import com.threegroup.tobedated.shareclasses.models.meetUpOptions
import com.threegroup.tobedated.shareclasses.models.passionsANDInterests
import com.threegroup.tobedated.shareclasses.models.politicsOptions
import com.threegroup.tobedated.shareclasses.models.pronounOptions
import com.threegroup.tobedated.shareclasses.models.relationshipOptions
import com.threegroup.tobedated.shareclasses.models.religionOptions
import com.threegroup.tobedated.shareclasses.models.seekingOptions
import com.threegroup.tobedated.shareclasses.models.sexOptions
import com.threegroup.tobedated.shareclasses.models.sexOrientationOptions
import com.threegroup.tobedated.shareclasses.models.smokeOptions
import com.threegroup.tobedated.shareclasses.models.starOptions
import com.threegroup.tobedated.shareclasses.models.tabs
import com.threegroup.tobedated.shareclasses.models.weedOptions
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import java.io.InputStream
import kotlin.random.Random

// Function to generate a random first name
fun generateRandomFirstName(): String {
    val firstNames = listOf(
        "Alice", "Bob", "Charlie", "David", "Emma", "Frank", "Grace", "Henry", "Ivy", "Jack", "Kate", "Liam", "Mia", "Nathan",
        "Olivia", "Peter", "Quinn", "Rachel", "Sam", "Taylor", "Uma", "Victor", "Wendy", "Xavier", "Yara", "Zoe", "Alice", "Bob",
        "Charlie", "David", "Emma", "Frank", "Grace", "Henry", "Ivy", "Jack", "Kate", "Liam", "Mia", "Nathan", "Olivia", "Peter",
        "Quinn", "Rachel", "Sam", "Taylor", "Uma", "Victor", "Wendy", "Xavier",
        // New names added below
        "Zara", "Aaron", "Bella", "Connor", "Daisy", "Elijah", "Faith", "Gavin", "Hannah", "Isaac",
        "Jessica", "Kai", "Layla", "Matthew", "Natalie", "Oscar", "Paige", "Quentin", "Rebecca", "Seth",
        "Tara", "Uriel", "Violet", "William", "Xena", "Yasmine", "Zachary", "Ava", "Benjamin", "Chloe",
        "Daniel", "Emily", "Finn", "Grace", "Hugo", "Isla", "Jayden", "Kylie", "Liam", "Mila",
        "Noah", "Olivia", "Peyton", "Quinn", "Ryan", "Samantha", "Theo", "Ursula", "Victoria", "Wyatt",
        "Xander", "Yara", "Zoe"
    )
    return firstNames.random()
}
fun getDrawableResource(ethnicity: String, gender: String): Int {
    val resourceId = when {
        ethnicity == "Black/African Descent" && gender == "Male" -> R.drawable._blackmale
        ethnicity == "Black/African Descent" && gender == "Female" -> R.drawable._blackmale
        ethnicity == "East Asian" && gender == "Male" -> R.drawable._eastmale
        ethnicity == "East Asian" && gender == "Female" -> R.drawable._eastfemale
        ethnicity == "Hispanic/Latino" && gender == "Male" -> R.drawable._hispanicmale
        ethnicity == "Hispanic/Latino" && gender == "Female" -> R.drawable._hispanicfemale
        ethnicity == "Middle Eastern" && gender == "Male" -> R.drawable._middlemale
        ethnicity == "Middle Eastern" && gender == "Female" -> R.drawable._middlemale
        ethnicity == "Native American" && gender == "Male" -> R.drawable._nativemale
        ethnicity == "Native American" && gender == "Female" -> R.drawable._nativefemale
        ethnicity == "Pacific Islander" && gender == "Male" -> R.drawable._pacificmale
        ethnicity == "Pacific Islander" && gender == "Female" -> R.drawable._pacificfemale
        ethnicity == "South Asian" && gender == "Male" -> R.drawable._southmale
        ethnicity == "South Asian" && gender == "Female" -> R.drawable._southfemale
        ethnicity == "Southeast Asian" && gender == "Male" -> R.drawable._southeastmale
        ethnicity == "Southeast Asian" && gender == "Female" -> R.drawable._southeastfemale
        ethnicity == "White/Caucasian" && gender == "Male" -> R.drawable._whitemale
        ethnicity == "White/Caucasian" && gender == "Female" -> R.drawable._whitefemale
        else -> R.drawable._hispanicfemale // Default resource ID
    }
    Log.d("getDrawableResource", "Resource ID: $resourceId")
    return resourceId
}
fun getResourceUri(context: Context, resourceId: Int): String {
    val uriString = "android.resource://${context.packageName}/$resourceId"
    Log.d("getResourceUri", "URI: $uriString")
    return uriString
}
suspend fun generateRandomUserData(number:String, context: Context, contentResolver:ContentResolver): UserModel {
    val prompt1 = Random.nextInt(1, 10)
    val prompt2= Random.nextInt(1, 10)
    val prompt3= Random.nextInt(1, 10)
    val tab1 = tabs.random()
    val tab2= tabs.random()
    val tab3 = tabs.random()
    val ethnicity = ethnicityOptions.random()
    val sex = sexOptions.random()
    val photoUrl = coroutineScope {
        async {
            val photoResourceId = getDrawableResource(ethnicity, sex)
            val photoUri = getResourceUri(context, photoResourceId)
            storeImageAttemptQUICK(photoUri, contentResolver, 1, number)
        }
    }.await()
    val userModel = UserModel(
        name = generateRandomFirstName(), // Generate a random first name
        birthday = "0${Random.nextInt(1, 8)}/0${Random.nextInt(1, 9)}/${1970 + (Random.nextInt(1, 31))}", // Example birthday, you can generate random birthdays as well
        pronoun = pronounOptions.random(),
        gender = genderOptions.random(),
        height = "5'${Random.nextInt(1, 11)}\"", // Generate random height
        ethnicity = ethnicity,
        star = starOptions.random(),
        sexOrientation = sexOrientationOptions.random(),
        seeking = seekingOptions.random(),
        sex = sex,
        testResultsMbti = mbtiList.random(),
        testResultTbd = Random.nextInt(1, 11),
        children = childrenOptions.random(),
        family = familyOptions.random(),
        education = educationOptions.random(),
        religion = religionOptions.random(),
        politics = politicsOptions.random(),
        relationship = relationshipOptions.random(),
        intentions = intentionsOptions.random(),
        drink = drinkOptions.random(),
        smoke = smokeOptions.random(),
        weed = weedOptions.random(),
        meetUp = meetUpOptions.random(),
        promptQ1 = when (tab1) {
            "Insights and Reflections" -> insightsANDReflections[prompt1]
            "Passions and Interests" -> passionsANDInterests[prompt1]
            "Curiosities and Imaginations" -> curiositiesANDImaginations[prompt1]
            else -> insightsANDReflections[prompt1]
        },
        promptA1 = when (tab1) {
            "Insights and Reflections" -> insightsANDReflectionsAnswers[prompt1]
            "Passions and Interests" -> passionsANDInterestsAnswers[prompt1]
            "Curiosities and Imaginations" -> curiositiesANDImaginationsAnswers[prompt1]
            else -> insightsANDReflectionsAnswers[prompt1]
        },
        promptQ2 = when (tab2) {
            "Insights and Reflections" -> insightsANDReflections[prompt2]
            "Passions and Interests" -> passionsANDInterests[prompt2]
            "Curiosities and Imaginations" -> curiositiesANDImaginations[prompt2]
            else -> insightsANDReflections[prompt2]
        },
        promptA2 = when (tab2) {
            "Insights and Reflections" -> insightsANDReflectionsAnswers[prompt2]
            "Passions and Interests" -> passionsANDInterestsAnswers[prompt2]
            "Curiosities and Imaginations" -> curiositiesANDImaginationsAnswers[prompt2]
            else -> insightsANDReflectionsAnswers[prompt2]
        },
        promptQ3 = when (tab3) {
            "Insights and Reflections" -> insightsANDReflections[prompt3]
            "Passions and Interests" -> passionsANDInterests[prompt3]
            "Curiosities and Imaginations" -> curiositiesANDImaginations[prompt3]
            else -> insightsANDReflections[prompt3]
        },
        promptA3 = when (tab3) {
            "Insights and Reflections" -> insightsANDReflectionsAnswers[prompt3]
            "Passions and Interests" -> passionsANDInterestsAnswers[prompt3]
            "Curiosities and Imaginations" -> curiositiesANDImaginationsAnswers[prompt3]
            else -> insightsANDReflectionsAnswers[prompt3]
        },
        bio = datingAppBios.random(),
        image1 = photoUrl,
        image2 = photoUrl,
        image3 = photoUrl,
        image4 = photoUrl,
        location = "${(40.7528570 + Random.nextDouble(-1.000, 1.000))}/${-73.4265742 + Random.nextDouble(-1.000, 1.000)}",//37.4220936/-122.083922
        status = System.currentTimeMillis(),
        number = number,//to gen random numbers
        verified = false,
        seeMe = false,
        hasThree = false,
        hasCasual = false,
        hasFriends = false,
        userPref = UserSearchPreferenceModel()
    )
    return userModel
}

fun makeProfiles(context:Context, contentResolver: ContentResolver) {
    val numberOfUsers = 25 // Change this to the number of users you want to generate
    val database = Firebase.database
    val reference = database.getReference("users")

    var number = 150

    runBlocking {
        repeat(numberOfUsers) {
            launch {
                val phoneNumber = "+16505558$number"
                val user = generateRandomUserData(phoneNumber, context, contentResolver)
                println(user)
                reference.child(phoneNumber).setValue(user)
                    .addOnSuccessListener {
                        println("User added successfully")

                    }
                    .addOnFailureListener { e ->
                        println("Failed to add user: $user, error: $e")
                    }
                number++
            }
            // Introduce a delay to ensure each user is added sequentially
            delay(1000) // Adjust the delay time as needed
        }
    }
}

suspend fun storeImageAttemptQUICK(uriString: String, contentResolver: ContentResolver, imageNumber: Int, userNumber: String): String {
    var downloadUrl = ""
    var inputStream: InputStream? = null
    try {
        val storageRef = FirebaseStorage.getInstance().reference
        val uri = Uri.parse(uriString)
        inputStream = contentResolver.openInputStream(uri) ?: run {
            Log.e("storeImageAttempt", "Failed to open input stream for URI: $uriString")
            return ""
        }

        // Define the image path including the user's ID and image number
        val imagePath = "mock_profiles/image$userNumber"

        // Upload the image to Firebase Storage
        val imageRef = storageRef.child(imagePath)
        val uploadTask = imageRef.putStream(inputStream).await()
        downloadUrl = imageRef.downloadUrl.await().toString()

        // Store the download URL in the Firebase Realtime Database under the user's ID and image number
        val databaseRef = FirebaseDatabase.getInstance().reference
        val userImagesRef = databaseRef.child("users").child(userNumber).child("image$imageNumber")
        userImagesRef.setValue(downloadUrl)
            .addOnSuccessListener {
                Log.d("storeImageAttempt", "Download URL stored successfully")
            }
            .addOnFailureListener { e ->
                Log.e("storeImageAttempt", "Failed to store download URL: ${e.message}")
            }
    } catch (e: Exception) {
        Log.e("storeImageAttempt", "Error uploading image: ${e.message}")
    } finally {
        inputStream?.close()
    }
    return downloadUrl
}

val insightsANDReflectionsAnswers = listOf(
    "People often think I'm an extrovert because I can be quite talkative in social settings, but I'm actually more introverted and recharge by spending time alone.",
    "Don't worry so much about what others think. Just focus on being your authentic self and follow your passion.",
    "I've always wanted to learn how to play the piano, but I haven't had the chance to start lessons yet.",
    "The day I moved to a new city by myself for a job was significant. It made me realize I was capable of more than I thought and taught me a lot about independence.",
    "I can talk for hours about technology and its impact on society because I'm fascinated by how it changes the way we live and interact.",
    "In a past relationship, I learned that communication is key. Misunderstandings often arise when people don't openly share their thoughts and feelings.",
    "If I could rewrite one moment from my past, it would be a job interview where I was extremely nervous. I'd go back and tell myself to relax and just be myself.",
    "Lately, I've been struggling with balancing work and personal life. To cope, I set strict boundaries for work hours and make sure to spend time doing things I enjoy.",
    "I once volunteered to give a public presentation, even though I'm usually not comfortable speaking in front of large groups. It made me realize that I could overcome my fears with practice and preparation.",
    "I wish more people understood that while I might seem calm on the outside, I sometimes feel anxious on the inside. I just choose to manage it in my own way."
)
val passionsANDInterestsAnswers = listOf(
    "I'm deeply passionate about animal welfare, but not many people know because I mostly support causes behind the scenes.",
    "I've been meaning to start painting, but I haven't yet found the time or gathered the necessary supplies.",
    "If I could attend any event, it would be Woodstock in 1969, just to experience the iconic music and culture of that era.",
    "I strongly support environmental conservation because I believe in preserving our planet for future generations.",
    "The book \"To Kill a Mockingbird\" profoundly influenced me, teaching me about empathy and social justice.",
    "I admire people who are excellent public speakers; I wish I had that level of confidence and eloquence.",
    "If I could spend a day with someone who inspires me, it would be with Jane Goodall. I'd love to learn about her work with chimpanzees and conservation.",
    "I'm currently working towards improving my fitness. To achieve this, I exercise regularly and maintain a healthy diet.",
    "A recent accomplishment I'm proud of is completing a challenging course that I was hesitant to take, proving to myself that I could do it.",
    "If I could make one positive change in the world, it would be to ensure universal access to clean water. It's fundamental to health and well-being."
)
val curiositiesANDImaginationsAnswers = listOf(
    "If I could witness any historical event, it would be the moon landing in 1969. It was a monumental achievement for humanity.",
    "I've always wanted to visit Machu Picchu in Peru. The history and natural beauty draw me in.",
    "I've been curious to try scuba diving, but I haven't had the chance to do it yet.",
    "If I could live in any era other than the present, it would be the Renaissance. The creativity and exploration during that time fascinate me.",
    "I'd love to visit the Shire from 'The Lord of the Rings.' It seems like such a peaceful and idyllic place.",
    "If I could master any language overnight, I'd choose Mandarin Chinese. It's a complex language with many speakers, which would open up new cultural experiences.",
    "I'm excited about the advancements in renewable energy. It's crucial for the future of our planet.",
    "If I could have dinner with any fictional character, it would be with Sherlock Holmes. I'd love to discuss his problem-solving techniques.",
    "I once had a dream where I was flying over a beautiful landscape. The feeling of freedom and serenity stayed with me.",
    "If I could have a conversation with my future self, I'd ask, 'Did everything turn out okay? What should I focus on to ensure the best outcome?'"
)
val datingAppBios = listOf(
    "An avid lover of art galleries, I'm always on the lookout for the next inspiring exhibit. My idea of a perfect date? A leisurely stroll through the museum followed by a cozy café for deep conversations over coffee and croissants.",
    "With a passion for fitness, you'll often find me hitting the gym or out for a run along the waterfront. But don't be fooled, I also have a weakness for indulging in culinary delights, and I'm on the constant lookout for the city's best taco joint.",
    "As a self-proclaimed bibliophile, there's nothing I love more than losing myself in the pages of a good book. Whether it's fiction, philosophy, or poetry, I'm always up for a literary adventure. Seeking someone to share quiet nights curled up with a good read and maybe a glass of wine.",
    "Music is my soul's language, and I'm fluent in everything from jazz to indie rock. Whether it's attending live shows or spending hours crate-digging for vintage vinyl, I'm always on the hunt for the perfect melody to soundtrack life's moments.",
    "My wanderlust knows no bounds, and I'm constantly plotting my next great escape. Whether it's backpacking through Southeast Asia or road-tripping across Europe, I'm seeking a fellow adventurer to join me on my quest to explore the world, one passport stamp at a time.",
    "With a deep reverence for the great outdoors, I'm most at peace when surrounded by nature's beauty. Whether it's hiking through lush forests or camping under the stars, I'm seeking a kindred spirit to share in the serenity of Mother Earth's embrace.",
    "Cinema is my passion, and I have an encyclopedic knowledge of everything from classic films to cult favorites. From cozy movie marathons to midnight screenings of indie gems, I'm looking for someone to share my love of storytelling and cinematic magic.",
    "A fervent advocate for sustainability and eco-conscious living, I'm passionate about reducing my carbon footprint and making a positive impact on the planet. Seeking a partner who shares my commitment to environmental stewardship and building a greener future together.",
    "As a tech enthusiast and gaming aficionado, I'm always up for a virtual adventure or friendly competition. From retro classics to cutting-edge VR experiences, I'm seeking a player two to join me on epic quests and pixelated escapades.",
    "With a creative spirit and an eye for beauty, I find inspiration in every corner of the world. Whether it's capturing fleeting moments with my camera or expressing myself through paint and canvas, I'm looking for someone who shares my passion for artistic expression and creative exploration.",
    "I'm a connoisseur of all things food and wine, with a palate that craves both exotic flavors and comforting classics. Whether it's exploring hidden gems in the city or experimenting with new recipes at home, I'm seeking a culinary companion to join me on gastronomic adventures.",
    "An eternal optimist and hopeless romantic, I believe in love at first sight and happily ever afters. Let's create our own fairytale together, filled with laughter, passion, and endless adventures.",
    "With a knack for adventure and a thirst for adrenaline, I'm always on the lookout for my next thrilling escapade. Whether it's skydiving over breathtaking landscapes or diving into crystal-clear waters, I'm seeking a fearless companion to share in the excitement.",
    "A true cinephile with a love for all things film, I'm equally at home discussing the latest Hollywood blockbusters and obscure indie flicks. Let's bond over our shared appreciation for the silver screen and see where the movie magic takes us.",
    "As a lifelong learner and curious explorer, I'm constantly seeking out new experiences and knowledge. Whether it's mastering a new language or delving into a new hobby, I'm looking for someone who shares my thirst for discovery and growth.",
    "With a passion for fashion and an eye for style, I believe that every outfit tells a story. Whether it's scouring vintage shops for unique finds or staying ahead of the latest trends, I'm seeking a fellow fashionista to strut the streets with.",
    "A true animal lover with a soft spot for furry friends, I'm always up for a trip to the dog park or a cuddle session with my four-legged companion. Seeking someone who shares my love for animals and isn't afraid to get a little muddy.",
    "An incurable romantic with a love for grand gestures and heartfelt declarations. Let's write our own love story, filled with unforgettable moments and whispered promises under the stars.",
    "With a passion for the performing arts, I'm equally at home on stage or in the audience. Whether it's belting out show tunes or reciting Shakespearean soliloquies, I'm seeking a fellow drama queen to share the spotlight with.",
    "A coffee aficionado with a penchant for caffeine-fueled adventures. Whether it's sampling single-origin brews or hunting down the perfect espresso, I'm seeking a fellow java junkie to join me on my quest for the ultimate cup of joe.",
    "With a love for the great outdoors and a thirst for adventure, I'm always up for a hike through the mountains or a camping trip under the stars. Seeking someone who shares my passion for nature and isn't afraid to get a little dirt under their nails.",
    "An incurable optimist with a sunny disposition and a smile that lights up the room. Let's chase sunsets and dance in the rain, embracing each moment with joy and gratitude.",
    "With a passion for photography and a keen eye for detail, I believe that beauty can be found in the simplest of moments. Whether it's capturing the golden hues of a sunset or the candid laughter of loved ones, I'm seeking a partner who sees the world through the same lens.",
    "A true foodie at heart, I'm always on the hunt for my next culinary adventure. Whether it's sampling street food in foreign lands or recreating gourmet dishes at home, I'm seeking someone to join me on a gastronomic journey around the world.",
    "With a love for all things vintage and retro, I'm drawn to the timeless elegance of bygone eras. Whether it's swing dancing to jazz classics or sipping cocktails in a speakeasy, I'm seeking a partner who shares my appreciation for the finer things in life.",
    "An eternal wanderer with a restless spirit, I'm always on the lookout for my next great adventure. Whether it's backpacking through the mountains or exploring hidden gems in the city, I'm seeking a fellow explorer to join me on my journey.",
    "A true believer in the power of love and the magic of serendipity. Let's throw caution to the wind and see where fate takes us, trusting that our paths were meant to cross for a reason.",
    "With a passion for culture and a love for exploration, I'm always on the hunt for my next cultural immersion. Whether it's sampling street food in bustling markets or marveling at ancient ruins, I'm seeking someone who shares my thirst for adventure and discovery.",
    "A true romantic with a heart full of dreams and a soul full of love. Let's embark on a journey of passion and romance, writing our own love story with every whispered word and stolen kiss.",
    "With a love for the written word and a passion for storytelling, I believe that every story deserves to be told. Whether it's penning my own tales of adventure or getting lost in the pages of a beloved book, I'm seeking a fellow wordsmith to share in the magic of storytelling.",
    "A true believer in the power of connection and the magic of chemistry. Let's see if we can ignite sparks and create fireworks with just a glance and a smile, trusting in the power of attraction to lead us to our happily ever after.",
)
