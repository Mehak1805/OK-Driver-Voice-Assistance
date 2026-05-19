package com.okdriver.voiceassistant;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0011\u001a\u00020\fJ\u0006\u0010\u0012\u001a\u00020\fJ\u0006\u0010\u0013\u001a\u00020\fJ\u0014\u0010\u0014\u001a\u00020\f2\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\f0\u000bJ\u0006\u0010\u0016\u001a\u00020\fJ\u0006\u0010\u0017\u001a\u00020\fJ\u000e\u0010\u0018\u001a\u00020\f2\u0006\u0010\u0019\u001a\u00020\u001aJ\u0006\u0010\u001b\u001a\u00020\fJ\u0006\u0010\u001c\u001a\u00020\fJ\u0006\u0010\u001d\u001a\u00020\fJ\u000e\u0010\u001e\u001a\u00020\f2\u0006\u0010\u001f\u001a\u00020 J\u000e\u0010!\u001a\u00020\f2\u0006\u0010\"\u001a\u00020\u001aJ\u000e\u0010#\u001a\u00020\f2\u0006\u0010$\u001a\u00020\u001aR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0007\"\u0004\b\b\u0010\tR\u0016\u0010\n\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006%"}, d2 = {"Lcom/okdriver/voiceassistant/VoiceOverlayManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "isShowing", "", "()Z", "setShowing", "(Z)V", "onCloseCallback", "Lkotlin/Function0;", "", "overlayView", "Lcom/okdriver/voiceassistant/VoiceOverlayView;", "windowManager", "Landroid/view/WindowManager;", "hideOverlay", "setIdleState", "setListening", "setOnCloseCallback", "callback", "setSleeping", "setSleepingIdle", "setSpeaking", "response", "", "setThinking", "showIdleTimeout", "showOverlay", "updateAmplitude", "amp", "", "updateOverlayStatus", "status", "updateTranscript", "text", "app_debug"})
public final class VoiceOverlayManager {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull
    private final android.view.WindowManager windowManager = null;
    @org.jetbrains.annotations.Nullable
    private com.okdriver.voiceassistant.VoiceOverlayView overlayView;
    private boolean isShowing = false;
    @org.jetbrains.annotations.Nullable
    private kotlin.jvm.functions.Function0<kotlin.Unit> onCloseCallback;
    
    @javax.inject.Inject
    public VoiceOverlayManager(@dagger.hilt.android.qualifiers.ApplicationContext
    @org.jetbrains.annotations.NotNull
    android.content.Context context) {
        super();
    }
    
    public final boolean isShowing() {
        return false;
    }
    
    public final void setShowing(boolean p0) {
    }
    
    public final void setOnCloseCallback(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> callback) {
    }
    
    public final void showOverlay() {
    }
    
    public final void hideOverlay() {
    }
    
    public final void setSleeping() {
    }
    
    public final void setSleepingIdle() {
    }
    
    public final void setIdleState() {
    }
    
    public final void showIdleTimeout() {
    }
    
    public final void setListening() {
    }
    
    public final void setThinking() {
    }
    
    public final void setSpeaking(@org.jetbrains.annotations.NotNull
    java.lang.String response) {
    }
    
    public final void updateTranscript(@org.jetbrains.annotations.NotNull
    java.lang.String text) {
    }
    
    public final void updateAmplitude(float amp) {
    }
    
    public final void updateOverlayStatus(@org.jetbrains.annotations.NotNull
    java.lang.String status) {
    }
}