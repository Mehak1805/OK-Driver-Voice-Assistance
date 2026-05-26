package com.okdriver.voiceassistant.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.okdriver.voiceassistant.services.VoiceListenerService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BootCompletedReceiver : BroadcastReceiver() {

    @Inject
    lateinit var serviceIntentProvider: ServiceIntentProvider

    override fun onReceive(context: Context, intent: Intent?) {
        if (Intent.ACTION_BOOT_COMPLETED == intent?.action) {
            Log.d("BootCompletedReceiver", "Boot completed – starting VoiceListenerService")
            val serviceIntent = serviceIntentProvider.getIntent(context)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(serviceIntent)
            } else {
                context.startService(serviceIntent)
            }
        }
    }
}

// Helper class to provide the service intent via Hilt (allows injection in receiver)
class ServiceIntentProvider @Inject constructor() {
    fun getIntent(context: Context): Intent {
        return Intent(context, VoiceListenerService::class.java)
    }
}