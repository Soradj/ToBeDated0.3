package com.threegroup.tobedated._causal

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.threegroup.tobedated._causal.composables.TopAndBotBarsCausal
import com.threegroup.tobedated._dating.DatingActivity
import com.threegroup.tobedated._dating.notifiChat
import com.threegroup.tobedated._dating.notifiGroup
import com.threegroup.tobedated._friends.FriendsActivity
import com.threegroup.tobedated.shareclasses.MyApp
import com.threegroup.tobedated.shareclasses.composables.Comeback
import com.threegroup.tobedated.shareclasses.composables.PolkaDotCanvas
import com.threegroup.tobedated.shareclasses.theme.AppTheme

class CausalActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("activityToken", "causal")
        editor.apply()
        setContent {
            AppTheme(
                activity = "casual"//causal
            ) {
                val viewModelCausal = viewModel { CausalViewModel(MyApp.x) }
                viewModelCausal.setLoggedInUser()

                if(viewModelCausal.getUser().hasCasual){
                    CausalNav(this@CausalActivity, viewModelCausal)
                }else{
                    PolkaDotCanvas()
                    SignUpCausalNav(this@CausalActivity, viewModelCausal)
                }
                //CausalNav(this@CausalActivity, token, location)
            }
        }
    }
    fun newContent(viewModelCausal:CausalViewModel){
        setContent {
            AppTheme(
                activity = "casual"//causal
            ) {
                CausalNav(this@CausalActivity, viewModelCausal)
            }
        }
    }
    //This is the fuck tab
    //adding it so I can say "fuck tab"
    fun switchActivities(switchActivity:String){
        val intent = when (switchActivity) {
            "dating" -> {
                Intent(this, DatingActivity::class.java)
            }

            "causal" -> {
                Intent(this, CausalActivity::class.java)
            }

            "friends" -> {
                Intent(this, FriendsActivity::class.java)
            }
            else -> {
                Intent(this, DatingActivity::class.java)
            }
        }
        startActivity(intent)
        finish()
    }
}
@Composable
fun SearchingScreen(navController: NavHostController, causal: CausalActivity, vmCausal: CausalViewModel){
    val state = rememberScrollState()
    TopAndBotBarsCausal(
        causal = causal,
        notifiChat = notifiChat,
        notifiGroup = notifiGroup,
        titleText = "To Be Casual",
        isPhoto = true,
        nav = navController,
        selectedItemIndex = 2,
        settingsButton = { },
        state = state,
        currentScreen = {
            PolkaDotCanvas()
        }
    )
}
@Composable
fun ProfileScreen(navController: NavHostController, causal: CausalActivity, vmCausal: CausalViewModel){
    val state = rememberScrollState()
    TopAndBotBarsCausal(
        causal = causal,
        notifiChat = notifiChat,
        notifiGroup = notifiGroup,
        titleText = "To Be Casual",
        isPhoto = true,
        nav = navController,
        selectedItemIndex = 0,
        settingsButton = { },
        state = state,
        currentScreen = {
            PolkaDotCanvas()
        }
    )
}
@Composable
fun EditProfileScreen(nav: NavHostController, causalActivity: CausalActivity, vmCausal: CausalViewModel){
    PolkaDotCanvas()
}
@Composable
fun SearchPreferenceScreen(nav: NavHostController, vmCausal: CausalViewModel){
    PolkaDotCanvas()
}
@Composable
fun ChatsScreen(navController: NavHostController, causal: CausalActivity, vmCausal: CausalViewModel){
    val state = rememberScrollState()
    TopAndBotBarsCausal(
        causal = causal,
        notifiChat = notifiChat,
        notifiGroup = notifiGroup,
        titleText = "To Be Casual",
        isPhoto = true,
        nav = navController,
        selectedItemIndex = 0,
        settingsButton = { },
        state = state,
        currentScreen = {
            PolkaDotCanvas()
        }
    )
}
@Composable
fun GroupsScreen(navController: NavHostController, causal: CausalActivity){
    val state = rememberScrollState()
    TopAndBotBarsCausal(
        causal = causal,
        notifiChat = notifiChat,
        notifiGroup = notifiGroup,
        titleText = "To Be Casual",
        isPhoto = true,
        nav = navController,
        selectedItemIndex = 0,
        settingsButton = { },
        state = state,
        currentScreen = {
            PolkaDotCanvas()
        }
    )
}
@Composable
fun SomeScreen(navController: NavHostController, causal: CausalActivity){
    val state = rememberScrollState()
    TopAndBotBarsCausal(
        causal = causal,
        notifiChat = notifiChat,
        notifiGroup = notifiGroup,
        titleText = "To Be Casual",
        isPhoto = true,
        nav = navController,
        selectedItemIndex = 0,
        settingsButton = { },
        state = state,
        currentScreen = {
            PolkaDotCanvas()
        }
    )
}
@Composable
fun MessagerScreen(nav: NavHostController, vmCausal: CausalViewModel){
    PolkaDotCanvas()
}
@Composable
fun ChangePreference(navController: NavHostController, title:String, index:Int, vmCausal: CausalViewModel){
    PolkaDotCanvas()
}
@Composable
fun ChangeProfileScreen(navController: NavHostController, title:String, index:Int, vmCausal: CausalViewModel){
    PolkaDotCanvas()
}
@Composable
fun ComeBackScreen(navController: NavHostController, causal: CausalActivity){
    TopAndBotBarsCausal(
        causal = causal,
        notifiChat = notifiChat,
        notifiGroup = notifiGroup,
        titleText = "To Be Casual",
        isPhoto = true,
        nav = navController,
        selectedItemIndex = 0,
        settingsButton = { navController.navigate("SearchPreferenceScreen") },
        currentScreen = {
            Comeback(text = "currently loading your future connection")
        }
    )
}

enum class Causal {
    SearchingScreen,
    SearchPreferenceScreen,
    ProfileScreen,
    EditProfileScreen,
    ChatsScreen,
    GroupsScreen,
    SomeScreen,
    MessagerScreen,
}