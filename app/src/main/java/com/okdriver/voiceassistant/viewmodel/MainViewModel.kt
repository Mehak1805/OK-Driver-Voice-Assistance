package com.okdriver.voiceassistant.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okdriver.voiceassistant.db.AppDatabase
import com.okdriver.voiceassistant.db.ConversationEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SimpleUiState(
    val recentInteractions: List<ConversationEntity> = emptyList()
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val database: AppDatabase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SimpleUiState())
    val uiState: StateFlow<SimpleUiState> = _uiState

    init {
        viewModelScope.launch {
            val history = database.conversationDao().getLast10()
            _uiState.value = SimpleUiState(recentInteractions = history.take(3))
        }
    }
}