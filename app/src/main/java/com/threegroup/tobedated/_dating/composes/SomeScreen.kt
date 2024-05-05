package com.threegroup.tobedated._dating.composes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.threegroup.tobedated.R
import com.threegroup.tobedated._dating.DatingActivity
import com.threegroup.tobedated._dating.DatingViewModel
import com.threegroup.tobedated._dating.TopAndBotBarsDating
import com.threegroup.tobedated._dating.notifiChat
import com.threegroup.tobedated._dating.notifiGroup
import com.threegroup.tobedated.composeables.composables.GenericTitleText
import com.threegroup.tobedated.shareclasses.api.ApiViewModel
import com.threegroup.tobedated.theme.AppTheme

@Composable
fun SomeScreen(
    navController: NavHostController,
    dating: DatingActivity,
    vmDating: DatingViewModel,
    vmApi: ApiViewModel
) {
    val userId = vmDating.getUser().number
    var passed by remember { mutableIntStateOf(0) }
    var liked by remember { mutableIntStateOf(0) }
    var seen by remember { mutableIntStateOf(0) }
    var suggestions by remember { mutableStateOf(listOf("")) }
    vmDating.getPasses(
        userId,
        onComplete = {
            total -> passed = total
        }
    )
    vmDating.getLikes(
        userId,
        onComplete = {
                total -> liked = total
        }
    )
    vmDating.getLikedAndPassedby(
        userId,
        onComplete = {
                total -> seen = total
        }
    )
    vmDating.getSuggestion(
        userId,
        onComplete = { list ->
            suggestions = list
        }
    )

    val unmeet = 1 //viewmodel call here
    TopAndBotBarsDating(
        dating = dating,
        notifiChat = notifiChat,
        notifiGroup = notifiGroup,
        titleText = "Stats",
        nav = navController,
        selectedItemIndex = 0,
        settingsButton = { },
        vmApi = vmApi,
        currentScreen = {
            Column(
                modifier = Modifier
                    .padding(horizontal = 25.dp)
                    .fillMaxSize()
            ) {
                GenericTitleText(text = "Your Stats", style = AppTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(2.dp))

                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    color = AppTheme.colorScheme.tertiary
                )
                Spacer(modifier = Modifier.height(12.dp))
                Column(modifier = Modifier.padding(horizontal = 0.dp).fillMaxSize())
                {
                    Row( modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        GenericTitleText(text = "• People you Liked on: ")
                        GenericTitleText(text = liked.toString())
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Row( modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        GenericTitleText(text = "• People you passed on: ")
                        GenericTitleText(text = passed.toString())
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row( modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        GenericTitleText(text = "• People who saw you: ")
                        GenericTitleText(text = seen.toString())
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                GenericTitleText(
                    text = "Unmeet connections",
                    style = AppTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(2.dp))
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    color = AppTheme.colorScheme.tertiary
                )
                Spacer(modifier = Modifier.height(12.dp))
                Column {
                    GenericTitleText(text = "• Currently: $unmeet")
                }
                Spacer(modifier = Modifier.height(24.dp))
                if(suggestions.isNotEmpty()){
                    GenericTitleText(
                        text = "Profile Suggestions",
                        style = AppTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        color = AppTheme.colorScheme.tertiary
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                    ) {
                        suggestions.forEach{ suggestion->
                            Row( modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ){
                                GenericTitleText(text = "• $suggestion",)
                                IconButton(modifier = Modifier
                                    .offset(y = (-14).dp),
                                    onClick = { /*TODO*/ }) {
                                    Icon(imageVector = ImageVector.vectorResource(id = R.drawable.close), contentDescription = "delete", tint = AppTheme.colorScheme.secondary)
                                }
                            }
                        }

                    }
                }
            }
        }
    )
}