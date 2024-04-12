package com.threegroup.tobedated

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import com.threegroup.tobedated._dating.composables.MessageUserList


class MessageFragment : Fragment() {
    private val viewModel: MessageViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val userList = mutableListOf<String>()
        val chatKeys = mutableListOf<String>()

        return ComposeView(requireContext()).apply {
            setContent {
                val userListState by remember { mutableStateOf(emptyList<String>()) }
                val chatKeysState by remember { mutableStateOf(emptyList<String>()) }

                MessageUserList(
                    userList = userListState,
                    chatKeys = chatKeysState,
                    onItemClick = { userId, chatId ->
                        val intent = Intent(requireContext(), MessageActivity::class.java)
                        intent.putExtra("userId", userId)
                        intent.putExtra("chat_id", chatId)
                        startActivity(intent)
                    }
                )
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val chatId = arguments?.getString("chat_id") // Retrieve chatId
        viewModel.getChatData(chatId)
    }
}
