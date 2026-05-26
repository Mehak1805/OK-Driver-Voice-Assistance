package com.okdriver.voiceassistant.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.okdriver.voiceassistant.viewmodel.MainViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: MainViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.Companion
            .fillMaxSize()
            .background(Color(0xFF0D1117))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.Companion.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Companion.CenterVertically
        ) {
            Text(
                text = "okDriver",
                color = Color(0xFF00E5A0),
                fontSize = 24.sp,
                fontWeight = FontWeight.Companion.Bold
            )
            IconButton(onClick = { navController.navigate("settings") }) {
                Icon(
                    Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = Color.Companion.White
                )
            }
        }

        Spacer(modifier = Modifier.Companion.height(48.dp))

        Text(
            text = "Say \"Ok Driver\" anytime...",
            color = Color.Companion.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Companion.Light,
            modifier = Modifier.Companion.align(Alignment.Companion.CenterHorizontally)
        )

        Spacer(modifier = Modifier.Companion.height(48.dp))

        Text(
            text = "Recent Conversations",
            color = Color.Companion.Gray,
            fontSize = 14.sp,
            fontWeight = FontWeight.Companion.Bold,
            modifier = Modifier.Companion.padding(bottom = 16.dp)
        )

        LazyColumn(
            modifier = Modifier.Companion.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(uiState.recentInteractions) { interaction ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF161B22)),
                    modifier = Modifier.Companion.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.Companion.padding(16.dp)) {
                        Text(
                            text = "You: ${interaction.query}",
                            color = Color.Companion.White,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.Companion.height(8.dp))
                        Text(
                            text = "AI: ${interaction.response}",
                            color = Color(0xFF00E5A0),
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}
