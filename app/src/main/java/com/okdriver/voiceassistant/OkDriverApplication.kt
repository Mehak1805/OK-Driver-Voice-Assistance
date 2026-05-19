package com.okdriver.voiceassistant

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

import android.content.Context
import android.util.Log
import kotlin.system.exitProcess

@HiltAndroidApp
class OkDriverApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { thread, exception ->
            Log.e("CRASH_HANDLER", "App crashed!", exception)
            val prefs = getSharedPreferences("crash_logs", Context.MODE_PRIVATE)
            prefs.edit().putString("last_crash", Log.getStackTraceString(exception)).commit()
            defaultHandler?.uncaughtException(thread, exception)
            exitProcess(1)
        }
    }
}
