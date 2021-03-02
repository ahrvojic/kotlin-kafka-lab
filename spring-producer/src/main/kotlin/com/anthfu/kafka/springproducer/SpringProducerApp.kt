package com.anthfu.kafka.springproducer

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.runApplication

@ConstructorBinding
@ConfigurationProperties("spring-producer")
data class SpringProducerProps(
    val topic: String
)

@SpringBootApplication
@ConfigurationPropertiesScan
class SpringProducerApp(private val producer: SpringProducer) : CommandLineRunner {
    override fun run(vararg args: String) {
        repeat(10) {
            producer.send(it.toString())
        }
    }
}

fun main(args: Array<String>) {
    runApplication<SpringProducerApp>(*args)
}
