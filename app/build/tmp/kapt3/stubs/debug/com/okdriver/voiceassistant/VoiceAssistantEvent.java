package com.okdriver.voiceassistant;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0001\u0003B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0001\u0004\u00a8\u0006\u0005"}, d2 = {"Lcom/okdriver/voiceassistant/VoiceAssistantEvent;", "", "()V", "StartListening", "Lcom/okdriver/voiceassistant/VoiceAssistantEvent$StartListening;", "app_debug"})
public abstract class VoiceAssistantEvent {
    
    private VoiceAssistantEvent() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/okdriver/voiceassistant/VoiceAssistantEvent$StartListening;", "Lcom/okdriver/voiceassistant/VoiceAssistantEvent;", "()V", "app_debug"})
    public static final class StartListening extends com.okdriver.voiceassistant.VoiceAssistantEvent {
        @org.jetbrains.annotations.NotNull
        public static final com.okdriver.voiceassistant.VoiceAssistantEvent.StartListening INSTANCE = null;
        
        private StartListening() {
        }
    }
}