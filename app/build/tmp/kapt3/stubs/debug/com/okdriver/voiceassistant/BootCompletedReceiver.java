package com.okdriver.voiceassistant;

@dagger.hilt.android.AndroidEntryPoint
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001a\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0016R\u001e\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b\u00a8\u0006\u000f"}, d2 = {"Lcom/okdriver/voiceassistant/BootCompletedReceiver;", "Landroid/content/BroadcastReceiver;", "()V", "serviceIntentProvider", "Lcom/okdriver/voiceassistant/ServiceIntentProvider;", "getServiceIntentProvider", "()Lcom/okdriver/voiceassistant/ServiceIntentProvider;", "setServiceIntentProvider", "(Lcom/okdriver/voiceassistant/ServiceIntentProvider;)V", "onReceive", "", "context", "Landroid/content/Context;", "intent", "Landroid/content/Intent;", "app_debug"})
public final class BootCompletedReceiver extends android.content.BroadcastReceiver {
    @javax.inject.Inject
    public com.okdriver.voiceassistant.ServiceIntentProvider serviceIntentProvider;
    
    public BootCompletedReceiver() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.okdriver.voiceassistant.ServiceIntentProvider getServiceIntentProvider() {
        return null;
    }
    
    public final void setServiceIntentProvider(@org.jetbrains.annotations.NotNull
    com.okdriver.voiceassistant.ServiceIntentProvider p0) {
    }
    
    @java.lang.Override
    public void onReceive(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.Nullable
    android.content.Intent intent) {
    }
}