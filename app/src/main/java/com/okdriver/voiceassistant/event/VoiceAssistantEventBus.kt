package com.okdriver.voiceassistant.event

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VoiceAssistantEventBus @Inject constructor() {
    private val _events = MutableSharedFlow<VoiceAssistantEvent>(extraBufferCapacity = 1)
    val events = _events.asSharedFlow()

    fun postEvent(event: VoiceAssistantEvent) {
        _events.tryEmit(event)
    }
}

sealed class VoiceAssistantEvent {
    object StartListening : VoiceAssistantEvent()
}