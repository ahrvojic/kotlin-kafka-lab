package com.anthfu.kafka.springconsumer

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class SpringConsumer(val props: SpringConsumerProps) {
    var logger: Logger = LoggerFactory.getLogger(SpringConsumer::class.java)

    @KafkaListener(topics = ["#{springConsumer.props.topic}'}"])
    fun receive(message: String) {
        logger.info("Received: $message")
    }
}
