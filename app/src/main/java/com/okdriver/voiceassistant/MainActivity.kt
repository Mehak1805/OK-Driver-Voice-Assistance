package com.okdriver.voiceassistant

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.okdriver.voiceassistant.services.VoiceListenerService
import com.okdriver.voiceassistant.ui.screens.HomeScreen
import com.okdriver.voiceassistant.ui.screens.SettingsScreen
import com.okdriver.voiceassistant.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // ── Permission launcher for RECORD_AUDIO ─────────────────────────────────
    private val audioPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            // Audio permission granted — check overlay permission next
            checkOverlayPermissionThenStart()
        } else {
            Toast.makeText(
                this,
                "Microphone permission is required for the voice assistant.",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    // ── Permission launcher for SYSTEM_ALERT_WINDOW (overlay) ────────────────
    // We re-check in onResume because the system settings screen returns here
    private val overlayPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (Settings.canDrawOverlays(this)) {
            startVoiceService()
        } else {
            Toast.makeText(
                this,
                "Overlay permission is required to show the voice assistant UI.",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val mainViewModel: MainViewModel =
                androidx.hilt.navigation.compose.hiltViewModel()
            OkDriverApp(
                crashLog = intent.getStringExtra("CRASH_LOG"),
                mainViewModel = mainViewModel
            )
        }

        // Start permission flow — service is only started after ALL permissions are granted
        checkPermissionsAndStart()

        // Request battery optimization exemption
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val pm = getSystemService(android.os.PowerManager::class.java)
            if (pm != null && !pm.isIgnoringBatteryOptimizations(packageName)) {
                val batteryIntent = Intent(
                    Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
                    Uri.parse("package:$packageName")
                )
                startActivity(batteryIntent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // If we returned from the overlay settings screen, re-check and start if ready
        if (hasAudioPermission() && Settings.canDrawOverlays(this)) {
            startVoiceService()
        }
        sendBroadcast(Intent("com.okdriver.APP_FOREGROUND"))
    }

    override fun onStop() {
        super.onStop()
        sendBroadcast(Intent("com.okdriver.APP_BACKGROUND"))
    }

    override fun onDestroy() {
        super.onDestroy()
        sendBroadcast(Intent("com.okdriver.APP_BACKGROUND"))
    }

    // ── Permission flow ──────────────────────────────────────────────────────

    private fun checkPermissionsAndStart() {
        when {
            !hasAudioPermission() -> audioPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
            !Settings.canDrawOverlays(this) -> requestOverlayPermission()
            else -> startVoiceService()
        }
    }

    private fun checkOverlayPermissionThenStart() {
        if (!Settings.canDrawOverlays(this)) {
            requestOverlayPermission()
        } else {
            startVoiceService()
        }
    }

    private fun requestOverlayPermission() {
        Toast.makeText(
            this,
            "Please enable 'Display over other apps' for okDriver.",
            Toast.LENGTH_LONG
        ).show()
        overlayPermissionLauncher.launch(
            Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            )
        )
    }

    private fun hasAudioPermission() =
        ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) ==
                PackageManager.PERMISSION_GRANTED

    // ── Start the service ────────────────────────────────────────────────────

    private var serviceStarted = false

    private fun startVoiceService() {
        if (serviceStarted) return  // idempotent
        serviceStarted = true
        val intent = Intent(this, VoiceListenerService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent)
        } else {
            startService(intent)
        }
    }
}

// ── App composable ────────────────────────────────────────────────────────────

@Composable
fun OkDriverApp(crashLog: String? = null, mainViewModel: MainViewModel) {
    val navController = rememberNavController()

    Scaffold(containerColor = Color(0xFF0D1117)) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") {
                Box(modifier = Modifier.fillMaxSize()) {
                    HomeScreen(navController = navController, viewModel = mainViewModel)
                    if (crashLog != null) {
                        Card(
                            modifier = Modifier
                                .padding(16.dp)
                                .align(Alignment.TopCenter),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.Red.copy(alpha = 0.9f)
                            )
                        ) {
                            Text(
                                text = "CRASH:\n${crashLog.take(500)}",
                                color = Color.White,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }
            composable("settings") {
                SettingsScreen(onBack = { navController.popBackStack() })
            }
        }
    }
}
