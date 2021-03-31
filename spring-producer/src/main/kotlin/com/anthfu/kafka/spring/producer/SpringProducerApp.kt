package com.anthfu.kafka.spring.producer

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.runApplication
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
    val logger: Logger = LoggerFactory.getLogger(SpringProducer::class.java)

    fun send(message: String) {
        logger.info("Sent: $message")
        kafkaTemplate.send(config.topic, message)
    }
}

@SpringBootApplication
@ConfigurationPropertiesScan
class SpringProducerApp(private val producer: SpringProducer) : CommandLineRunner {
    override fun run(vararg args: String) {
        repeat(1000) {
            producer.send(it.toString())
        }
    }
}

fun main(args: Array<String>) {
    runApplication<SpringProducerApp>(*args)
}
