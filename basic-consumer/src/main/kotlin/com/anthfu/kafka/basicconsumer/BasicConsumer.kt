package com.anthfu.kafka.basicconsumer

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class BasicConsumer(val props: BasicConsumerProps) {
    var logger: Logger = LoggerFactory.getLogger(BasicConsumer::class.java)

    @KafkaListener(topics = ["#{basicConsumer.props.topic}'}"])
    fun receive(message: String) {
        logger.info("Received: $message")
    }
}
