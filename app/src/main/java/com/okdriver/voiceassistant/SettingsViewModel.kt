package com.okdriver.voiceassistant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SettingsUiState(
    val apiKey: String = "",
    val sensitivity: Float = 0.6f,
    val hdVoice: Boolean = false,
    val language: String = "en-IN"
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val securePreferences: SecurePreferencesProvider,
    private val conversationDao: ConversationDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        _uiState.value = SettingsUiState(
            apiKey = securePreferences.getGroqApiKey() ?: "",
            sensitivity = securePreferences.getWakeWordSensitivity(),
            hdVoice = securePreferences.isHdVoiceEnabled(),
            language = securePreferences.getLanguage()
        )
    }

    fun updateApiKey(key: String) {
        securePreferences.setGroqApiKey(key)
        _uiState.value = _uiState.value.copy(apiKey = key)
    }

    fun updateSensitivity(value: Float) {
        securePreferences.setWakeWordSensitivity(value)
        _uiState.value = _uiState.value.copy(sensitivity = value)
    }

    fun updateHdVoice(enabled: Boolean) {
        securePreferences.setHdVoiceEnabled(enabled)
        _uiState.value = _uiState.value.copy(hdVoice = enabled)
    }

    fun clearHistory() {
        viewModelScope.launch {
            conversationDao.clearAll()
        }
    }
}
