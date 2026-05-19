package com.okdriver.voiceassistant

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "conversations")
data class ConversationEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val query: String,
    val response: String,
    val t1: Long,
    val t2: Long,
    val t3: Long,
    val t4: Long,
    val totalLatency: Long,
    val timestamp: Long = System.currentTimeMillis()
)
