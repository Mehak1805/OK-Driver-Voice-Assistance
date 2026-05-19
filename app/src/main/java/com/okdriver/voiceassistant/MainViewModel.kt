package com.okdriver.voiceassistant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SimpleUiState(
    val recentInteractions: List<ConversationEntity> = emptyList()
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val database: AppDatabase
) : ViewModel() {

    private val _uiState = kotlinx.coroutines.flow.MutableStateFlow(SimpleUiState())
    val uiState: StateFlow<SimpleUiState> = _uiState

    init {
        viewModelScope.launch {
            val history = database.conversationDao().getLast10()
            _uiState.value = SimpleUiState(recentInteractions = history.take(3))
        }
    }
}
