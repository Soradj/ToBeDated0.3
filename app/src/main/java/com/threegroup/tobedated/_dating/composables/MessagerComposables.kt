package com.threegroup.tobedated._dating.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.threegroup.tobedated.MessageViewModel
import com.threegroup.tobedated.R
import com.threegroup.tobedated.shareclasses.MyApp
import com.threegroup.tobedated.shareclasses.composables.GenericLabelText
import com.threegroup.tobedated.shareclasses.composables.baseAppTextTheme
import com.threegroup.tobedated.shareclasses.models.MessageModel
import com.threegroup.tobedated.shareclasses.models.UserModel
import com.threegroup.tobedated.shareclasses.theme.AppTheme


@Composable
fun MessageStart(
    noMatches:Boolean = true,
    userPhoto:String,
    userName:String,
    userLastMessage:String,
    openChat: () -> Unit,
    notification:Boolean = false,
    ) {
    val maxLength = 35
    val cleanedMessage = userLastMessage.replace("\n", " ")
    val displayedMessage = if (cleanedMessage.length > maxLength) {
        "${cleanedMessage.take(maxLength)}..."
    } else {
        cleanedMessage
    }
    Column(
        Modifier
            .fillMaxSize()
            .padding(15.dp, 0.dp)
    ) {
        if(noMatches){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "You have no connects at this moment\nGo try and met with some people!",
                    style = AppTheme.typography.titleMedium,
                    color = AppTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                )
            }
        }else{
            Spacer(modifier = Modifier.height(16.dp))
            Row(Modifier.clickable(onClick = openChat)){
                AsyncImage(
                    modifier = Modifier
                        .size(58.dp)
                        .clip(shape = CircleShape),
                    model = userPhoto,
                    contentDescription = "UserPfp",
                    contentScale = ContentScale.Crop,
                )
                Column(
                    modifier = Modifier.padding(15.dp, 6.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Row{
                        Text(text = userName, style = AppTheme.typography.titleSmall, color = AppTheme.colorScheme.onBackground)
                        if(notification){
                            Icon(imageVector = ImageVector.vectorResource(R.drawable.notification), contentDescription = "New Message",
                                modifier = Modifier.offset(y= (-4).dp), tint = AppTheme.colorScheme.primary)
                        }
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(text = displayedMessage,
                        style = AppTheme.typography.labelSmall, color = AppTheme.colorScheme.onBackground)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(modifier = Modifier.height(20.dp),color = Color(0xDDB39DB7), thickness = 1.dp)
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsideMessages(
    messages: @Composable () -> Unit,
    titleText:String,
    value:String = "",
    onValueChange: (String) -> Unit = {},
    sendMessage: () -> Unit = {},
    goToProfile: () -> Unit = {},
    nav: NavHostController,
    startVideoCall: () -> Unit = {},
    startCall:() -> Unit = {},
    chatSettings:() -> Unit,
    sendAttachment:() -> Unit = {},
    hideCall: Boolean = true,
) {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = AppTheme.colorScheme.background,
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                CenterAlignedTopAppBar(
                    modifier = Modifier.height(46.dp),
                    colors = TopAppBarColors(
                        containerColor = AppTheme.colorScheme.onTertiary,
                        navigationIconContentColor = AppTheme.colorScheme.primary,
                        titleContentColor = AppTheme.colorScheme.secondary,
                        actionIconContentColor = AppTheme.colorScheme.primary,
                        scrolledContainerColor = AppTheme.colorScheme.background
                    ),
                    title = { Button(onClick = goToProfile,
                        colors = ButtonColors(
                            contentColor = AppTheme.colorScheme.onBackground,
                            containerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            disabledContentColor = Color.Transparent
                        ),
                    ) { Text(text = titleText, style = AppTheme.typography.titleMedium) }
                    },//TitleTextGen(title= titleText)},
                    navigationIcon = {
                        IconButton(onClick = { nav.popBackStack() }) { //Showing in stuff like messages, editing profile and stuff
                            Icon(imageVector = ImageVector.vectorResource(id = R.drawable.arrow_back), contentDescription = "Go back")
                        }
                    },
                    actions = {
                        if(hideCall){
                            IconButton(onClick = startVideoCall) {
                                Icon(imageVector = ImageVector.vectorResource(id = R.drawable.videocall), contentDescription = "Settings")
                            }
                            IconButton(onClick = startCall) {
                                Icon(imageVector = ImageVector.vectorResource(id = R.drawable.call), contentDescription = "Settings")
                            }
                        }
                        IconButton(onClick = chatSettings) {
                            Icon(imageVector = ImageVector.vectorResource(id = R.drawable.settings), contentDescription = "Settings")
                        }
                    }
                )
            },
            bottomBar = {
                if(hideCall){
                    Box(modifier = Modifier
                        .background(AppTheme.colorScheme.onTertiary)
                        .fillMaxWidth()
                        .padding(12.dp)){
                        Row (
                            modifier = Modifier.fillMaxWidth(),
                            //horizontalArrangement = Arrangement.SpaceEvenly
                        ){
                            IconButton(onClick = sendAttachment,
                                modifier = Modifier
                                    .offset(y = 5.dp)
                                    .weight(1.0F),
                                colors= IconButtonDefaults.iconButtonColors(
                                    containerColor = Color.Transparent,
                                    contentColor = AppTheme.colorScheme.secondary,
                                ),
                            ) {
                                Icon(imageVector = ImageVector.vectorResource(id = R.drawable.attachment), contentDescription = "Send")
                            }
                            OutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(7.5F),
                                value = value, onValueChange = onValueChange,
                                textStyle = baseAppTextTheme(),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Text,
                                    capitalization = KeyboardCapitalization.Sentences,
                                    autoCorrect = true,
                                ),
                                maxLines = 4,

                                )
                            IconButton(onClick = sendMessage,
                                modifier = Modifier
                                    .offset(y = 5.dp)
                                    .weight(1.0F),
                                colors= IconButtonDefaults.iconButtonColors(
                                    containerColor = Color.Transparent,
                                    contentColor = AppTheme.colorScheme.secondary,
                                ),
                            ) {
                                Icon(imageVector = ImageVector.vectorResource(id = R.drawable.send), contentDescription = "Send")
                            }
                        }
                    }
                }
            },
        ) {
                paddingValues ->
            //val state = rememberScrollState()
            //LaunchedEffect(Unit) { state.animateScrollTo(state.maxValue) }
            Column(
                Modifier
                    .padding(paddingValues)
                    //.verticalScroll(state)
                    .fillMaxSize()
            ){
                Spacer(modifier = Modifier.height(24.dp))
                messages()
            }
        }
    }
}
@Composable
fun UserMessage(
    myMessage:String,
    color: Color = AppTheme.colorScheme.surface,
    time:String,
    last: Boolean,
    read:Boolean,

){
    Column {
        Row(
            modifier = Modifier
                .padding(45.dp, 0.dp, 15.dp, 0.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom // Align bottom vertically
        ) {
            GenericLabelText(text = time)
            Surface(
                modifier = Modifier.padding(5.dp),
                color = color,
                shape = RoundedCornerShape(10.dp)
            ) {
                Box(modifier = Modifier.padding(15.dp)) {
                    Text(text = myMessage, style = AppTheme.typography.body, color= AppTheme.colorScheme.onSurface)
                }
            }
        }
        Row(
            modifier = Modifier
                .padding(45.dp, 0.dp, 20.dp, 0.dp)
                .offset(y = (-10).dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom // Align bottom vertically
        ) {
            if (last) {
                if (!read) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.sent),
                        contentDescription = "sent",
                        tint = AppTheme.colorScheme.primary
                    )
                } else {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.read),
                        contentDescription = "read",
                        tint = AppTheme.colorScheme.primary
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
    }
}
@Composable
fun TheirMessage(
    replyMessage:String,
    userPhoto:String = "",
    photoClick: () -> Unit = {},
    time:String,
    last:Boolean,
){
    Column {
        val bubbleColor = Color.LightGray
        Row(
            modifier = Modifier
                .padding(15.dp, 0.dp, 45.dp, 0.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Bottom
        ) {
            if(last){
                IconButton(onClick = photoClick) {
                    AsyncImage(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(shape = CircleShape),
                        model = userPhoto,
                        contentDescription = "UserPfp",
                        contentScale = ContentScale.Crop,
                    )
                }
            }
            Surface(
                modifier = Modifier.padding(5.dp),
                color = bubbleColor,
                shape = RoundedCornerShape(10.dp)
            ) {
                Box(modifier = Modifier.padding(15.dp) ){
                    Text(text= replyMessage, style = AppTheme.typography.body)
                }
            }
            GenericLabelText(text = time)
        }
        Spacer(modifier = Modifier.height(6.dp))
    }
}

// TODO make this template work with the app
// My attempt to get messages working--These are just basic composable functions
@Composable
fun MessageUserList(
    userList: List<String>,
    chatKeys: List<String>,
    onItemClick: (userName: String, chatId: String) -> Unit
) {
    //val state = rememberScrollState()
    //LaunchedEffect(Unit) { state.animateScrollTo(state.maxValue) }
    LazyColumn {
        items(userList) { userName ->
            val index = userList.indexOf(userName)
            val chatId = chatKeys[index]
            UserItem(userName, chatId, onItemClick)
        }
    }
}

@Composable
fun UserItem(userName: String, chatId: String, onItemClick: (userId: String, chatId: String) -> Unit) {
    // Implement UI for a single user item here
    // TODO Rework this as it is only a skeleton
    Text(
        text = "User Name: $userName",
        modifier = Modifier.clickable { onItemClick(userName, chatId) }
    )
}

@Composable
fun MessageScreen(
    chatId: String,
    viewModel: MessageViewModel,
    match: UserModel,
    currentUserSenderId: String
) {
    val messageList by viewModel.chatDataList.collectAsState()

    Column {
        LazyColumn {
            items(messageList) { message ->
                val isCurrentUser = message?.senderId == currentUserSenderId
                MessageItem(match = match ,message = message!!, isCurrentUser = isCurrentUser)
            }
        }
    }
}
@Composable
fun MessageItem(match: UserModel, message: MessageModel, isCurrentUser: Boolean) {
    val messageModel = viewModel { MessageViewModel(MyApp.x) }
    //messageModel.getChatData(chatId = ) //get last index item .lastIndex
    if (!isCurrentUser) {
        //if(last message){TheirMessage(replyMessage = message.message!!, photo, {}), read reciept)}else{}
        TheirMessage(replyMessage = message.message!!, time =  message.currentTime.toString(), last = true, userPhoto = match.image1)
    }

    if (isCurrentUser) {
        //if(last message){UserMessage(myMessage = message.message!!), read reciept)}else{}
        UserMessage(myMessage = message.message!!, time =  message.currentTime.toString(), last = true, read = true)//current Time is no correct...
    }
}
