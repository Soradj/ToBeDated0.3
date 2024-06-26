package com.threegroup.tobedated._dating

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.threegroup.tobedated.MainActivity
import com.threegroup.tobedated.MyApp
import com.threegroup.tobedated.RealtimeDBMatch
import com.threegroup.tobedated.shareclasses.Repository
import com.threegroup.tobedated.shareclasses.getChatId
import com.threegroup.tobedated.shareclasses.models.Match
import com.threegroup.tobedated.shareclasses.models.MatchedUserModel
import com.threegroup.tobedated.shareclasses.models.NewMatch
import com.threegroup.tobedated.shareclasses.models.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DatingViewModel(private var repository: Repository) : ViewModel() {
    private lateinit var signedInUser: StateFlow<UserModel?>

    /**
     *
     * This is for matches
     */
    private var _likedProfile = mutableStateOf(NewMatch())
    private val likedProfile: State<NewMatch> = _likedProfile

    private var _potentialUserData = MutableStateFlow(listOf<MatchedUserModel>())
    val potentialUserData = _potentialUserData.asStateFlow()

    fun fetchPotentialUserData() {
        viewModelScope.launch {
            repository.getPotentialUserData().collect { userData ->
                _potentialUserData.value = userData
            }
        }
    }

    fun likeCurrentProfile(currentUserId: String, currentProfile: MatchedUserModel): NewMatch {
        viewModelScope.launch(IO) {
            val deferredResult = async {
                repository.likeOrPass(currentUserId, currentProfile.number, true, "")
                    ?.let { model ->
                        NewMatch( // can use NewMatch to display the match splash screen
                            model.id,
                            currentProfile.number,
                            currentProfile.name,
                            listOf(
                                currentProfile.image1,
                                currentProfile.image2,
                                currentProfile.image3,
                                currentProfile.image4
                            )
                        )
                    }
            }
            val queryResult = deferredResult.await()
            withContext(Dispatchers.Main) { // TODO with context might be an issue
                if (queryResult != null) {
                    _likedProfile.value = queryResult
                }
            }
        }
        return likedProfile.value
    }

    fun passCurrentProfile(currentUserId: String, currentProfile: MatchedUserModel) {
        viewModelScope.launch(IO) {
            repository.likeOrPass(currentUserId, currentProfile.number, false, "")
        }
    }

    fun suggestCurrentProfile(currentPotential: String, suggestion: String) {
        repository.suggest(currentPotential, suggestion)
    }

    fun getSuggestion(currentUser: String, onComplete: (List<String>) -> Unit) {
        repository.getSuggestion(currentUser, onComplete)
    }

    fun markMatchAsViewed(matchId: String, userId: String) {
        viewModelScope.launch(IO) {
            repository.markMatchAsViewed(matchId, userId)
        }
    }

    private var _matchList = MutableStateFlow(listOf<Match>())
    val matchList = _matchList.asStateFlow()
    // call this in the composable as val match list by viewModel.matchList.observeAsState()

    fun getMatchesFlow(userId: String) {
        viewModelScope.launch(IO) {
            repository.getMatchesFlow(userId).collect { matches ->
                val convertedMatches = matches.map { match ->
                    val updatedMatch = repository.getMatch(match, userId)
                    observeLastMessage(match, updatedMatch)
                }
                _matchList.value = convertedMatches.filterIsInstance<Match>() // Filter out Unit
            }
        }
    }


    private fun observeLastMessage(match: RealtimeDBMatch, updatedMatch: Match): Match {
        val chatId = getChatId(match.usersMatched[0], match.usersMatched[1])
        val chatsRef = FirebaseDatabase.getInstance().getReference("chats").child(chatId)
        val listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val messageNodes = dataSnapshot.children.filter { it.key != "notifications" }
                val lastMessageNode = messageNodes.lastOrNull()
                val lastMessage =
                    lastMessageNode?.child("message")?.getValue(String::class.java) ?: ""
                updatedMatch.lastMessage = lastMessage
                // Update match list
                _matchList.value =
                    _matchList.value.map { if (it.id == updatedMatch.id) updatedMatch else it }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error
            }
        }
        chatsRef.addValueEventListener(listener)
        return updatedMatch // Return the updated match
    }


    fun getMatchSize(): Int {
        return if (matchList.value.isEmpty()) {
            0
        } else {
            matchList.value.size
        }
    }

    /**
     * This is for blocking
     */
    fun reportUser(reportedUserId: String, reportingUserId: String) {
        viewModelScope.launch(IO) {
            repository.reportUser(reportedUserId, reportingUserId)
        }
    }

    fun blockUser(blockedUserId: String, blockingUserId: String) {
        viewModelScope.launch(IO) {
            repository.blockUser(blockedUserId, blockingUserId)
        }
    }


    /**
     *
     * This is for Chats
     */
    private var _selectedUser = MutableStateFlow<MatchedUserModel?>(null)
    var selectedUser: StateFlow<MatchedUserModel?> = _selectedUser

    //Stuff for setting and getting matches
    fun setTalkedUser(number: String) {
        viewModelScope.launch(IO) {
            repository.setMatchInfo(number).collect { userInfo ->
                _selectedUser.value = userInfo
            }
        }
    }

    fun getTalkedUser(): MatchedUserModel {
        return selectedUser.value!!
    }

    fun deleteMatch(matchedUser: String, userId: String) {
        viewModelScope.launch(IO) {
            repository.deleteMatch(matchedUser, userId)
        }
    }

    fun openChat(chatId: String) {
        viewModelScope.launch(IO) {
            repository.openChat(chatId)
        }
    }




    fun checkRead(chatId: String, callBack:(Boolean)->Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.checkRead(chatId, "", callBack)
        }
    }
    /**
     *  generates a unique chatId made from the UIDs of the sender and receiver
     */


    /**
     *
     * This is for Profiles
     */
    fun getUser(): UserModel {
        return signedInUser.value!!
    }

    fun updateUser(updatedUser: UserModel) {
        val userPhoneNumber = updatedUser.number
        val databaseReference =
            FirebaseDatabase.getInstance().getReference("users").child(userPhoneNumber)
        databaseReference.setValue(updatedUser)
        MyApp._signedInUser.value = updatedUser
    }

    fun setLoggedInUser() {
        signedInUser = MyApp.signedInUser
    }

    fun deleteProfile(number: String, datingActivity: MainActivity, mainNav: NavHostController) {
        viewModelScope.launch {
            repository.deleteProfile(number,
                onSuccess = {
                    //datingActivity.clearUserToken()
                    goToLogin(datingActivity, mainNav)
                },
                onFailure = { exception ->
                    println(exception)
                }
            )
        }
    }

    fun goToLogin(dating: MainActivity, mainNav: NavHostController) {
        dating.clearUserToken()

        mainNav.navigate("Login/${getUser().location}") {
            popUpTo("Dating") {
                inclusive = true
                saveState = false
            }
        }
    }

    /**
     * This is for someScreen
     */
    fun getLikes(userId: String, onComplete: (Int) -> Unit) {
        repository.getLikes(userId, onComplete)
    }

    fun getPasses(userId: String, onComplete: (Int) -> Unit) {
        repository.getPasses(userId, onComplete)
    }

    fun getLikedAndPassedby(userId: String, onComplete: (Int) -> Unit) {
        repository.getLikedAndPassedby(userId, onComplete)
    }

    /**
     * Notifications
     */
    private val _chatAlert = MutableStateFlow(false)
    val chatAlert: StateFlow<Boolean> = _chatAlert
    fun updateNewChatsCount(inOther: String) {
        viewModelScope.launch(IO) {
            repository.updateNewChatsCount(inOther).collect { value ->
                _chatAlert.value = value != 0
            }
        }
    }
}
