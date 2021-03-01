package com.anthfu.kafka.basicproducer

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class BasicProducer(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val props: BasicProducerProps
) {
    var logger: Logger = LoggerFactory.getLogger(BasicProducer::class.java)

    fun send(message: String) {
        logger.info("Sent: $message")
        kafkaTemplate.send(props.topic, message)
    }
}
