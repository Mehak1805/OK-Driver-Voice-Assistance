package com.okdriver.voiceassistant;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\r\b\u0007\u0018\u00002\u00020\u0001B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u0010\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\u0019H\u0002J\b\u0010\u001c\u001a\u00020\u0017H\u0002J\u0010\u0010\u001d\u001a\u00020\t2\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u0018\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u00192\u0006\u0010!\u001a\u00020\u0019H\u0002J\u0018\u0010\"\u001a\u00020\u00172\u000e\u0010#\u001a\n\u0018\u00010$j\u0004\u0018\u0001`%H\u0016J\u0012\u0010&\u001a\u00020\u00172\b\u0010\'\u001a\u0004\u0018\u00010\u0019H\u0016J\u0012\u0010(\u001a\u00020\u00172\b\u0010\'\u001a\u0004\u0018\u00010\u0019H\u0016J\u0012\u0010)\u001a\u00020\u00172\b\u0010\'\u001a\u0004\u0018\u00010\u0019H\u0016J\b\u0010*\u001a\u00020\u0017H\u0016J\u0006\u0010+\u001a\u00020\u0017J\u0006\u0010,\u001a\u00020\u0017J\u0006\u0010-\u001a\u00020\u0017J\u0006\u0010.\u001a\u00020\u0017J\b\u0010/\u001a\u00020\u0017H\u0002J\u0006\u00100\u001a\u00020\u0017J\u0006\u00101\u001a\u00020\u0017R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00070\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015\u00a8\u00062"}, d2 = {"Lcom/okdriver/voiceassistant/WakeWordDetector;", "Lorg/vosk/android/RecognitionListener;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "_wakeWordFlow", "Lkotlinx/coroutines/flow/MutableSharedFlow;", "Lcom/okdriver/voiceassistant/WakeWordEvent;", "isPaused", "", "lastWakeWordTime", "", "mainHandler", "Landroid/os/Handler;", "model", "Lorg/vosk/Model;", "speechService", "Lorg/vosk/android/SpeechService;", "wakeWordFlow", "Lkotlinx/coroutines/flow/SharedFlow;", "getWakeWordFlow", "()Lkotlinx/coroutines/flow/SharedFlow;", "checkForWakeWord", "", "text", "", "extractText", "json", "initVosk", "isWakeWord", "levenshteinDistance", "", "s1", "s2", "onError", "exception", "Ljava/lang/Exception;", "Lkotlin/Exception;", "onFinalResult", "hypothesis", "onPartialResult", "onResult", "onTimeout", "pauseForQuery", "resumeAfterQuery", "sleep", "start", "startEngine", "stop", "wakeUp", "app_debug"})
public final class WakeWordDetector implements org.vosk.android.RecognitionListener {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull
    private final android.os.Handler mainHandler = null;
    @org.jetbrains.annotations.Nullable
    private org.vosk.android.SpeechService speechService;
    @org.jetbrains.annotations.Nullable
    private org.vosk.Model model;
    private long lastWakeWordTime = 0L;
    private boolean isPaused = false;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableSharedFlow<com.okdriver.voiceassistant.WakeWordEvent> _wakeWordFlow = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.SharedFlow<com.okdriver.voiceassistant.WakeWordEvent> wakeWordFlow = null;
    
    @javax.inject.Inject
    public WakeWordDetector(@dagger.hilt.android.qualifiers.ApplicationContext
    @org.jetbrains.annotations.NotNull
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.SharedFlow<com.okdriver.voiceassistant.WakeWordEvent> getWakeWordFlow() {
        return null;
    }
    
    private final void initVosk() {
    }
    
    private final void startEngine() {
    }
    
    /**
     * ACTUALLY releases the microphone by stopping Vosk's AudioRecord.
     * setPause() does NOT release the mic hardware — stop() does.
     * Safe to call because we post to mainHandler (never from inside C++ callback).
     */
    public final void pauseForQuery() {
    }
    
    /**
     * Restart Vosk after Android STT is done with the microphone.
     */
    public final void resumeAfterQuery() {
    }
    
    public final void start() {
    }
    
    public final void stop() {
    }
    
    public final void wakeUp() {
    }
    
    public final void sleep() {
    }
    
    @java.lang.Override
    public void onResult(@org.jetbrains.annotations.Nullable
    java.lang.String hypothesis) {
    }
    
    @java.lang.Override
    public void onPartialResult(@org.jetbrains.annotations.Nullable
    java.lang.String hypothesis) {
    }
    
    @java.lang.Override
    public void onFinalResult(@org.jetbrains.annotations.Nullable
    java.lang.String hypothesis) {
    }
    
    private final void checkForWakeWord(java.lang.String text) {
    }
    
    /**
     * Fast wake word detection with optimized fuzzy matching.
     * Handles mishearings like "okay", "okey", "ok. driver", etc.
     * Uses Levenshtein distance ONLY as fallback (expensive operation).
     */
    private final boolean isWakeWord(java.lang.String text) {
        return false;
    }
    
    /**
     * Optimized Levenshtein distance - only called as fallback for fuzzy matching.
     * Adds early termination for distance > 2 to avoid unnecessary computation.
     */
    private final int levenshteinDistance(java.lang.String s1, java.lang.String s2) {
        return 0;
    }
    
    @java.lang.Override
    public void onError(@org.jetbrains.annotations.Nullable
    java.lang.Exception exception) {
    }
    
    @java.lang.Override
    public void onTimeout() {
    }
    
    private final java.lang.String extractText(java.lang.String json) {
        return null;
    }
}