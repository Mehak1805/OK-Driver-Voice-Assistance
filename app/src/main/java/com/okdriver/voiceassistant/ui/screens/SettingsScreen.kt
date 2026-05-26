package com.okdriver.voiceassistant.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.okdriver.voiceassistant.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(onBack: () -> Unit, viewModel: SettingsViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.Companion
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text("Groq API Key", fontWeight = FontWeight.Companion.Bold, fontSize = 16.sp)
            OutlinedTextField(
                value = uiState.apiKey,
                onValueChange = { viewModel.updateApiKey(it) },
                modifier = Modifier.Companion.fillMaxWidth(),
                placeholder = { Text("Enter your API Key") },
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.Companion.height(24.dp))

            Text(
                "Wake Word Sensitivity: ${String.format("%.1f", uiState.sensitivity)}",
                fontWeight = FontWeight.Companion.Bold
            )
            Slider(
                value = uiState.sensitivity,
                onValueChange = { viewModel.updateSensitivity(it) },
                valueRange = 0f..1f
            )

            Spacer(modifier = Modifier.Companion.height(24.dp))

            Row(
                modifier = Modifier.Companion.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("HD Voice (Google Cloud TTS)", fontWeight = FontWeight.Companion.Bold)
                Switch(checked = uiState.hdVoice, onCheckedChange = { viewModel.updateHdVoice(it) })
            }

            Spacer(modifier = Modifier.Companion.height(40.dp))

            Button(
                onClick = onBack,
                modifier = Modifier.Companion.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4ECCA3))
            ) {
                Text("Back to Home", color = Color.Companion.White)
            }

            Spacer(modifier = Modifier.Companion.height(16.dp))

            OutlinedButton(
                onClick = { viewModel.clearHistory() },
                modifier = Modifier.Companion.fillMaxWidth()
            ) {
                Text("Clear History", color = Color.Companion.Red)
            }
        }
    }
}
