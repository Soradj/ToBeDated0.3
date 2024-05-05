package com.threegroup.tobedated._dating

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CenterAlignedTopAppBar
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
import androidx.navigation.NavHostController
import com.threegroup.tobedated.MyApp
import com.threegroup.tobedated.R
import com.threegroup.tobedated.composeables.composables.NavDraw
import com.threegroup.tobedated.composeables.composables.TopBarText
import com.threegroup.tobedated.composeables.composables.getBottomColors
import com.threegroup.tobedated.composeables.composables.getTopColors
import com.threegroup.tobedated.shareclasses.NotificationCountCallback
import com.threegroup.tobedated.shareclasses.api.ApiViewModel
import com.threegroup.tobedated.theme.AppTheme
import kotlinx.coroutines.launch

data class BotNavItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNew: Boolean = false,
    val badgeCount: Int? = null,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAndBotBarsDating(
    notifiChat: Int = 0, // May not need this
    notifiGroup: Boolean = false,
    notifiSearching: Boolean = false,
    currentScreen: @Composable () -> Unit = {},
    titleText: String = "To Be Dated",
    isPhoto: Boolean,
    nav: NavHostController,
    selectedItemIndex: Int,
    settingsButton: () -> Unit,
    state: ScrollState = rememberScrollState(),
    dating: DatingActivity,
    vmApi: ApiViewModel,
) {
    val vmDating = viewModel { DatingViewModel(MyApp.x) } // Could pass as a parameter
    var notificationCount by remember { mutableStateOf(0) }
    // Initialize the notification count when the composable is first composed
    LaunchedEffect(Unit) {
        vmDating.updateNotificationCount { count ->
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
            hasNew = notifiSearching,
            badgeCount = 0
        ),
        BotNavItem(
            title = "GroupsScreen",
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
            Scaffold(
                containerColor = if (isSystemInDarkTheme()) Color(0xFF181618) else Color(0xFFCDC2D0),
                bottomBar = {
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
                                },//HANDLE NAVIGATION
                                label = { },
                                alwaysShowLabel = false,
                                icon = {
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
                                })
                        }
                    }
                },
                topBar = {
                    CenterAlignedTopAppBar(
                        modifier = Modifier.height(46.dp),
                        colors = getTopColors(),
                        title = {
                            TopBarText(
                                title = titleText,
                                isPhoto = isPhoto,
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
                            IconButton(onClick = settingsButton) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = R.drawable.settings),
                                    contentDescription = "Settings"
                                )
                            }
                        }
                    )
                },
            ) { paddingValues ->
                LaunchedEffect(Unit) { state.animateScrollTo(0) }
                Column(
                    Modifier
                        .padding(paddingValues)
                        .verticalScroll(state)
                        .fillMaxSize()
                ) {
                    Spacer(modifier = Modifier.height(24.dp))
                    currentScreen() //All 5 screens go here
                }
            }
        }
    }
}