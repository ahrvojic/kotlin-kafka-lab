package com.anthfu.kafka.springconsumer

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.runApplication
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@ConstructorBinding
@ConfigurationProperties("app")
data class Config(
    val topic: String
)

@Component
class SpringConsumer(val config: Config) {
    val logger: Logger = LoggerFactory.getLogger(SpringConsumer::class.java)

    @KafkaListener(topics = ["#{springConsumer.config.topic}"])
    fun receive(message: String) {
        logger.info("Received: $message")
    }
}

@SpringBootApplication
@ConfigurationPropertiesScan
class SpringConsumerApp

fun main(args: Array<String>) {
    runApplication<SpringConsumerApp>(*args)
}
