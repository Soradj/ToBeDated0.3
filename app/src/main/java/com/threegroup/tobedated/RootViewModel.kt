package com.threegroup.tobedated

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.threegroup.tobedated.shareclasses.Repository
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
}
