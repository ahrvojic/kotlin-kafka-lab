package com.anthfu.kafka.basicproducer

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.runApplication

@ConstructorBinding
@ConfigurationProperties("basic-producer")
data class BasicProducerProps(
    val topic: String
)

@SpringBootApplication
@ConfigurationPropertiesScan
class BasicProducerApp(private val producer: BasicProducer) : CommandLineRunner {
    override fun run(vararg args: String) {
        repeat(10) {
            producer.send(it.toString())
        }
    }
}

fun main(args: Array<String>) {
    runApplication<BasicProducerApp>(*args)
}
