package com.threegroup.tobedated

import android.Manifest
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.threegroup.tobedated._casual.CasualNav
import com.threegroup.tobedated._casual.signup.SignUpCasualNav
import com.threegroup.tobedated._dating.DatingNav
import com.threegroup.tobedated._login.LoginNav
import com.threegroup.tobedated._signUp.SignUpNav
import com.threegroup.tobedated.composeables.composables.PolkaDotCanvas
import com.threegroup.tobedated.composeables.composables.SplashScreen
import com.threegroup.tobedated.shareclasses.api.ApiViewModel
import com.threegroup.tobedated.theme.AppTheme

@Composable
fun AppNav(mainActivity: MainActivity, activityToken:String) {
    val navMain = rememberNavController()
    val vmApi = viewModel { ApiViewModel(MyApp.x) }
    var inMainActivities by remember { mutableStateOf(false) }
    LaunchedEffect(inMainActivities) {
        if(inMainActivities){
            vmApi.fetchWordOfTheDay()
            vmApi.fetchHoroscope(MyApp.signedInUser.value!!.star)
            vmApi.fetchPoem()
        }
    }
    //TODO BACKSTACK
    NavHost(navController = navMain, startDestination = "SlashScreen",
        enterTransition = { EnterTransition.None }, exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },popExitTransition = { ExitTransition.None })
    {
        composable(route = "SlashScreen") {
            AppTheme(activityToken){
                PolkaDotCanvas()
                SlashScreen(activityToken = activityToken, mainActivity, navMain)
            }
        }
        composable(route = "Dating") {
            inMainActivities = true
            AppTheme(activity = "dating") {
                TopAndBotBars(
                    vmApi = vmApi,
                    mainNav = navMain,
                    mainActivity = mainActivity,
                    navScreen = { nav, callback ->
                        DatingNav(mainActivity, vmApi, navMain, nav) { inside ->
                            callback(inside)
                        }
                    },
                    currentActivity = "dating"
                )
            }
        }
        composable(route = "Casual") {
            inMainActivities = true
            mainActivity.setLastActivity("casual")
            AppTheme(activity = "casual") {
                TopAndBotBars(
                    vmApi = vmApi,
                    mainNav = navMain,
                    mainActivity = mainActivity,
                    navScreen = { nav, callback ->
                        CasualNav(mainActivity, vmApi, navMain, nav) { inside ->
                            callback(inside)
                        }
                    },
                    currentActivity = "casual"
                )
            }
        }
        composable(route = "CasualSignup"){
            AppTheme(activity = "casual") {
                PolkaDotCanvas()
                SignUpCasualNav(mainNav = navMain)
            }
        }
        composable(route = "Friends") {
//            inMainActivities = true
//            mainActivity.setLastActivity("friend")
//            AppTheme(activity = "friend") {
//                TopAndBotBars(
//                    vmApi = vmApi,
//                    mainNav = navMain,
//                    mainActivity = mainActivity,
//                    navScreen = { nav, callback ->
//                        FriendsNav(mainActivity, vmApi, navMain, nav) { inside ->
//                            callback(inside)
//                        }
//                    },
//                    currentActivity = "friend"
//                )
//            }
        }
        composable(route = "SignUp/{location1}/{location2}/{userPhone}",
            arguments = listOf(
                navArgument("location1") { type = NavType.StringType },
                navArgument("location2") { type = NavType.StringType },
                navArgument("userPhone") { type = NavType.StringType },
            )
            ) { backStackEntry ->
            val location1 = backStackEntry.arguments?.getString("location1") ?: ""
            val location2 = backStackEntry.arguments?.getString("location2") ?: ""
            val location = "$location1/$location2"
            val userPhone = backStackEntry.arguments?.getString("userPhone") ?: ""
            AppTheme(activity = "dating") {
                //navMain.popBackStack()
                PolkaDotCanvas()
                SignUpNav(mainActivity, location, userPhone, navMain)
            }
        }
        composable(route = "Login/{location1}/{location2}",
            arguments = listOf(
                navArgument("location1") { type = NavType.StringType },
                navArgument("location2") { type = NavType.StringType }
            )) { backStackEntry ->
            val location1 = backStackEntry.arguments?.getString("location1") ?: ""
            val location2 = backStackEntry.arguments?.getString("location2") ?: ""
            val location = "$location1/$location2"
            AppTheme(activity = "dating") {
                PolkaDotCanvas()
                LoginNav(navMain, mainActivity, location)
            }
        }
    }
}
@Composable
fun SlashScreen(activityToken:String, mainActivity: MainActivity, navMain: NavHostController){
    val passedLocation by remember { mutableStateOf(mainActivity.isLocationPermissionGranted(mainActivity)) }
    val permissionsToRequest = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)

    when (activityToken) {
        "dating" -> {
            SplashScreen(activity = activityToken, text1 = "To Be Dated")
        }
        "casual" -> {
            SplashScreen(activity = activityToken, text1 = "To Be Casual")
        }
        "friend" -> {
            SplashScreen(activity = activityToken, text1 = "To Be Friended")
        }
    }

    LaunchedEffect(Unit) {
        if (!passedLocation) {
            mainActivity.setNav(navMain)
            mainActivity.requestLocationPermissionLauncher.launch(permissionsToRequest)
        } else {
            mainActivity.checkUserTokenAndNavigate { whereTo, location, no ->
                if (no == "no") {
                    when (whereTo) {
                        "dating" -> {navMain.navigate("Dating"){
                            popUpTo("SlashScreen") {
                                inclusive = true
                                saveState = false
                            }
                        }}
                        "casual" -> {navMain.navigate("Casual"){
                            popUpTo("SlashScreen") {
                                inclusive = true
                                saveState = false
                            }
                        }}
                        "friend" -> {navMain.navigate("Friends"){
                            popUpTo("SlashScreen") {
                                inclusive = true
                                saveState = false
                            }
                        }}
                        else -> {navMain.navigate("Login/$location"){
                            popUpTo("SlashScreen") {
                                inclusive = true
                                saveState = false
                            }
                        }}
                    }
                } else {
                    navMain.navigate("Login/$location") {
                        popUpTo("SlashScreen") {
                            inclusive = true
                            saveState = false
                        }
                    }
                }
            }
        }
    }
}