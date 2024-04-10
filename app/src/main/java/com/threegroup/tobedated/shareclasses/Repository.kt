package com.threegroup.tobedated.shareclasses

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.threegroup.tobedated.shareclasses.models.MessageModel
import com.threegroup.tobedated.shareclasses.models.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class Repository(
    private var firebaseDataSource: FirebaseDataSource
) {
    suspend fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        return firebaseDataSource.signInWithPhoneAuthCredential(credential)
    }

    suspend fun checkUserExist(number: String) {
        return firebaseDataSource.checkUserExist(number)
    }

    suspend fun storeUserData(data: UserModel) {
        return firebaseDataSource.storeUserData(data)
    }

    suspend fun getUserData(): ArrayList<UserModel>? {
        return firebaseDataSource.getUserData()
    }

    suspend fun updateUserData(userUpdates: UserModel) {
        return firebaseDataSource.updateUserData(userUpdates)
    }

    fun getChatData(chatId: String?): Flow<List<MessageModel>> =
        firebaseDataSource.getChatData(chatId).map { list ->
            list.map {
                val sender = FirebaseAuth.getInstance().currentUser!!.phoneNumber
                val text = it.message
                MessageModel(sender, text)
            }
        }

    suspend fun storeChatData(chatId:String, message: String) {
        return firebaseDataSource.storeChatData(chatId, message)
    }

    suspend fun displayChats() {
        return firebaseDataSource.displayChats()
    }


    /*
    Not sure where to put this function at the moment
    /**
     * Function to verify the chat id
     */
    suspend fun verifyChatId() {
        val receiverId = intent.getStringExtra("userId")
        val senderId: String? = FirebaseAuth.getInstance().currentUser!!.phoneNumber
        var chatId = senderId + receiverId
        val reverseChatId = receiverId + senderId
        val reference = FirebaseDatabase.getInstance().getReference("chats")
        //.child(chatId)
        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.hasChild(chatId!!)) {
                    runBlocking {
                        firebaseDataSource.getChatData(chatId)
                    }
                } else if (snapshot.hasChild(reverseChatId)) {
                    runBlocking {
                        chatId = reverseChatId
                        firebaseDataSource.getChatData(chatId)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                // Toast.makeText(this@MessageActivity, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        })
    }
     */
}

