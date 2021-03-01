package com.anthfu.kafka.basicconsumer

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class BasicConsumer() {
    @KafkaListener(topics = ["topic"], groupId = "group-id")
    fun consume(message: String) {
        // TODO
    }
}
