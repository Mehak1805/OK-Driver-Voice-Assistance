package com.okdriver.voiceassistant;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001:\u0004\u0011\u0012\u0013\u0014B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\b0\u000f2\u0006\u0010\u0010\u001a\u00020\bR\u000e\u0010\u0007\u001a\u00020\bX\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\bX\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/okdriver/voiceassistant/LLMRepository;", "", "okHttpClient", "Lokhttp3/OkHttpClient;", "securePreferences", "Lcom/okdriver/voiceassistant/SecurePreferencesProvider;", "(Lokhttp3/OkHttpClient;Lcom/okdriver/voiceassistant/SecurePreferencesProvider;)V", "baseUrl", "", "gson", "Lcom/google/gson/Gson;", "maxTokens", "", "model", "queryAssistant", "Lkotlinx/coroutines/flow/Flow;", "userQuery", "ChatMessage", "ChatRequest", "ChatResponse", "ChatResponseChoice", "app_debug"})
public final class LLMRepository {
    @org.jetbrains.annotations.NotNull
    private final okhttp3.OkHttpClient okHttpClient = null;
    @org.jetbrains.annotations.NotNull
    private final com.okdriver.voiceassistant.SecurePreferencesProvider securePreferences = null;
    @org.jetbrains.annotations.NotNull
    private final com.google.gson.Gson gson = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String baseUrl = "https://api.groq.com/openai/v1/chat/completions";
    @org.jetbrains.annotations.NotNull
    private final java.lang.String model = "llama-3.1-8b-instant";
    private final int maxTokens = 1024;
    
    @javax.inject.Inject
    public LLMRepository(@org.jetbrains.annotations.NotNull
    okhttp3.OkHttpClient okHttpClient, @org.jetbrains.annotations.NotNull
    com.okdriver.voiceassistant.SecurePreferencesProvider securePreferences) {
        super();
    }
    
    /**
     * Sends a user query to Groq and returns the assistant reply as a Flow.
     * Retries up to 3 times with exponential back‑off (500ms, 1s, 2s).
     */
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.lang.String> queryAssistant(@org.jetbrains.annotations.NotNull
    java.lang.String userQuery) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\n\u001a\u00020\u0003H\u00c6\u0003J\u001d\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u000f\u001a\u00020\u0010H\u00d6\u0001J\t\u0010\u0011\u001a\u00020\u0003H\u00d6\u0001R\u0016\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007\u00a8\u0006\u0012"}, d2 = {"Lcom/okdriver/voiceassistant/LLMRepository$ChatMessage;", "", "role", "", "content", "(Ljava/lang/String;Ljava/lang/String;)V", "getContent", "()Ljava/lang/String;", "getRole", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
    public static final class ChatMessage {
        @com.google.gson.annotations.SerializedName(value = "role")
        @org.jetbrains.annotations.NotNull
        private final java.lang.String role = null;
        @com.google.gson.annotations.SerializedName(value = "content")
        @org.jetbrains.annotations.NotNull
        private final java.lang.String content = null;
        
        public ChatMessage(@org.jetbrains.annotations.NotNull
        java.lang.String role, @org.jetbrains.annotations.NotNull
        java.lang.String content) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getRole() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getContent() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.okdriver.voiceassistant.LLMRepository.ChatMessage copy(@org.jetbrains.annotations.NotNull
        java.lang.String role, @org.jetbrains.annotations.NotNull
        java.lang.String content) {
            return null;
        }
        
        @java.lang.Override
        public boolean equals(@org.jetbrains.annotations.Nullable
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override
        @org.jetbrains.annotations.NotNull
        public java.lang.String toString() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0016\b\u0086\b\u0018\u00002\u00020\u0001B7\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f\u00a2\u0006\u0002\u0010\rJ\t\u0010\u0018\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\bH\u00c6\u0003J\t\u0010\u001b\u001a\u00020\nH\u00c6\u0003J\t\u0010\u001c\u001a\u00020\fH\u00c6\u0003JA\u0010\u001d\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\fH\u00c6\u0001J\u0013\u0010\u001e\u001a\u00020\f2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010 \u001a\u00020\bH\u00d6\u0001J\t\u0010!\u001a\u00020\u0003H\u00d6\u0001R\u0016\u0010\u0007\u001a\u00020\b8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001c\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0016\u0010\u000b\u001a\u00020\f8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0016\u0010\t\u001a\u00020\n8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017\u00a8\u0006\""}, d2 = {"Lcom/okdriver/voiceassistant/LLMRepository$ChatRequest;", "", "model", "", "messages", "", "Lcom/okdriver/voiceassistant/LLMRepository$ChatMessage;", "maxTokens", "", "temperature", "", "stream", "", "(Ljava/lang/String;Ljava/util/List;IFZ)V", "getMaxTokens", "()I", "getMessages", "()Ljava/util/List;", "getModel", "()Ljava/lang/String;", "getStream", "()Z", "getTemperature", "()F", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "other", "hashCode", "toString", "app_debug"})
    public static final class ChatRequest {
        @com.google.gson.annotations.SerializedName(value = "model")
        @org.jetbrains.annotations.NotNull
        private final java.lang.String model = null;
        @com.google.gson.annotations.SerializedName(value = "messages")
        @org.jetbrains.annotations.NotNull
        private final java.util.List<com.okdriver.voiceassistant.LLMRepository.ChatMessage> messages = null;
        @com.google.gson.annotations.SerializedName(value = "max_tokens")
        private final int maxTokens = 0;
        @com.google.gson.annotations.SerializedName(value = "temperature")
        private final float temperature = 0.0F;
        @com.google.gson.annotations.SerializedName(value = "stream")
        private final boolean stream = false;
        
