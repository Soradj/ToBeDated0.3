package com.threegroup.tobedated

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import com.google.firebase.storage.FirebaseStorage
import com.threegroup.tobedated.casual.casualAppBios
import com.threegroup.tobedated.casual.expectationsANDCommunication
import com.threegroup.tobedated.casual.expectationsANDCommunicationAnswers
import com.threegroup.tobedated.casual.limitsANDBoundaries
import com.threegroup.tobedated.casual.limitsANDBoundariesAnswers
import com.threegroup.tobedated.casual.preferencesAndDesires
import com.threegroup.tobedated.casual.preferencesAndDesiresAnswers
import com.threegroup.tobedated.casual.tabsCasual
import com.threegroup.tobedated.dating.curiositiesANDImaginations
import com.threegroup.tobedated.dating.curiositiesANDImaginationsAnswers
import com.threegroup.tobedated.dating.datingAppBios
import com.threegroup.tobedated.dating.insightsANDReflections
import com.threegroup.tobedated.dating.insightsANDReflectionsAnswers
import com.threegroup.tobedated.dating.passionsANDInterests
import com.threegroup.tobedated.dating.passionsANDInterestsAnswers
import com.threegroup.tobedated.dating.tabs
import com.threegroup.tobedated.generic.afterCareOptions
import com.threegroup.tobedated.generic.childrenOptions
import com.threegroup.tobedated.generic.commOptions
import com.threegroup.tobedated.generic.drinkOptions
import com.threegroup.tobedated.generic.educationOptions
import com.threegroup.tobedated.generic.ethnicityOptions
import com.threegroup.tobedated.generic.experienceOptions
import com.threegroup.tobedated.generic.familyOptions
import com.threegroup.tobedated.generic.firstNames
import com.threegroup.tobedated.generic.genderOptions
import com.threegroup.tobedated.generic.getPhotoUri
import com.threegroup.tobedated.generic.intentionsOptions
import com.threegroup.tobedated.generic.leaningOptions
import com.threegroup.tobedated.generic.locationOptions
import com.threegroup.tobedated.generic.lookingForOptions
import com.threegroup.tobedated.generic.mbtiOption
import com.threegroup.tobedated.generic.meetUpOptions
import com.threegroup.tobedated.generic.politicsOptions
import com.threegroup.tobedated.generic.pronounOptions
import com.threegroup.tobedated.generic.relationshipOptions
import com.threegroup.tobedated.generic.religionOptions
import com.threegroup.tobedated.generic.seekingOptions
import com.threegroup.tobedated.generic.sexHealthOptions
import com.threegroup.tobedated.generic.sexOptions
import com.threegroup.tobedated.generic.sexOrientationOptions
import com.threegroup.tobedated.generic.smokeOptions
import com.threegroup.tobedated.generic.starOptions
import com.threegroup.tobedated.generic.weedOptions
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import java.io.InputStream
import kotlin.random.Random

// Function to generate a random first name


