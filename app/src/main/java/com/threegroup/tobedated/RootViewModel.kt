package com.threegroup.tobedated

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.threegroup.tobedated.shareclasses.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RootViewModel(private var repository: Repository) : ViewModel() {
    private val _totalNotificationCountStateFlow = MutableStateFlow(0)
    val totalNotificationCountStateFlow: StateFlow<Int> = _totalNotificationCountStateFlow
    fun updateNotificationCounts() {
        viewModelScope.launch(IO) {
            repository.updateNotificationCounts().collect { totalNotificationCount ->
                _totalNotificationCountStateFlow.value = totalNotificationCount
            }
        }
    }

    private var _isRead = MutableStateFlow(false)
    val isRead: StateFlow<Boolean> = _isRead
    fun checkRead(chatId: String) {
        viewModelScope.launch(IO) {
            repository.checkRead(chatId).collect { read ->
                _isRead.value = read
            }
        }
    }
}
