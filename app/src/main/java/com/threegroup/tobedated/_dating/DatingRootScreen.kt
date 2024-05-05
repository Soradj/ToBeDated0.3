package com.threegroup.tobedated._dating

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.threegroup.tobedated.MyApp
import com.threegroup.tobedated.R
import com.threegroup.tobedated.composeables.composables.NavDraw
import com.threegroup.tobedated.composeables.composables.TopBarText
import com.threegroup.tobedated.composeables.composables.getBottomColors
import com.threegroup.tobedated.composeables.composables.getTopColors
import com.threegroup.tobedated.shareclasses.api.ApiViewModel
import com.threegroup.tobedated.theme.AppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

data class BotNavItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNew: Boolean = false,
    val badgeCount: Int? = null,
)

@Composable
fun TopAndBotBarsDating(
    dating: DatingActivity,
//    vmApi: ApiViewModel,
) {
    val nav = rememberNavController()
    val vmApi = viewModel { ApiViewModel(MyApp.x) }
    val viewModelDating = viewModel { DatingViewModel(MyApp.x) }
    var inMain by remember { mutableStateOf(true) }
    var insideWhat by remember { mutableStateOf("") }



    val vmDating = viewModel { DatingViewModel(MyApp.x) } // Could pass as a parameter
    var notificationCount by remember { mutableIntStateOf(0) }
    // Initialize the notification count when the composable is first composed
    LaunchedEffect(Unit) {
        vmDating.updateNotificationCounts { count ->
            notificationCount = count
        }
    }
    val items = listOf(
        BotNavItem(
            title = "SomeScreen",
            selectedIcon = ImageVector.vectorResource(id = R.drawable.some_filled),
            unselectedIcon = ImageVector.vectorResource(id = R.drawable.some_outlined),
            badgeCount = 0
        ),
        BotNavItem(
            title = "ChatsScreen",
            selectedIcon = ImageVector.vectorResource(id = R.drawable.chats_filled),
            unselectedIcon = ImageVector.vectorResource(id = R.drawable.chats_outlined),
            badgeCount = notificationCount
        ),
        BotNavItem(
            title = "SearchingScreen",
            selectedIcon = ImageVector.vectorResource(id = R.drawable.logo_filled),
            unselectedIcon = ImageVector.vectorResource(id = R.drawable.logo_outlined),
            hasNew = false,
            badgeCount = 0
        ),
        BotNavItem(
            title = "BlindScreen",
            selectedIcon = ImageVector.vectorResource(id = R.drawable.groups_filled),
            unselectedIcon = ImageVector.vectorResource(id = R.drawable.groups_outlined),
            hasNew = notifiGroup,
            badgeCount = 0
        ),
        BotNavItem(
            title = "ProfileScreen",
            selectedIcon = ImageVector.vectorResource(id = R.drawable.profile_filled),
            unselectedIcon = ImageVector.vectorResource(id = R.drawable.profile_outlined),
            badgeCount = 0
        ),
    )

    //var selectedItemIndex by rememberSaveable { mutableIntStateOf(2) }
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color.Transparent,
    ) {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet(
                    drawerContainerColor = AppTheme.colorScheme.background
                ) {
                    NavDraw(
                        vmApi = vmApi,
                        colorDating = AppTheme.colorScheme.primary,
                        datingClickable = {},
                        causalClickable = {
                            dating.switchActivities("causal")
                        },
                        friendsClickable = {
                            dating.switchActivities("friends")
                        }
                    )
                }
            },
        ) {
            var selectedItemIndex by remember { mutableIntStateOf(2) }
            Scaffold(
                containerColor = if (isSystemInDarkTheme()) Color(0xFF181618) else Color(0xFFCDC2D0),
                bottomBar = {
                    if(inMain){
                        NavigationBar(
                            containerColor = AppTheme.colorScheme.onTertiary,
                            modifier = Modifier.height(46.dp)
                        ) {
                            items.forEachIndexed { index, item ->
                                NavigationBarItem(
                                    colors = getBottomColors(),
                                    selected = selectedItemIndex == index,
                                    onClick = { //selectedItemIndex = index
                                        nav.navigate(item.title)
                                        selectedItemIndex = index
                                    },//HANDLE NAVIGATION
                                    label = { },
                                    alwaysShowLabel = false,
                                    icon = { BadgedBox(item, index, selectedItemIndex) }
                                )
                            }
                        }
                    }
                },
                topBar = {
                    if(inMain){
                        TopBarMain(titleTextNum = selectedItemIndex, scope, drawerState,
                            action = {
                                when(selectedItemIndex){
                                    0 -> 1+1
                                    1 -> 1+1
                                    2 -> {nav.navigate("SearchPreferenceScreen")
                                    inMain = false}
                                    3 -> 1+1
                                    4 -> {nav.navigate("EditProfileScreen")
                                    inMain = false}
                                }
                            }
                        )
                    }else{
                        when(insideWhat){
                            "Match"->{
                                InsideSettings(backAction = {
                                    nav.popBackStack()
                                    insideWhat = ""
                                }, titleTextNum = 6,
                                )
                            }
                            "Settings"->{
                                InsideSettings(backAction = {
                                    nav.popBackStack()
                                    inMain = true
                                }, titleTextNum = selectedItemIndex,
                                )
                            }
                            else-> 1+1
                        }
                    }

                },
            ) { paddingValues ->
                //LaunchedEffect(Unit) { state.animateScrollTo(0) }
                Column(
                    Modifier
                        .padding(paddingValues)
                        //.verticalScroll(state)
                        .fillMaxSize()
                ) {
                    if(insideWhat == "Main" || insideWhat == "Match" || insideWhat == "Settings"){
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                    DatingNav(dating, nav, vmApi, viewModelDating,
                    insideWhat ={ inside ->
                        insideWhat = inside
                        inMain = inside == "Main"
                    }) //All 5 screens go here
                }
            }
        }
    }
}
@Composable
fun BadgedBox(item: BotNavItem, index:Int, selectedItemIndex:Int){
    BadgedBox(
        badge = {
            if (item.badgeCount != 0) {
                Badge {
                    Text(text = item.badgeCount.toString())
                }
            } else if (item.hasNew) {
                Badge()
            }
        }) {
        Icon(
            imageVector = if (index == selectedItemIndex) {
                item.selectedIcon
            } else {
                item.unselectedIcon
            },
            contentDescription = item.title
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarMain(titleTextNum:Int, scope: CoroutineScope, drawerState: DrawerState, action:()->Unit ){
    val titleText = when(titleTextNum){
        0 -> "Stats"
        1 -> "Messages"
        2 -> "Searching"
        3 -> "Blind"
        4 -> "Profile"
        else -> ""
    }
    CenterAlignedTopAppBar(
        modifier = Modifier.height(46.dp),
        colors = getTopColors(),
        title = {
            TopBarText(
                title = titleText,
                isPhoto = (titleText == "Searching" || titleText == ""),
                activity = "dating"
            )
        },//TitleTextGen(title= titleText)},
        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    drawerState.apply {
                        if (isClosed) open() else close()
                    }
                }
            }) {//Show my default
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.hamburger),
                    contentDescription = "Change Looking tab"
                )
            }
        },
        actions = {
            IconButton(onClick = action) {//settingsButton
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.settings),
                    contentDescription = "Settings"
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsideSettings(backAction: ()->Unit, titleTextNum: Int){
    val titleText = when(titleTextNum){
            0 -> ""
            1 -> ""
            2 -> "Search Preferences"
            3 -> ""
            4 -> "Edit Profile"
            else -> ""
        }
    CenterAlignedTopAppBar(
        modifier = Modifier.height(46.dp),
        colors = getTopColors(),
        title = { TopBarText(title= titleText, isPhoto = titleText == "") },
        navigationIcon = {
            IconButton(onClick = backAction) { //Showing in stuff like messages, editing profile and stuff
                Icon(imageVector = ImageVector.vectorResource(id = R.drawable.arrow_back), contentDescription = "Go back")
            }
        },
        actions = {}
    )
}