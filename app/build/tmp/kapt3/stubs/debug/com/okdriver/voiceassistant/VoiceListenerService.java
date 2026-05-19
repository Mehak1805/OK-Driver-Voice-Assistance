package com.okdriver.voiceassistant;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0010\u0018\u0000 32\u00020\u0001:\u00013B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0013\u001a\u00020\u0014H\u0002J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\b\u0010\u0019\u001a\u00020\u001aH\u0002J\n\u0010\u001b\u001a\u0004\u0018\u00010\u0018H\u0002J\u0010\u0010\u001c\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\u0018\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u00182\u0006\u0010 \u001a\u00020\u0018H\u0002J\u0014\u0010!\u001a\u0004\u0018\u00010\"2\b\u0010#\u001a\u0004\u0018\u00010$H\u0016J\b\u0010%\u001a\u00020\u001aH\u0016J\b\u0010&\u001a\u00020\u001aH\u0016J\"\u0010\'\u001a\u00020\u001e2\b\u0010#\u001a\u0004\u0018\u00010$2\u0006\u0010(\u001a\u00020\u001e2\u0006\u0010)\u001a\u00020\u001eH\u0016J\b\u0010*\u001a\u00020\u001aH\u0002J\b\u0010+\u001a\u00020\u001aH\u0002J\u0010\u0010,\u001a\u00020\u001a2\u0006\u0010-\u001a\u00020\u0018H\u0002J\u0010\u0010.\u001a\u00020\u001a2\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\b\u0010/\u001a\u00020\u001aH\u0002J\b\u00100\u001a\u00020\u001aH\u0002J\u0010\u00101\u001a\u00020\u001a2\u0006\u00102\u001a\u00020\u000eH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u00064"}, d2 = {"Lcom/okdriver/voiceassistant/VoiceListenerService;", "Landroid/app/Service;", "()V", "handler", "Landroid/os/Handler;", "overlayManager", "Lcom/okdriver/voiceassistant/VoiceOverlayManager;", "recognitionListener", "Landroid/speech/RecognitionListener;", "recognizer", "Landroid/speech/SpeechRecognizer;", "serviceScope", "Lkotlinx/coroutines/CoroutineScope;", "state", "Lcom/okdriver/voiceassistant/AssistantState;", "tts", "Landroid/speech/tts/TextToSpeech;", "ttsListener", "Landroid/speech/tts/UtteranceProgressListener;", "buildNotification", "Landroid/app/Notification;", "checkForCloseCommand", "", "text", "", "createNotificationChannel", "", "getApiKeyFromEncryptedPrefs", "isWakeWord", "levenshtein", "", "s1", "s2", "onBind", "Landroid/os/IBinder;", "intent", "Landroid/content/Intent;", "onCreate", "onDestroy", "onStartCommand", "flags", "startId", "onWakeWordDetected", "playBeep", "sendQueryToGroq", "query", "speakAndRestart", "startQueryListening", "startWakeWordListening", "updateOverlay", "newState", "Companion", "app_debug"})
public final class VoiceListenerService extends android.app.Service {
    private android.speech.SpeechRecognizer recognizer;
    private android.speech.tts.TextToSpeech tts;
    private com.okdriver.voiceassistant.VoiceOverlayManager overlayManager;
    private android.os.Handler handler;
    @org.jetbrains.annotations.NotNull
    private com.okdriver.voiceassistant.AssistantState state = com.okdriver.voiceassistant.AssistantState.SLEEPING;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.CoroutineScope serviceScope = null;
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String CHANNEL_ID = "voice_listener_channel";
    public static final int NOTIFICATION_ID = 1;
    @org.jetbrains.annotations.NotNull
    private final android.speech.RecognitionListener recognitionListener = null;
    @org.jetbrains.annotations.NotNull
    private final android.speech.tts.UtteranceProgressListener ttsListener = null;
    @org.jetbrains.annotations.NotNull
    public static final com.okdriver.voiceassistant.VoiceListenerService.Companion Companion = null;
    
    public VoiceListenerService() {
        super();
    }
    
    @java.lang.Override
    public void onCreate() {
    }
    
    private final void startWakeWordListening() {
    }
    
    private final void startQueryListening() {
    }
    
    private final void onWakeWordDetected() {
    }
    
    private final void sendQueryToGroq(java.lang.String query) {
    }
    
    private final void speakAndRestart(java.lang.String text) {
    }
    
    private final boolean isWakeWord(java.lang.String text) {
        return false;
    }
    
    private final int levenshtein(java.lang.String s1, java.lang.String s2) {
        return 0;
    }
    
    private final boolean checkForCloseCommand(java.lang.String text) {
        return false;
    }
    
    private final void playBeep() {
    }
    
    private final java.lang.String getApiKeyFromEncryptedPrefs() {
        return null;
    }
    
    private final void updateOverlay(com.okdriver.voiceassistant.AssistantState newState) {
    }
    
    private final void createNotificationChannel() {
    }
    
    private final android.app.Notification buildNotification() {
        return null;
    }
    
    @java.lang.Override
    public int onStartCommand(@org.jetbrains.annotations.Nullable
    android.content.Intent intent, int flags, int startId) {
        return 0;
    }
    
    @java.lang.Override
    public void onDestroy() {
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public android.os.IBinder onBind(@org.jetbrains.annotations.Nullable
    android.content.Intent intent) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2 = {"Lcom/okdriver/voiceassistant/VoiceListenerService$Companion;", "", "()V", "CHANNEL_ID", "", "NOTIFICATION_ID", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}