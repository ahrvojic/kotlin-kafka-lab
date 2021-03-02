package com.anthfu.kafka.springproducer

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class SpringProducer(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val props: SpringProducerProps
) {
    val logger: Logger = LoggerFactory.getLogger(SpringProducer::class.java)

    fun send(message: String) {
        logger.info("Sent: $message")
        kafkaTemplate.send(props.topic, message)
    }
}
