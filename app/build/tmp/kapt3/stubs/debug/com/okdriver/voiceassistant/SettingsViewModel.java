package com.okdriver.voiceassistant;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0012J\u000e\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u0015J\u000e\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u0018R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r\u00a8\u0006\u0019"}, d2 = {"Lcom/okdriver/voiceassistant/SettingsViewModel;", "Landroidx/lifecycle/ViewModel;", "securePreferences", "Lcom/okdriver/voiceassistant/SecurePreferencesProvider;", "conversationDao", "Lcom/okdriver/voiceassistant/ConversationDao;", "(Lcom/okdriver/voiceassistant/SecurePreferencesProvider;Lcom/okdriver/voiceassistant/ConversationDao;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/okdriver/voiceassistant/SettingsUiState;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "clearHistory", "", "updateApiKey", "key", "", "updateHdVoice", "enabled", "", "updateSensitivity", "value", "", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel
public final class SettingsViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.okdriver.voiceassistant.SecurePreferencesProvider securePreferences = null;
    @org.jetbrains.annotations.NotNull
    private final com.okdriver.voiceassistant.ConversationDao conversationDao = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.okdriver.voiceassistant.SettingsUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.okdriver.voiceassistant.SettingsUiState> uiState = null;
    
    @javax.inject.Inject
    public SettingsViewModel(@org.jetbrains.annotations.NotNull
    com.okdriver.voiceassistant.SecurePreferencesProvider securePreferences, @org.jetbrains.annotations.NotNull
    com.okdriver.voiceassistant.ConversationDao conversationDao) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.okdriver.voiceassistant.SettingsUiState> getUiState() {
        return null;
    }
    
    public final void updateApiKey(@org.jetbrains.annotations.NotNull
    java.lang.String key) {
    }
    
    public final void updateSensitivity(float value) {
    }
    
    public final void updateHdVoice(boolean enabled) {
    }
    
    public final void clearHistory() {
    }
}