fun makeProfiles(context:Context, numberOfUsers:Int, startNumber: Int) {
    //val numberOfUsers = 6 // Change this to the number of users you want to generate
    val database = Firebase.database
    val reference = database.getReference("users")

    var number = 100 + startNumber

    runBlocking {
        repeat(numberOfUsers) {
            launch {
                val phoneNumber = "+16505558$number"
                val user = generateRandomUserData(phoneNumber, context)
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
suspend fun generateRandomUserData(number:String, context: Context): UserModel {
    val hasCas = Random.nextBoolean()
    val prompt1 = Random.nextInt(1, 10)
    val prompt2= Random.nextInt(1, 10)
    val prompt3= Random.nextInt(1, 10)
    val tab1 = tabs.random()
    val tab2= tabs.random()
    val tab3 = tabs.random()
    val tab1c = tabsCasual.random()
    val tab2c= tabsCasual.random()
    val tab3c = tabsCasual.random()
    val ethnicity = ethnicityOptions.random()
    val sex = sexOptions.random()
    //broken location --- 37.4220936/-122.083922
    //farmingDale ---- 40.7528570 -73.4265742
    val latitude = 40.7528570
    val longitude = -73.4265742

    val roundedLatitude = String.format("%.7f", latitude + Random.nextDouble(-1.000, 1.000))
    val roundedLongitude = String.format("%.7f", longitude + Random.nextDouble(-1.000, 1.000))
    val photoUrl = coroutineScope {
        async {
            val photoUri = getPhotoUri(ethnicity, sex, context)
            storeImageAttemptQUICK(photoUri, context.contentResolver, 1, number)
        }
    }.await()
    val userModel = UserModel(
        name = firstNames.random(), // Generate a random first name
        birthday = "0${Random.nextInt(1, 8)}/0${Random.nextInt(1, 9)}/${1970 + (Random.nextInt(1, 31))}", // Example birthday, you can generate random birthdays as well
        pronoun = pronounOptions.random(),
        gender = genderOptions.random(),
        height = "5'${Random.nextInt(1, 11)}\"", // Generate random height
        ethnicity = ethnicity,
        star = starOptions.random(),
        sexOrientation = sexOrientationOptions.random(),
        seeking = seekingOptions.random(),
        sex = sex,
        testResultsMbti = mbtiOption.random(),
        testResultTbd = Random.nextInt(1, 60),
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
            "Insights and Reflections" -> insightsANDReflectionsAnswers[prompt1].random()
            "Passions and Interests" -> passionsANDInterestsAnswers[prompt1].random()
            "Curiosities and Imaginations" -> curiositiesANDImaginationsAnswers[prompt1].random()
            else -> insightsANDReflectionsAnswers[prompt1].random()
        },
        promptQ2 = when (tab2) {
            "Insights and Reflections" -> insightsANDReflections[prompt2]
            "Passions and Interests" -> passionsANDInterests[prompt2]
            "Curiosities and Imaginations" -> curiositiesANDImaginations[prompt2]
            else -> insightsANDReflections[prompt2]
        },
        promptA2 = when (tab2) {
            "Insights and Reflections" -> insightsANDReflectionsAnswers[prompt2].random()
            "Passions and Interests" -> passionsANDInterestsAnswers[prompt2].random()
            "Curiosities and Imaginations" -> curiositiesANDImaginationsAnswers[prompt2].random()
            else -> insightsANDReflectionsAnswers[prompt2].random()
        },
        promptQ3 = when (tab3) {
            "Insights and Reflections" -> insightsANDReflections[prompt3]
            "Passions and Interests" -> passionsANDInterests[prompt3]
            "Curiosities and Imaginations" -> curiositiesANDImaginations[prompt3]
            else -> insightsANDReflections[prompt3]
        },
        promptA3 = when (tab3) {
            "Insights and Reflections" -> insightsANDReflectionsAnswers[prompt3].random()
            "Passions and Interests" -> passionsANDInterestsAnswers[prompt3].random()
            "Curiosities and Imaginations" -> curiositiesANDImaginationsAnswers[prompt3].random()
            else -> insightsANDReflectionsAnswers[prompt3].random()
        },
        bio = datingAppBios.random(),
        image1 = photoUrl,
        image2 = photoUrl,
        image3 = photoUrl,
        image4 = photoUrl,
        location = "$roundedLatitude/$roundedLongitude",
        status = System.currentTimeMillis(),
        number = number,//to gen random numbers
        verified = Random.nextBoolean(),
        seeMe = false,
        hasThree = false,
        hasCasual = hasCas,
        hasFriends = false,
        hasThreeCasual = false,
        userPref = UserSearchPreferenceModel(),
        casualAdditions = CasualAdditions(
            leaning= if(!hasCas){""}else{
                leaningOptions.random()},
            lookingFor=if(!hasCas){""}else{
                lookingForOptions.random()},
            experience=if(!hasCas){""}else{
                experienceOptions.random()},
            location=if(!hasCas){""}else{
                locationOptions.random()},
            comm=if(!hasCas){""}else{
                commOptions.random()},
            sexHealth=if(!hasCas){""}else{
                sexHealthOptions.random()},
            afterCare=if(!hasCas){""}else{
                afterCareOptions.random()},
            casualBio=if(!hasCas){""}else{
                casualAppBios.random()},
            promptQ1=if(!hasCas){""}else{when (tab2c) {
                "Preferences and Desires" -> preferencesAndDesires[prompt2]
                "Limits and Boundaries" -> limitsANDBoundaries[prompt2]
                "Expectations and Communication" -> expectationsANDCommunication[prompt2]
                else -> preferencesAndDesires[prompt2]
            }},
            promptA1=if(!hasCas){""}else{when (tab1c) {
                "Preferences and Desires" -> preferencesAndDesiresAnswers[prompt1].random()
                "Limits and Boundaries" -> limitsANDBoundariesAnswers[prompt1].random()
                "Expectations and Communication" -> expectationsANDCommunicationAnswers[prompt1].random()
                else -> preferencesAndDesiresAnswers[prompt1].random()
            }},
            promptQ2=if(!hasCas){""}else{when (tab2c) {
                "Preferences and Desires" -> preferencesAndDesires[prompt2]
                "Limits and Boundaries" -> limitsANDBoundaries[prompt2]
                "Expectations and Communication" -> expectationsANDCommunication[prompt2]
                else -> preferencesAndDesires[prompt2]
            }},
            promptA2=if(!hasCas){""}else{when (tab2c) {
                "Preferences and Desires" -> preferencesAndDesiresAnswers[prompt2].random()
                "Limits and Boundaries" -> limitsANDBoundariesAnswers[prompt2].random()
                "Expectations and Communication" -> expectationsANDCommunicationAnswers[prompt2].random()
                else -> preferencesAndDesiresAnswers[prompt2].random()
            }},
            promptQ3=if(!hasCas){""}else{when (tab3c) {
                "Preferences and Desires" -> preferencesAndDesires[prompt3]
                "Limits and Boundaries" -> limitsANDBoundaries[prompt3]
                "Expectations and Communication" -> expectationsANDCommunication[prompt3]
                else -> preferencesAndDesires[prompt3]
            }},
            promptA3=if(!hasCas){""}else{when (tab3c) {
                "Preferences and Desires" -> preferencesAndDesiresAnswers[prompt3].random()
                "Limits and Boundaries" -> limitsANDBoundariesAnswers[prompt3].random()
                "Expectations and Communication" -> expectationsANDCommunicationAnswers[prompt3].random()
                else -> preferencesAndDesiresAnswers[prompt3].random()
            }},
        )
    )
    return userModel
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