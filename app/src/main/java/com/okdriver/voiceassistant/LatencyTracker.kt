package com.okdriver.voiceassistant

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

data class LatencyMetrics(
    val t1: Long = 0L,
    val t2: Long = 0L,
    val t3: Long = 0L,
    val t4: Long = 0L,
    val sttLatency: Long = 0L,
    val llmLatency: Long = 0L,
    val ttsLatency: Long = 0L,
    val totalLatency: Long = 0L
)

@Singleton
class LatencyTracker @Inject constructor() {
    private val _metrics = MutableStateFlow(LatencyMetrics())
    val metrics: StateFlow<LatencyMetrics> = _metrics

    fun recordT1(timestamp: Long) {
        _metrics.value = _metrics.value.copy(t1 = timestamp)
    }

    fun recordT2(timestamp: Long) {
        val t1 = _metrics.value.t1
        val sttLatency = if (t1 > 0) timestamp - t1 else 0L
        _metrics.value = _metrics.value.copy(t2 = timestamp, sttLatency = sttLatency)
    }

    fun recordT3(timestamp: Long) {
        val t2 = _metrics.value.t2
        val llmLatency = if (t2 > 0) timestamp - t2 else 0L
        _metrics.value = _metrics.value.copy(t3 = timestamp, llmLatency = llmLatency)
    }

    fun recordT4(timestamp: Long) {
        val t3 = _metrics.value.t3
        val ttsLatency = if (t3 > 0) timestamp - t3 else 0L
        val total = if (_metrics.value.t1 > 0) timestamp - _metrics.value.t1 else 0L
        _metrics.value = _metrics.value.copy(t4 = timestamp, ttsLatency = ttsLatency, totalLatency = total)
    }

    fun reset() {
        _metrics.value = LatencyMetrics()
    }
}
