package com.anthfu.kafka.basicconsumer

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class BasicConsumer() {
    @KafkaListener(topics = ["basic-stream"])
    fun consume(message: String) {
        // TODO
    }
}
