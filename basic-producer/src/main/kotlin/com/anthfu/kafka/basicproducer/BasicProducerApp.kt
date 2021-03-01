package com.anthfu.kafka.basicproducer

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BasicProducerApp(private val producer: BasicProducer) : CommandLineRunner {
    override fun run(vararg args: String) {
        producer.send("", "")
    }
}

fun main(args: Array<String>) {
    runApplication<BasicProducerApp>(*args)
}
