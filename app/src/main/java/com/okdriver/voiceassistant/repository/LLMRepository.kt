package com.okdriver.voiceassistant.repository

import android.util.Log
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.okdriver.voiceassistant.util.SecurePreferencesProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LLMRepository @Inject constructor(
    private val okHttpClient: OkHttpClient,
    private val securePreferences: SecurePreferencesProvider
) {
    private val gson = Gson()
    private val baseUrl = "https://api.groq.com/openai/v1/chat/completions"
    private val model = "openai/gpt-oss-120b"
    private val maxTokens = 1024

    data class ChatMessage(
        @SerializedName("role") val role: String,
        @SerializedName("content") val content: String
    )

    data class ChatRequest(
        @SerializedName("model") val model: String,
        @SerializedName("messages") val messages: List<ChatMessage>,
        @SerializedName("max_tokens") val maxTokens: Int,
        @SerializedName("temperature") val temperature: Float = 0.7f,
        @SerializedName("stream") val stream: Boolean = false
    )

    data class ChatResponseChoice(
        @SerializedName("message") val message: ChatMessage
    )

    data class ChatResponse(
        @SerializedName("choices") val choices: List<ChatResponseChoice>
    )

    /**
     * Sends a user query to Groq and returns the assistant reply as a Flow.
     * Retries up to 3 times with exponential back‑off (500ms, 1s, 2s).
     */
    fun queryAssistant(userQuery: String): Flow<String> = flow {
        val apiKey = securePreferences.getGroqApiKey()?.takeIf { it.isNotBlank() }.orEmpty()
        if (apiKey.isBlank()) {
            emit("❗ API key not set. Please add it in Settings.")
            return@flow
        }
        val requestBody = ChatRequest(
            model = model,
            messages = listOf(
                ChatMessage(
                    "system",
                    """You are okDriver, an AI road safety and driving assistant for Indian drivers.

LANGUAGE RULE: Match your response language to the user's query language:
- If user speaks in ENGLISH → respond in clear English only.
- If user speaks in HINDI or HINGLISH → respond in Hinglish (natural mix of Hindi + English in Roman script, never Devanagari).
- Never mix languages in a way that feels unnatural.

RESPONSE RULES:
- Keep responses very short: 1-2 sentences maximum.
- Always be helpful, calm, and safety-focused.
- Never use Devanagari script."""
                ),
                ChatMessage("user", userQuery)
            ),
            maxTokens = maxTokens,
            temperature = 0.7f,
            stream = false
        )
        val json = gson.toJson(requestBody)
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body = json.toRequestBody(mediaType)
        val request = Request.Builder()
            .url(baseUrl)
            .addHeader("Authorization", "Bearer $apiKey")
            .addHeader("Content-Type", "application/json")
            .post(body)
            .build()

        var attempt = 0
        var delayMillis = 500L
        while (true) {
            try {
                okHttpClient.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")
                    val respBody = response.body?.string() ?: throw IOException("Empty response")
                    val chatResp = gson.fromJson(respBody, ChatResponse::class.java)
                    val reply = chatResp.choices.firstOrNull()?.message?.content ?: "(no response)"
                    emit(reply)
                    return@flow
                }
            } catch (e: Exception) {
                Log.w("LLMRepository", "Attempt ${attempt + 1} failed: ${e.message}")
                attempt++
                if (attempt >= 3) {
                    emit("❗ Failed to get response after retries.")
                    return@flow
                }
                // exponential back‑off
                delay(delayMillis)
                delayMillis *= 2
            }
        }
    }.flowOn(Dispatchers.IO)
}