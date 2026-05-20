package com.okdriver.voiceassistant

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SecurePreferencesProvider @Inject constructor(@ApplicationContext private val context: Context) {

    private val sharedPrefs by lazy {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        EncryptedSharedPreferences.create(
            "okdriver_secure_prefs",
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun setGroqApiKey(apiKey: String) {
        sharedPrefs.edit().putString("groq_api_key", apiKey).apply()
    }

    fun getGroqApiKey(): String? = sharedPrefs.getString("groq_api_key", null)

    fun setWakeWordSensitivity(sensitivity: Float) {
        sharedPrefs.edit().putFloat("wake_word_sensitivity", sensitivity).apply()
    }

    fun getWakeWordSensitivity(): Float = sharedPrefs.getFloat("wake_word_sensitivity", 0.6f)

    fun setLanguage(code: String) {
        sharedPrefs.edit().putString("language_code", code).apply()
    }

    fun getLanguage(): String = sharedPrefs.getString("language_code", "en-IN") ?: "en-IN"

    fun setHdVoiceEnabled(enabled: Boolean) {
        sharedPrefs.edit().putBoolean("hd_voice", enabled).apply()
    }

    fun isHdVoiceEnabled(): Boolean = sharedPrefs.getBoolean("hd_voice", false)
}
