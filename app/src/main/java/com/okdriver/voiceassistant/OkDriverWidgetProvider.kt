package com.okdriver.voiceassistant

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.core.app.PendingIntentCompat
import com.okdriver.voiceassistant.R

class OkDriverWidgetProvider : AppWidgetProvider() {

    companion object {
        const val ACTION_TOGGLE_LISTENING = "com.okdriver.voiceassistant.ACTION_TOGGLE_LISTENING"
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            val views = RemoteViews(context.packageName, R.layout.ok_driver_widget)
            // Intent to trigger a voice query directly via the Service overlay
            val intent = Intent(context, VoiceListenerService::class.java).apply {
                action = "com.okdriver.voiceassistant.ACTION_TRIGGER_VOICE"
            }
            val pendingIntent = PendingIntent.getService(
                context,
                0,
                intent,
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) PendingIntent.FLAG_MUTABLE else 0
            )
            views.setOnClickPendingIntent(R.id.widget_mic_icon, pendingIntent)
            // Update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}
