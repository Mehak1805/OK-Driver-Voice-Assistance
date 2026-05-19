package com.okdriver.voiceassistant;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u0007\n\u0002\b\u0006\u0018\u00002\u00020\u0001B%\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\b\u0010 \u001a\u00020\u0012H\u0002J\u0014\u0010!\u001a\u00020\u00122\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011J\b\u0010#\u001a\u00020\u0012H\u0002J\u0014\u0010$\u001a\u00020\u00122\f\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011J\u0006\u0010&\u001a\u00020\u0012J\u0006\u0010\'\u001a\u00020\u0012J\u0006\u0010(\u001a\u00020\u0012J\u000e\u0010)\u001a\u00020\u00122\u0006\u0010*\u001a\u00020+J\u0006\u0010,\u001a\u00020\u0012J\b\u0010-\u001a\u00020\u0012H\u0002J\u0006\u0010.\u001a\u00020\u0012J\u0006\u0010/\u001a\u00020\u0012J\b\u00100\u001a\u00020\u0012H\u0002J\u000e\u00101\u001a\u00020\u00122\u0006\u00102\u001a\u000203J\u0006\u00104\u001a\u00020\u0012J\u000e\u00105\u001a\u00020\u00122\u0006\u00106\u001a\u00020+J\u000e\u00107\u001a\u00020\u00122\u0006\u00108\u001a\u00020+R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0010\u001a\n\u0012\u0004\u0012\u00020\u0012\u0018\u00010\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001c\u001a\u0004\u0018\u00010\u001dX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u00069"}, d2 = {"Lcom/okdriver/voiceassistant/VoiceOverlayView;", "Landroid/widget/FrameLayout;", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "closeButton", "Landroid/widget/TextView;", "handler", "Landroid/os/Handler;", "idlePromptText", "idlePulseAnimator", "Landroid/animation/ValueAnimator;", "onCloseListener", "Lkotlin/Function0;", "", "orbView", "Lcom/okdriver/voiceassistant/OrbAnimationView;", "responseText", "scrim", "Landroid/view/View;", "sheet", "Landroid/widget/LinearLayout;", "statusText", "transcriptText", "typewriterRunnable", "Ljava/lang/Runnable;", "waveformView", "Lcom/okdriver/voiceassistant/WaveformView;", "cancelIdlePulse", "dismiss", "onHidden", "dismissOverlay", "setOnCloseListener", "listener", "setStateIdlePulse", "setStateListening", "setStateSleeping", "setStateSpeaking", "response", "", "setStateThinking", "setupSwipeGesture", "show", "showIdleTimeoutWarning", "startIdlePulse", "updateAmplitude", "amp", "", "updateIdlePrompt", "updateStatus", "status", "updateTranscript", "text", "app_debug"})
public final class VoiceOverlayView extends android.widget.FrameLayout {
    @org.jetbrains.annotations.NotNull
    private final android.view.View scrim = null;
    @org.jetbrains.annotations.NotNull
    private final android.widget.LinearLayout sheet = null;
    @org.jetbrains.annotations.NotNull
    private final android.widget.TextView statusText = null;
    @org.jetbrains.annotations.NotNull
    private final android.widget.TextView transcriptText = null;
    @org.jetbrains.annotations.NotNull
    private final android.widget.TextView responseText = null;
    @org.jetbrains.annotations.NotNull
    private final com.okdriver.voiceassistant.WaveformView waveformView = null;
    @org.jetbrains.annotations.NotNull
    private final com.okdriver.voiceassistant.OrbAnimationView orbView = null;
    @org.jetbrains.annotations.NotNull
    private final android.widget.TextView closeButton = null;
    @org.jetbrains.annotations.NotNull
    private final android.widget.TextView idlePromptText = null;
    @org.jetbrains.annotations.NotNull
    private final android.os.Handler handler = null;
    @org.jetbrains.annotations.Nullable
    private java.lang.Runnable typewriterRunnable;
    @org.jetbrains.annotations.Nullable
    private android.animation.ValueAnimator idlePulseAnimator;
    @org.jetbrains.annotations.Nullable
    private kotlin.jvm.functions.Function0<kotlin.Unit> onCloseListener;
    
    @kotlin.jvm.JvmOverloads
    public VoiceOverlayView(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.Nullable
    android.util.AttributeSet attrs, int defStyleAttr) {
        super(null);
    }
    
    private final void setupSwipeGesture() {
    }
    
    public final void show() {
    }
    
    public final void dismiss(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onHidden) {
    }
    
    private final void dismissOverlay() {
    }
    
    public final void setOnCloseListener(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> listener) {
    }
    
    public final void setStateListening() {
    }
    
    public final void updateAmplitude(float amp) {
    }
    
    public final void updateTranscript(@org.jetbrains.annotations.NotNull
    java.lang.String text) {
    }
    
    public final void updateStatus(@org.jetbrains.annotations.NotNull
    java.lang.String status) {
    }
    
    public final void setStateThinking() {
    }
    
    public final void setStateSleeping() {
    }
    
    public final void setStateIdlePulse() {
    }
    
    public final void updateIdlePrompt() {
    }
    
    public final void showIdleTimeoutWarning() {
    }
    
    private final void startIdlePulse() {
    }
    
    private final void cancelIdlePulse() {
    }
    
    public final void setStateSpeaking(@org.jetbrains.annotations.NotNull
    java.lang.String response) {
    }
    
    @kotlin.jvm.JvmOverloads
    public VoiceOverlayView(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        super(null);
    }
    
    @kotlin.jvm.JvmOverloads
    public VoiceOverlayView(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.Nullable
    android.util.AttributeSet attrs) {
        super(null);
    }
}