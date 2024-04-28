package com.threegroup.tobedated._dating

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.FirebaseDatabase
import com.threegroup.tobedated.shareclasses.MyApp
import com.threegroup.tobedated.shareclasses.Repository
import com.threegroup.tobedated.shareclasses.models.Match
import com.threegroup.tobedated.shareclasses.models.MatchedUserModel
import com.threegroup.tobedated.shareclasses.models.NewMatch
import com.threegroup.tobedated.shareclasses.models.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
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
    val potentialUserData: StateFlow<Pair<List<MatchedUserModel>, Int>> =
            repository.getPotentialUserData()
                .map { (userList, currentIndex) -> userList to currentIndex }
                .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Pair(emptyList(), 0))

    fun getNextPotential(currentProfileIndex: Int): MatchedUserModel? {
        val potentialUsers = potentialUserData.value.first
        return potentialUsers.getOrNull(currentProfileIndex)
    }

    // TODO not 100% sure on this one--wrote it kinda fast
    fun likeCurrentProfile(currentUserId: String, currentProfile: MatchedUserModel): NewMatch {
        viewModelScope.launch(IO) {
            val deferredResult = async {
                repository.likeOrPass(currentUserId, currentProfile.number, true)?.let { model ->
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
            repository.likeOrPass(currentUserId, currentProfile.number, false)
        }
    }

    private var _matchList = mutableStateOf(listOf<Match>())
    private val matchList: State<List<Match>> = _matchList
    // call this in the composable as val matchlist by viewModel.matchList.observeAsState()

    private fun getMatchesFlow(userId: String) {
        viewModelScope.launch(IO) {
            repository.getMatchesFlow(userId).collect { matches ->
                val convertedMatches = matches.mapNotNull { match ->
                    repository.getMatch(match)
                }
                _matchList.value = convertedMatches
            }
        }
    }

    fun getCurrentUserId(): String {
        return repository.getCurrentUserId()
    }

    /**
     *
     * This is for Chats
     */
    private var _selectedUser = MutableStateFlow<MatchedUserModel?>(null)
    var selectedUser: StateFlow<MatchedUserModel?> = _selectedUser

    //Stuff for setting and getting matches
    fun getMatches(): List<Match> {
        getMatchesFlow(signedInUser.value!!.number)
        return matchList.value
    }

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

    /**
     *  generates a unique chatId made from the UIDs of the sender and receiver
     */
    fun getChatId(senderId: String, receiverId: String): String {
        return if (senderId > receiverId) {
            senderId + receiverId
        } else receiverId + senderId
    }

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
//    fun deleteProfile(number:String, datingActivity: DatingActivity) {
//        repository.deleteProfile(number,
//            onSuccess = {
//                datingActivity.clearUserToken()
//            },
//            onFailure = { exception ->
//                println(exception)
//            }
//        )
//    }

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

    fun deleteUserAndData(userId: String) {
        viewModelScope.launch {
            try {
                repository.deleteUserAndData(userId)
                // Optionally, perform any additional cleanup or navigation after deletion
            } catch (e: Exception) {
                // Handle any errors, such as network issues or database errors
            }
        }
    }

}