package com.threegroup.tobedated._dating

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.threegroup.tobedated.NewMatch
import com.threegroup.tobedated.RealtimeDBMatch
import com.threegroup.tobedated.shareclasses.Repository
import com.threegroup.tobedated.shareclasses.models.UserModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DatingViewModel(private var repository: Repository) : ViewModel() {
    val list = ArrayList<UserModel>()
    private var signedInUser: UserModel = UserModel() //= repository.getUser()
    private var selectedUser: UserModel = UserModel() //The chat you open

    //TODO this funcuation makes a list of potentional users from the database, for you to swipe one.
    //TODO change this to flows.
    fun getPotentialUserData(callback: () -> Unit) {
        try {
            FirebaseDatabase.getInstance().getReference("users")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            val tempList =
                                ArrayList<UserModel>() // Temporary list to store potential users
                            for (data in snapshot.children) {
                                val model = data.getValue(UserModel::class.java)
                                if (model?.number != FirebaseAuth.getInstance().currentUser?.phoneNumber) {
                                    model?.let {
                                        if (!it.seeMe) {//TODO THIS IS IMPORTANT NEED TO CHECK "user.seeMe"
                                            tempList.add(it)
                                        }
                                    }
                                }
                            }
                            // Sort the list by status (currentTimeMillis)
                            val sortedList = tempList.sortedByDescending { it.status }

                            // Update the original list with the sorted list
                            list.clear()
                            list.addAll(sortedList)
                        }
                        // Invoke the callback to indicate that data retrieval is complete
                        callback()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.d("MY_DEBUGGER", "ERROR")
                        // Handle error
                    }
                })
        } catch (e: Exception) {
            Log.d("MY_DEBUGGER", "ERROR")
            // Handle error
        }
    }

    //TODO this goes through the list, flows might make thi work flow differnt....Might change based on how fflows work
    fun getNextPotential(currentProfileIndex: Int): UserModel? {///MUST RETURN USERMODEL
        return if (currentProfileIndex < list.size) {
            list[currentProfileIndex]
        } else {
            null
        }
    }

    //Stuff for liking and passing
    fun likedCurrentPotential(currentProfileIndex: Int, currentPotential: UserModel): UserModel? {

        ///signedInUser.gotliked = currentPotential sample
        //TODO This returns a userModel....
        return getNextPotential(currentProfileIndex)
    }

    fun passedCurrentPotential(currentProfileIndex: Int, currentPotential: UserModel): UserModel? {

        return getNextPotential(currentProfileIndex)
    }

    fun reportedCurrentPotential(
        currentProfileIndex: Int,
        currentPotential: UserModel
    ): UserModel? {

        return getNextPotential(currentProfileIndex)
    }

    private var _likedProfile = mutableStateOf(NewMatch())
    val likedProfile: State<NewMatch> = _likedProfile

    // TODO not 100% sure on this one--wrote it kinda fast
    fun likeCurrentProfile(currentUserId: String, currentProfile: UserModel): NewMatch? {
        viewModelScope.launch(IO) {
            val deferredResult = async {
                repository.likeOrPass(currentUserId, currentProfile.number, true)?.let { model ->
                    NewMatch( // can use NewMatch to display the match splash screen
                        model.matchId,
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
            withContext(Main) { // TODO with context might be an issue
                if (queryResult != null) {
                    _likedProfile.value = queryResult
                }
            }
        }
        return likedProfile.value
    }

    suspend fun passCurrentProfile(currentUserId: String, currentProfile: UserModel) {
        repository.likeOrPass(currentUserId, currentProfile.number, false)
    }

    private var _matchList = mutableStateOf(listOf<RealtimeDBMatch>())
    val matchList: State<List<RealtimeDBMatch>> =
        _matchList // call this in the composable as val matchlist by viewModel.matchList.observeAsState()

    fun getMatchesFlow(userId: String) {
        viewModelScope.launch(IO) {
            repository.getMatchesFlow(userId).collect { matches ->
                _matchList.value = matches
            }
        }
    }

    //Stuff for setting and getting matches
    fun getMatches(): ArrayList<UserModel> {
        return list
    }


    //Stuff for chats
    fun setTalkedUser(userModel: UserModel) {
        selectedUser = userModel
    }

    fun getTalkedUser(): UserModel {
        return selectedUser
    }

    /**
     *  generates a unique chatId made from the UIDs of the sender and receiver
     */
    fun getChatId(senderId: String, receiverId: String): String {
        return if (senderId > receiverId) {
            senderId + receiverId
        } else receiverId + senderId
    }

    //Stuff for signed in user

    fun getCurrentUserId(): String {
        return repository.getCurrentUserId()
    }

    fun getUser(): UserModel {
        return signedInUser
    }

    fun updateUser(updatedUser: UserModel) {
        val userPhoneNumber = updatedUser.number
        val databaseReference =
            FirebaseDatabase.getInstance().getReference("users").child(userPhoneNumber)
        databaseReference.setValue(updatedUser)
        signedInUser = updatedUser
    }

    fun setLoggedInUser(userPhoneNumber: String, location: String) {
        signedInUser = repository.setUserInfo(userPhoneNumber, location)
    }

}