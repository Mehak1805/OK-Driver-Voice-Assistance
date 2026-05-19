package com.okdriver.voiceassistant;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\b\b\u0007\u0018\u00002\u00020\u0001B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u000b\u001a\u0004\u0018\u00010\fJ\u0006\u0010\r\u001a\u00020\fJ\u0006\u0010\u000e\u001a\u00020\u000fJ\u0006\u0010\u0010\u001a\u00020\u0011J\u000e\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\fJ\u000e\u0010\u0015\u001a\u00020\u00132\u0006\u0010\u0016\u001a\u00020\u0011J\u000e\u0010\u0017\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\fJ\u000e\u0010\u0019\u001a\u00020\u00132\u0006\u0010\u001a\u001a\u00020\u000fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u0005\u001a\u00020\u00068BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\b\u00a8\u0006\u001b"}, d2 = {"Lcom/okdriver/voiceassistant/SecurePreferencesProvider;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "sharedPrefs", "Landroid/content/SharedPreferences;", "getSharedPrefs", "()Landroid/content/SharedPreferences;", "sharedPrefs$delegate", "Lkotlin/Lazy;", "getGroqApiKey", "", "getLanguage", "getWakeWordSensitivity", "", "isHdVoiceEnabled", "", "setGroqApiKey", "", "apiKey", "setHdVoiceEnabled", "enabled", "setLanguage", "code", "setWakeWordSensitivity", "sensitivity", "app_debug"})
public final class SecurePreferencesProvider {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull
    private final kotlin.Lazy sharedPrefs$delegate = null;
    
    @javax.inject.Inject
    public SecurePreferencesProvider(@dagger.hilt.android.qualifiers.ApplicationContext
    @org.jetbrains.annotations.NotNull
    android.content.Context context) {
        super();
    }
    
    private final android.content.SharedPreferences getSharedPrefs() {
        return null;
    }
    
    public final void setGroqApiKey(@org.jetbrains.annotations.NotNull
    java.lang.String apiKey) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getGroqApiKey() {
        return null;
    }
    
    public final void setWakeWordSensitivity(float sensitivity) {
    }
    
    public final float getWakeWordSensitivity() {
        return 0.0F;
    }
    
    public final void setLanguage(@org.jetbrains.annotations.NotNull
    java.lang.String code) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getLanguage() {
        return null;
    }
    
    public final void setHdVoiceEnabled(boolean enabled) {
    }
    
    public final boolean isHdVoiceEnabled() {
        return false;
    }
}