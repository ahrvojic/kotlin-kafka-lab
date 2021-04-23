package com.anthfu.kafka.spring.producer

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class SpringProducer(private val kafkaTemplate: KafkaTemplate<String, String>) {
    val logger: Logger = LoggerFactory.getLogger(javaClass)

    fun send(message: String) {
        logger.info("Sent: $message")
        kafkaTemplate.sendDefault(message)
    }
}
