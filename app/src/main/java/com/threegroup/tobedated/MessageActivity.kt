package com.threegroup.tobedated

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.threegroup.tobedated._dating.composables.MessageScreen
import kotlinx.coroutines.runBlocking

class MessageActivity : AppCompatActivity() {
    private val viewModel: MessageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val chatId = intent.getStringExtra("chat_id") ?: ""
            val match by viewModel.match.collectAsState(null)
            match?.let {match ->
                val currentUserSenderId = runBlocking { viewModel.getCurrentUserSenderId() }
                MessageScreen(
                    chatId = chatId,
                    viewModel = viewModel,
                    match = match,
                    currentUserSenderId = currentUserSenderId!!
                )
            }
        }
    }
}