        public ChatRequest(@org.jetbrains.annotations.NotNull
        java.lang.String model, @org.jetbrains.annotations.NotNull
        java.util.List<com.okdriver.voiceassistant.LLMRepository.ChatMessage> messages, int maxTokens, float temperature, boolean stream) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getModel() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<com.okdriver.voiceassistant.LLMRepository.ChatMessage> getMessages() {
            return null;
        }
        
        public final int getMaxTokens() {
            return 0;
        }
        
        public final float getTemperature() {
            return 0.0F;
        }
        
        public final boolean getStream() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<com.okdriver.voiceassistant.LLMRepository.ChatMessage> component2() {
            return null;
        }
        
        public final int component3() {
            return 0;
        }
        
        public final float component4() {
            return 0.0F;
        }
        
        public final boolean component5() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.okdriver.voiceassistant.LLMRepository.ChatRequest copy(@org.jetbrains.annotations.NotNull
        java.lang.String model, @org.jetbrains.annotations.NotNull
        java.util.List<com.okdriver.voiceassistant.LLMRepository.ChatMessage> messages, int maxTokens, float temperature, boolean stream) {
            return null;
        }
        
        @java.lang.Override
        public boolean equals(@org.jetbrains.annotations.Nullable
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override
        @org.jetbrains.annotations.NotNull
        public java.lang.String toString() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\u0002\u0010\u0005J\u000f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\u0019\u0010\t\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0001J\u0013\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\t\u0010\u000f\u001a\u00020\u0010H\u00d6\u0001R\u001c\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\u0011"}, d2 = {"Lcom/okdriver/voiceassistant/LLMRepository$ChatResponse;", "", "choices", "", "Lcom/okdriver/voiceassistant/LLMRepository$ChatResponseChoice;", "(Ljava/util/List;)V", "getChoices", "()Ljava/util/List;", "component1", "copy", "equals", "", "other", "hashCode", "", "toString", "", "app_debug"})
    public static final class ChatResponse {
        @com.google.gson.annotations.SerializedName(value = "choices")
        @org.jetbrains.annotations.NotNull
        private final java.util.List<com.okdriver.voiceassistant.LLMRepository.ChatResponseChoice> choices = null;
        
        public ChatResponse(@org.jetbrains.annotations.NotNull
        java.util.List<com.okdriver.voiceassistant.LLMRepository.ChatResponseChoice> choices) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<com.okdriver.voiceassistant.LLMRepository.ChatResponseChoice> getChoices() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<com.okdriver.voiceassistant.LLMRepository.ChatResponseChoice> component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.okdriver.voiceassistant.LLMRepository.ChatResponse copy(@org.jetbrains.annotations.NotNull
        java.util.List<com.okdriver.voiceassistant.LLMRepository.ChatResponseChoice> choices) {
            return null;
        }
        
        @java.lang.Override
        public boolean equals(@org.jetbrains.annotations.Nullable
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override
        @org.jetbrains.annotations.NotNull
        public java.lang.String toString() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\f\u001a\u00020\rH\u00d6\u0001J\t\u0010\u000e\u001a\u00020\u000fH\u00d6\u0001R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0010"}, d2 = {"Lcom/okdriver/voiceassistant/LLMRepository$ChatResponseChoice;", "", "message", "Lcom/okdriver/voiceassistant/LLMRepository$ChatMessage;", "(Lcom/okdriver/voiceassistant/LLMRepository$ChatMessage;)V", "getMessage", "()Lcom/okdriver/voiceassistant/LLMRepository$ChatMessage;", "component1", "copy", "equals", "", "other", "hashCode", "", "toString", "", "app_debug"})
    public static final class ChatResponseChoice {
        @com.google.gson.annotations.SerializedName(value = "message")
        @org.jetbrains.annotations.NotNull
        private final com.okdriver.voiceassistant.LLMRepository.ChatMessage message = null;
        
        public ChatResponseChoice(@org.jetbrains.annotations.NotNull
        com.okdriver.voiceassistant.LLMRepository.ChatMessage message) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.okdriver.voiceassistant.LLMRepository.ChatMessage getMessage() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.okdriver.voiceassistant.LLMRepository.ChatMessage component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.okdriver.voiceassistant.LLMRepository.ChatResponseChoice copy(@org.jetbrains.annotations.NotNull
        com.okdriver.voiceassistant.LLMRepository.ChatMessage message) {
            return null;
        }
        
        @java.lang.Override
        public boolean equals(@org.jetbrains.annotations.Nullable
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override
        @org.jetbrains.annotations.NotNull
        public java.lang.String toString() {
            return null;
        }
    }
}