package com.anthfu.kafka.spring.producer

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@ConstructorBinding
@ConfigurationProperties("app")
data class Config(
    val topic: String
)

@Component
class SpringProducer(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val config: Config
) {
    val logger: Logger = LoggerFactory.getLogger(javaClass)

    fun send(message: String) {
        logger.info("Sent: $message")
        kafkaTemplate.send(config.topic, message)
    }
}
