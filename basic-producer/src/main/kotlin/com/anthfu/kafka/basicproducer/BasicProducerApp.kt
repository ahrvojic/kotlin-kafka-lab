package com.anthfu.kafka.basicproducer

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class BasicProducerApp(private val producer: BasicProducer) : CommandLineRunner {
    override fun run(vararg args: String) {
        repeat(10) {
            producer.send("basic-stream", it.toString())
        }
    }
}

fun main(args: Array<String>) {
    runApplication<BasicProducerApp>(*args)
}
