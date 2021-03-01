package com.anthfu.kafka.basicproducer

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class BasicProducer(private val kafkaTemplate: KafkaTemplate<String, String>) {
    fun send(topic: String, message: String) {
        kafkaTemplate.send(topic, message)
    }
}
