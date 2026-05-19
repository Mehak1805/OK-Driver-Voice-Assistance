package com.okdriver.voiceassistant

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ConversationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(conversation: ConversationEntity)

    @Query("SELECT * FROM conversations ORDER BY timestamp DESC LIMIT 10")
    suspend fun getLast10(): List<ConversationEntity>

    @Query("DELETE FROM conversations")
    suspend fun clearAll()
}
